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

public class ID2GtaPrintBean extends DataBean {

    public ID2GtaPrintBean() {
    }

    public void Imprimir() {
        try {
            Hashtable reportParams = new Hashtable();
            reportParams.put("ID2NUMGTA", (String) parent.getMbo().getString("ID2NUMGTA"));
            reportParams.put("ID2SERIE", (String) parent.getMbo().getString("ID2SERIE"));
            reportParams.put("ID2LOCUF", (String) parent.getMbo().getString("ID2LOCUF"));

            reportParams.put("MAFUN", (String) getMbo().getString("MAFUN"));
            reportParams.put("MACAB", (String) getMbo().getString("MACAB"));
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
