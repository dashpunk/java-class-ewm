package psdi.id2.seedf;

import java.rmi.*;
import psdi.mbo.*;
import psdi.util.MXException;

// Referenced classes of package psdi.app.workorder:
//            WOSet, LocationCustomy, LocationCustomSetRemote
public class ID2ALNValueSet extends psdi.app.system.ALNValueSet
        implements ID2ALNValueSetRemote {

    public ID2ALNValueSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {

        super(mboserverinterface);
        //System.out.println("----------------- alndomain antes");
        setOrderBy("DESCRIPTION");
        //System.out.println("----------------- alndomain depois");
    }
}
