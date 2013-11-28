/**
 * @author Patrick
 */
package br.inf.id2.mapa.bean.report;

import java.rmi.RemoteException;
import java.util.Hashtable;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ID2EstabelecimentoRuralPrintBean extends DataBean {

    public ID2EstabelecimentoRuralPrintBean() {
    }

    public void Imprimir() {
        try {
            Hashtable reportParams = new Hashtable();
            reportParams.put("LOCATION", (String) parent.getMbo().getString("LOCATION"));
            System.out.println("*** ID2EstabelecimentoRuralPrintBean");
            System.out.println("*** LOCATION "+(String) parent.getMbo().getString("LOCATION"));

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
