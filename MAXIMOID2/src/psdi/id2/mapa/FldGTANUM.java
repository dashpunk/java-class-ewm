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

// Referenced classes of package psdi.app.financial:
//FinancialService

public class FldGTANUM extends MboValueAdapter
{

	public FldGTANUM(MboValue mbv)
	{
		super(mbv);
	}

	public void validate()
		throws MXException, RemoteException
	{
		String param[] = {new String()};

		super.validate();
		if(getMboValue().getString().length()==6)
			return;
		else
			throw new MXApplicationException("po", "GTANUMInvalido", param);
	}
	
}