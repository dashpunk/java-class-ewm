// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package psdi.id2.mapa;

import java.rmi.RemoteException;

import psdi.app.po.ID2POCustom;
import psdi.app.po.POSet;
import psdi.mbo.*;
import psdi.util.MXException;

// Referenced classes of package psdi.app.workorder:
//            WOSet, ID2POCustom, ID2POCustomSetRemote

public class ID2POCustomSet extends POSet
    implements ID2POCustomSetRemote
{

    public ID2POCustomSet(MboServerInterface mboserverinterface)
        throws MXException, RemoteException
    {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset)
        throws MXException, RemoteException
    {
        return new ID2POCustom(mboset);
    }
}
