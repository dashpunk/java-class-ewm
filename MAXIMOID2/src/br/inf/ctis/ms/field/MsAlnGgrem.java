package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsAlnGgrem extends MboValueAdapter {

    public MsAlnGgrem(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
  	
        super.validate();
        if (getMboValue().getString() == null) {
        	getMboValue().getMbo().setValue("MSNUFLGCODGGREMACAO", false, MboConstants.NOACCESSCHECK);
            getMboValue().getMbo().setValueNull("MSALNCMED");
        } else {
        	getMboValue().getMbo().setValue("MSNUFLGCODGGREMACAO", true, MboConstants.NOACCESSCHECK);
            getMboValue().getMbo().setValue("MSALNCMED", "SIM", MboConstants.NOACCESSCHECK);
        }
        
    }

}
