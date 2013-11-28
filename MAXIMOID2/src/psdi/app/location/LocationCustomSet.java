// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package psdi.app.location;

import java.rmi.RemoteException;

import psdi.id2.mapa.LocationCustom;
import psdi.mbo.*;
import psdi.util.MXException;

// Referenced classes of package psdi.app.workorder:
//            WOSet, LocationCustom, LocationCustomSetRemote

public class LocationCustomSet extends LocationSet
    implements LocationCustomSetRemote
{

    public LocationCustomSet(MboServerInterface mboserverinterface)
        throws MXException, RemoteException
    {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset)
        throws MXException, RemoteException
    {
        return new LocationCustom(mboset);
    }
}
