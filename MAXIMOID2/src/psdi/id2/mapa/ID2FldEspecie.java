package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.server.MXServer;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

public class ID2FldEspecie extends psdi.app.item.FldItemCommodityCode
{

	public ID2FldEspecie(MboValue mbv) 
		throws MXException, java.rmi.RemoteException
	{
		super(mbv);
	}

	public void action()
            throws MXException,	java.rmi.RemoteException
	{
		super.action();
        MboRemote especie = getMboValue().getMbo().getMboSet("ID2CLASSIFICACAO").getMbo(0);
        try
        {
            if(especie != null)
            {
				getMboValue().getMbo().setValue("ITEMNUM",getMboValue().getString()+"."+(getMboValue().getMbo().getMboSet("ID2BROTHERS").count()+1));
                //getMboValue().getMbo().setValue("CLASSSTRUCTUREID", especie.getMboSet("CLASSSTRUCTURE").getMbo(0).getString("CLASSSTRUCTUREID"));
				getMboValue().getMbo().setValue("CLASSSTRUCTURE.HIERARCHYPATH", especie.getString("CLASSIFICATIONID"));
				getMboValue().getMbo().getMboSet("ITEMSPECCLASS").moveLast();
            }
        }
        catch(Exception exception) { 		System.out.println(exception.getMessage()); }
	}
}