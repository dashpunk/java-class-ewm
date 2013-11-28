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

public class ID2InvVendorTableBean extends DataBean
{

    public ID2InvVendorTableBean()
    {
		try
		{
			//DataBean fabrica = app.getDataBean("MAINRECORD");
			setAppWhere("vendor='"+parent.getMbo().getString("COMPANY")+"'");			
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

}
