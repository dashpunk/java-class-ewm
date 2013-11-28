package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class LocationID2BloqStatus extends MboValueAdapter {

    public LocationID2BloqStatus(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("----------------- validade em bloqStatus "+getMboValue().getMbo().getThisMboSet().count());
        getMboValue().getMbo().getThisMboSet().unselectAll();
        super.validate();
        System.out.println("----------------- FIM validade em bloqStatus ");
    }
}
