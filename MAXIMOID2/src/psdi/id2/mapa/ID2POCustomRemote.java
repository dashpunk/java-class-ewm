// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package psdi.id2.mapa;

import psdi.app.po.PORemote;
import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

// Referenced classes of package psdi.app.workorder:
//            WORemote

public interface ID2POCustomRemote
    extends PORemote
{
	public void save()
          throws MXException, java.rmi.RemoteException;
}
