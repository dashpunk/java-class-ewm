package br.inf.id2.me.bean;

import java.rmi.RemoteException;
import java.util.ArrayList;

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
public class TermoDeJuntada extends DataBean {

	public TermoDeJuntada() {
		super();
		System.out.println("#################### TermoDeJuntada - Construtor");
	}

	public int realizarOperacao() throws MXException, RemoteException {
		System.out.println("########################## TermoDeJuntada - realizarOperacao");

		
		MboSet setTermo = (MboSet) getMboSet();
		MboSet setPecasTermo = (MboSet) getMbo().getMboSet("RL01PECASTERMO");
		MboRemote mboMain = app.getDataBean("MAINRECORD").getMbo();
		int tamPecasTermo = 0;
		if (setTermo != null) {
			if (getMbo().getString("STATUS").equals("NOVO")) {
				if (setPecasTermo != null) {
					tamPecasTermo = setPecasTermo.count();
					System.out.println("############### TAMANHO DO PECASTERMO=" + tamPecasTermo);
					ArrayList<MboRemote> arrAsset = new ArrayList<MboRemote>();
					for (int i=0; i < tamPecasTermo; i++) {
						arrAsset.add(getMbo().getMboSet("RL01PECASTERMO").getMbo(i));
					}
					System.out.println("############### TAMANHO DO ARRAY=" + arrAsset.size());
					
					for (int j=0; j < arrAsset.size(); j++) {
						System.out.println("############### REGISTRO ATUAL=" + j);
						MboRemote mboTemp = arrAsset.get(j);
						System.out.println("################# Movimentando (" + j + ")= " + mboTemp);
						try {
							getMbo().getThisMboSet().save();
							System.out.println("############### TAMANHO DO PECASTERMO=" + arrAsset.size());
							AssetSetRemote assetSet = (AssetSetRemote) mboTemp.getMboSet("RL01ASSET");
							System.out.println("1");
							assetSet.setMoveAssetPageFlag(true);
							//((AssetSetRemote)getMbo().getMboSet("RL01PECASTERMO").getMbo(i).getMboSet("RL01ASSET")).setMoveAssetPageFlag(true);
							System.out.println("2");
							assetSet.getMbo(0).setValueNull("NEWSITE");
							//getMbo().getMboSet("RL01PECASTERMO").getMbo(i).getMboSet("RL01ASSET").getMbo(0).setValueNull("NEWSITE");
							System.out.println("3");
							assetSet.getMbo(0).setValue("NEWSITE",assetSet.getMbo(0).getString("SITEID"));
							//getMbo().getMboSet("RL01PECASTERMO").getMbo(i).getMboSet("RL01ASSET").getMbo(0).setValue("NEWSITE",assetSet.getMbo(0).getString("SITEID"));
							System.out.println("4");
							assetSet.getMbo(0).setFieldFlag("NEWPARENT", MboConstants.READONLY, false);
							//getMbo().getMboSet("RL01PECASTERMO").getMbo(i).getMboSet("RL01ASSET").getMbo(0).setFieldFlag("NEWPARENT", MboConstants.READONLY, false);
							System.out.println("5");
							assetSet.getMbo(0).setValue("NEWPARENT", mboMain.getString("ASSETNUM"));
							//getMbo().getMboSet("RL01PECASTERMO").getMbo(i).getMboSet("RL01ASSET").getMbo(0).setValue("ASSETNUM", mboMain.getString("ASSETNUM"));
							System.out.println("6");
							assetSet.getMbo(0).setFieldFlag("NEWPARENT", MboConstants.READONLY, true);
							//getMbo().getMboSet("RL01PECASTERMO").getMbo(i).getMboSet("RL01ASSET").getMbo(0).setFieldFlag("NEWPARENT", MboConstants.READONLY, true);
							System.out.println("7");
							assetSet.setLastESigTransId(mboMain.getThisMboSet().getESigTransactionId());
							//((AssetSetRemote)getMbo().getMboSet("RL01PECASTERMO").getMbo(i).getMboSet("RL01ASSET")).setLastESigTransId(mboMain.getThisMboSet().getESigTransactionId());
							System.out.println("8");
							assetSet.moveAsset();
							//((AssetSetRemote)getMbo().getMboSet("RL01PECASTERMO").getMbo(i).getMboSet("RL01ASSET")).moveAsset();
							System.out.println("9");
							assetSet.setMoveAssetPageFlag(false);
							//((AssetSetRemote)getMbo().getMboSet("RL01PECASTERMO").getMbo(i).getMboSet("RL01ASSET")).setMoveAssetPageFlag(false);
							System.out.println("10");
							//assetSet.save();
							System.out.println("11");
							
						} catch (Exception e) {
							System.out.println("##################### Ocorreu uma exceção ao mover a ASSET=" + e.getMessage());
							e.printStackTrace();
							if (e.getMessage() != null && e.getMessage().contains("inserido como pai aqui")) {
								WebClientEvent event = sessionContext.getCurrentEvent();
								Utility.showMessageBox(event, new MXApplicationException("protocolo", "ExcecaoInseridoPai"));
							}
						}
					}
				}
				System.out.println("##################### Definindo o Status REALIZADO, Status atual = " + getMbo().getString("STATUS"));
				//getMbo().setValue("STATUS", "REALIZADO");
			}
		}
		
		getMbo().getThisMboSet().save();
		System.out.println("####### Fechando..." + app.getCurrentPageId());
        System.out.println("########## FIM");
		sessionContext.queueRefreshEvent();
		WebClientEvent event = sessionContext.getCurrentEvent();
		Utility.showMessageBox(event, new MXApplicationException("protocolo", "TermoJuntadaRealizado"));
		Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
		
        app.getDataBean("MAINRECORD").refreshTable();
        app.getDataBean("MAINRECORD").reloadTable();
        //app.getDataBean("MAINRECORD").save();
        
        return 1;


	}
}
