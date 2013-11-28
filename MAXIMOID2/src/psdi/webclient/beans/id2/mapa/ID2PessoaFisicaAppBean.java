package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.app.location.LocationSetRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.*;
import java.util.Date;
import psdi.mbo.MboSet;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.*;
import psdi.server.*;
import psdi.id2.mapa.Uteis;

public class ID2PessoaFisicaAppBean extends psdi.webclient.beans.common.StatefulAppBean {

    /**
     * M?todo construtor de ID2PessoaFisicaAppBean
     */
    public ID2PessoaFisicaAppBean() {
    }

    @Override
    public synchronized void insert() throws MXException, RemoteException {
        Uteis.espera("------------------------------------add");
        WebClientEvent event = sessionContext.getCurrentEvent();
        Uteis.espera("------------------------------------T1");
        Utility.sendEvent(new WebClientEvent("addrow", (String) event.getValue(), event.getValue(), sessionContext));
        Uteis.espera("------------------------------------fim add");
        super.insert();
    }

}

