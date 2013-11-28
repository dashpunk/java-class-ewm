package br.inf.id2.mintur.bean;

import br.inf.id2.common.util.Data;
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
public class FnrhDialogCheckout extends psdi.webclient.system.beans.DataBean {

    public FnrhDialogCheckout() {
        System.out.println("--- FnrhDialogCheckout ");
    }

    @Override
    public synchronized int execute() throws MXException, RemoteException {

        if (app.getDataBean().getMbo().isNull("SNSAI")) {
            String[] param = {"Saída - Checkout"};
            throw new MXApplicationException("system", "null", param);
        }
        
        if (!app.getDataBean().getMboSet().getMbo().getString("SNSTATUS").equals("CHECKIN")) {
            throw new MXApplicationException("fnrh", "checkoutSemCheckin");
        }

        System.out.println("*** ANTES dataHoraInicialMenorFinal B");
        if (Data.dataHoraInicialMenorFinal(app.getDataBean().getMboSet().getMbo().getDate("SNSAI"), app.getDataBean().getMboSet().getMbo().getDate("SNENT"))) {
            throw new MXApplicationException("fnrh", "checkoutMenorCheckin");
        }

        int retorno = super.execute();


        app.getDataBean().getMboSet().getMbo().setValue("SNSTATUS", "CHECKOUT", MboConstants.NOACCESSCHECK);
        app.getDataBean().getMboSet().save();

        WebClientEvent event = sessionContext.getCurrentEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));

        sessionContext.queueRefreshEvent();
        Utility.showMessageBox(event, new MXApplicationException("fnrh", "redirecionamentoCentroInicioCheckout"));

        WebClientEvent eventType = new WebClientEvent("loadapp", null, "startcntr", sessionContext);
        WebClientEvent eventExec = new WebClientEvent("execevent", sessionContext.getCurrentAppId(), eventType, sessionContext);

        sessionContext.setCurrentEvent(eventExec);

        Utility.sendEvent(eventExec);

        return retorno;
    }
}
