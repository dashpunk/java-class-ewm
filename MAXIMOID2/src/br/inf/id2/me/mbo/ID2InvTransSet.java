package br.inf.id2.me.mbo;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class ID2InvTransSet extends psdi.app.inventory.InvTransSet implements ID2InvTransSetRemote {

    public ID2InvTransSet(MboServerInterface mboserverinterface) throws MXException, RemoteException {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset) throws MXException, RemoteException {
        return new ID2InvTrans(mboset);
    }
}
