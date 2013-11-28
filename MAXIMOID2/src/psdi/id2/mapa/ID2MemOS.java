// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;
import psdi.app.inventory.*;

public class ID2MemOS extends Mbo
        implements ID2MemOSRemote {

    public ID2MemOS(MboSet mboset)
            throws MXException, RemoteException {
        super(mboset);
    }

    public void add() throws
            MXException, java.rmi.RemoteException {
        super.add();
        Inventory inv = (Inventory) getOwner();
        setValue("ITEMNUM", inv.getString("ITEMNUM"), 11L);
        setValue("LOCATION", inv.getString("LOCATION"), 11L);
    }
}
