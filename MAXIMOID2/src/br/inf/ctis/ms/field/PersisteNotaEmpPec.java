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

public class PersisteNotaEmpPec extends MAXTableDomain {

	public PersisteNotaEmpPec(MboValue mbv) {
		super(mbv);
		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro da Classe PersisteNotaEmpPec versao 01 ");
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
	    
	    if ((valor.length() < 10) || (valor.length() > 10)) {
	    	throw new MXApplicationException("notaempenho", "MascaraInvalida");
	    }
	    
	    System.out.println("###############getMboValue().getString().substring(4, 6) = " + getMboValue().getString().substring(4, 6));
	    if (!getMboValue().getString().substring(4, 6).equalsIgnoreCase("NE")) {
	    	throw new MXApplicationException("notaempenho", "MascaraInvalida");
	    }
	    
	    Calendar cAtual = Calendar.getInstance();        
	    
	    System.out.println("###############valor.substring(0, 4)" + valor.substring(0, 4));
        if (/*Integer.valueOf(cAtual.get(Calendar.YEAR)) <= Integer.valueOf(valor.substring(0, 4)).intValue()*/ !getMboValue().isNull()) {
        	getMboValue().setValue(Uteis.getValorMascarado("####NE######", valor, false));
        }
	    else {
	      throw new MXApplicationException("notaempenho", "AnoInvalido");
	    }
		
        
        
		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do validate");
		
      
        MboSet mboSetEmpOriginal = (MboSet) getMboValue().getMbo().getMboSet("MSTBNOTAEMPENHO");

        
        boolean encontrado = false;        
        
        System.out.println(">>>>>>>>>>>>>>>>>>>> Quantidade de registros na MSTBNOTAEMPENHO: "+mboSetEmpOriginal.count());
        
        for (int i = 0; i < mboSetEmpOriginal.count(); i++) {
           
            encontrado = !mboSetEmpOriginal.getMbo(i).isNull("MSALNUMNOTAEMPENHO");
            
                                 
	            if (encontrado) {
	                
	            	System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do for o valor de encontrado e:"+encontrado);
	            	
	                getMboValue().getMbo().setValue("MSALNUMNOTAEMPENHO", mboSetEmpOriginal.getMbo(i).getString("MSALNUMNOTAEMPENHO"));
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
	                    throw new MXApplicationYesNoCancelException(yesNoId, "PersisteEmpcase", "novoRegistroEmp", params);
	
	                case MXApplicationYesNoCancelException.YES:
	                   System.out.println(">>>>>>>>>>>>>>>>>>>>Criando Inexigibilidade");
	                   
	                                              
	                  
	                   MboRemote EmpAdd = getMboValue().getMbo().getMboSet("MSTBNOTAEMPENHO").add();
	                   
	                   EmpAdd.setValue("MSALNUMNOTAEMPENHO", getMboValue().getMbo().getString("MSALNUMNOTAEMPENHO"));
	                   EmpAdd.setValue("MSALCODMODALIDADE", getMboValue().getMbo().getString("MSALCODMODALIDADE"));
	                   EmpAdd.setValue("MSALNUMMODALIDADE", getMboValue().getMbo().getString("MSALNUMMODALIDADE"));                
	                    
	                   
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


