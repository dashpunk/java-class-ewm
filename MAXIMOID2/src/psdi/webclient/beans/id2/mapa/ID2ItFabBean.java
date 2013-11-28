// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ID2InvVendorTableBean.java

package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.ResultsBean;
import psdi.webclient.system.beans.*;
import psdi.webclient.system.controller.Utility;

public class ID2ItFabBean extends DataBean
{

    public ID2ItFabBean()
    {
    }

	public void initialize()
        throws MXException, RemoteException
    {
        super.initialize();
		try
		{
			DataBean fabrica = app.getDataBean("MAINRECORD");
			if(fabrica != null)
			{
				setAppWhere("vendor = '"+fabrica.getMbo().getString("COMPANY")+"'");
				setUserWhere("vendor = '"+fabrica.getMbo().getString("COMPANY")+"'");
			}
		}
		catch (MXException mx)
		{
			System.out.println(mx.getMessage());
		}
		catch (RemoteException re)
		{
			System.out.println(re.getMessage());
		}
	}

	public int addrow()
           throws MXException
	{
		int ret = -1;
		try
		{
			ret = super.addrow();
		}
		catch (Exception e)
		{
			System.out.println("erro "+e.getMessage());
		}
		return ret;
	}

}
