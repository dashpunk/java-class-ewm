package br.inf.id2.mintur.field;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class SetaCargoAgenda extends psdi.app.company.FldCompanyCompany {

    public SetaCargoAgenda(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("*** SetaCargoAgenda ***");
    }
    
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
    	super.validate();
    	System.out.println("*** validate");

    	if(!getMboValue().isNull()){
	    	getMboValue().getMbo().setFieldFlag("MXCARGO", MboConstants.READONLY, false);
	    	getMboValue().getMbo().setValue("MXCARGO", getMboValue().getMbo().getMboSet("RL01CARGO").getMbo(0).getMboSet("SGRLCARGO01").getMbo(0).getString("DESCRIPTION"),MboConstants.NOVALIDATION_AND_NOACTION);
	    	getMboValue().getMbo().setFieldFlag("MXCARGO", MboConstants.READONLY, true);
    	}
    }
}
