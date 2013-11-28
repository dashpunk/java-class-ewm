package br.inf.id2.common.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;


/**
 * @author Patrick
 */
public class AutoAtendimentoNovoRegistro extends psdi.webclient.system.beans.AppBean {

    public AutoAtendimentoNovoRegistro() {
    	System.out.println("*** AutoAtendimentoNovoRegistro ***");
    }

    @Override
    protected void initialize() throws MXException, RemoteException {
    	super.initialize();
    	System.out.println("*** antes INSERT");
    	INSERT();
    	System.out.println("*** antes INSERT");
    }
    
}
