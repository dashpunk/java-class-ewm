package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.beans.contpurch.ContPurchAppBean;
import psdi.webclient.system.beans.AppBean;

public class MsContrato extends ContPurchAppBean {

	/**
	 * @author marcelosydney.lima
	 */
	public MsContrato() {
	}
	
	@Override
	public void save() throws MXException {
		try {
			if(getMbo().isNew()){
		
				MboRemote mbo;
				MboRemote mboDestino = null;
				
				for (int i = 0; ((mbo= getMbo().getMboSet("MSTBITENSNOTAEMPENHO").getMbo(i)) !=null); i++) {
					mboDestino = getMbo().getMboSet("CONTRACTLINE").add();
					System.out.println("############ add() na itens pregao");
					mboDestino.setValue("polineid", mbo.getInt("polineid"), 2L);
					mboDestino.setValue("id2itemnum", mbo.getMboSet("POLINE").getMbo(0).getString("id2itemnum"), 2L);
					mboDestino.setValue("linetype", mbo.getMboSet("POLINE").getMbo(0).getString("linetype"), 2L);
					mboDestino.setValue("itemnum", mbo.getMboSet("POLINE").getMbo(0).getString("itemnum"), 11L);
					mboDestino.setValue("itemsetid", mbo.getMboSet("POLINE").getMbo(0).getString("itemsetid"), 11L);
					mboDestino.setValue("conditioncode", mbo.getMboSet("POLINE").getMbo(0).getString("conditioncode"), 11L);
					mboDestino.setValue("description", mbo.getMboSet("POLINE").getMbo(0).getString("description"), 11L);
					mboDestino.setValue("manufacturer", mbo.getMboSet("POLINE").getMbo(0).getString("manufacturer"), 11L);
					mboDestino.setValue("modelnum", mbo.getMboSet("POLINE").getMbo(0).getString("modelnum"), 11L);
					mboDestino.setValue("catalogcode", mbo.getMboSet("POLINE").getMbo(0).getString("catalogcode"), 11L);
					mboDestino.setValue("orderunit", mbo.getMboSet("POLINE").getMbo(0).getString("orderunit"), 11L);
					mboDestino.setValue("inspectionrequired", mbo.getMboSet("POLINE").getMbo(0).getString("inspectionrequired"), 11L);
					mboDestino.setValue("commodity", mbo.getMboSet("POLINE").getMbo(0).getString("commodity"), 11L);
					mboDestino.setValue("commoditygroup", mbo.getMboSet("POLINE").getMbo(0).getString("commoditygroup"), 11L);
					
					if (mbo.getString("MSALCODMODALIDADE").equals("PREGAO")) {
						mboDestino.setValue("orderqty", mbo.getMboSet("MSTBFORNECEDORESITEMPREGAO").getMbo(0).getString("MSNUNUMQUANTIDADE"), 11L);
						mboDestino.setValue("unitcost", mbo.getMboSet("MSTBFORNECEDORESITEMPREGAO").getMbo(0).getString("MSNUNUMVALORUNITARIO"), 11L);
						mboDestino.setValue("linecost", mbo.getMboSet("MSVWFORNECEDORESITEMPREGAO").getMbo(0).getString("MSNUNUMVALORTOTAL"), 2L);
						mboDestino.setValue("company", mbo.getMboSet("MSTBFORNECEDORESITEMPREGAO").getMbo(0).getString("COMPANY"), 2L);
						mboDestino.setValue("MSALCODREPRESENTANTE", mbo.getMboSet("MSTBFORNECEDORESITEMPREGAO").getMbo(0).getString("PERSONID"), 2L);
					} else if (mbo.getString("MSALCODMODALIDADE").equals("INEXIGIBILIDADE")) {
						mboDestino.setValue("orderqty", mbo.getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getString("MSNUNUMQTDCONTRATADAINEX"), 11L);
						mboDestino.setValue("unitcost", mbo.getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getString("MSNUNUMVALUNICONTRATADOINEX"), 11L);
						mboDestino.setValue("linecost", (mbo.getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getDouble("MSNUNUMQTDCONTRATADAINEX")*mbo.getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getDouble("MSNUNUMVALUNICONTRATADOINEX")), 2L);
						mboDestino.setValue("company", mbo.getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getString("MSNUCODFORNECEDORINEX"), 11L);
						mboDestino.setValue("MSALCODREPRESENTANTE", mbo.getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getString("MSALCODREPRESENTANTEINEX"), 11L);
					} else if (mbo.getString("MSALCODMODALIDADE").equals("ARP")) {
						mboDestino.setValue("orderqty", mbo.getMboSet("MSTBITENSARP").getMbo(0).getMboSet("MSTBITENSPREGAO").getMbo(0).getMboSet("MSVWVALORTOTALITEMPREGAO").getMbo(0).getString("QUANTIDADETOTALITEM"), 11L);
						mboDestino.setValue("unitcost", (mbo.getMboSet("MSTBITENSARP").getMbo(0).getMboSet("MSTBITENSPREGAO").getMbo(0).getMboSet("MSVWVALORTOTALITEMPREGAO").getMbo(0).getDouble("VALORTOTALITEM")/mbo.getMboSet("MSTBITENSARP").getMbo(0).getMboSet("MSTBITENSPREGAO").getMbo(0).getMboSet("MSVWVALORTOTALITEMPREGAO").getMbo(0).getDouble("QUANTIDADETOTALITEM")), 11L);
						mboDestino.setValue("linecost", mbo.getMboSet("MSTBITENSARP").getMbo(0).getMboSet("MSTBITENSPREGAO").getMbo(0).getMboSet("MSVWVALORTOTALITEMPREGAO").getMbo(0).getString("VALORTOTALITEM"), 2L);
						mboDestino.setValue("company", mbo.getMboSet("MSTBITENSARP").getMbo(0).getMboSet("MSTBARP").getMbo(0).getString("COMPANY"), 11L);
						if(mbo.getMboSet("MSTBITENSARP").getMbo(0).getMboSet("MSTBARP").getMbo(0).getMboSet("MSTBREPRESENTANTE").count() > 0){
							mboDestino.setValue("MSALCODREPRESENTANTE", mbo.getMboSet("MSTBITENSARP").getMbo(0).getMboSet("MSTBARP").getMbo(0).getMboSet("MSTBREPRESENTANTE").getMbo(0).getString("PERSONID"), 11L);
						}
					}
				}
			}
			//mboDestino.getThisMboSet().save();
			super.save();
			
			/*WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
			Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));*/
		
			} catch (RemoteException ex) {
	            Logger.getLogger(MsContrato.class.getName()).log(Level.SEVERE, null, ex);
	        }
	}
}
