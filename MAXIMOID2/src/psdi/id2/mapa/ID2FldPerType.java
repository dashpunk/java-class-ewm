package psdi.id2.mapa;

import psdi.app.pr.*;
import psdi.app.common.FldChangeStatus;
import psdi.app.rfq.virtual.*;
import psdi.app.rfq.*;

import psdi.mbo.*;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Bruno e Ricardo Gomes
 */
public class ID2FldPerType extends MboValueAdapter {

    public ID2FldPerType(MboValue mbv)
            throws MXException, RemoteException {
        super(mbv);
    }

    public void initValue() throws MXException, RemoteException {
        Uteis.espera("--------- antes super.initValue");
        super.initValue();
        Uteis.espera("--------- após super.initValue");
        getMboValue("ID2PERTYPE").setValueNull();
        Uteis.espera("--------- após setValue ");
        return;
    }
}
