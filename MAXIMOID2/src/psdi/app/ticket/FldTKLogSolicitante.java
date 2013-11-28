// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FldTKLogSolicitante.java

package psdi.app.ticket;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

public class FldTKLogSolicitante extends MAXTableDomain
{

    public FldTKLogSolicitante(MboValue mbovalue)
    {
        super(mbovalue);
        setRelationship("VW_SOLICITANTES", "logsolicitante=:logsolicitante");
        String as[] = {
            "logsolicitante"
        };
        String as1[] = {
            "logsolicitante"
        };
        setLookupKeyMapInOrder(as, as1);
    }

    public void action()
        throws MXException, RemoteException
    {
        super.action();
        MboRemote mboremote = getMboSet().getMbo(0);
        try
        {
            if(mboremote != null)
            {
                Mbo mbo = getMboValue().getMbo();
                String s = mboremote.getString("logsolicitante");
                String s1 = mboremote.getString("carsolicitante");
                String s2 = mboremote.getString("emasolicitante");
                String s3 = mboremote.getString("lotsolicitante");
                String s4 = mboremote.getString("nomsolicitante");
                String s5 = mboremote.getString("ramsolicitante");
                mbo.setValue("logsolicitante", s, 11L);
                mbo.setValue("carsolicitante", s1, 11L);
                mbo.setValue("emasolicitante", s2, 11L);
                mbo.setValue("lotsolicitante", s3, 11L);
                mbo.setValue("nomsolicitante", s4, 11L);
                mbo.setValue("ramsolicitante", s5, 11L);
            }
        }
        catch(Exception exception) { }
    }
}
