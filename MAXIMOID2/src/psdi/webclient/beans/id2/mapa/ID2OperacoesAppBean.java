package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.app.location.LocationSetRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.*;
import psdi.webclient.system.controller.*;

public class ID2OperacoesAppBean extends psdi.webclient.beans.location.LocationAppBean
{

    public ID2OperacoesAppBean()
    {
    }

    public void initializeApp()
        throws MXException, RemoteException
    {
        DataBean resultsBean = app.getResultsBean();
        MboSetRemote set = resultsBean.getMboSet();
        MboRemote zombie = set.getZombie();
        zombie.setValue("type", "!OPERATING!", 11L);
        resultsBean.setQbe("type", zombie.getString("type"));
        resultsBean.reset();
        super.initializeApp();
    }

	public int INSERT() throws MXException, RemoteException
	{
		int ret = super.INSERT();
		getMbo().setValue("type","!OPERATING!");
		return ret;
	}

}