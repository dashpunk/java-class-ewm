package br.inf.id2.mintur.field;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class Mxtbcavis01Mxispar extends MboValueAdapter {

    public Mxtbcavis01Mxispar(MboValue mbv) throws MXException {
        super(mbv);
    }
    

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        super.validate();

        getMboValue("MXNOMVIS").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("MXINSVIS").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("MXINDVIS").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("MXSIGPAR").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("MXESTPAR").setValueNull(MboConstants.NOACCESSCHECK);
        
    }

}
