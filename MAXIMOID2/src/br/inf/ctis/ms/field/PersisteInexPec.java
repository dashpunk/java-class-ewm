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

public class PersisteInexPec extends MAXTableDomain {

	public PersisteInexPec(MboValue mbv) {
		super(mbv);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro da Classe PersisteRegistrosPec versao 01 ");
				
	}
	
	public void validate() throws MXException, RemoteException {
        
		if (getMboValue().isNull()) {
            System.out.println(">>>>>>>>>>>>>>>>>>>> is null");
            return;
        }
		
		//valicao da mascara
		String valor = new String();
	    valor = Uteis.getApenasNumeros(getMboValue().getString());
	    System.out.println("###############getMboValue().getString() = " + getMboValue().getString());
	    System.out.println("###############valor = " + valor);
	    
	    if ((valor.length() < 6) || (valor.length() > 7)) {
	    	throw new MXApplicationException("generica", "MascaraInvalida");
	    }

	    if (valor.length() == 6) {
	    	valor = "0" + valor;
	    }
	    
	    Calendar cAtual = Calendar.getInstance();        

        if (Integer.valueOf(cAtual.get(Calendar.YEAR)) >= Integer.valueOf(valor.substring(3, 7)).intValue()) {
        	getMboValue().setValue(Uteis.getValorMascarado("###/####", valor, false));
        }
	    else {
	      throw new MXApplicationException("generica", "AnoInvalido");
	    }		
		
		

		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do validate");
		
        MboSet mboSetInexOriginal = (MboSet) getMboValue().getMbo().getMboSet("MSTBINEXIGIBILIDADE");

        
        boolean encontrado = false;        
        
        System.out.println(">>>>>>>>>>>>>>>>>>>> Quantidade de registros na MSTBINEXIGIBILIDADE: "+mboSetInexOriginal.count());
        
        for (int i = 0; i < mboSetInexOriginal.count(); i++) {
           
            encontrado = !mboSetInexOriginal.getMbo(i).isNull("MSNUNUMINEXIGIBILIDADE");
            
                                 
	            if (encontrado) {
	                
	            	System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do for o valor de encontrado e:"+encontrado);
	            	
	                getMboValue().getMbo().setValue("MSNUNUMINEXIGIBILIDADE", mboSetInexOriginal.getMbo(i).getString("MSNUNUMINEXIGIBILIDADE"));
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
	                    throw new MXApplicationYesNoCancelException(yesNoId, "Persistecase", "novoRegistro", params);
	
	                case MXApplicationYesNoCancelException.YES:
	                   System.out.println(">>>>>>>>>>>>>>>>>>>>Criando Inexigibilidade");
	                   
	                                              
	                  
	                   MboRemote InexAdd = getMboValue().getMbo().getMboSet("MSTBINEXIGIBILIDADE").add();
	                   
	                   InexAdd.setValue("MSNUNUMINEXIGIBILIDADE", getMboValue().getMbo().getString("MSNUNUMINEXIGIBILIDADE"));
	                   InexAdd.setValue("PONUM", getMboValue().getMbo().getString("PONUM"));
	                   InexAdd.setValue("MS_SIPARNUM", getMboValue().getMbo().getString("MS_SIPARNUM"));                
	                    
	                   
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


