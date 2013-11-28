// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package psdi.id2.stf;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.app.common.*;

// Referenced classes of package psdi.app.common.purchasing:
//            PurchasingMbo

public class ID2FldSRStatus extends FldChangeStatus
{

	private boolean EMATEND;

	public ID2FldSRStatus(MboValue mbovalue)
		throws MXException, java.rmi.RemoteException    
	{
        super(mbovalue);
    }

    public void action()
        throws MXException, RemoteException
    {
		super.action();
		if(EMATEND || getMboValue().getString().equals("EMATEND"))
			getMboValue("MEMO").setFlag(REQUIRED,true);
    }

	public void initValue()
		throws MXException, java.rmi.RemoteException
	{
		EMATEND =  getMboValue().getMbo().getOwner().getString("STATUS").equals("EMATEND");
		getMboValue("MEMO").setFlag(REQUIRED,EMATEND==true);
		super.validate();
	}
}