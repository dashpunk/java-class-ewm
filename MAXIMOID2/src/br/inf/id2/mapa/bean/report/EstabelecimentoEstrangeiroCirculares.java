package br.inf.id2.mapa.bean.report;

import java.rmi.RemoteException;
import java.util.Hashtable;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

import psdi.webclient.system.beans.WebClientBean;

/**
 *
 * @author Wilians Andrade
 */
public class EstabelecimentoEstrangeiroCirculares extends DataBean {

    public EstabelecimentoEstrangeiroCirculares() {
    }

    @Override
    public synchronized int execute() throws MXException, RemoteException {
        try {
            Hashtable reportParams = new Hashtable();

            MboSet mboa = (MboSet) app.getDataBean("MAINRECORD").getMbo().getMboSet("MATBCIRCULAR");


            //throw new MXApplicationException("estEstrangeiro", "salvarAntesEmitir");


            // if (mboa.getMbo().getBoolean("ID2CHECKCIRC")) {

            int registroCorrente = app.getDataBean("MAINRECORD").getMbo().getMboSet("MATBCIRCULAR").getCurrentPosition();

            reportParams.put("MATBESTESTID", mboa.getMbo(registroCorrente).getLong("MATBESTESTID"));
            reportParams.put("ID2REV", mboa.getMbo(registroCorrente).getLong("ID2REV"));
            reportParams.put("MATBCIRCULARID", mboa.getMbo(registroCorrente).getLong("MATBCIRCULARID"));

            WebClientEvent event = sessionContext.getCurrentEvent();
            reportParams.put("relatorio", (String) event.getValue());
            app.put("relatorio", reportParams);

            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
            //}
        } catch (MXException mx) {
            mx.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
        return super.execute();
    }
}