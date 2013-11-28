package br.inf.id2.mapa.bean;

import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * 
 * @author Ricardo S Gomes 53269192930020120121100002808
 */
public class Id2po07DialogConfirmar extends DataBean {

	public Id2po07DialogConfirmar() {
		super();
	}
	
	public void confirmar() {
		System.out.println("-----> redirect xxxxx");

		Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));

		String aplicacao = "ID2PO05";

    	WebClientEvent newEvent = new WebClientEvent("changeapp", this.app.getId(), aplicacao, null, "insert", null,0, this.clientSession);

		newEvent.setSourceControl(this.app);
		
		this.clientSession.queueEvent(newEvent);
}
	
	
}
