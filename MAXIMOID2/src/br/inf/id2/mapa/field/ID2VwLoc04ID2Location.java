package br.inf.id2.mapa.field;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class ID2VwLoc04ID2Location extends psdi.mbo.ALNDomain {

    public ID2VwLoc04ID2Location(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        getMboValue().setValue(getMboValue().getMbo().getString("LOCATION"));
    }
}
