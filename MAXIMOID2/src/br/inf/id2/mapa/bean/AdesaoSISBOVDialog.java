package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class AdesaoSISBOVDialog extends AppBean {

	
	
	public void confirmaAdesaoSisbov() throws MXException, RemoteException {
		defineCampo("MAADESAO", true);
	}
	
	public void cancelaAdesaoSisbov() throws MXException, RemoteException {

		defineCampo("MAADESAO", false);
	}
	
	public void confirmaAnimaisId() throws MXException, RemoteException {
		defineCampo("MAIDENT", true);
	}

	public void cancelaAnimaisId() throws MXException, RemoteException {
		defineCampo("MAIDENT", false);
	}

		
	private void defineCampo(String campo, boolean bol) throws MXException, RemoteException {

		System.out.println("################ adesaoSisbov()");
		app.getDataBean("MAINRECORD").getMbo().setValue(campo, bol, MboConstants.NOACCESSCHECK);

		WebClientEvent event = sessionContext.getCurrentEvent();
		Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
		
		app.getDataBean("MAINRECORD").save();
		app.getDataBean("MAINRECORD").refreshTable();
		app.getDataBean("MAINRECORD").reloadTable();
	}


}