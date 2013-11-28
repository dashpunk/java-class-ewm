package br.inf.id2.mapa.mbo;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboServerInterface;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class POLine extends psdi.mbo.custapp.CustomMboSet implements DataChangeCustomMboSetRemote {

    public POLine(MboServerInterface mboserverinterface) throws MXException, RemoteException {
        super(mboserverinterface);
        System.out.println("------------- DataChangeCustomMboSet");
    }

    @Override
    public void saveMbos() throws MXException, RemoteException {
        System.out.println("----- S5");
        atualizaData();
        super.saveMbos();
    }



    private void atualizaData() throws RemoteException, MXException {
        System.out.println("------------- save en DataChangeCustomMboSet");

        MboRemote mbo;
        MboRemote mboB;
        System.out.println("------------- save en DataChangeCustomMboSet count " + count());
        MboSet locationMboSet;
        locationMboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("LOCATIONS", getUserInfo());
        for (int i = 0; ((mbo = getMbo(i)) != null); i++) {
            System.out.println("---i " + i);
            System.out.println("############# IsModified=" +mbo.isModified());
            System.out.println("############# IsNew=" +mbo.isNew());
            //if (mbo.isModified() || mbo.isNew()) {
                System.out.println(mbo.getString("LOCATION"));
                locationMboSet.setWhere("LOCATION = '" + mbo.getString("LOCATION") + "'");
                locationMboSet.reset();
                System.out.println("--- after reset() " + locationMboSet.count());
                System.out.println("############# MBO=" + locationMboSet.getMbo(0));
                if ((mboB = locationMboSet.getMbo(0)) != null) {
                    System.out.println("--- before setValue");
                    mboB.setValue("ID2DATCHANGE", new Date());
                    System.out.println("--- before save");
                    locationMboSet.save();
                    System.out.println("--- after save");
                }
            //}
        }
    }
}
//mbo.setValue("ID2DATCHANGE", new Date());

