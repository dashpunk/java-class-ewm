package br.inf.id2.common.field;

import java.rmi.RemoteException;

import br.inf.id2.common.util.Serial;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class AplicarSerial extends MboValueAdapter {

    public AplicarSerial(MboValue mbv) {
        super(mbv);
        System.out.println("*** AplicarSerial ***");
    }
    
    @Override
    public void initValue() throws MXException, RemoteException {
    	super.initValue();
    	
    	System.out.println("*** initValue");
    	
    	if(getMboValue().getMbo().isNew()){
	    	String valor = Serial.getSerialMascarado(getMboValue(), getMboValue().getMbo().getUserInfo());
	    	System.out.println("*** valor "+valor);
	    	getMboValue().setFlag(MboConstants.READONLY, false);
	    	getMboValue().setValue(valor, MboConstants.NOVALIDATION_AND_NOACTION);
	    	getMboValue().setFlag(MboConstants.READONLY, true);
    	}
    }
}
