package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.util.*;
import psdi.webclient.beans.servicedesk.CreateSRSSAppBean;

public class ID2SRAppBean extends CreateSRSSAppBean {

    /**
     * Método construtor de ID2ExploracaoPecuariaAppBean
     */
    public ID2SRAppBean() {
        //Uteis.espera("/////////////////////////// metodo constutor 222222222");
    }

    @Override
    public synchronized void saverecord() throws MXException {
        //Uteis.espera("/////////////////////////// Antes de setValue");
        try {
            getMbo().setValue("MS_CODCOO", getMbo().getMboSet("MS_RL01PER").getMbo(0).getString("LOTACAO"), MboConstants.NOVALIDATION_AND_NOACTION);
        } catch (RemoteException ex) {
            //Uteis.espera("/////////////////////////// erro " + ex.getMessage());
        }

        //Uteis.espera("/////////////////////////// após setValue");
        super.saverecord();
    }
}
