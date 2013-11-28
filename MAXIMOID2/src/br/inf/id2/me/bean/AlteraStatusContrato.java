package br.inf.id2.me.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Patrick
 */
public class AlteraStatusContrato extends psdi.webclient.beans.contpurch.ReviseContractBean {

    public AlteraStatusContrato() {
    }

    @Override
    protected void initialize() throws MXException, RemoteException {
        System.out.println(">>>>>>> status = " + getMbo().getString("STATUS"));
        if (getMbo().getString("STATUS").equalsIgnoreCase("VIGENTE")) {
            getMbo().setValue("STATUS", "EM ALTERAÇÃO", MboConstants.NOACCESSCHECK);
        }
        System.out.println(">>>>>>> status = " + getMbo().getString("STATUS"));
        super.initialize();
    }

    public synchronized void btnOk() throws MXException {
        System.out.println("*** btnOk ***");
        try {
            System.out.println("*** STATUS antes if - " + app.getDataBean("MAINRECORD").getMbo().getString("STATUS"));
            //se o status for VIGENTE ele altera para EM ALTERAÇÃO
            if (app.getDataBean("MAINRECORD").getMbo().getString("STATUS").equals("VIGENTE")) {

                app.getDataBean("MAINRECORD").getMboSet().getMbo().setFieldFlag("STATUS", MboConstants.READONLY, false);
                app.getDataBean("MAINRECORD").getMboSet().getMbo().setValue("STATUS", "EM ALTERAÇÃO", MboConstants.NOVALIDATION_AND_NOACTION);
                app.getDataBean("MAINRECORD").getMboSet().getMbo().setFieldFlag("STATUS", MboConstants.READONLY, true);

                System.out.println("*** STATUS alterado if - " + app.getDataBean("MAINRECORD").getMbo().getString("STATUS"));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Utility.sendEvent(new WebClientEvent("dialogok", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));
    }
}
