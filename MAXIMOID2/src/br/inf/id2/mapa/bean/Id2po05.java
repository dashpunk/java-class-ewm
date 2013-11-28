package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.controller.BoundComponentInstance;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class Id2po05 extends psdi.webclient.system.beans.AppBean {

    public Id2po05() {
        super();
        System.out.println("... Id2po05.class");
    }

    public int novoRecebimento() throws MXException, RemoteException {
        String aplicacao = "ID2PO08";

        WebClientEvent newEvent = new WebClientEvent("changeapp", this.app.getId(), aplicacao, null, null, null, 0, this.clientSession);

        newEvent.setSourceControl(this.app);

        this.clientSession.queueEvent(newEvent);
    	return EVENT_HANDLED;
    }
}
