/**
 * @author Dyogo
 */
package br.inf.id2.me.bean.report;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class EtiquetaProtocoloPrintBean extends DataBean {

    public EtiquetaProtocoloPrintBean() {
    }

    public void Imprimir() {
        try {
            Hashtable reportParams = new Hashtable();
            reportParams.put("ASSETNUM", (String) app.getDataBean("MAINRECORD").getMbo().getString("ASSETNUM"));
            reportParams.put("ASSUNTO", (String) getMbo().getString("ASSUNTO"));
            reportParams.put("VOLUME", (String) getMbo().getString("VOLUME"));
            reportParams.put("DADOS", (String) getMbo().getString("DADOS"));
            reportParams.put("ID2DATA", (Date) getMbo().getDate("DATA"));
            reportParams.put("DISPLAYNAME", (String) getMbo().getUserInfo().getUserName());
            WebClientEvent event = sessionContext.getCurrentEvent();
            reportParams.put("relatorio", (String) event.getValue());
            app.put("relatorio", reportParams);

            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
        } catch (MXException mx) {
            mx.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
}
