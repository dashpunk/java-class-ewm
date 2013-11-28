// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:FldPartialGLAccount.java

package psdi.id2.mapa;

import psdi.app.pr.*;
import psdi.app.common.FldChangeStatus;
import psdi.app.rfq.virtual.*;
import psdi.app.rfq.*;

import psdi.mbo.*;
import psdi.server.MXServer;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

// Referenced classes of package psdi.app.financial:
//FinancialService

public class FldTeste extends MboValueAdapter
{

	private int count = 0;

	public FldTeste(MboValue mbv)
	{
		super(mbv);
	}

	public void validate()
		throws MXException, RemoteException
	{
		super.validate();
		count++;
		System.out.println("oi validate "+count);
	}

	public void initValue()
		throws MXException, RemoteException
	{
		super.initValue();
		count++;
		System.out.println("oi initValue "+count);
	}

	public void init()
		throws MXException, RemoteException
	{
		super.init();
		count++;
		System.out.println("oi init "+count);
	}

	public void action()
		throws MXException, RemoteException
	{
		super.action();
		count++;
		System.out.println("oi action "+count);
	}

	public boolean hasList()
	{
		count++;
		System.out.println("oi hasList "+count);
		return super.hasList();
	}

}