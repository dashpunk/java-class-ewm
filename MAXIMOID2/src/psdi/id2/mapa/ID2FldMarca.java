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

public class ID2FldMarca extends MboValueAdapter
{

	public ID2FldMarca(MboValue mbv)
	{
		super(mbv);
	}

	public void action()
            throws MXException, java.rmi.RemoteException
	{
		super.action();
		Mbo marca;
		if(getMboValue().getBoolean()==true)
		{
			marca = (Mbo)getMboValue().getMbo().getMboSet("ID2MARCA").add();
			marca.setFieldFlag("m1",REQUIRED,true);
		}
		else
		{
			getMboValue().getMbo().getMboSet("ID2MARCA").deleteAll();
		}
	}
}