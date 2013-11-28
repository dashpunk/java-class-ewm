package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class MaTbReceAttsReadOnly extends MboValueAdapter {

    public MaTbReceAttsReadOnly(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        getMboValue().getMbo().setFieldFlag("ID2CAT", MboConstants.READONLY, (getMboValue().getMbo().getString("ID2ARE").length() == 0));
        getMboValue().getMbo().setFieldFlag("ID2PRO", MboConstants.READONLY, (getMboValue().getMbo().getString("ID2ARE").length() == 0 || getMboValue().getMbo().getString("ID2CAT").length() == 0));

    }

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        getMboValue().getMbo().setFieldFlag("ID2CAT", MboConstants.READONLY, (getMboValue().getMbo().getString("ID2ARE").length() == 0));
        getMboValue().getMbo().setFieldFlag("ID2PRO", MboConstants.READONLY, (getMboValue().getMbo().getString("ID2ARE").length() == 0 || getMboValue().getMbo().getString("ID2CAT").length() == 0));
    }
}
