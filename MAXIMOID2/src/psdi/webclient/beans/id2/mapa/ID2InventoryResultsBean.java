package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

public class ID2InventoryResultsBean extends DataBean
{

    public ID2InventoryResultsBean() {
    	System.out.println("########### CONSTRUTOR ID2InventoryResultsBean");
    }

    public MboSetRemote getMboSet()
        throws MXException, RemoteException {
    	System.out.println("############ Entrou no ID2InventoryResultsBean");
        DataBean databean = app.getDataBean("locationslines");
        if(databean.getMbo() != null) {
            mboSetRemote.setRelationship("location='" + databean.getMbo().getString("LOCATION") + "'");
            mboSetRemote.reset();
        } else if(mboSetRemote != null) {
            mboSetRemote.setRelationship("location is null'");
            mboSetRemote.reset();
        }
        return mboSetRemote;
    }

    public void refreshTable() {
        try {
        	System.out.println("########## RefreshTable");
            MboSetRemote mbosetremote = getMboSet();
            super.refreshTable();
        } catch(MXException mxexception) {
            System.out.println(mxexception.getMessage());
        } catch(RemoteException remoteexception) {
            System.out.println(remoteexception.getMessage());
        }
    }
}