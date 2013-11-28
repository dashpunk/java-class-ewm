package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.app.asset.Asset;
import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.session.WebClientSession;

/**
 *
 * @author Willians Andrade
 */
public class MaAsset01 extends DataBean {

    int totalElementos = 0;

	public MaAsset01() {
    	System.out.print("*****  MaAsset01 *****");
    }

    public int receberElementos() throws MXException, RemoteException {
    	System.out.print("*****  MaAsset01 (receberElementos) *****");

    	// Tabela de Hístorico de Elementos
    	MboSet MaAssetSet;
    	MaAssetSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MAASSETSTATUS", sessionContext.getUserInfo());
    	 
    	MboSet receber = (MboSet) getMbo().getMboSet("MARLASSETPEND");
    	
    	
    	for (int i = 0; i < receber.count(); i++) {
    		if(receber.getMbo(i).getString("STATUS2").equals("PEND")){

	    		totalElementos = 1;
	    		receber.getMbo(i).setValue("STATUS", "EM ESTOQUE", MboConstants.NOACCESSCHECK);
	    		receber.getMbo(i).setValue("STATUS2", "", MboConstants.NOACCESSCHECK);
	    		receber.getMbo(i).setValue("LOCATION", receber.getMbo(i).getString("MACODLOC"), MboConstants.NOACCESSCHECK);
	    		receber.getMbo(i).setValue("MACODLOC", "", MboConstants.NOACCESSCHECK);
	    		receber.getMbo(i).setValue("SELECIONADO", "N", MboConstants.NOACCESSCHECK);
	    		
	    		MaAssetSet.add();
	    		
	    		MaAssetSet.setValue("DESCRIPTION", receber.getMbo(i).getString("ID2OBS") , MboConstants.NOACCESSCHECK);
	    		MaAssetSet.setValue("STATUS", receber.getMbo(i).getString("STATUS") , MboConstants.NOACCESSCHECK);
	    		MaAssetSet.setValue("DATA", new Date() , MboConstants.NOACCESSCHECK);
	    		MaAssetSet.setValue("LOCATION", receber.getMbo(i).getString("MACODLOC") , MboConstants.NOACCESSCHECK);
	    		MaAssetSet.setValue("ALTERADO", sessionContext.getUserInfo().getPersonId() + " - " + sessionContext.getUserInfo().getUserName() , MboConstants.NOACCESSCHECK);
	    		MaAssetSet.setValue("NUMELEMENTO", receber.getMbo(i).getString("ASSETNUM") , MboConstants.NOACCESSCHECK);
    		}
    	}
    	reloadTable();
    	refreshTable();
    	
    	
    	if (totalElementos > 0){
    		save();
    		MaAssetSet.save();
    		throw new MXApplicationException("maasset", "todosElementosRecebido");
    	}
    	if (totalElementos <= 0){
    		throw new MXApplicationException("maasset", "semElementosRecebido");
    	}

    	return EVENT_HANDLED;
    }
    
    public int receberSelecionado() throws MXException, RemoteException {
    	save();
    	System.out.print("*****  MaAsset01 (receberSelecionado) *****");
    	
    	// Tabela de Hístorico de Elementos
    	MboSet MaAssetSet;
    	MaAssetSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MAASSETSTATUS", sessionContext.getUserInfo());
    	
    	MboSet receber = (MboSet) getMbo().getMboSet("MARLASSETPEND");
    	for (int i = 0; i < receber.count(); i++) {
            if (receber.getMbo(i).getString("SELECIONADO").equals("S")) {
    		totalElementos = 1;
    		receber.getMbo(i).setValue("STATUS", "EM ESTOQUE", MboConstants.NOACCESSCHECK);
    		receber.getMbo(i).setValue("STATUS2", "", MboConstants.NOACCESSCHECK);
    		receber.getMbo(i).setValue("LOCATION", receber.getMbo(i).getString("MACODLOC"), MboConstants.NOACCESSCHECK);
    		receber.getMbo(i).setValue("MACODLOC", "", MboConstants.NOACCESSCHECK);
    		receber.getMbo(i).setValue("SELECIONADO", "N", MboConstants.NOACCESSCHECK);
    		
    		MaAssetSet.add();
    		
    		MaAssetSet.setValue("DESCRIPTION", receber.getMbo(i).getString("ID2OBS") , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("STATUS", receber.getMbo(i).getString("STATUS") , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("DATA", new Date() , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("LOCATION", receber.getMbo(i).getString("MACODLOC") , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("ALTERADO", sessionContext.getUserInfo().getPersonId() + " - " + sessionContext.getUserInfo().getUserName() , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("NUMELEMENTO", receber.getMbo(i).getString("ASSETNUM") , MboConstants.NOACCESSCHECK);
    		}
    	}
    	reloadTable();
    	refreshTable();
    	
    	if (totalElementos > 0){
    		totalElementos = 0;
    		save();
    		MaAssetSet.save();
    		throw new MXApplicationException("maasset", "todosElementosRecebido");
    	}
    	if (totalElementos <= 0){
    		throw new MXApplicationException("maasset", "semElementosRecebido");
    	}
    	save();
    	return EVENT_HANDLED;
    }
}
