package psdi.id2.mintur;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.tloam.app.po.POSet;
import psdi.util.MXException;

public class ID2POSet extends POSet
        implements ID2POSetRemote {

	private static final long serialVersionUID = 1L;

	public ID2POSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset)
            throws MXException, RemoteException {
        return new ID2PO(mboset);
    }
}
