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
		
		setRelationship("BGTBDOCP01", "bgstnumnumdoc = :bgstnumnumdoc and bgstnumnumdoc not in (select bgstnumnumdoc from rhtbcase01)");
        setErrorMessage("person", "InvalidPerson");
        setListCriteria((new StringBuilder()).append("personid not in (select personid from rhtbcase01) and bgstcodtipdoc = :bgstcodtipdoc").toString());
    
	}
	
	public void validate() throws MXException, RemoteException {
        
		if (getMboValue().isNull()) {
            System.out.println(">>>>>>>>>>>>>>>>>>>> is null");
            return;
        }

       /*
        //a pedido do Felipe Marinho dia 27/10/2011
        String tipoDocumento = getMboValue("BGSTCODTIPDOC").getString();
        String nrDocumento = getMboValue("BGSTNUMNUMDOC").getString();
        System.out.println("... BGSTCODTIPDOC "+tipoDocumento);
        System.out.println("... BGSTNUMNUMDOC "+nrDocumento);
        String numDocumento = "";
        if (tipoDocumento.equalsIgnoreCase("CPF")) {

           if (!Validar.CPF(Uteis.getApenasNumeros(nrDocumento))) {
               throw new MXApplicationException("id2message", "CpfInvalido");
           }

           numDocumento = Uteis.getValorMascarado("999.999.999-99", Uteis.getApenasNumeros(nrDocumento), true);

           getMboValue().setValue(nrDocumento, MboConstants.NOVALIDATION_AND_NOACTION);
        } else{
        	 nrDocumento =  Uteis.getApenasNumeros(nrDocumento);
        	 getMboValue().setValue(nrDocumento, MboConstants.NOVALIDATION_AND_NOACTION);
        }

       
        //FIM das alterações requisitadas pelo Felipe Marinho dia 27/10/2011

        MboRemote mbo = getMboValue().getMbo();


        MboSet mboSetDocPes = (MboSet) getMboValue().getMbo().getMboSet("RHRLDOCP02");

        MboRemote gMbo;
        boolean encontrado = false;

        for (int i = 0; ((gMbo = mboSetDocPes.getMbo(i)) != null); i++) {
            System.out.println("------------- i " + i + " valorDoc " + gMbo.getString("BGSTNUMNUMDOC"));
            encontrado = gMbo.getString("BGSTNUMNUMDOC").equalsIgnoreCase(numDocumento);
          
            if (encontrado) {
                System.out.println("----------encontrou");
                getMboValue().getMbo().setValue("PERSONID", gMbo.getString("PERSONID"));
                getMboValue().getMbo().setValue("RHNUFLGSALVO", true);
                
                break;
            }
        */

        MboRemote mbo = getMboValue().getMbo();
     

        MboRemote gMbo;
        boolean encontrado = false;
        
        System.out.println(">>>>>>>>>>>>>>>>>>>> antes da validacao de encontrado");

        if (!encontrado) {
            
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
                    System.out.println(">>>>>>>>>>>>>>>>>>>> pessoas");
                    MboSet pessoas;
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

                    
                    System.out.println("------------------ bb  antes " + ++aonde);
                    pessoa.setFieldFlag("STATUS", MboConstants.READONLY, false);
                    pessoa.setFieldFlag("STATUSDATE", MboConstants.READONLY, false);
                    pessoa.setFieldFlag("STATUSIFACE", MboConstants.READONLY, false);
                    pessoa.setFieldFlag("TRANSEMAILELECTION", MboConstants.READONLY, false);
                   
                    System.out.println("------------------ bb  antes " + ++aonde);

                    System.out.println("----------- pessoas save");
                    pessoas.save();
                    System.out.println("----------- pessoas save a");                    

                   
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
