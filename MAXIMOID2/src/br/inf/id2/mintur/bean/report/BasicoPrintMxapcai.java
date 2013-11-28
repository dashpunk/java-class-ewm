/**
 *
 * @author Ricardo S Gomes
 */
package br.inf.id2.mintur.bean.report;

import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;

import java.util.*;
import java.rmi.*;

public class BasicoPrintMxapcai extends DataBean {

    public BasicoPrintMxapcai() {
    }

    @Override
    protected void initialize() throws MXException, RemoteException {
        super.initialize();
        System.out.println("---initialize");
    }


    public void Imprimir() {
        try {
            System.out.println("---imprimir");
            Hashtable reportParams = new Hashtable();
            
            reportParams.put("MXTBCAIID", app.getDataBean().getMbo().getString("MXTBCAIID"));

            System.out.println("---imprimir param snnum");
            WebClientEvent event = sessionContext.getCurrentEvent();
            reportParams.put("relatorio", (String) event.getValue());
            app.put("relatorio", reportParams);
            System.out.println("---imprimir params all");
            //Utility.sendEvent(new WebClientEvent("spawnjasperreport", event.getTargetId(), reportParams, sessionContext));
            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
        } catch (MXException mx) {
            mx.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }

    
}