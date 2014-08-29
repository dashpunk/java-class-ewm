package br.inf.ctis.ms.bean.report;

import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;
import java.util.*;
import java.rmi.*;


public class PrintDespacho extends DataBean {

    public PrintDespacho() {
    }

    public void Imprimir() {
        try {
            Hashtable reportParams = new Hashtable();

            System.out.println("########## MSTBOCID" + (String) parent.getMbo().getString("MSTBOCID"));
            reportParams.put("MSTBOCID", (String) parent.getMbo().getString("MSTBOCID"));
            reportParams.put("REPORTTYPE", "PDF");

            WebClientEvent event = sessionContext.getCurrentEvent();
            reportParams.put("relatorio", (String) event.getValue());
            app.put("relatorio", reportParams);
            //Utility.sendEvent(new WebClientEvent("spawnjasperreport", event.getTargetId(), reportParams, sessionContext));
            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
        } catch (MXException mx) {
            mx.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
}
