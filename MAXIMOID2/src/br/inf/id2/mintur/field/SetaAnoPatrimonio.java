package br.inf.id2.mintur.field;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 * @author Patrick
 */
public class SetaAnoPatrimonio extends psdi.app.company.FldCompanyCompany {

    public SetaAnoPatrimonio(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("*** SetaAnoPatrimonio ***");
    }
    
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
    	super.validate();
    	System.out.println("*** validate");

    	if(!getMboValue().isNull()){
    		System.out.println("*** INSTALLDATE "+getMboValue().getDate());
    		System.out.println("*** ANO "+Data.getAno(getMboValue().getDate()));
	    	getMboValue().getMbo().setFieldFlag("MXANO", MboConstants.READONLY, false);
	    	getMboValue().getMbo().setValue("MXANO", String.valueOf(Data.getAno(getMboValue().getDate())));
	    	getMboValue().getMbo().setFieldFlag("MXANO", MboConstants.READONLY, true);
    	}
    }
}
