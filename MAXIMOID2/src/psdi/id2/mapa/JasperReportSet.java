// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;
import psdi.app.financial.*;
import psdi.server.*;

// Referenced classes of package psdi.app.mr.virtual:
//            JasperReport

public class JasperReportSet extends NonPersistentMboSet
    implements JasperReportSetRemote
{

    public JasperReportSet(MboServerInterface mboserverinterface)
        throws MXException, RemoteException
    {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset)
        throws MXException, RemoteException
    {
        return new JasperReport(mboset);
    }

}
