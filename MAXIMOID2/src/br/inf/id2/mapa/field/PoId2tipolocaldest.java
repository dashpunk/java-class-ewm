package br.inf.id2.mapa.field;

import br.inf.id2.common.util.Data;
import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 * 
 */
public class PoId2tipolocaldest extends MboValueAdapter {

    public PoId2tipolocaldest(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        getMboValue("ID2DESTIPEST").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2DESNUMCONT").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2DESABA").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2DESAGLCNPJ").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2DESTIPEVE").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2DESAGLNOMEVE").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2DESAGL").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2DESEXPPER").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2DESEXPPROP").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2DESEXP").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2DESEXPCOD").setValueNull(MboConstants.NOACCESSCHECK);
        
    }
}
