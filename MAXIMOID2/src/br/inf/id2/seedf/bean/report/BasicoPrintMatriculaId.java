/**
 *
 * @author Ricardo S Gomes
 */
package br.inf.id2.seedf.bean.report;

import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;

import java.util.*;
import java.rmi.*;

public class BasicoPrintMatriculaId extends DataBean {

    public BasicoPrintMatriculaId() {
    }

    public void Imprimir() {
        try {
            Hashtable reportParams = new Hashtable();
            
            reportParams.put("MATRICULAID", (String) parent.getMbo().getString("MATRICULAID"));

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
