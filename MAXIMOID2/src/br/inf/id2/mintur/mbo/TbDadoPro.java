/**
 *
 * @author Patrick
 */
package br.inf.id2.mintur.mbo;

import br.inf.id2.seedf.mbo.*;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.util.MXException;

public class TbDadoPro extends psdi.mbo.custapp.CustomMbo
        implements ID2FechamentoPeriodoRemote {

    //private MboRemote owner;
    @Override
    public void init()
            throws MXException {
        super.init();
    }

    public TbDadoPro(MboSet mboset) throws MXException, RemoteException {
        super(mboset);

    }

    @Override
    public void add() throws MXException, RemoteException {

        super.add();
        System.out.println("0------ add em classx");
    }
    

}
