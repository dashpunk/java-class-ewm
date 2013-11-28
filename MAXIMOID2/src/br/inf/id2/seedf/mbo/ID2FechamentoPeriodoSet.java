package br.inf.id2.seedf.mbo;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class ID2FechamentoPeriodoSet extends psdi.mbo.custapp.CustomMboSet implements ID2FechamentoPeriodoSetRemote {

    public ID2FechamentoPeriodoSet(MboServerInterface mboserverinterface) throws MXException, RemoteException {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset) throws MXException, RemoteException {
        return new ID2FechamentoPeriodo(mboset);
    }
}
