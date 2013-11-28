package br.inf.id2.me.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * 
 * @author Ricardo S Gomes
 *
 */
public class PintandoLiberdadeDialog extends AppBean {

    public PintandoLiberdadeDialog() {
        System.out.println("___ PintandoLiberdadeDialog");
    }

    @Override
    protected void initialize() throws MXException, RemoteException {
        super.initialize();
        System.out.println("___ PintandoLiberdadeDialog antes do INSERT");
        INSERT();
        System.out.println("___ PintandoLiberdadeDialog apos o INSERT");
    }

    @Override
    public synchronized int execute() throws MXException, RemoteException {
        System.out.println("___ PintandoLiberdadeDialog antes do save");
        getMbo().getThisMboSet().save();
        reloadTable();
        refreshTable();
        System.out.println("___ PintandoLiberdadeDialog apos save");
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));
        return super.execute();
    }
}
