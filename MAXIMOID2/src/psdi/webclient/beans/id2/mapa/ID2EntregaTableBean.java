package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.id2.mapa.Uteis;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;

public class ID2EntregaTableBean extends DataBean {

    public ID2EntregaTableBean() {
    }

    @Override
    public int addrow() throws MXException
    {
        int ret = -1;
        int row;

        DataBean mainrec = Utility.getDataBean(sessionContext, "MAINRECORD");
        row = parent.getCurrentRow();
      
        parent.save();
        //parent.getMboRowIndex(row);
        //parent.getMbo(row);
        ret = super.addrow();

        return ret;
    }

}