package br.inf.id2.common.field;

import java.rmi.RemoteException;

import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Dyogo
 * 
 */
public class SalvaMBOAposMudarCampo extends MboValueAdapter {

    public SalvaMBOAposMudarCampo(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        
        MboValue mboValue = getMboValue();
        MboSetRemote set = mboValue.getMbo().getThisMboSet();
        set.save();

    }
}
