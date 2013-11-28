package br.inf.id2.mapa.field;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Leysson Barbosa Moreira
 *  
 */
public class Map01SemAbate extends MboValueAdapter {

    public Map01SemAbate(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	super.validate();
    	
    if(getMboValue().getMbo().getThisMboSet().getApp().equalsIgnoreCase("ID2MAP01")) {
    	if(getMboValue().getMbo().getBoolean("ID2SEMREG") == false && !getMboValue().getMbo().getString("ID2OBSREG").equals("")) {
    		getMboValue().getMbo().setValueNull("ID2OBSREG", MboConstants.NOACCESSCHECK);
    	}
      }
    }
}
