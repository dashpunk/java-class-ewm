/*
 * Esta classe � a bean que ap�ia a tela de passagem de par�metros para o relat�rio e faz a chamada do servlet.
 * Para que essa classe funcione, algumas intera��es em tela devem ocorrer, como por exemplo passar o nome do relat�rio incluindo
 * a extens�o .jasper no atributo "Valor do Evento" no bot�o de OK da caixa de di�logo
 */
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

public class BasicoPrintFnrh extends DataBean {

    public BasicoPrintFnrh() {
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
            
            reportParams.put("SNNUM", app.getDataBean().getMbo().getString("SNNUM"));

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
