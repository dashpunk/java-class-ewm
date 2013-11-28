package br.inf.id2.tj.mbo;

import br.inf.id2.mintur.mbo.*;
import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class MatUseTransSet extends psdi.plusp.app.inventory.PlusPMatUseTransSet
        implements MatUseTransSetRemote {

    public MatUseTransSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {

        super(mboserverinterface);
    }

    @Override
    public void save() throws MXException, RemoteException {
        super.save();
        
    }

    protected Mbo getMboInstance(MboSet mboset)
            throws MXException, RemoteException {
        return new br.inf.id2.tj.mbo.MatUseTrans(mboset);
    }
}
