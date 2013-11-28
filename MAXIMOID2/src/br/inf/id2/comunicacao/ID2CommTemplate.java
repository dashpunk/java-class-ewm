package br.inf.id2.comunicacao;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.common.commtmplt.CommTemplateRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class ID2CommTemplate extends psdi.common.commtmplt.CommTemplate implements CommTemplateRemote {

    public ID2CommTemplate(MboSet ms) throws MXException, RemoteException {
        super(ms);

    }

    @Override
    public void sendMessage(MboRemote targetMbo) throws MXException, RemoteException {
        try {
            System.out.println("***************** sendMessege(MboRemote targetMbo)");
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ID2CommTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.sendMessage(targetMbo);

    }

    @Override
    public void sendMessage(MboRemote targetMbo, MboRemote originatingMbo) throws MXException, RemoteException {
        try {
            System.out.println("***************** sendMessege(MboRemote targetMbo, MboRemote originatingMbo)");
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ID2CommTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.sendMessage(targetMbo, originatingMbo);
    }

    @Override
    public void sendMessage() throws MXException, RemoteException {
        try {
            System.out.println("***************** sendMessege()");
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ID2CommTemplate.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.sendMessage();
    }
}
