// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FldTKLogSolicitante.java

package psdi.id2.stf;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

public class ID2FldOCUPLOG extends MAXTableDomain
{

    public ID2FldOCUPLOG(MboValue mbovalue)
    {
        super(mbovalue);
        setRelationship("VW_SOLICITANTES", "logsolicitante=:ID2OCUPLOG");
        String target[] = {"ID2OCUPLOG"};
        String source[] = {"logsolicitante"};
        setLookupKeyMapInOrder(target, source);
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
                String s2 = mboremote.getString("emasolicitante");
                String s4 = mboremote.getString("nomsolicitante");
                String s5 = mboremote.getString("ramsolicitante");
                mbo.setValue("ID2OCUPLOG", s, 11L);
                //mbo.setValue("carsolicitante", s1, 11L);
                mbo.setValue("ID2OCUPEMAIL", s2, 11L);
                //mbo.setValue("lotsolicitante", s3, 11L);
                mbo.setValue("ID2OCUPNAME", s4, 11L);
                mbo.setValue("ID2OCUPRAMAL", s5, 11L);
            }
        }
        catch(Exception exception) { }
    }
}
