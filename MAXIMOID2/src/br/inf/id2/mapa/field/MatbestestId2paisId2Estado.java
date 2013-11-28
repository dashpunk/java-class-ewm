package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class MatbestestId2paisId2Estado extends MboValueAdapter {

    public MatbestestId2paisId2Estado(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        if (getMboValue().getName().equalsIgnoreCase("ID2PAIS")) {
            getMboValue("ID2ESTADO").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("ID2CIDADE").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("ID2CIDADE1").setValueNull(MboConstants.NOACCESSCHECK);
        } else {
            getMboValue("ID2CIDADE").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("ID2CIDADE1").setValueNull(MboConstants.NOACCESSCHECK);
        }

    }
}
