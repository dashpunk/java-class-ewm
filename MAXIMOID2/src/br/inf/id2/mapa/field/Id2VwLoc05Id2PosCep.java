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
public class Id2VwLoc05Id2PosCep extends MboValueAdapter {

    public Id2VwLoc05Id2PosCep(MboValue mbv) {
        super(mbv);
    }
    
    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        boolean valor = getMboValue().getBoolean();
        System.out.println("ID2POSCEP " + valor);
        //if (!valor) {
        	System.out.println("######### Antes = " + getMboValue("ID2CEPCODE2").getString());
        	getMboValue("ID2CEPCODE2").setValueNull(MboConstants.NOACCESSCHECK);
        	getMboValue("ID2CEPCODE2").setValue("");
        	System.out.println("######### Depois = " + getMboValue("ID2CEPCODE2").getString());
        	getMboValue("ID2CODMUN").setValueNull(MboConstants.NOACCESSCHECK);
        	getMboValue("ID2CODMUN").setValue("");
        	getMboValue("ID2ADDUF").setValueNull(MboConstants.NOACCESSCHECK);
        	getMboValue("ID2ADDUF").setValue("");
        //}
    }
    
}
