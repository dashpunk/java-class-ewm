/**
 * @author Willians Andrade
 */
package br.inf.id2.mapa.bean.report;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Hashtable;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ID2TransitoEstadualPrintBean extends DataBean {

    public ID2TransitoEstadualPrintBean() {
    }

    public void Imprimir() {
    	System.out.println("*** ID2TransitoEstadualPrintBean");
        try {
            Hashtable reportParams = new Hashtable();
            reportParams.put("UFSIGLA", (String) parent.getMbo().getString("UFSIGLA"));
            reportParams.put("DATAINI", (Date) parent.getMbo().getDate("DATAINI"));
            reportParams.put("DATAFIM", (Date) parent.getMbo().getDate("DATAFIM"));
            
            System.out.println("*** UFSIGLA "+(String) parent.getMbo().getString("LOCATION"));
            System.out.println("*** DATAINI "+(Date) parent.getMbo().getDate("LOCATION"));
            System.out.println("*** DATAFIM "+(Date) parent.getMbo().getDate("LOCATION"));
            
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
