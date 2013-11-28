package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class ArquivosSa extends psdi.webclient.beans.desktopreq.DesktopReqAppBean {

    public ArquivosSa() {
        System.out.println("---ArquivosSa 1");
    }


    @Override
    public int SAVE() throws RemoteException, MXException {
        return super.SAVE();
    }

}
