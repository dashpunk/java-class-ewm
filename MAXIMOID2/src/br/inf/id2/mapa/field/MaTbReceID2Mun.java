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
public class MaTbReceID2Mun extends MboValueAdapter {

    public MaTbReceID2Mun(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        getMboValue().getMbo().setFieldFlag("ID2ESTADO", MboConstants.READONLY, (getMboValue().getMbo().getString("ID2MUN").length() > 0));

    }

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        getMboValue().getMbo().setFieldFlag("ID2ESTADO", MboConstants.READONLY, (getMboValue().getMbo().getString("ID2MUN").length() > 0));
    }
}
