package br.inf.id2.seedf.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class Turma extends psdi.webclient.system.beans.AppBean {

    /**
     *
     */
    public Turma() {
    }

    @Override
    public synchronized void listenerChangedEvent(DataBean speaker) {

        try {
            if (speaker.getUniqueIdName().equals("TOFEIEID")) {
                refreshTable();
                reloadTable();
            }
        } catch (MXException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Turma.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.listenerChangedEvent(speaker);
    }
}
