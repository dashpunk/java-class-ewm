package br.inf.id2.ms.field;

import psdi.id2.mapa.*;
import psdi.mbo.*;

import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldPRStatus extends MboValueAdapter {

    /**
     * Método construtor de ID2FldEmissaoTipoLocal
     * @param mbv 
     * @throws MXException
     * 
     */
    public ID2FldPRStatus(MboValue mbv) throws MXException {
        super(mbv);
    }

    /**
     *  Sobrescrita do método <b>validate</b>
     * <p>
     *
     * Quando o "PR.STATUS" (status) mudar para "ENVIADO" e o atributo "PR.ID2DATAPLANO" (data do plano) estiver null executar update em "PR.ID2DATAPLANO" com o valor de "PR.STATUSDATE"
     * e
     * Alterar "PRLINE.ID2STATUS" para "ENVIADO" do relacionamento "PRLINE"
     * 
     * @throws MXException
     * @throws java.rmi.RemoteException
     */
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {

        super.validate();

        if ((getMboValue().getString().equals("ENVIADO")) && (getMboValue().getMbo().getMboValue("ID2DATAPLANO").isNull())) {

            getMboValue().getMbo().getMboValue("ID2DATAPLANO").setValue(getMboValue().getMbo().getDate("STATUSDATE"), MboConstants.NOACCESSCHECK);

            Executa.atualizaAtributo((MboSet) getMboValue().getMbo().getMboSet("PRLINE"), "ID2STATUS", "ENVIADO");
        }

        MboRemote mbo;

        if (getMboValue().getString().equals("ENVIADO")) {
            for (int i = 0; ((mbo = getMboValue().getMbo().getMboSet("PRLINE").getMbo(i)) != null); i++) {
                if (mbo.getString("ID2STATUS").equalsIgnoreCase("RASCUNHO")) {
                    mbo.setValue("ID2STATUS", "ENVIADO", MboConstants.NOVALIDATION_AND_NOACTION);
                }

            }
        }

    }
    
    
    @Override
    public void init() throws MXException, RemoteException {
    	//System.out.println("################### INIT");
	    if (getMboValue().getMbo().getString("STATUS") != null && !getMboValue().getMbo().getString("STATUS").equals("EM ALTERAÇÃO")) {
			
	    	try {
	    		getMboValue().getMbo().getMboSet("MS_RL01LOCDIS").setFlag(MboConstants.READONLY, true);
	    	} catch (Exception e) {
	    		//Caso n�o exista
	    	}
	    	try {
	    		getMboValue().getMbo().getMboSet("MS_RL01LOCDIS").getMbo().getMboSet("MS_RL01ITEDIS").setFlag(MboConstants.READONLY, true);
	    	} catch (Exception e) {
	    		//Caso n�o exista
	    	}
	    	try {
	    		getMboValue().getMbo().getMboSet("MS_RL01LOCREC").setFlag(MboConstants.READONLY, true);
	    	} catch (Exception e) {
	    		//Caso n�o exista
	    	}
	    	try {
	    		getMboValue().getMbo().getMboSet("MS_RL01LOCREC").getMbo().getMboSet("MS_RL01ITEREC").setFlag(MboConstants.READONLY, true);
	    	} catch (Exception e) {
	    		//Caso n�o exista
	    	}
	    	//System.out.println("############ PASSOU!");
	    }
    	super.init();
    }

    
}
