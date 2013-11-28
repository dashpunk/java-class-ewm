package br.inf.id2.mintur.field;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.tamit.app.asset.TLOAMFldAssetItemnum;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 * @author Patrick
 */
public class SetaContaContabil extends TLOAMFldAssetItemnum {

    public SetaContaContabil(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("*** SetaContaContabil ***");
    }
    
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
    	super.validate();
    	System.out.println("*** validate");

    	if(!getMboValue().isNull()){
    		System.out.println("*** ITEM "+getMboValue().getMbo().getMboSet("ITEM").count());
    		System.out.println("*** ITEM.MXGLACCOUNT "+getMboValue().getMbo().getMboSet("ITEM").getMbo(0).getString("MXGLACCOUNT"));
	    	getMboValue().getMbo().setFieldFlag("MXGLACCOUNT", MboConstants.READONLY, false);
	    	getMboValue().getMbo().setValue("MXGLACCOUNT", getMboValue().getMbo().getMboSet("ITEM").getMbo(0).getString("MXGLACCOUNT"));
	    	getMboValue().getMbo().setFieldFlag("MXGLACCOUNT", MboConstants.READONLY, true);
    	}
    }
}
