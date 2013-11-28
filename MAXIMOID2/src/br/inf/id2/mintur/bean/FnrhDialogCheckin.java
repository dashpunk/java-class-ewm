package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.util.MXApplicationException;

import psdi.util.MXException;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class FnrhDialogCheckin extends psdi.webclient.system.beans.DataBean {

    public FnrhDialogCheckin() {
    }

    @Override
    public synchronized int execute() throws MXException, RemoteException {

        System.out.println("---FnrhDialogCheckin status atual "+app.getDataBean().getMbo().getString("SNSTATUS"));

        if (app.getDataBean().getMbo().isNull("SNENT")) {
            String [] param = {"Entrada - Checkin"} ;
            throw new MXApplicationException("system", "null", param);
        }
        
        if (!app.getDataBean().getMbo().getString("SNSTATUS").equals("AG.CHECKIN")) {
            throw new MXApplicationException("fnrh", "checkinComCheckin");
        }

        int retorno = super.execute();

        System.out.println("---FnrhDialogCheckin passou, retorno = "+retorno);

        app.getDataBean().getMboSet().getMbo().setValue("SNSTATUS", "CHECKIN", MboConstants.NOACCESSCHECK);

        app.getDataBean().save();
        WebClientEvent event = sessionContext.getCurrentEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));

        sessionContext.queueRefreshEvent();
        Utility.showMessageBox(event, new MXApplicationException("fnrh", "redirecionamentoCentroInicioCheckin"));

        WebClientEvent eventType = new WebClientEvent("loadapp", null, "startcntr", sessionContext);
        WebClientEvent eventExec = new WebClientEvent("execevent", sessionContext.getCurrentAppId(), eventType, sessionContext);

        sessionContext.setCurrentEvent(eventExec);

        Utility.sendEvent(eventExec);

        return retorno;
    }
}
