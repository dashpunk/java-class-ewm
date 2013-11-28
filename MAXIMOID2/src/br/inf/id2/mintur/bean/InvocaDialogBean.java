package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Patrick L. Silva
 */
public class InvocaDialogBean extends AppBean {

//	public synchronized boolean fetchRecordData() throws MXException, RemoteException {
//		System.out.println("*** InvocaDialogBean ***");
//		System.out.println("*** fetchRecordData");
//
//		RELPRG();
//		return super.fetchRecordData();
//	}
	
	@Override
	protected void initialize() throws MXException, RemoteException {
		System.out.println("*** InvocaDialogBean ***");
		System.out.println("*** initialize");
		
		RELPRG();
		super.initialize();
	}

	public void RELPRG() throws MXException, RemoteException {
		System.out.println("*** RELPRG");
		
		System.out.println("*** antes");
		this.clientSession.loadDialog("RELPRG");
		WebClientEvent event = sessionContext.getCurrentEvent();
		Utility.sendEvent(new WebClientEvent("RELPRG", event.getTargetId(), event.getValue(), sessionContext));
		System.out.println("*** depois");
		
//		return EVENT_HANDLED;
	}

}