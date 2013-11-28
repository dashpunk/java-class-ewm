/**
 *
 * @author Ricardo S Gomes
 */
package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class ID2POSet extends psdi.tloam.app.po.POSet
        implements ID2POSetRemote {

    public ID2POSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset)
            throws MXException, RemoteException {
        return new ID2PO(mboset);
    }
}
