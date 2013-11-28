package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.WebClientEvent;

public class MsConsulTr extends AppBean {

	/**
	 * @author Marcelo
	 */
	public MsConsulTr() {
	}
	
	public void gototr() throws RemoteException, MXException {
		
		int nrCurrent = getMboSet().getCurrentPosition();
		System.out.println("##############getMboSet().getCurrentPosition(): " + getMboSet().getCurrentPosition());
		System.out.println("##############nrCurrent: " + nrCurrent);
		
		int tabelaId = getMboSet().getMbo(nrCurrent).getInt("POID");
		System.out.println("#############POID: " + getMboSet().getMbo(nrCurrent).getInt("POID"));
		System.out.println("#############tabelaId: " + tabelaId);
		
		WebClientEvent newEvent = new WebClientEvent("changeapp",this.app.getId(), "MS_CLPO01", null, null, null, tabelaId, this.clientSession);
	    System.out.println("############# CHEGOU NO NEWEVENT!");
		
	    newEvent.setSourceControl(this.app);
	    System.out.println("############# PASSOU NO NEWEVENT!");
	    
		this.clientSession.queueEvent(newEvent);

	}

}
