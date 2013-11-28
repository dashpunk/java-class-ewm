package br.inf.id2.me.bean;

import java.rmi.RemoteException;

import psdi.app.asset.AssetSetRemote;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Dyogo
 */
public class TermoDeRetirada extends DataBean {

	public TermoDeRetirada() {
		super();
		System.out.println("#################### TermoDeRetirada - Construtor");
	}

	public int realizarOperacao() throws MXException, RemoteException {
		System.out.println("########################## TermoDeRetirada - AdicionarPecas");

		
		MboSet setTermo = (MboSet) getMboSet();
		MboSet setPecasTermo = (MboSet) getMbo().getMboSet("RL01PECASTERMO");
		if (setTermo != null) {
			if (getMbo().getString("STATUS").equals("NOVO")) {
				if (setPecasTermo != null) {
					for (int i=0; i < setPecasTermo.count(); i++) {
						try {
							MboRemote mboTemp = setPecasTermo.getMbo(i);
							System.out.println("################# Movimentando (" + i + ")= " + mboTemp);
							AssetSetRemote assetSet = (AssetSetRemote) mboTemp.getMboSet("RL01ASSET");
							assetSet.getMbo(0).setValueNull("NEWSITE");
							assetSet.getMbo(0).setValue("NEWSITE",assetSet.getMbo(0).getString("SITEID"));
							assetSet.getMbo(0).setFieldFlag("PARENT", MboConstants.READONLY, false);
							assetSet.getMbo(0).setValueNull("PARENT");
							assetSet.getMbo(0).setFieldFlag("PARENT", MboConstants.READONLY, true);
							assetSet.save();
						} catch (Exception e) {
							System.out.println("##################### Ocorreu uma exceção ao mover a ASSET=" + e.getMessage());
							e.printStackTrace();
						
						}
					}
				}
				System.out.println("##################### Definindo o Status REALIZADO, Status atual = " + getMbo().getString("STATUS"));
				getMbo().setValue("STATUS", "REALIZADO");
			}
		}
		
		getMbo().getThisMboSet().save();

		System.out.println("####### Fechando..." + app.getCurrentPageId());
        System.out.println("########## FIM");
		sessionContext.queueRefreshEvent();
		WebClientEvent event = sessionContext.getCurrentEvent();
		Utility.showMessageBox(event, new MXApplicationException("protocolo", "TermoRetiradaRealizado"));
		Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
		
		app.getDataBean("MAINRECORD").refreshTable();
        app.getDataBean("MAINRECORD").reloadTable();
        //app.getDataBean("MAINRECORD").save();
        return 1;

	}
}
