// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ID2AddUserSet.java

package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.server.MXServer;
import psdi.server.MXServerInfo;
import psdi.util.*;

// Referenced classes of package psdi.app.signature.virtual:
//            ID2AddUser

public class ID2AddUserSet extends NonPersistentMboSet
    implements NonPersistentMboSetRemote
{

    public ID2AddUserSet(MboServerInterface ms)
        throws MXException, RemoteException
    {
        super(ms);
    }

    protected Mbo getMboInstance(MboSet ms)
        throws MXException, RemoteException
    {
        return new ID2AddUser(ms);
    }

    public void canAdd()
        throws MXException
    {
        String app = null;
        try
        {
            app = getApp();
        }
        catch(Exception e) { }
        try
        {
            if(MXServer.getMXServer().getMXServerInfo().useAppServerSecurity())
            {
                if(app != null && app.equals("SELFREG"))
                    throw new MXAccessException("access", "mxauthenabled");
                if(MXServer.getMXServer().getProperty("mxe.LDAPUserMgmt").equals("1"))
                    throw new MXAccessException("access", "mxauthenabled");
            }
        }
        catch(Exception e)
        {
            throw new MXAccessException("access", "mxauthenabled");
        }
    }


    protected void addMbo(Mbo mbo) {
        super.addMbo(mbo);
    }


    public void execute()
        throws MXException, RemoteException
    {
        MboRemote mbo = getMbo(0);
        String valOrder[] = ((ID2AddUser)mbo).getValidateOrder();
        int i;
        for(i = 0; i < valOrder.length; i++)
        {
            MboValueInfo mvi = getMboSetInfo().getMboValueInfo(valOrder[i]);
            if(mvi == null)
                continue;
            MboValue mbv = ((Mbo)mbo).getMboValue(valOrder[i]);
            if((mbv == null || mbv.isNull()) && (mvi.isRequired() || mbv.isFlagSet(128L)))
            {
                Object params[] = {
                    mvi.getTitle()
                };
                throw new MXApplicationException("system", "null", params);
            }
        }

        validate();
        i = 0;
        for(mbo = getMbo(i); mbo != null; mbo = getMbo(i))
        {
            if(!mbo.toBeDeleted())
                ((ID2AddUser)mbo).setRelatedValues();
            i++;
        }

        save();
    }

    static final long serialVersionUID = 0xed8547d761a0bbb2L;
}
