// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FldPosGTA.java

package psdi.id2.mapa;

import java.rmi.RemoteException;

import psdi.app.location.FldLocLocation;
import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.util.MXException;

// Referenced classes of package psdi.app.location:
//            FldLocLocation

public class FldPosGTA extends FldLocLocation
{

    public FldPosGTA(MboValue mbovalue)
        throws MXException
    {
        super(mbovalue);
    }

    public void validate()
        throws MXException, RemoteException
    {
		/*
        if(getMboValue().getMbo().getString("TYPE").equals("ALMOXARIFADO"))
            getMboValue().setValue(getMboValue().getMbo().getString("APPTYPE") + getMboValue().getString(), 2L);
        else
        if(getMboValue().getMbo().getString("TYPE").equals("UNIDADE") && getMboValue().getMbo().getString("UETYPE").equals("UC"))
            getMboValue().setValue(getMboValue().getMbo().getString("UF") + getMboValue().getString(), 2L);
		*/
		//getMboValue().setValue("AGL" + getMboValue().getString(), 2L);
        super.validate();
    }
}
