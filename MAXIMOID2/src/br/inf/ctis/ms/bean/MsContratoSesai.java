package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.contpurch.ContPurchAppBean;


public class MsContratoSesai extends ContPurchAppBean {

	/**
	 * @author marcelosydney.lima
	 */
	public MsContratoSesai() {
	}
	
	@Override
	public void save() throws MXException {
		try {
			if(getMbo().isNew()){
		
				MboRemote mbo;
				MboRemote mboDestino = null;
				
				for (int i = 0; ((mbo= getMbo().getMboSet("MSVWSIASGITEMCONTRATO").getMbo(i)) !=null); i++) {
					mboDestino = getMbo().getMboSet("CONTRACTLINE").add();
					System.out.println("############ add() na itens pregao");
					mboDestino.setValue("id2itemnum", mbo.getString("id2itemnum"), 2L);
					mboDestino.setValue("MSALCODCATMAT", mbo.getString("id2itemnum"), 2L);
					mboDestino.setValue("linetype", mbo.getString("linetype"), 2L);			
					mboDestino.setValue("description", mbo.getString("description"), 11L);
					mboDestino.setValue("orderqty", mbo.getDouble("ORDERQTY"), 11L);
					mboDestino.setValue("unitcost", mbo.getDouble("UNITCOST"), 11L);
					mboDestino.setValue("linecost", mbo.getDouble("LINECOST"), 2L);
				}
				super.save();
			} else {
				
				MboRemote mboContractline;
				MboRemote mboParcelas;
				
				for (int i = 0; ((mboContractline= getMbo().getMboSet("CONTRACTLINE").getMbo(i)) != null); i++) {
					
					double quantidade = 0d;
					System.out.println("########## quantidade = " + quantidade);
					super.save();
					
					for (int j = 0; ((mboParcelas = mboContractline.getMboSet("MSTBPARCELASCONTRATO").getMbo(j)) != null); j++) {
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
				System.out.println("########## X = " + getMbo().getMboSet("MSTBNECONTRATO").getMbo(j).getMboSet("MSTBNOTAEMPENHO").getMbo(0).getString("MSALCODTIPO"));
				
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
	            Logger.getLogger(MsContratoSesai.class.getName()).log(Level.SEVERE, null, ex);
	        }
	}
}
