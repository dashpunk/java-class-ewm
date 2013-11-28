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

public class ID2FldProperty extends MAXTableDomain
{

	public ID2FldProperty(MboValue mbv)
	{
		super(mbv);
		setRelationship("COMPANIES", "company=:property");
		String target[] = {"property"};
		String source[] = {"company"};
		setLookupKeyMapInOrder(target,source);
	}

	public void action()
            throws MXException,	java.rmi.RemoteException
	{
        MboRemote company = getMboSet().getMbo(0);
        try
        {
            if(company != null)
            {
                Mbo property = getMboValue().getMbo();
                property.setValue("property", company.getString("company"), 11L);
                property.setValue("uf", company.getString("uf"), 11L);
                property.setValue("code", company.getString("code"), 11L);
                property.setValue("addresscode", company.getString("addresscode"), 11L);
            }
        }
        catch(Exception exception) { }
	}
}