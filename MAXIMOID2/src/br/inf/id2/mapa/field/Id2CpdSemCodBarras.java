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
public class Id2CpdSemCodBarras extends MboValueAdapter {

    public Id2CpdSemCodBarras(MboValue mbv) {
        super(mbv);
        System.out.println("Id2CpdSemCodBarras");  		
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	super.validate();
    	
    	if(!getMboValue().getMbo().getString("ID2CODBARRA").equals("") && getMboValue().getMbo().getBoolean("MATEMCODBAR") == false) {
    		
    		getMboValue().getMbo().setFieldFlag("ID2NUMGTA", MboConstants.READONLY, false);
    		getMboValue().getMbo().setFieldFlag("ID2SERIE", MboConstants.READONLY, false);
    		getMboValue().getMbo().setFieldFlag("ID2ADDUF", MboConstants.READONLY, false);
    		
    		getMboValue().getMbo().setValue("ID2CODBARRA", "", MboConstants.NOACCESSCHECK);
    		getMboValue().getMbo().setValue("ID2NUMGTA", "", MboConstants.NOACCESSCHECK);
    		getMboValue().getMbo().setValue("ID2SERIE", "", MboConstants.NOACCESSCHECK);
    		getMboValue().getMbo().setValue("ID2ADDUF", "", MboConstants.NOACCESSCHECK);
    	
    	}
    	
    }
}
