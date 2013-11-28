/*
 * Esta classe � a bean que ap�ia a tela de passagem de par�metros para o relat�rio e faz a chamada do servlet.
 * Para que essa classe funcione, algumas intera��es em tela devem ocorrer, como por exemplo passar o nome do relat�rio incluindo
 * a extens�o .jasper no atributo "Valor do Evento" no bot�o de OK da caixa de di�logo
 */
/**
 *
 * @author Jessé Rovira
 */
package psdi.webclient.beans.report;

import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.*;
import psdi.server.*;

import java.util.*;
import java.io.*;
import java.rmi.*;

public class ID2ReportBasicoBean extends AppBean {

    public ID2ReportBasicoBean() {
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
