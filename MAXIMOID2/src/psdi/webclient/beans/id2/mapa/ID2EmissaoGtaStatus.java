// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   POChangeStatusBean.java

package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.app.po.virtual.POChangeStatusRemote;
import psdi.util.MXException;
import psdi.webclient.beans.common.ChangeStatusBean;
import psdi.webclient.system.controller.AppInstance;
import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.*;
import psdi.server.*;
import psdi.webclient.beans.po.*;

import java.util.*;
import java.io.*;
import java.rmi.*;

public class ID2EmissaoGtaStatus extends POChangeStatusBean
{

    public ID2EmissaoGtaStatus()
    {
    }

    public void Emitir()
        throws MXException, RemoteException
    {
		Hashtable reportParams = new Hashtable();
		java.lang.String[] attrs = getAttributes();
		WebClientEvent event = sessionContext.getCurrentEvent();

		if(getMbo().getString("STATUS").equals("EMITIDA"))
		{
			try
			{
				reportParams.put("ID2NUMGTA",(String)parent.getMbo().getString("ID2NUMGTA"));
				reportParams.put("ID2SERIE",(String)parent.getMbo().getString("ID2SERIE"));
				reportParams.put("ID2LOCUF",(String)parent.getMbo().getString("ID2LOCUF"));
				reportParams.put("relatorio","RelatorioGTA.jasper");
				app.put("relatorio", reportParams);
				//Utility.sendEvent(new WebClientEvent("spawnjasperreport", event.getTargetId(), reportParams, sessionContext));
			}
			catch(MXException mx)
			{
				mx.printStackTrace();
			}
			catch(RemoteException re)
			{
				re.printStackTrace();
			}
		}
		Utility.sendEvent(new WebClientEvent("dialogok", event.getTargetId(), event.getValue(), sessionContext));
    }
}
