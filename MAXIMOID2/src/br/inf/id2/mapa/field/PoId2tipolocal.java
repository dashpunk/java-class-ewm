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
public class PoId2tipolocal extends MboValueAdapter {

    public PoId2tipolocal(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        getMboValue("ID2PROTIPEST").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2PRONUMCONT").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2PROABA").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2PROAGLCNPJ").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2PROTIPEVE").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2PROAGLNOMEVE").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2PROAGL").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2PROEXPPER").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2PROEXPPROP").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2PROEXP").setValueNull(MboConstants.NOACCESSCHECK);
        
        if (getMboValue().getMbo().getMboSet("POLINE").count() > 0) {
            getMboValue().getMbo().getMboSet("POLINE").deleteAll(MboConstants.NOACCESSCHECK);
        }
    }
}
