package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldCodCoo extends MboValueAdapter {

    public ID2FldCodCoo(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void initValue()
            throws MXException, java.rmi.RemoteException {
        if (getMboValue().getMbo().toBeAdded()) {
            getMboValue().setValue(getMboValue().getMbo().getMboSet("MS_RL01PER").getMbo(0).getString("LOTACAO"), MboConstants.NOACTION);
        }
        super.init();
    }
}
