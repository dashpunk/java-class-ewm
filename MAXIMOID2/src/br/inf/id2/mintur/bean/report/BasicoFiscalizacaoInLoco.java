package br.inf.id2.mintur.bean.report;

import psdi.util.*;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.*;

import java.util.*;
import java.rmi.*;

/**
 *
 * @author Ricardo S Gomes
 */
public class BasicoFiscalizacaoInLoco extends AppBean {

    public BasicoFiscalizacaoInLoco() {
    }

    @Override
    public void bindComponent(BoundComponentInstance boundComponent) {
        super.bindComponent(boundComponent);
        try {
            System.out.println("--- bindcomponent 1036");
            System.out.println("--- bindcomponent 1036 val MTTBFISCONID " + app.getDataBean("MAINRECORD").getMbo().getLong("MTTBFISCONID"));
            getMbo().setValue("MTTBFISCONID", app.getDataBean("MAINRECORD").getMbo().getLong("MTTBFISCONID"));
        } catch (Exception e) {
        }
    }

    @Override
    protected void initialize() throws MXException, RemoteException {
        super.initialize();
        try {
            System.out.println("--- bindcomponent 1036");
            System.out.println("--- bindcomponent 1036 val MTTBFISCONID " + app.getDataBean("MAINRECORD").getMbo().getLong("MTTBFISCONID"));
            getMbo().setValue("MTTBFISCONID", app.getDataBean("MAINRECORD").getMbo().getLong("MTTBFISCONID"));
        } catch (Exception e) {
        }
    }

    public void Imprimir() {
        try {
            System.out.println("################ ID2ReportBasicoBean - Imprimir");
            Hashtable reportParams = new Hashtable();
            java.lang.String[] attrs = getAttributes();
            for (int i = 0; i < attrs.length; i++) {
                System.out.println("################ Atributo (" + i + ")=" + attrs[i]);
                reportParams.put(attrs[i], (String) getMbo().getString(attrs[i]));
            }
            reportParams.put("DISPLAYNAME", getMbo().getUserInfo().getUserName());
            WebClientEvent event = sessionContext.getCurrentEvent();
            reportParams.put("relatorio", (String) event.getValue());
            app.put("relatorio", reportParams);
            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
            System.out.println("############ Enviado ao M�ximo");

        } catch (MXException mx) {
            mx.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
}
