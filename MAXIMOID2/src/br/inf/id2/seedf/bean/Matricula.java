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
public class Matricula extends psdi.webclient.system.beans.AppBean {

    /**
     *
     */
    public Matricula() {
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        try {
            //System.out.println("------- dataChangedEvent " + speaker.getUniqueIdName());

            if (speaker.getUniqueIdName().equals("MATRICULAID")) {
                //System.out.println(" --- refreshTable()");
                refreshTable();
                reloadTable();
            }
        } catch (MXException ex) {
            Logger.getLogger(Matricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Matricula.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.dataChangedEvent(speaker);
    }

    @Override
    public synchronized void listenerChangedEvent(DataBean speaker) {

        try {
            //System.out.println("----> listenerChangedEvent");
            //System.out.println(")))))) " + speaker.getUniqueIdName());
            if (speaker.getUniqueIdName().equals("SEETBMTID")) {
                //System.out.println(" --- refreshTable()");
                refreshTable();
                reloadTable();
            }
        } catch (MXException ex) {
            Logger.getLogger(Matricula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Matricula.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.listenerChangedEvent(speaker);
    }
}
