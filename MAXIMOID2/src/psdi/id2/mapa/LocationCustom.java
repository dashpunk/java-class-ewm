// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package psdi.id2.mapa;

import java.rmi.RemoteException;

import psdi.app.location.Location;
import psdi.mbo.*;
import psdi.util.MXException;

// Referenced classes of package psdi.app.workorder:
//            Mbo, LocationCustomRemote
public class LocationCustom extends Location
        implements LocationCustomRemote {

    public LocationCustom(MboSet mboset)
            throws MXException, RemoteException {
        super(mboset);
    }

    public void appValidate()
            throws MXException, RemoteException {
        setValue("LOCATION", "AGL" + getString("LOCATION"));
        setValue("DESCRIPTION", "EXECUTEI");
        if (getString("TYPE").equals("ALMOXARIFADO")) {
            setValue("LOCATION", getString("TYPE") + getString("LOCATION"), 2L);
        } else {
            if (getString("TYPE").equals("OPERAÇÃO") && getString("UETYPE").equals("UC")) {
                setValue("LOCATION", getString("UF") + getString("LOCATION"), 2L);
            }
        }
        if (getString("UETYPE").equals("UC")) {
            setValue("PARENT", "UE", 2L);
        }
    }

    public void save()
            throws MXException, java.rmi.RemoteException {
        if (getString("UETYPE").equals("UC")) {
            setValue("PARENT", "UE", 2L);
        }
        super.save();
    }

    public void add()
            throws MXException, java.rmi.RemoteException {
        System.out.println("ssksksksksksksksksksksksks");
        System.out.println(getString("TYPE"));
        System.out.println(getString("UETYPE"));
        if (getString("TYPE").equals("ALMOXARIFADO")) {
            setValue("LOCATION", getString("TYPE") + getString("LOCATION"), 2L);
        } else {
            if (getString("TYPE").equals("OPERA??O") && getString("UETYPE").equals("UC")) {
                setValue("LOCATION", getString("UF") + getString("LOCATION"), 2L);
            }
        }
    }
}
