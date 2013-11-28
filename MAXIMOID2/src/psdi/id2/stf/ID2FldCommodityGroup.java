// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package psdi.id2.stf;

import java.rmi.RemoteException;
import psdi.app.ticket.*;
import psdi.mbo.*;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

// Referenced classes of package psdi.app.common.purchasing:
//            PurchasingMbo

public class ID2FldCommodityGroup extends FldTkCommodityGroup
{

    public ID2FldCommodityGroup(MboValue mbovalue)
        throws MXException, RemoteException
    {
        super(mbovalue);
    }

    public void action()
        throws MXException, RemoteException
    {
		super.action();
		getMboValue().getMbo().setValue("OWNERGROUP",getMboValue().getString());
    }

}