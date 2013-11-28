package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldCodSec extends MboValueAdapter {

    public ID2FldCodSec(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void initValue()
            throws MXException, java.rmi.RemoteException {
        if (getMboValue().getMbo().toBeAdded()) {
            getMboValue().setValue(getMboValue().getMbo().getMboSet("MS_RL01PER").getMbo(0).getString("SECRETARIA"), MboConstants.NOACTION);
        }
        super.initValue();
    }
}
