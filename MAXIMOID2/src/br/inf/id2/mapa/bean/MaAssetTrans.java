package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.app.asset.Asset;
import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import  psdi.webclient.controls.Dialog;

/**
 *
 * @author Willians Andrade
 */
public class MaAssetTrans extends DataBean {

	public MaAssetTrans() {
    	System.out.print("*****  MaAssetTrans *****");
    }

    public int verificarExistencia() throws MXException, RemoteException {
    	System.out.print("*****  MaAssetTrans (verificarExistencia) *****");
    	
    	String localDestino = getMboSet().getString("LOCALDEST");
    	
    	MboSet MboLocDes = (MboSet)MXServer.getMXServer().getMboSet("ID2VWLOC04", sessionContext.getUserInfo());
    	MboLocDes.setWhere("LOCATION = '" + localDestino + "'");
    	MboLocDes.reset();
        
    	if (localDestino.equals("")){
    		throw new MXApplicationException("maasset", "informeLocDes");
    	} else {
        if (MboLocDes.count() > 0 ) {
            throw new MXApplicationException("maasset", "inserirDados");
        } else {
        	throw new MXApplicationException("maasset", "LocDesInvalido");
        }
    	}

    }
    public int confTransferencia() throws MXException, RemoteException {
    	System.out.print("*****  MaAssetTrans (confTransferencia) *****");
    	
    	// Tabela de Hístorico de Elementos
    	MboSet MaAssetSet;
    	MaAssetSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MAASSETSTATUS", sessionContext.getUserInfo());
    	
    	String localDestino = getMboSet().getString("LOCALDEST");
    	
    	MboSet MboLocDes = (MboSet)MXServer.getMXServer().getMboSet("ID2VWLOC04", sessionContext.getUserInfo());
    	MboLocDes.setWhere("LOCATION = '" + localDestino + "'");
    	MboLocDes.reset();

    	if (getMboSet().getString("ACEITOTERMOS").equals("S")){
    	if (localDestino.equals("")){
    		throw new MXApplicationException("maasset", "informeLocDes");
    	} else {
        if (MboLocDes.count() > 0 ) {
        	getMboSet().setValue("MACODLOC", localDestino, MboConstants.NOACCESSCHECK);
        	getMboSet().setValue("STATUS2", "PEND", MboConstants.NOACCESSCHECK);
        	getMboSet().setValue("STATUS", "TRANSFERIDO", MboConstants.NOACCESSCHECK);

    		MaAssetSet.add();
    		
    		MaAssetSet.setValue("DESCRIPTION", getMboSet().getString("ID2OBS") , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("STATUS", getMboSet().getString("STATUS") , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("DATA", new Date() , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("LOCATION", getMboSet().getString("MACODLOC") , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("ALTERADO", sessionContext.getUserInfo().getPersonId() + " - " + sessionContext.getUserInfo().getUserName() , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("NUMELEMENTO", getMboSet().getString("ASSETNUM") , MboConstants.NOACCESSCHECK);
        } else {
        	throw new MXApplicationException("maasset", "LocDesInvalido");
        }
    	}
    	} else {
    		throw new MXApplicationException("maasset", "AceitarTermos");
    	}
    	
	WebClientEvent event = sessionContext.getCurrentEvent();
	Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));

	MaAssetSet.save();
	app.getDataBean("MAINRECORD").save();
	app.getDataBean("MAINRECORD").refreshTable();
	app.getDataBean("MAINRECORD").reloadTable();

    
  	throw new MXApplicationException("maasset", "TransRealizada");

    }
    public int inutilizarElementoIden() throws MXException, RemoteException {
    	System.out.print("*****  MaAssetTrans (inutilizarElementoIden) *****");
    	
    	MboSet MaAssetSet;
    	MaAssetSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MAASSETSTATUS", sessionContext.getUserInfo());
    	
        	getMboSet().setValue("STATUS", "INUTILIZADO", MboConstants.NOACCESSCHECK);
    	
    		MaAssetSet.add();
    		
    		MaAssetSet.setValue("DESCRIPTION", getMboSet().getString("ID2OBS") , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("STATUS", getMboSet().getString("STATUS") , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("DATA", new Date() , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("LOCATION", getMboSet().getString("MACODLOC") , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("ALTERADO", sessionContext.getUserInfo().getPersonId() + " - " + sessionContext.getUserInfo().getUserName() , MboConstants.NOACCESSCHECK);
    		MaAssetSet.setValue("NUMELEMENTO", getMboSet().getString("ASSETNUM") , MboConstants.NOACCESSCHECK);
    		
	WebClientEvent event = sessionContext.getCurrentEvent();
	Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
    
	MaAssetSet.save();
	app.getDataBean("MAINRECORD").save();
	app.getDataBean("MAINRECORD").refreshTable();
	app.getDataBean("MAINRECORD").reloadTable();


    
  	throw new MXApplicationException("maasset", "ElementoInutilizado");
  	
  	//return EVENT_HANDLED;

    }

}
