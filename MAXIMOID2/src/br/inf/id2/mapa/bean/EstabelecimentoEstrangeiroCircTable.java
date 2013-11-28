package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.Hashtable;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Willians Andrade
 */
public class EstabelecimentoEstrangeiroCircTable extends DataBean {

    public int EREPORT() throws MXException, RemoteException {

        MboSetRemote mboa = getMboSet();

        Hashtable reportParams = new Hashtable();

        int registroCorrente = getMboSet().getCurrentPosition();

            reportParams.put("MATBESTESTID", mboa.getMbo(registroCorrente).getLong("MATBESTESTID"));
            reportParams.put("ID2REV", mboa.getMbo(registroCorrente).getLong("ID2REV"));
            reportParams.put("MATBCIRCULARID", mboa.getMbo(registroCorrente).getLong("MATBCIRCULARID"));

            WebClientEvent event = sessionContext.getCurrentEvent();
            reportParams.put("relatorio", "estabestrangeiro.jasper");
            app.put("relatorio", reportParams);

        return EVENT_HANDLED;
    }
    public int RASCUNHO() throws MXException, RemoteException {

        MboSetRemote mboa = getMboSet();

        Hashtable reportParams = new Hashtable();

        int registroCorrente = getMboSet().getCurrentPosition();

            reportParams.put("MATBESTESTID", mboa.getMbo(registroCorrente).getLong("MATBESTESTID"));
            reportParams.put("MATBCIRCULARID", mboa.getMbo(registroCorrente).getLong("MATBCIRCULARID"));
            reportParams.put("ID2REV", mboa.getMbo(registroCorrente).getLong("ID2REV"));

            WebClientEvent event = sessionContext.getCurrentEvent();
            reportParams.put("relatorio", "estabestrangeirorascunho.jasper");
            app.put("relatorio", reportParams);

        return EVENT_HANDLED;
    }
}