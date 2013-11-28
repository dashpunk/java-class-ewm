// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServReqsAppBean.java

package psdi.webclient.beans.id2.me;

import java.rmi.RemoteException;
import java.util.*;
import psdi.app.person.PersonRemote;
import psdi.app.person.PersonSetRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.util.MXException;
import psdi.util.MXSession;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;

public class ID2SelfServiceAppBean extends AppBean
{

    public ID2SelfServiceAppBean()
    {
    }

    protected void initialize()
        throws MXException, RemoteException
    {
        super.initialize();
        insert();
    }

    public synchronized void saverecord()
        throws MXException, RemoteException
    {
        if(mboSetRemote == null)
            return;
        MboRemote newMbo;
        newMbo = null;
        if(isNewRow())
        {
            try
            {
                long uniqueId = getUniqueIdValue();
                mboSetRemote.save();
                newMbo = getMboForUniqueId(uniqueId);
            }
            catch(NullPointerException e) { }
        } else
        {
            autoInitiateWorkflow();
            if(app.get("wfinitiated") != null && app.get("wfinitiated").equals("false"))
            {
                fireStructureChangedEvent();
            }
            return;
        }
        try
        {
            if(newMbo == null)
            {
                currentRecordData = null;
                currentRow = -1;
            } else
            {
                currentRecordData = newMbo.getMboData(getAttributes());
                autoInitiateWorkflow();
            }
            if(app.get("wfinitiated") != null && app.get("wfinitiated").equals("false"))
            {
                fireStructureChangedEvent();
            }
        }
        catch(RemoteException e)
        {
            handleRemoteException(e);
        }
        return;
    }
}