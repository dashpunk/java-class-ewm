/**
 *
 * @author Ricardo S Gomes
 */
package br.inf.id2.tj.bean.report;

import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;

import java.util.*;
import java.rmi.*;

public class BasicoPrintOS extends DataBean {

    public BasicoPrintOS() {
    }

    public void Imprimir() {
        try {
            Hashtable reportParams = new Hashtable();
            
            reportParams.put("WONUM", (String) parent.getMbo().getString("WONUM"));

            WebClientEvent event = sessionContext.getCurrentEvent();
            reportParams.put("relatorio", (String) event.getValue());
            app.put("relatorio", reportParams);
            //Utility.sendEvent(new WebClientEvent("spawnjasperreport", event.getTargetId(), reportParams, sessionContext));
            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
        } catch (MXException mx) {
            System.out.println("----------------- exceção em BasicoPrintOS mx "+mx.getMessage());
            mx.printStackTrace();
        } catch (RemoteException re) {
            System.out.println("----------------- exceção em BasicoPrintOS re "+re.getMessage());
            re.printStackTrace();
        }
    }
}
