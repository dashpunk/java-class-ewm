// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PRLineBean.java

package br.inf.id2.ms.bean;

import psdi.server.*;
import java.rmi.RemoteException;
import psdi.app.po.PORemote;
import psdi.app.pr.PRServiceRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.util.MXSession;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.session.WebClientSession;

public class ID2POPRLine extends DataBean
{

    public ID2POPRLine()
    {
    }

    public void duplicarLinha()
        throws MXException, RemoteException
    {
    	long uid = getMbo().getUniqueIDValue();
    	
    	try
    	{
    		parent.save();
    	}
    	finally
    	{
        	MboSetRemote linhasPR = MXServer.getMXServer().getMboSet("PRLINE", getMbo().getUserInfo());
        	linhasPR.setWhere("PRNUM='"+getMbo().getString("PRNUM")+"' and prlinenum='"+getMbo().getString("PRLINENUM")+"'");
        	linhasPR.reset();
        	linhasPR.copy(parent.getMbo().getMboSet("PRLINE"));    		
    	}    	
    }

}
