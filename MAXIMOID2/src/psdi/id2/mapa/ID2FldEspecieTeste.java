package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.server.MXServer;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

public class ID2FldEspecieTeste extends psdi.app.item.FldItemCommodityCode
{

	public ID2FldEspecieTeste(MboValue mbv) 
		throws MXException, java.rmi.RemoteException
	{
		super(mbv);
	}

	public void action()
            throws MXException,	java.rmi.RemoteException
	{
        //MboRemote especie = getMboValue().getMbo().getMboSet("ID2CLASSIFICACAO").getMbo(0);
        try
        {
            //if(especie != null)
            //{
				//getMboValue().getMbo().setValue("ITEMNUM",getMboValue().getString()+"."+(getMboValue().getMbo().getMboSet("ID2BROTHERS").count()+1));
				System.out.println("teste teste teste teste teste teste teste teste teste");
                //getMboValue().getMbo().setValue("CLASSSTRUCTUREID", "1002",11L);
				getMboValue().getMbo().setValue("CLASSSTRUCTURE.HIERARCHYPATH", "BOVINO",11L);
				//getMboValue().getMbo().getMboSet("ITEMSPECCLASS").save();
			//}
			super.action();
        }
        catch(Exception exception) { 		System.out.println(exception.getMessage()); }
	}
}