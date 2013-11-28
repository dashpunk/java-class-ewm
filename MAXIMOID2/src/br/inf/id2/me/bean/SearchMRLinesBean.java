package br.inf.id2.me.bean;

import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class SearchMRLinesBean extends psdi.webclient.beans.desktopreq.AllMRLinesBean {

    public SearchMRLinesBean() {
    }

    @Override
    public synchronized void reset() throws MXException {
        super.reset();
        //System.out.println("----------- reset");
        try {
            for (int i = 0; i < getMboSet().count(); i++) {
                getMboSet().getMbo(i).setValue("QTY_V", getMboSet().getMbo(i).getDouble("QTY"));
            }
        } catch (Exception e) {
            System.out.println("----------- exception " + e.getMessage());
        }
    }

    @Override
    protected void initialize() throws MXException, RemoteException {

        super.initialize();

        try {
            //System.out.println("--------------- Inicializado");
            for (int i = 0; i < getMboSet().count(); i++) {
                getMboSet().getMbo(i).setValue("QTY_V", getMboSet().getMbo(i).getDouble("QTY"));
            }

        } catch (Exception e) {
            System.out.println("----------- exception " + e.getMessage());
        }

    }
}
