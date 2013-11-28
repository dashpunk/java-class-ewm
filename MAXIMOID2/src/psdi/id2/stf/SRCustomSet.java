// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package psdi.id2.stf;

import psdi.server.*;
import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.app.ticket.*;
import psdi.util.MXException;

// Referenced classes of package psdi.app.mr.virtual:
//            SRCustom

public class SRCustomSet extends SRSet
    implements SRCustomSetRemote
{

    public SRCustomSet(MboServerInterface mboserverinterface)
        throws MXException, RemoteException
    {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset)
        throws MXException, RemoteException
    {
        return new SRCustom(mboset);
    }

}
