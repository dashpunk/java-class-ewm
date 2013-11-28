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
public class Magos01Id2Areint extends MboValueAdapter {

    public Magos01Id2Areint(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	super.validate();

    	if (getMboValue("ID2AREINT").isModified()) {
            getMboValue("ID2TIPOS").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("ID2TIPLOC").setValueNull(MboConstants.NOACCESSCHECK);   
            getMboValue("ID2NUMLOC").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("ID2NUMCONT").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("LOCATION").setValueNull(MboConstants.NOACCESSCHECK);
        }
    }
}
