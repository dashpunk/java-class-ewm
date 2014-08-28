package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.ArrayList;

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

public class PersiteInexPec extends MAXTableDomain {

	public PersiteInexPec(MboValue mbv) {
		super(mbv);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro da Classe PersiteRegistrosPec versao 01 ");
		
		//setRelationship("BGTBDOCP01", "bgstnumnumdoc = :bgstnumnumdoc and bgstnumnumdoc not in (select bgstnumnumdoc from rhtbcase01)");
        //setErrorMessage("person", "InvalidPerson");
        //setListCriteria((new StringBuilder()).append("personid not in (select personid from rhtbcase01) and bgstcodtipdoc = :bgstcodtipdoc").toString());
    
	}
	
	public void validate() throws MXException, RemoteException {
        
		if (getMboValue().isNull()) {
            System.out.println(">>>>>>>>>>>>>>>>>>>> is null");
            return;
        }

		 System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do validate");
		
        MboRemote mbo = getMboValue().getMbo();


        MboSet mboSetInexOriginal = (MboSet) getMboValue().getMbo().getMboSet("MSTBINEXIGIBILIDADE");

        
        boolean encontrado = false;        
        
        System.out.println(">>>>>>>>>>>>>>>>>>>> Quantidade de registros na MSTBINEXIGIBILIDADE: "+mboSetInexOriginal.count());
        System.out.println(">>>>>>>>>>>>>>>>>>>> antes do for o valor de encontrado e:"+encontrado);
        for (int i = 0; i < mboSetInexOriginal.count(); i++) {
           
            encontrado = !mboSetInexOriginal.getMbo(i).isNull("MSNUNUMINEXIGIBILIDADE");
            
            System.out.println(">>>>>>>>>>>>>>>>>>>> nao encontrou nada");
                      
	            if (encontrado) {
	                
	            	System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do for o valor de encontrado e:"+encontrado);
	            	System.out.println(">>>>>>>>>>>>>>>>>>>> encontrou");
	                getMboValue().getMbo().setValue("MSNUNUMINEXIGIBILIDADE", mboSetInexOriginal.getMbo(i).getString("MSNUNUMINEXIGIBILIDADE"));
	                break;                
	            }	
        }
        
        if (!encontrado) {
        
            	System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do IF de valor encontrado=false");

	        	String yesNoId = getClass().getName();
	            int userInput = MXApplicationYesNoCancelException.getUserInput(yesNoId, MXServer.getMXServer(), getMboValue().getMbo().getUserInfo());
	            
	            System.out.println("retorno = " + userInput);
	            switch (userInput) {
	                case MXApplicationYesNoCancelException.NULL:
	                    System.out.println(">>>>>>>>>>>>>>>>>>>> userImpot null");
	                    Object params[] = {
	                        getMboValue().getString()
	                    };
	                    throw new MXApplicationYesNoCancelException(yesNoId, "Persistecase", "novoRegistro", params);
	
	                case MXApplicationYesNoCancelException.YES:
	                    System.out.println(">>>>>>>>>>>>>>>>>>>> userImpot YES");
	                    System.out.println(">>>>>>>>>>>>>>>>>>>> Inexigibilidade");
	                   /* MboSet pessoas;
	                    pessoas = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("PERSON", mbo.getUserInfo());
	                    pessoas.setWhere("personid = '>>>>>>>>>>>>>>>>>>>>'");
	                    pessoas.reset();                   
	
	
	                    System.out.println("----------- pessoa");
	                    MboRemote pessoa = pessoas.add();
	                    String personId = pessoa.getString("PERSONID");
	                    System.out.println("---------- personId " + personId);
	                    System.out.println("---------- " + pessoa.getString("HASLD"));
	                    System.out.println("---------- " + pessoa.getString("LANGCODE"));
	                    System.out.println("---------- " + pessoa.getString("PERSONID"));
	                    System.out.println("---------- " + pessoa.getString("PERSONUID"));
	
	                    
	                    System.out.println("------------------ bb  antes " );
	                    pessoa.setFieldFlag("STATUS", MboConstants.READONLY, false);
	                    pessoa.setFieldFlag("STATUSDATE", MboConstants.READONLY, false);
	                    pessoa.setFieldFlag("STATUSIFACE", MboConstants.READONLY, false);
	                    pessoa.setFieldFlag("TRANSEMAILELECTION", MboConstants.READONLY, false);
	                   
	                    System.out.println("------------------ bb  antes " );
	
	                    System.out.println("----------- pessoas save");
	                    pessoas.save();
	                    System.out.println("----------- pessoas save a");                    
						*/
	                   
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


