/**
 * @author Patrick L. Silva
 */
package br.inf.id2.valec.bean.report;

import java.rmi.RemoteException;
import java.util.Hashtable;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ReportTermoResponsabilidadeUnidade extends DataBean {

    public ReportTermoResponsabilidadeUnidade() {
    }

    public void Imprimir() { 
        try {
            Hashtable reportParams = new Hashtable();

            System.out.println("*** ReportTermoResponsabilidadeUnidade ***");
            System.out.println("*** LOCATION "+(String) app.getDataBean("MAINRECORD").getMbo().getString("LOCATION"));
            
            reportParams.put("LOCATION", (String) (String) app.getDataBean("MAINRECORD").getMbo().getString("LOCATION"));

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
