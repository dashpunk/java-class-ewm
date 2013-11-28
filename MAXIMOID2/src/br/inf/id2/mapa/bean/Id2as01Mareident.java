package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * 
 * @author Ricardo S Gomes
 *
 */
public class Id2as01Mareident extends DataBean {

	public Id2as01Mareident() {
		System.out.println("--- Id2as01Mareident");
	}

    public int confirmar() throws MXException, RemoteException {
    	
    	System.out.println("--- Id2as01Mareident EXECUTE");
    	
    	int retorno = super.execute();
    	
        MboSetRemote assetHistSet;
        assetHistSet = psdi.server.MXServer.getMXServer().getMboSet("ID2ASSETHISTORY", sessionContext.getUserInfo());
//        assetHistSet.setWhere("");
//        assetHistSet.reset();
        
        MboRemote mbo;
        
        mbo = assetHistSet.add();
        
        mbo.setValue("ID2NOVOASSETNUM", app.getDataBean("MAINRECORD").getMbo().getString("ID2ASSETNUM"), MboConstants.NOACCESSCHECK);
        mbo.setValue("ID2ANTASSETNUM", app.getDataBean("MAINRECORD").getMbo().getString("ASSETNUM"), MboConstants.NOACCESSCHECK);
        mbo.setValue("PERSONID", sessionContext.getUserInfo().getUserName(), MboConstants.NOACCESSCHECK);

        assetHistSet.save();
        
        MboSetRemote assetSet;
        assetSet = psdi.server.MXServer.getMXServer().getMboSet("ASSET", sessionContext.getUserInfo());
        assetSet.setWhere("assetnum = \'"+app.getDataBean("MAINRECORD").getMbo().getString("ID2ASSETNUM")+"\'");
        assetSet.reset();
        
        if (assetSet.count() > 0) {
        	
        	assetSet.getMbo(0).setValue("MAGEN", app.getDataBean("MAINRECORD").getMbo().getString("MAGEN"), MboConstants.NOACCESSCHECK);
        	assetSet.getMbo(0).setValue("MADATENASC", app.getDataBean("MAINRECORD").getMbo().getDate("MADATENASC"), MboConstants.NOACCESSCHECK);
        	assetSet.getMbo(0).setValue("MADATIDENT", app.getDataBean("MAINRECORD").getMbo().getDate("MADATIDENT"), MboConstants.NOACCESSCHECK);
        	assetSet.getMbo(0).setValue("MADTINSPLA", new Date(), MboConstants.NOACCESSCHECK);
        	assetSet.getMbo(0).setValue("LOCATION", app.getDataBean("MAINRECORD").getMbo().getString("LOCATION"), MboConstants.NOACCESSCHECK);
        	assetSet.getMbo(0).setValue("MAESTRATIFICACAO", app.getDataBean("MAINRECORD").getMbo().getString("MAESTRATIFICACAO"), MboConstants.NOACCESSCHECK);
        	assetSet.getMbo(0).setValue("STATUS", "EM USO", MboConstants.NOACCESSCHECK);
        	
        	assetSet.save();
        }

        assetSet.setWhere("assetnum = \'"+app.getDataBean("MAINRECORD").getMbo().getString("ASSETNUM")+"\'");
        assetSet.reset();
        
        if (assetSet.count() > 0) {
        	
        	assetSet.getMbo(0).setValue("STATUS", "DESL/SUBS", MboConstants.NOACCESSCHECK);
        	
        	assetSet.save();
        }
        
        WebClientEvent event = sessionContext.getCurrentEvent();

        Utility.sendEvent(new WebClientEvent("refreshTable", event.getTargetId(), event.getValue(), sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", event.getTargetId(), event.getValue(), sessionContext));

        Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));

        return retorno;
        
    }


}
