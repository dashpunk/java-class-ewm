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

public class ID2FldOrderBy extends MAXTableDomain
{

	public ID2FldOrderBy(MboValue mbv)
	{
		super(mbv);
		setRelationship("PERSON", "personid=:orderby");
		String target[] = {"orderby"};
		String source[] = {"personid"};
		setLookupKeyMapInOrder(target,source);
	}

	public void action()
            throws MXException,	java.rmi.RemoteException
	{
        MboRemote person = getMboSet().getMbo(0);
        try
        {
			System.out.println("Passo -1");
            if(person != null)
            {
                Mbo gta = getMboValue().getMbo();
				System.out.println("Passo 0");
				if(!person.getMboSet("location").isEmpty())
				{
					System.out.println("Passo 1");
					gta.setValue("location", person.getMboSet("location").getMbo(0).getString("location"), 11L);
					System.out.println("Passo 2 "+person.getMboSet("location").getMbo(0).getString("location"));
					gta.setValue("uetype", person.getMboSet("location").getMbo(0).getString("uetype"), 11L);
					System.out.println("Passo 3 "+person.getMboSet("location").getMbo(0).getString("uetype"));
					gta.setValue("uf", person.getMboSet("location").getMbo(0).getString("uf"), 11L);
					System.out.println("Passo 4 "+person.getMboSet("location").getMbo(0).getString("uf"));
				}
				System.out.println("Passo 5");
            }
			System.out.println("Passo 6");
        }
        catch(Exception exception) { }
		super.action();
	}
}