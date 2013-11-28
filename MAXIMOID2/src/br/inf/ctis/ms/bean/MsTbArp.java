package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class MsTbArp extends AppBean {

	/**
	 * @author Marcelo
	 */
	public MsTbArp() {
	}
	
	@Override
	public void save() throws MXException {
		try {
			if(getMbo().isNew()){
			
				MboRemote mbo;
				MboRemote mboDestino = null;
				
				for (int i = 0; ((mbo= getMbo().getMboSet("POLINE").getMbo(i)) !=null); i++) {
					mboDestino = getMbo().getMboSet("MSTBITENSARP").add();
					System.out.println("############ add() na itens arp");
					mboDestino.setValue("POLINEID", mbo.getInt("POLINEID"));
					System.out.println("############ POLINEID = " + mbo.getInt("POLINEID"));
					mboDestino.setValue("MSTBARPID", getMbo().getInt("MSTBARPID"));
					System.out.println("########### MSTBARPID" + getMbo().getInt("MSTBARPID"));
				}
			}
			//mboDestino.getThisMboSet().save();
			super.save();
			
			/*WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
			Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));*/
		
		} catch (RemoteException ex) {
            Logger.getLogger(MsTbArp.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}