
/**
 *
 * @author jesse.rovira
 *
 * antes extendia psdi.mbo.custapp.CustomMboSet
 */
package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.*;

public class ID2StoCommSet extends MboSet
	implements ID2StoCommSetRemote
{
    public ID2StoCommSet(MboServerInterface mboserverinterface)
        throws MXException, RemoteException
    {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset)
        throws MXException, RemoteException
    {
        return new ID2StoComm(mboset);
    }
}
