/**
 *
 * @author Jesse Rovira
 */
package br.inf.id2.common.bean.report;

import java.rmi.RemoteException;
import java.util.Hashtable;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ReportBasicoHybridBean extends DataBean {

    public ReportBasicoHybridBean() {
        System.out.println("################## ReportBasicoBean()");
    }

    public void Imprimir() {
        try {
            System.out.println("############### Entrou no ReportBasicoBean");
            Hashtable reportParams = new Hashtable();
            //Carrega os dados da tela
            String[] attrs = app.getDataBean("MAINRECORD").getAttributes();
            System.out.println(attrs);
            for (int i = 0; i < attrs.length; i++) {
                System.out.println("#################### Atributo (" + i + ") = " + attrs[i]);
                reportParams.put(attrs[i], (String) app.getDataBean("MAINRECORD").getMbo().getString(attrs[i]));
            }
            //Carrega os dados da dialog
            attrs = getAttributes();
            for (int i = 0; i < attrs.length; i++) {
            	System.out.println("################ Atributo (" + i + ")=" + attrs[i]);
                reportParams.put(attrs[i], (String) getMbo().getString(attrs[i]));
            }
            WebClientEvent event = sessionContext.getCurrentEvent();
            System.out.println("############## nome do relatorio "+(String) event.getValue());
            reportParams.put("relatorio", (String) event.getValue());
            app.put("relatorio", reportParams);
            System.out.println("############### Enviando parametros... saindo da classe");
            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));

        } catch (MXException mx) {
            mx.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
}
