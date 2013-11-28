package psdi.id2.seedf;


import psdi.mbo.*;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

public class ID2FldEmail extends MboValueAdapter
{

	public ID2FldEmail(MboValue mbv)
		throws MXException, RemoteException
	{
		super(mbv);
	}
	
	public void validate()
		throws MXException, RemoteException
	{
		String param[] = {new String()};
		super.validate();
		if(getMboValue().getString().indexOf("@")>-1)
		{
			return;
		}
		else
			throw new MXApplicationException("company", "EmailInvalido", param);
	}
}