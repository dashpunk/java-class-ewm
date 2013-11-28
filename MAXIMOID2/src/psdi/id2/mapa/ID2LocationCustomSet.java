package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.app.location.LocationSet;
import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class ID2LocationCustomSet extends LocationSet implements ID2LocationCustomSetRemote {

    public ID2LocationCustomSet(MboServerInterface mboserverinterface) throws MXException, RemoteException {
        super(mboserverinterface);
        setOrderBy("DESCRIPTION");
    }
}
