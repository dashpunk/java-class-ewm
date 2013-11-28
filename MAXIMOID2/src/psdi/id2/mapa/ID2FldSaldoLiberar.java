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

public class ID2FldSaldoLiberar extends MboValueAdapter
{

	public ID2FldSaldoLiberar(MboValue mbv)
	{
		super(mbv);
	}

	public void initValue()
               throws MXException, java.rmi.RemoteException
	{
		double valor = getMboValue().getMbo().getMboSet("ID2PARCELAS").sum("ID2VALOR");
		if(valor > 0)
		{
			getMboValue().setValue(getMboValue().getMbo().getDouble("WOEQ2")-getMboValue().getMbo().getDouble("WOEQ3")-valor);
		}
	}
}