package br.inf.id2.valec.mbo;

import br.inf.id2.mintur.mbo.*;
import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Patrick
 */
public class ContractSet extends psdi.app.contract.ContractSet
        implements ContractSetRemote {

    public ContractSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {

        super(mboserverinterface);
    }

    @Override
    public void save() throws MXException, RemoteException {
        super.save();
        
    }

    protected Mbo getMboInstance(MboSet mboset)
            throws MXException, RemoteException {
        return new br.inf.id2.valec.mbo.Contract(mboset);
    }
}
