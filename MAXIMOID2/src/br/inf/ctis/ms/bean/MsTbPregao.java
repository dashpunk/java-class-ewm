package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class MsTbPregao extends AppBean {

	/**
	 * @author marcelosydney.lima
	 */
	public MsTbPregao() {
	}
	
	@Override
	public void save() throws MXException {
		try {
			if(getMbo().isNew()){
			
				MboRemote mbo;
				MboRemote mboDestino = null;
				
				for (int i = 0; ((mbo= getMbo().getMboSet("POLINE").getMbo(i)) !=null); i++) {
					mboDestino = getMbo().getMboSet("MSTBITENSPREGAO").add();
					System.out.println("############ add() na itens pregao");
					mboDestino.setValue("POLINEID", mbo.getInt("POLINEID"));
					System.out.println("############ POLINEID = " + mbo.getInt("POLINEID"));
					mboDestino.setValue("MSTBPREGAOID", getMbo().getInt("MSTBPREGAOID"));
					System.out.println("########### MSTBPREGAOID" + getMbo().getInt("MSTBPREGAOID"));
				}
				super.save();
			} else {
				
				MboRemote mbo1;
				MboRemote mbo2;
				
				double valorglobal = 0d;
				
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBITENSPREGAO").getMbo(i)) != null); i++) {
					super.save();
					if (mbo1.getString("MSALCODSITUACAO").equalsIgnoreCase("HOMOLOGADO")) {
						System.out.println("########## Count Fornecedores do Item do Pregão = " + mbo1.getMboSet("MSTBFORNECEDORESITEMPREGAO").count());
						if (mbo1.getMboSet("MSTBFORNECEDORESITEMPREGAO").count() > 0) {
							super.save();
							System.out.println("########## MSNUNUMVALORUNITARIO = " + mbo1.getMboSet("MSTBFORNECEDORESITEMPREGAOVENCEDOR").getMbo(0).getDouble("MSNUNUMVALORUNITARIO"));
							mbo1.setValue("MSNUNUMVALORUNITARIO", mbo1.getMboSet("MSTBFORNECEDORESITEMPREGAOVENCEDOR").getMbo(0).getDouble("MSNUNUMVALORUNITARIO"));
							System.out.println("########## MSALCODMOEDA = " + mbo1.getMboSet("MSTBFORNECEDORESITEMPREGAOVENCEDOR").getMbo(0).getString("MSALCODMOEDA"));
							mbo1.setValue("MSALCODMOEDA", mbo1.getMboSet("MSTBFORNECEDORESITEMPREGAOVENCEDOR").getMbo(0).getString("MSALCODMOEDA"));
							
							double quantidade = 0d;
							double valortotal = 0d;
							
							for (int j = 0; ((mbo2 = mbo1.getMboSet("MSTBFORNECEDORESITEMPREGAO").getMbo(j)) != null); j++) {
								double valor = mbo2.getDouble("MSNUNUMQUANTIDADE") * mbo2.getDouble("MSNUNUMVALORUNITARIO");
								System.out.println("########## Valor = " + valor);
								mbo2.setValue("MSNUNUMVALORTOTAL", valor);
								quantidade += mbo2.getDouble("MSNUNUMQUANTIDADE");
								valortotal += valor;
							}
							
							System.out.println("########## Quantidade = " + quantidade);
							mbo1.setValue("MSNUNUMQUANTIDADEREGISTRADA", quantidade);
							System.out.println("########## Valor Total = " + valortotal);
							mbo1.setValue("MSNUNUMVALORTOTAL", valortotal);
							
							valorglobal += mbo1.getDouble("MSNUNUMVALORTOTAL");
							super.save();
						} else {
							throw new MXApplicationException("pregao", "FornecedorNulo");
						}
						super.save();	
					}
				}
				
				System.out.println("########## Valor Global = " + valorglobal);
				getMbo().setValue("MSNUNUMVALORGLOBAL", valorglobal);
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
