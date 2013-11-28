package br.inf.id2.mintur.field;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class Rhtbvaga01Rhstcodserv extends MboValueAdapter {

    public Rhtbvaga01Rhstcodserv(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("*** Rhtbvaga01Rhstcodserv ***");
    }
    
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
    	super.validate();
    	System.out.println("*** validate");

    	getMboValue("RHSTDTAENTRAD").setValueNull();
    	getMboValue("RHSTDTANOMEACAO").setValueNull();
    	getMboValue("RHSTDTAPOSSE").setValueNull();
    	getMboValue("RHSTDSCATONOR").setValueNull();

    }
}
