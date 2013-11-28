// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package psdi.id2.stf;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

// Referenced classes of package psdi.app.common.purchasing:
//            PurchasingMbo

public class ID2FldTPVaga extends MboValueAdapter
{

	public ID2FldTPVaga(MboValue mbovalue)
    {
        super(mbovalue);
    }

    public void action()
        throws MXException, RemoteException
    {
		super.action();
		getMboValue("TARGETFINISH").setFlag(REQUIRED,false);
		getMboValue("TARGETFINISH").setFlag(READONLY,false);
		if(getMboValue().getString().equals("PERMANENTE"))
		{
			getMboValue("TARGETFINISH").setValueNull();
		}
		getMboValue("TARGETFINISH").setFlag(getMboValue().getString().equals("PERMANENTE")?READONLY:REQUIRED,true);
    }

	public void initValue()
		throws MXException, java.rmi.RemoteException
	{
		getMboValue("TARGETFINISH").setFlag(REQUIRED,false);
		getMboValue("TARGETFINISH").setFlag(READONLY,false);
		getMboValue("TARGETFINISH").setFlag(getMboValue().getString().equals("PERMANENTE")?READONLY:REQUIRED,true);
		super.validate();
	}
}