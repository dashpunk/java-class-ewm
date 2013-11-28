package br.inf.id2.me.mbo;

import java.rmi.RemoteException;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class ID2InvTrans extends psdi.app.inventory.InvTrans
        implements ID2InvTransRemote {

    //private MboRemote owner;
    @Override
    public void init()
            throws MXException {
        super.init();
    }

    public ID2InvTrans(MboSet mboset) throws MXException, RemoteException {
        super(mboset);
    }

    @Override
    protected void save() throws MXException, RemoteException {

        ////System.out.println("=== getOwner().getUniqueIDName() " + getOwner().getUniqueIDName());
        ////System.out.println("=== getOwner().getUniqueIDName()  MEJUS " + getOwner().getString("MEJUS"));
        ////System.out.println("=== before save()");
        //String justificativa = "";
        ////System.out.println("=== before get value");
        ////System.out.println("=== invbalances count " + getMboSet("INVBALANCES").count());
        //justificativa = getMboSet("INVBALANCES").getMbo(0).getString("MEJUS");
        ////System.out.println("=== MEJUS " + justificativa);
        setValue("MEJUS", getOwner().getString("MEJUS"));
        super.save();
        ////System.out.println("=== after save()");
    }
}
