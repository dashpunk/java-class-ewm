package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Dyogo Dantas
 *
 */
public class VagaTableBean extends AppBean {

    /**
     *
     */
    public VagaTableBean() {
    }

    
    public int EXONERARSERVSUB() throws RemoteException, MXException {
        
    	//RHSTCODSERV
    	
    	String yesNoId = getClass().getName();
    	int userInput = MXApplicationYesNoCancelException.getUserInput(yesNoId, MXServer.getMXServer(), getMbo().getUserInfo());
    	System.out.println("############## Userinput = " + userInput);
    	switch (userInput) {
    	
	    	case MXApplicationYesNoCancelException.NULL:
	            System.out.println("---------------- userImpot null");
	            throw new MXApplicationYesNoCancelException(yesNoId, "uo", "LimparServidorSub");

	        case MXApplicationYesNoCancelException.NO:
	            System.out.println("############# NADA");
	            break;
	            
	        case MXApplicationYesNoCancelException.YES:
	            System.out.println("################# EXONERAR SERVIDOR SUB");
	            System.out.println("#### RHST = " + getMbo().getString("RHSTCODSERVSUB"));
	            getMbo().setValueNull("RHSTCODSERVSUB");
	            System.out.println("#### RHST = " + getMbo().getString("RHSTCODSERV"));
	            getMbo().getThisMboSet().save();
	            refreshTable();
	            reloadTable();
    	}
    	return 1;
    }
    
    public void EXONERARSERV() throws RemoteException, MXException {
        
        System.out.println("################# EXONERAR SERVIDOR");
        System.out.println("#### RHSTCODSERV = " + getMbo().getString("RHSTCODSERV"));
        String rhCodServ = getMbo().getString("RHSTCODSERV");
        MboSet mboSet;
        mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("RHTBHULE01", sessionContext.getUserInfo());
        mboSet.setWhere("PERSONID = \'" + rhCodServ + "\' AND RHSTCODTIPO = \'LOT\' AND RHSTDTASAIDA is NULL");
        mboSet.reset();
        System.out.println("############ Aplicado setWhere = " + mboSet.count());

        if (mboSet.count() > 0) {
        	System.out.println("*** tchelo1 "+getMbo().getMboSet("RHRLCASE01"));
        	getMbo().getMboSet("RHRLCASE01").getMbo(0).setValueNull("RHNUCODUOLOTID", MboConstants.NOVALIDATION_AND_NOACTION);

        	System.out.println("*** RHSTCODSERV "+getMbo().getString("RHSTCODSERV"));
            getMbo().setValueNull("RHSTCODSERV");
            System.out.println("*** RHSTDTAPOSSE "+getMbo().getDate("RHSTDTAPOSSE"));
            getMbo().setValueNull("RHSTDTAPOSSE");
            System.out.println("*** RHSTDTAENTRAD "+getMbo().getDate("RHSTDTAENTRAD"));
            getMbo().setValueNull("RHSTDTAENTRAD");
            System.out.println("*** RHSTDSCATONOR "+getMbo().getString("RHSTDSCATONOR"));
            getMbo().setValueNull("RHSTDSCATONOR");
            
            System.out.println("#### RHSTCODSERV = " + getMbo().getString("RHSTCODSERV"));
        	System.out.println("########### Encontrou o registro");
            mboSet.getMbo(0).setValue("RHSTDTASAIDA", getMbo().getDate("RHSTDTASAIDA"));
            mboSet.getMbo(0).setValue("RHSTDSCATONOREXO", getMbo().getString("RHSTDSCATONOREXO"));
        }
        System.out.println("--- muda OLD save1");
        mboSet.save();
        getMbo().getThisMboSet().save();
        refreshTable();
        reloadTable();
        
    	WebClientEvent event = sessionContext.getCurrentEvent();
    	Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
    }
}
