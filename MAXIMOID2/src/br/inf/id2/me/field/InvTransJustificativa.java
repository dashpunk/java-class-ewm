package br.inf.id2.me.field;

import java.rmi.RemoteException;
import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class InvTransJustificativa extends MboValueAdapter {

    public InvTransJustificativa(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        //System.out.println("init InvTransJustificativa");
        MboSet invBalances;
        invBalances = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("INVBALANCES", getMboValue().getMbo().getUserInfo());
        invBalances.setWhere("ITEMNUM = '" + getMboValue().getMbo().getString("ITEMNUM") + "'");
        //System.out.println("---before reset");
        invBalances.reset();
        //System.out.println("---after reset " + invBalances.count());
        if (invBalances.count() > 0) {
            getMboValue().getMbo().setValue("MEJUS", invBalances.getMbo(invBalances.count() - 1).getString("MEJUS"));
            //System.out.println("---after setValue " + invBalances.getMbo(invBalances.count() - 1).getString("MEJUS"));
        } else {
            //System.out.println("-- none");
        }
        //System.out.println("end InvTransJustificativa");
    }
}
