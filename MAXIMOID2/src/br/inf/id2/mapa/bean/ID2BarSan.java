package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2BarSan extends AppBean {

    public ID2BarSan() {
        super();
    }

    @Override
    public void initializeApp() throws MXException, RemoteException {
        super.initializeApp();
        //System.out.println("--- a");
        addrow();
        //System.out.println("--- b");
        getMboSet().add();
        //System.out.println("--- c");
    }
}
