package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
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
					mboDestino.setValue("id2itemnum", mbo.getString("id2itemnum"), 2L);
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
										
					mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDA"), 2L);
					mboDestino.setValue("orderqty", mbo.getDouble("MSNUNUMQUANTIDADEREGISTRADA"), 11L);
					mboDestino.setValue("unitcost", mbo.getDouble("MSNUNUMVALORUNITARIOREGISTRADO"), 11L);
					mboDestino.setValue("linecost", mbo.getDouble("MSNUNUMVALORTOTALREGISTRADO"), 2L);
					mboDestino.setValue("company", mbo.getString("COMPANY"), 2L);
					mboDestino.setValue("PERSONID", mbo.getString("PERSONID"), 2L);
					mboDestino.setValue("MSNUNUMTABELAORIGEMID", mbo.getInt("MSNUNUMTABELAORIGEMID"), 2L);
					mboDestino.setValue("MSALCODMODALIDADE", mbo.getString("MSALCODMODALIDADE"), 2L);
					mboDestino.setValue("MSALNUMMODALIDADE", mbo.getString("MSALNUMMODALIDADE"), 2L);
					mboDestino.setValue("MSALCODINSTRUMENTOCONTRATACAO", mbo.getString("MSALCODINSTRUMENTOCONTRATACAO"), 2L);
					mboDestino.setValue("MSALNUMINSTRUMENTOCONTRATACAO", mbo.getString("MSALNUMINSTRUMENTOCONTRATACAO"), 2L);
				}
				super.save();
			} else {
				
				MboRemote mboContractline;
				MboRemote mboParcelas;
				
				for (int i = 0; ((mboContractline= getMbo().getMboSet("CONTRACTLINE").getMbo(i)) != null); i++) {
					
					double quantidade = 0d;
					System.out.println("########## quantidade = " + quantidade);
					super.save();
					
					for (int j = 0; ((mboParcelas= getMbo().getMboSet("MSTBPARCELASCONTRATO").getMbo(j)) != null); j++) {
						quantidade += mboParcelas.getDouble("MSNUNUMQUANTIDADEPARCELA");
						System.out.println("########## qtdParcela = " + mboParcelas.getDouble("MSNUNUMQUANTIDADEPARCELA"));
						System.out.println("########## quantidade = " + quantidade);
						System.out.println("########## quantidadeItem = " + mboContractline.getDouble("ORDERQTY"));
						if (quantidade > mboContractline.getDouble("ORDERQTY")) {
							mboParcelas.setValue("MSNUNUMQUANTIDADEPARCELA", 0);
							super.save();
							throw new MXApplicationException("contrato", "quantidadeparcelaexcedente");
						}
					}
					super.save();
				}
				
				super.save();
				
			}
			//mboDestino.getThisMboSet().save();
			super.save();
			
			System.out.println("########## TOTALCOST = " + getMbo().getDouble("TOTALCOST"));
			double valorglobal = getMbo().getDouble("TOTALCOST");
			System.out.println("########## valorglobal = " + valorglobal);
			
			MboRemote mbo1;
			
			for (int j = 0; ((mbo1 = getMbo().getMboSet("MSTBNECONTRATO").getMbo(j)) != null); j++) {
				if (mbo1.getMboSet("MSTBNOTAEMPENHO").getMbo(0).getString("MSALCODTIPO").equalsIgnoreCase("REFORCO")) {
					valorglobal += (mbo1.getMboSet("MSTBNOTAEMPENHO").getMbo(0).getDouble("MSNUNUMVALOREMPENHO"));
					System.out.println("########## valorglobal REFORCO = " + valorglobal);
				} else if (mbo1.getMboSet("MSTBNOTAEMPENHO").getMbo(0).getString("MSALCODTIPO").equalsIgnoreCase("ANULACAO")) {
					valorglobal -= (mbo1.getMboSet("MSTBNOTAEMPENHO").getMbo(0).getDouble("MSNUNUMVALOREMPENHO"));
					System.out.println("########## valorglobal ANULACAO = " + valorglobal);
				} else {
					System.out.println("########## valorglobal ORIGINAL = " + valorglobal);
				}
				super.save();
			}
			
			System.out.println("########## valorglobal = " + valorglobal);
			getMbo().setValue("MSNUNUMVALORGLOBAL", valorglobal);
			super.save();
			
			
			/*WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
			Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));*/
		
			} catch (RemoteException ex) {
	            Logger.getLogger(MsContrato.class.getName()).log(Level.SEVERE, null, ex);
	        }
	}
}
