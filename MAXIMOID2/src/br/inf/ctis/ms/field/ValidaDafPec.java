package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author andrel.almeida
 */

public class ValidaDafPec extends MboValueAdapter {
	
	 public ValidaDafPec(MboValue mbv) throws MXException {
	        super(mbv);
	    }

	    @Override
	    public void validate() throws MXException, RemoteException {
	    	
	        super.validate();
	        
	        if (!getMboValue().getBoolean()) {
	        	
	        	getMboValue().getMbo().setValueNull("MSAREADAF");
	        }
	    }
}
