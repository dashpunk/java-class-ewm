// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package psdi.id2.mapa;

import java.rmi.*;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import psdi.app.system.ALNValue;
import psdi.app.system.ALNValueSet;
import psdi.mbo.*;
import psdi.txn.MXTransaction;
import psdi.util.MXException;

// Referenced classes of package psdi.app.workorder:
//            WOSet, LocationCustomy, LocationCustomSetRemote
public class ID2ALNValueSet extends ALNValueSet
        implements ID2ALNValueSetRemote {

    public ID2ALNValueSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {

        super(mboserverinterface);
       setOrderBy("DESCRIPTION");

    }

}
