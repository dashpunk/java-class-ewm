package br.inf.id2.mapa.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class NumeroNucleos extends MboValueAdapter {

    public NumeroNucleos(MboValue mbv) {
    	super(mbv);
//        System.out.println("*** NumeroNucleos ***");
    }

    @Override
    public void initValue() throws MXException, RemoteException {
    	super.initValue();
    	
        if(getMboValue().getMbo().getMboSet("MATBNUCAVE").isEmpty()){
        	getMboValue().setValue(0);
        }else{
        	getMboValue().setValue(getMboValue().getMbo().getMboSet("MATBNUCAVE").count());
        }
    }
    
}
