// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package psdi.id2.mapa;

import psdi.app.location.LocationRemote;
import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

// Referenced classes of package psdi.app.workorder:
//            WORemote
public interface LocationCustomRemote
        extends LocationRemote {

    public void appValidate()
            throws MXException, RemoteException;

    public void save()
            throws MXException, java.rmi.RemoteException;

    public void add()
            throws MXException, java.rmi.RemoteException;
}
