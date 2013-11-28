package br.inf.id2.me.mbo;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class ID2LocationCustomSet extends psdi.tloam.app.location.TloamLocationSet
        implements ID2LocationCustomSetRemote {

    public ID2LocationCustomSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {
        super(mboserverinterface);
        //System.out.println("************ID2LocationCustomSet() antes");
        super.setOrderBy("DESCRIPTION");
        //System.out.println("************ID2LocationCustomSet() depois");
        /*try {
        for (int i = 0; i < getMbo().getThisMboSet().count(); i++) {
        //System.out.println("... description " + getMbo().getThisMboSet().getMbo(i).getString("DESCRIPTION"));
        }
        } catch (RemoteException ex) {
        Logger.getLogger(ID2LocationCustomSet.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }
}
