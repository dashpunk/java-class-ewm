/*
 * Esta classe � a bean que ap�ia a tela de passagem de par�metros para o relat�rio e faz a chamada do servlet.
 * Para que essa classe funcione, algumas intera��es em tela devem ocorrer, como por exemplo passar o nome do relat�rio incluindo
 * a extens�o .jasper no atributo "Valor do Evento" no bot�o de OK da caixa de di�logo
 */
/**
 *
 * @author Jessé Rovira
 */
package br.inf.id2.me.bean.report;

import java.rmi.RemoteException;
import java.util.Hashtable;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ReportTramiteMultiplo extends AppBean {

    public ReportTramiteMultiplo() {
    }

    public void Imprimir() {
        try {
        	System.out.println("################ ReportTramiteMultiplo - Imprimir");
            Hashtable reportParams = new Hashtable();
            
            reportParams.put("PERSONID", getMbo().getUserInfo().getPersonId());
                        
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
            System.out.println("############ Enviado ao Máximo");

        } catch (MXException mx) {
            mx.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
}
