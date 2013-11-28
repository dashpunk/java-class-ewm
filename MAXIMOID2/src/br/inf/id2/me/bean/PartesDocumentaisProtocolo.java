package br.inf.id2.me.bean;

import java.rmi.RemoteException;
import java.util.Iterator;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Dyogo
 */
public class PartesDocumentaisProtocolo extends DataBean {

	public PartesDocumentaisProtocolo() {
		super();
		System.out.println("#################### PartesDocumentaisProtocolo - Construtor");
	}

	public int adicionarPecas() throws MXException, RemoteException {
		System.out.println("########################## PartesDocumentaisProtocolo - AdicionarPecas");

		MboSet mboPecasDialog = (MboSet) app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01TERMO");
		DataBean termo;
		if (mboPecasDialog != null) {
			
			mboPecasDialog = (MboSet) app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01TERMO").getMbo(mboPecasDialog.count()-1).getMboSet("RL01PECASTERMO");
			MboSet assets;
			if (app.getDataBean("seljunt") == null)	{
				assets = (MboSet) app.getDataBean("selret").getMboSet();
				termo = app.getDataBean("dlgtret");
			} else {
				assets = (MboSet) app.getDataBean("seljunt").getMboSet();
				termo = app.getDataBean("dlgtjun");
			}
			Mbo mboPeca;
			if (mboPecasDialog != null) {
				if (assets.getSelection() != null) {
					Iterator itSelec = assets.getSelection().iterator();
					int i = 0;
					while (itSelec.hasNext()) {
						System.out.println("############### Entrou = " + ++i);
						MboRemote mboSelec = (MboRemote) itSelec.next();
						System.out.println("############### ASSETNUM = " + mboSelec.getString("ASSETNUM"));
						System.out.println("############### TBTERMOPROTID = " + termo.getMbo().getInt("TBID"));
						System.out.println("###############3 Tamanho MboPecas = " + mboPecasDialog.count());
						mboPeca = (Mbo) mboPecasDialog.add();
						mboPeca.setValue("TBTERMOPROTID", termo.getMbo().getInt("TBID"));
						mboPeca.setValue("ASSETNUM", mboSelec.getString("ASSETNUM"));
					}
				}
	
			}
		}
		System.out.println("################ Databean=" + app.getDataBean("1308753780039"));
		if (app.getDataBean("itensJuntada") == null) {
			app.getDataBean("itensRetirada").refreshTable();
	        app.getDataBean("itensRetirada").reloadTable();
		} else {
	        app.getDataBean("itensJuntada").refreshTable();
	        app.getDataBean("itensJuntada").reloadTable();
		}
        
		sessionContext.queueRefreshEvent();
		Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
		System.out.println("####### Fechando..." + app.getCurrentPageId());

        System.out.println("########## FIM");
		return 1;

	}
}
