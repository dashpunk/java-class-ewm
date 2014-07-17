package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ComentDocPecDialog extends AppBean {
	
	public ComentDocPecDialog(){
		System.out.println(">>>>>>>>> Dentro da classe: br.inf.ctis.ms.bean.ComentDocPecDialog");
	}
	
	public void salvar() throws MXException, RemoteException {
		defineCampo();
	}
	
	private void defineCampo() throws MXException, RemoteException {
		
		System.out.println(">>>>>>>>> Dentro do metodo defineCampo");
		WebClientEvent event = sessionContext.getCurrentEvent();
		Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
		save();
		app.getDataBean("MAINRECORD").save();
		app.getDataBean("MAINRECORD").refreshTable();
		app.getDataBean("MAINRECORD").reloadTable();
	}

}
