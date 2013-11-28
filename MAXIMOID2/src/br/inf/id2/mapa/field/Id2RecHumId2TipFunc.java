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
public class Id2RecHumId2TipFunc extends MboValueAdapter {

    public Id2RecHumId2TipFunc(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	super.validate();
    	
        if (!getMboValue().getString().equalsIgnoreCase("001")) {
            getMboValue("ID2SIAPE").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("ID2CPF").setValueNull(MboConstants.NOACCESSCHECK);
        }
    }
}
