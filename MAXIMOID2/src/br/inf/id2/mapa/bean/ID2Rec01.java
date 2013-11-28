package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.util.MXException;
import psdi.webclient.system.controller.BoundComponentInstance;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2Rec01 extends psdi.webclient.beans.receipts.ReceiptsAppBean {

    public ID2Rec01() {
        super();
    }

    @Override
    public void bindComponent(BoundComponentInstance boundComponent) {
        super.bindComponent(boundComponent);
        System.out.println("-------field " + boundComponent.getProperty("dataattribute"));
        if (boundComponent.getProperty("dataattribute").equalsIgnoreCase("ID2PERRECEBE")) {
            try {
                getMbo().setValue("ID2PERRECEBE", sessionContext.getUserInfo().getUserName());
            } catch (MXException ex) {
                Logger.getLogger(ID2Rec01.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(ID2Rec01.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int novoRecebimento() throws MXException, RemoteException {
        String aplicacao = "ID2PO08";

        WebClientEvent newEvent = new WebClientEvent("changeapp", this.app.getId(), aplicacao, null, null, null, 0, this.clientSession);

        newEvent.setSourceControl(this.app);

        this.clientSession.queueEvent(newEvent);
    	return EVENT_HANDLED;
    }
    
}
