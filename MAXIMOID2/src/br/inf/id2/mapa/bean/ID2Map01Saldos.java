package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * 
 * @author Leysson Barbosa Moreira
 * 
 */
public class ID2Map01Saldos extends DataBean {

	public ID2Map01Saldos() {

	}
	
    public int inserirLoteIndividual() throws MXException, RemoteException {
    	MboRemote mboDestino = app.getDataBean().getMboSet().getMbo().getMboSet("MATBLOTABA").add();

    	String personId = getMboSet().getMbo(getMboSet().getCurrentPosition()).getString("PERSONID");
    	String prorprietario = getMboSet().getMbo(getMboSet().getCurrentPosition()).getString("ID2CODPROP");
    	
    	mboDestino.setValue("MACODPROP", prorprietario, MboConstants.NOACCESSCHECK);
    	mboDestino.setValue("PERSONID", personId, MboConstants.NOACCESSCHECK);
    	mboDestino.setValue("location", app.getDataBean().getMboSet().getMbo().getString("location"), MboConstants.NOACCESSCHECK);
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));
    	return EVENT_HANDLED;
    }

}
