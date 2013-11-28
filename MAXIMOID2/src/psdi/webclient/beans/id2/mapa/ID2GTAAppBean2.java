package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.app.location.LocationSetRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.*;
import psdi.webclient.system.controller.*;

public class ID2GTAAppBean2 extends psdi.webclient.beans.po.POAppBean
{

    public ID2GTAAppBean2()
    {
    }

	public int SAVE() throws MXException, RemoteException
	{
		
		if(getMbo().getString("STATUS").equals("CRIAR") || getMbo().getString("STATUS").equals("CRIADO"))
		{
			getMbo().setFieldFlag("STORELOC",psdi.mbo.MboConstants.READONLY,false);

			if(getMbo().getString("id2tipolocal").equals("02"))
				getMbo().setValue("STORELOC",getMbo().getString("ID2PROABA"));
			else if(getMbo().getString("id2tipolocal").equals("03"))
				getMbo().setValue("STORELOC",getMbo().getString("ID2PROAGL"));
			else if(getMbo().getString("id2tipolocal").equals("04"))
				getMbo().setValue("STORELOC",getMbo().getString("ID2PROEXP"));

			int i = 0;
			do
			{
				MboRemote linhaAnimal = getMbo().getMboSet("POLINE").getMbo(i);

				if(getMbo().getString("id2tipolocaldest").equals("02"))
					linhaAnimal.setValue("STORELOC",getMbo().getString("ID2DESABA"));
				else if(getMbo().getString("id2tipolocaldest").equals("03"))
					linhaAnimal.setValue("STORELOC",getMbo().getString("ID2DESAGL"));
				else if(getMbo().getString("id2tipolocaldest").equals("04"))
					linhaAnimal.setValue("STORELOC",getMbo().getString("ID2DESEXP"));

				if(linhaAnimal == null)
					break;
				i++;
			} while (true);
		}
		return super.SAVE();
	}

}