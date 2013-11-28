package psdi.webclient.beans.id2.mapa;

import java.io.PrintStream;
import java.rmi.RemoteException;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class ID2LocalizacoesTableBean extends DataBean
{

    public ID2LocalizacoesTableBean()
    {
    }

    public int selectrecord()
        throws MXException, RemoteException
    {
        int i = super.selectrecord();
        DataBean databean = app.getDataBean("results_showlist");
        MboSetRemote mbosetremote = databean.getMboSet();
        mbosetremote.reset();
        databean.refreshTable();
        return i;
    }

    public int getCurrentRow()
    {
        int i = super.getCurrentRow();
        try
        {
            DataBean databean = app.getDataBean("results_showlist");
            MboSetRemote mbosetremote = databean.getMboSet();
            mbosetremote.reset();
            databean.refreshTable();
        }
        catch(MXException mxexception)
        {
            System.out.println(mxexception.getMessage());
        }
        catch(RemoteException remoteexception)
        {
            System.out.println(remoteexception.getMessage());
        }
        return i;
    }
}
