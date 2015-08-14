package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
/**
 * @author andrel.almeida
 */

public class ComentDocSaaDialog extends AppBean {
	
	public ComentDocSaaDialog(){
		System.out.println(">>>>>>>>> Dentro da classe: br.inf.ctis.ms.bean.ComentDocSaaDialog vesao 00");
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
