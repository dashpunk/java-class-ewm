package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.id2.mapa.Uteis;
import psdi.mbo.MboConstants;
import psdi.util.*;
import psdi.webclient.beans.desktopreq.DesktopReqAppBean;

public class ID2MRAppBean extends DesktopReqAppBean {

    /**
     * Método construtor de ID2ExploracaoPecuariaAppBean
     */
    public ID2MRAppBean() {
        Uteis.espera("/////////////////////////// metodo constutor 222222222");
    }

    @Override
    public void submit() throws MXException, RemoteException {
        Uteis.espera("/////////////////////////// Antes de setValue");
        try {
            getMbo().setValue("LOCATION", getMbo().getMboSet("REQUESTEDBY").getMbo(0).getString("DEPARTMENT"), MboConstants.NOVALIDATION_AND_NOACTION);
        } catch (RemoteException ex) {
            Uteis.espera("/////////////////////////// erro " + ex.getMessage());
        }

        Uteis.espera("/////////////////////////// após setValue");
        super.submit();
    }


}
