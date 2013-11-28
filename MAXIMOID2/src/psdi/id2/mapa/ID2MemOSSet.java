// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package psdi.id2.mapa;

import psdi.server.*;
import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

// Referenced classes of package psdi.app.mr.virtual:
//            InvCC

public class ID2MemOSSet extends MboSet
    implements ID2MemOSSetRemote
{

    public ID2MemOSSet(MboServerInterface mboserverinterface)
        throws MXException, RemoteException
    {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset)
        throws MXException, RemoteException
    {
        return new ID2MemOS(mboset);
    }
}
