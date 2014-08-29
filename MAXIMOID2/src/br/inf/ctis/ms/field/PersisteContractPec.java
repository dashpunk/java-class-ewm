package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;
import br.inf.id2.common.util.Validar;

public class PersisteContractPec extends MAXTableDomain {

	public PersisteContractPec(MboValue mbv) {
		super(mbv);
		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro da Classe PersisteContractPec versao 10 ");
	}
	
	public void validate() throws MXException, RemoteException {
		
		if (getMboValue().isNull()) {
            System.out.println(">>>>>>>>>>>>>>>>>>>> is null");
            return;
        }
		
		//validacao da mascara
		String valor = new String();
	    valor = Uteis.getApenasNumeros(getMboValue().getString());
	    System.out.println("###############getMboValue().getString() = " + getMboValue().getString());
	    System.out.println("###############valor = " + valor);
	    
	    if ((valor.length() < 6) || (valor.length() > 7)) {
	    	throw new MXApplicationException("genericaContract", "MascaraInvalida");
	    }

	    if (valor.length() == 6) {
	    	valor = "0" + valor;
	    }
	    
	    Calendar cAtual = Calendar.getInstance();        

        if (Integer.valueOf(cAtual.get(Calendar.YEAR)) >= Integer.valueOf(valor.substring(3, 7)).intValue()) {
        	getMboValue().setValue(Uteis.getValorMascarado("###/####", valor, false));
        }
	    else {
	      throw new MXApplicationException("genericaContract", "AnoInvalido");
	    }
		
        System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do validate");
		
        
        MboSet mboSetContractOriginal = (MboSet) getMboValue().getMbo().getMboSet("PURCHVIEW");

        
        boolean encontrado = false;        
        
        System.out.println(">>>>>>>>>>>>>>>>>>>> Quantidade de registros na PURCHVIEW: "+mboSetContractOriginal.count());
        
        for (int i = 0; i < mboSetContractOriginal.count(); i++) {
           
            encontrado = !mboSetContractOriginal.getMbo(i).isNull("PURCHVIEW");
            
                                 
	            if (encontrado) {
	                
	            	System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do for o valor de encontrado e:"+encontrado);
	            	
	                getMboValue().getMbo().setValue("CONTRACTNUM", mboSetContractOriginal.getMbo(i).getString("PURCHVIEW"));
	                break;                
	            }	
        }
        
        if (!encontrado) {
        
            	System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do IF de valor encontrado=false");

            	          	
	        	String yesNoId = getClass().getName();
	            int userInput = MXApplicationYesNoCancelException.getUserInput(yesNoId, MXServer.getMXServer(), getMboValue().getMbo().getUserInfo());
	            
	            System.out.println("retorno = " + userInput);
	            System.out.println("UserInput = " + getMboValue().getMbo().getUserInfo());
	            switch (userInput) {
	                case MXApplicationYesNoCancelException.NULL:
	                    System.out.println(">>>>>>>>>>>>>>>>>>>> userImpot null");
	                    Object params[] = {
	                        getMboValue().getString()
	                    };
	                    throw new MXApplicationYesNoCancelException(yesNoId, "PersisteContractcase", "novoRegistroContract", params);
	
	                case MXApplicationYesNoCancelException.YES:
	                   System.out.println(">>>>>>>>>>>>>>>>>>>>Criando Inexigibilidade");
	                   
	                                              
	                  
	                   MboRemote ContractAdd = getMboValue().getMbo().getMboSet("PURCHVIEW").add();
	                   
	                   ContractAdd.setValue("CONTRACTNUM", getMboValue().getMbo().getString("CONTRACTNUM"));
	                   ContractAdd.setValue("MSALNUMNOTAEMPENHO", getMboValue().getMbo().getString("MSALNUMNOTAEMPENHO"));
	                                
	                   					
	                   
	                    break;
	                case MXApplicationYesNoCancelException.NO: // '\020'
	                    System.out.println(">>>>>>>>>>>>>>>>>>>> userImpot NO");
	                    //throw new MXApplicationException("system", "null");
	                    getMboValue().setValueNull();
	                default:
	                    System.out.println(">>>>>>>>>>>>>>>>>>>> userImpot DEFAULT");
	                    break;
	            }
	
	
	        }

		}

    }
