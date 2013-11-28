package br.inf.id2.mintur.mbo;

import br.inf.id2.seedf.mbo.*;
import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Patrick
 */
public class TbDadoProSet extends psdi.mbo.custapp.CustomMboSet
        implements ID2FechamentoPeriodoSetRemote {

    public TbDadoProSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {

        super(mboserverinterface);
    }

    @Override
    protected void addMbo(Mbo mbo) {
        super.addMbo(mbo);
        System.out.println("0------ addMbo em classx");
    }

    protected Mbo getMboInstance(MboSet mboset)
            throws MXException, RemoteException {

        return new TbDadoPro(mboset);
    }
}
