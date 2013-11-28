/*
 * Esta classe � a bean que ap�ia a tela de passagem de par�metros para o relat�rio e faz a chamada do servlet.
 * Para que essa classe funcione, algumas intera��es em tela devem ocorrer, como por exemplo passar o nome do relat�rio incluindo
 * a extens�o .jasper no atributo "Valor do Evento" no bot�o de OK da caixa de di�logo
 */
/**
 *
 * @author Dyogo Dantas
 */
package br.inf.id2.valec.bean.report;

import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;

import java.util.*;
import java.rmi.*;

public class ReportAlmoxarifado extends DataBean {

    public ReportAlmoxarifado() {
    }

    public void Imprimir() {
        try {
            Hashtable reportParams = new Hashtable();
            System.out.println("############ Chamando relat�rio ReportAlmoxarifado...");
            System.err.println("############ Par�metros passados: MRNUM=" + getMbo().getInt("MRNUM") + 
            				   "|" + getMbo().getInt("PONUM") + "|" + getMbo().getInt("MXNUMTERMO"));
            
            reportParams.put("MRNUM", getMbo().getInt("MRNUM"));
            reportParams.put("PONUM", getMbo().getInt("PONUM"));
            reportParams.put("MXNUMTERMO", getMbo().getInt("MXNUMTERMO"));

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
