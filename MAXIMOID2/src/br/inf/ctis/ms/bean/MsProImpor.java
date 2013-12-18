package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;


public class MsProImpor extends AppBean {

	/**
	 * @author marcelosydney.lima
	 */
	public MsProImpor() {
	}
	
	@Override
	public void save() throws MXException {
		try {
			if(getMbo().isNew()){
			
				MboRemote mbo;
				MboRemote mboDestino = null;
					
				if (getMbo().getMboSet("MSTBOBIMP").isEmpty()){
					for (int i = 0; ((mbo= getMbo().getMboSet("CONTRACTLINE").getMbo(i)) !=null); i++) {
						mboDestino = getMbo().getMboSet("MSTBOBIMP").add();
						System.out.println("############ add() na itens importacao");
						mboDestino.setValue("MSTBIMPORTACAOID", getMbo().getInt("MSTBIMPORTACAOID"));
						System.out.println("############ MSTBIMPORTACAOID = " + getMbo().getInt("MSTBIMPORTACAOID"));
						mboDestino.setValue("CONTRACTLINEID", mbo.getInt("CONTRACTLINEID"));
						System.out.println("############ CONTRACTLINEID = " + mbo.getInt("CONTRACTLINEID"));
						mboDestino.setValue("POLINEID", mbo.getInt("POLINEID"));
						System.out.println("############ POLINEID = " + mbo.getInt("POLINEID"));
						
						mboDestino.setValue("ID2ITEMNUM", mbo.getString("ID2ITEMNUM"));
						System.out.println("############ ID2ITEMNUM = " + mbo.getString("ID2ITEMNUM"));
						mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDA"));
						System.out.println("############ MSALCODMOEDA = " + mbo.getString("MSALCODMOEDA"));
						mboDestino.setValue("ORDERUNIT", mbo.getString("ORDERUNIT"));
						System.out.println("############ ORDERUNIT = " + mbo.getString("ORDERUNIT"));
						mboDestino.setValue("COMPANY", mbo.getString("COMPANY"));
						System.out.println("############ COMPANY = " + mbo.getString("COMPANY"));
						mboDestino.setValue("PERSONID", mbo.getString("PERSONID"));
						System.out.println("############ PERSONID = " + mbo.getString("PERSONID"));
						
						mboDestino.setValue("ORDERQTY", mbo.getString("ORDERQTY"));
						System.out.println("############ ORDERQTY = " + mbo.getString("ORDERQTY"));
						mboDestino.setValue("UNITCOST", mbo.getDouble("UNITCOST"));
						System.out.println("############ UNITCOST = " + mbo.getDouble("UNITCOST"));
						mboDestino.setValue("LINECOST", mbo.getDouble("LINECOST"));
						System.out.println("############ LINECOST = " + mbo.getDouble("LINECOST"));				
						
					}
				}
				
				super.save();
				
			}			
			//mboDestino.getThisMboSet().save();
			super.save();
			
			/*WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
			Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));*/
		
		} catch (RemoteException ex) {
            Logger.getLogger(MsTbPregao.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}