package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;

import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class LaudoVistoria extends AppBean
{

    public LaudoVistoria()
    {
    }

    protected void initialize()
        throws MXException, RemoteException
    {
        String nome = (new StringBuilder()).append(getMbo().getUserName()).append("-").append(sessionContext.getUserInfo().getUserName()).toString();
        super.initialize();
        throw new MXApplicationException("id2", nome);
    }
}
