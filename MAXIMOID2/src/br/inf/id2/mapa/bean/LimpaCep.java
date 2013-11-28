package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class LimpaCep extends AppBean {

    public LimpaCep() {
        super();
    }

    @Override
    protected void initialize() throws MXException, RemoteException {
        app.getDataBean("MAINRECORD").getMbo().setValueNull("ID2ADDUF");
        super.initialize();
    }

    @Override
    public void initializeApp() throws MXException, RemoteException {
        app.getDataBean("MAINRECORD").getMbo().setValueNull("ID2ADDUF");
        super.initializeApp();
    }
}
