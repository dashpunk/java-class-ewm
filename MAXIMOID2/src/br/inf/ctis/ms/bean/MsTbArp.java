package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class MsTbArp extends AppBean {
	
	private String historicoNumPortaria;

	/**
	 * @author Marcelo
	 */
	public MsTbArp() {
	}
	
	@Override
	public void save() throws MXException {
		try {
			if(getMbo().getMboSet("MSTBITENSARP").isEmpty()){
			
				MboRemote mbo;
				MboRemote mboDestino = null;
				
				if (getMbo().getMboSet("MSTBITENSARP").isEmpty()){
					for (int i = 0; ((mbo= getMbo().getMboSet("MSTBITENSPREGAO").getMbo(i)) !=null); i++) {
						mboDestino = getMbo().getMboSet("MSTBITENSARP").add();
						System.out.println("############ add() na itens arp");
						mboDestino.setValue("POLINEID", mbo.getInt("POLINEID"));
						System.out.println("############ POLINEID = " + mbo.getInt("POLINEID"));
						
						mboDestino.setValue("MSTBITENSPREGAOID", mbo.getInt("MSTBITENSPREGAOID"));
						System.out.println("############ MSTBITENSPREGAOID = " + mbo.getInt("MSTBITENSPREGAOID"));
						
						mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDA"));
						System.out.println("############ MSALCODMOEDA = " + mbo.getString("MSALCODMOEDA"));
						
						mboDestino.setValue("MSNUNUMQUANTIDADEREGISTRADA", mbo.getDouble("MSNUNUMQUANTIDADEREGISTRADA"));
						System.out.println("############ MSNUNUMQUANTIDADEREGISTRADA = " + mbo.getDouble("MSNUNUMQUANTIDADEREGISTRADA"));
						
						mboDestino.setValue("MSNUNUMVALORUNITARIO", mbo.getDouble("MSNUNUMVALORUNITARIO"));
						System.out.println("############ MSNUNUMVALORUNITARIO = " + mbo.getDouble("MSNUNUMVALORUNITARIO"));
						
						mboDestino.setValue("MSNUNUMVALORTOTAL", mbo.getDouble("MSNUNUMVALORTOTAL"));
						System.out.println("############ MSNUNUMVALORTOTAL = " + mbo.getDouble("MSNUNUMVALORTOTAL"));
					
						mboDestino.setValue("MSTBARPID", getMbo().getInt("MSTBARPID"));
						System.out.println("########### MSTBARPID" + getMbo().getInt("MSTBARPID"));
					}
				}
				
				
				if(getMbo().getString("MSALNUMPORTARIAFISCAL") != null && 
						!getMbo().getString("MSALNUMPORTARIAFISCAL").toString().equalsIgnoreCase("")){
					// chamar método para salvar o histórico.
					
					MboRemote mbo2;
					boolean flag = false;
					
					for (int i = 0; ((mbo2 = getMbo().getMboSet("MSTBHISTORICOFISCAIS").getMbo(i)) != null); i++){
						
						if (mbo2.getString("MSALNUMPORTARIAFISCAL").equalsIgnoreCase(getMbo().getString("MSALNUMPORTARIAFISCAL"))){
							flag = true;						
						}	
					}	
					
					if(!flag) {
						MboRemote mboHistorico = getMbo().getMboSet("MSTBHISTORICOFISCAIS").add();
						mboHistorico.setValue("MSALNOMFISCALCONTRATO", getMbo().getString("MSALNOMFISCALCONTRATO"));
						mboHistorico.setValue("MSALNOMFISCALCONTRATOSUB", getMbo().getString("MSALNOMFISCALCONTRATOSUB"));
						mboHistorico.setValue("MSALNUMSIAPEFISCALCONTRATO", getMbo().getString("MSALNUMSIAPEFISCALCONTRATO"));
						mboHistorico.setValue("MSALNUMSIAPEFISCALCONTRATOSUB", getMbo().getString("MSALNUMSIAPEFISCALCONTRATOSUB"));
						mboHistorico.setValue("MSALNUMBSEFISCAL", getMbo().getString("MSALNUMBSEFISCAL"));
						mboHistorico.setValue("MSALNUMPORTARIAFISCAL", getMbo().getString("MSALNUMPORTARIAFISCAL"));
						mboHistorico.setValue("MSDTDTAPORTARIAFISCAL", getMbo().getString("MSDTDTAPORTARIAFISCAL"));
						mboHistorico.setValue("MSDTDTAPUBLICACAOPORTARIAFISCAL", getMbo().getString("MSDTDTAPUBLICACAOPORTARIA"));
						mboHistorico.setValue("APPNAME", "MSARP");
						mboHistorico.setValue("TABLENAME", "MSTBARP");
						mboHistorico.setValue("ORIGEMID", getMbo().getInt("MSTBARPID"));
						mboHistorico.setValue("PERSONID", sessionContext.getUserInfo().getPersonId());
					}
					
					//MSTBHISTORICOFISCAIS
				}
				
				super.save();
				
			} else {
				
				MboRemote mbo1;
				
				double valorglobal = 0d;
				
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBITENSARP").getMbo(i)) != null); i++) {
					super.save();
					if (mbo1.getBoolean("MSNUFLGSELECAOARP")) {
						super.save();
						valorglobal += mbo1.getDouble("MSNUNUMVALORTOTAL");
					}
				}
				
				System.out.println("########## Valor Global = " + valorglobal);
				getMbo().setValue("MSNUNUMVALORGLOBAL", valorglobal);
				
				if(getMbo().getString("MSALNUMPORTARIAFISCAL") != null && 
						!getMbo().getString("MSALNUMPORTARIAFISCAL").toString().equalsIgnoreCase("")){
					// chamar método para salvar o histórico.
					
					MboRemote mbo2;
					boolean flag = false;
					
					for (int i = 0; ((mbo2 = getMbo().getMboSet("MSTBHISTORICOFISCAIS").getMbo(i)) != null); i++){
						
						if (mbo2.getString("MSALNUMPORTARIAFISCAL").equalsIgnoreCase(getMbo().getString("MSALNUMPORTARIAFISCAL"))){
							flag = true;						
						}	
					}	
					
					if(!flag) {
						MboRemote mboHistorico = getMbo().getMboSet("MSTBHISTORICOFISCAIS").add();
						mboHistorico.setValue("MSALNOMFISCALCONTRATO", getMbo().getString("MSALNOMFISCALCONTRATO"));
						mboHistorico.setValue("MSALNOMFISCALCONTRATOSUB", getMbo().getString("MSALNOMFISCALCONTRATOSUB"));
						mboHistorico.setValue("MSALNUMSIAPEFISCALCONTRATO", getMbo().getString("MSALNUMSIAPEFISCALCONTRATO"));
						mboHistorico.setValue("MSALNUMSIAPEFISCALCONTRATOSUB", getMbo().getString("MSALNUMSIAPEFISCALCONTRATOSUB"));
						mboHistorico.setValue("MSALNUMBSEFISCAL", getMbo().getString("MSALNUMBSEFISCAL"));
						mboHistorico.setValue("MSALNUMPORTARIAFISCAL", getMbo().getString("MSALNUMPORTARIAFISCAL"));
						mboHistorico.setValue("MSDTDTAPORTARIAFISCAL", getMbo().getString("MSDTDTAPORTARIAFISCAL"));
						mboHistorico.setValue("MSDTDTAPUBLICACAOPORTARIAFISCAL", getMbo().getString("MSDTDTAPUBLICACAOPORTARIA"));
						mboHistorico.setValue("APPNAME", "MSARP");
						mboHistorico.setValue("TABLENAME", "MSTBARP");
						mboHistorico.setValue("ORIGEMID", getMbo().getInt("MSTBARPID"));
						mboHistorico.setValue("PERSONID", sessionContext.getUserInfo().getPersonId());
					}
					
					//MSTBHISTORICOFISCAIS
				}
				
				super.save();
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