// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:FldPartialGLAccount.java

package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.server.MXServer;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

public class ID2FldNovaClasse extends MboValueAdapter
{

	public ID2FldNovaClasse(MboValue mbv)
	{
		super(mbv);
	}

	public void action()
            throws MXException, java.rmi.RemoteException
	{
		super.action();
		if(getMboValue().getBoolean()==true)
		{
			getMboValue().getMbo().setFieldFlag("ID2MARCA",READONLY,false);
		}
		else
		{
			getMboValue().getMbo().setValue("ID2MARCA",false);
			getMboValue().getMbo().setFieldFlag("ID2MARCA",READONLY,true);
		}
	}
	
	public void initValue()
               throws MXException, java.rmi.RemoteException
	{
		if(getMboValue().getBoolean()==true)
		{
			getMboValue().getMbo().setFieldFlag("ID2MARCA",READONLY,false);
		}
		else
		{
			getMboValue().getMbo().setFieldFlag("ID2MARCA",READONLY,true);
		}
	}
}