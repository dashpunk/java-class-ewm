package psdi.webclient.beans.id2.mapa;

import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;
import psdi.mbo.*;

import java.util.*;
import java.rmi.*;

public class ID2DuplicarAppBean extends DataBean {

    public ID2DuplicarAppBean() {
    }

    public void duplicar() throws MXException, RemoteException {

        WebClientEvent event = sessionContext.getCurrentEvent();
        Mbo cloneMbo = (Mbo)parent.getMbo().duplicate();
        //cloneMbo.getThisMboSet().getMbo(0);
        Utility.sendEvent(new WebClientEvent("dialogok", event.getTargetId(), event.getValue(), sessionContext));
        
    }
}
