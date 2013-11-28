package br.inf.id2.me.bean;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Patrick
 */
public class AdicionarPesquisadorBean extends AppBean {

	public AdicionarPesquisadorBean() {
		super();
	}

	public int adicionar() throws MXException, RemoteException {
		System.out.println("*** AdicionarPesquisadorBean ***");
		super.validate();
		int ret = -1;

		MboSet tbPesqSet = (MboSet) app.getDataBean("MAINRECORD").getMbo().getMboSet("MERLINTPESQ01");
		Mbo tbPesq;

		getMbo().setValue("MEADDPESQID", 1);
		
		tbPesq = (Mbo) tbPesqSet.add();
		tbPesq.setValue("MEPESQID", app.getDataBean("MAINRECORD").getMbo().getInt("METBPESQID"));
		tbPesq.setValue("MEGRPEST", getMbo().getString("MEGRPEST"));		
		System.out.println("*** MEGRPEST "+ getMbo().getString("MEGRPEST"));
		tbPesq.setValue("PERSONID", getMbo().getString("PERSONID"));
		System.out.println("*** PERSONID "+ getMbo().getString("PERSONID"));
		tbPesq.setValue("METIPINT", getMbo().getString("METIPINT"));
		System.out.println("*** METIPINT "+ getMbo().getString("METIPINT"));
		
		tbPesq.setValue("HASLD", 0);

		app.getDataBean("MAINRECORD").refreshTable();
		app.getDataBean("MAINRECORD").reloadTable();

		WebClientEvent event = sessionContext.getCurrentEvent();
		Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
		
		return ret;
	}
}
