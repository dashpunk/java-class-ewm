package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class MsTermoAditivo extends AppBean {

	/**
	 * @author marcelosydney.lima
	 */
	public MsTermoAditivo() {
	}
	
	@Override
	public void save() throws MXException {
		try {
			if(getMbo().isNew()){
			
				MboRemote mbo;
				MboRemote mboDestino = null;
				
				for (int i = 0; ((mbo= getMbo().getMboSet("MSTBITENSNOTAEMPENHO").getMbo(i)) !=null); i++) {
					mboDestino = getMbo().getMboSet("MSTBITENSTERMOADITIVO").add();
					System.out.println("############ add() na itens Termo Aditivo");
					mboDestino.setValue("POLINEID", mbo.getInt("POLINEID"));
					System.out.println("############ POLINEID = " + mbo.getInt("POLINEID"));
					mboDestino.setValue("MSTBITENSNOTAEMPENHOID", mbo.getInt("MSTBITENSNOTAEMPENHOID"));
					System.out.println("############ MSTBITENSNOTAEMPENHOID = " + mbo.getInt("MSTBITENSNOTAEMPENHOID"));
					mboDestino.setValue("ID2ITEMNUM", mbo.getString("ID2ITEMNUM"));
					System.out.println("############ ID2ITEMNUM = " + mbo.getString("ID2ITEMNUM"));
					mboDestino.setValue("MSNUNUMTABELAORIGEMID", mbo.getInt("MSNUNUMTABELAORIGEMID"));
					System.out.println("############ MSNUNUMTABELAORIGEMID = " + mbo.getInt("MSNUNUMTABELAORIGEMID"));
					mboDestino.setValue("MSTBTERMOADITIVOID", getMbo().getInt("MSTBTERMOADITIVOID"));
					System.out.println("########### MSTBTERMOADITIVOID" + getMbo().getInt("MSTBTERMOADITIVOID"));
					
					mboDestino.setValue("MSNUNUMPORCENTAGEMADITIVADA", 0);
					mboDestino.setValue("MSNUNUMQUANTIDADEADITIVADA", 0);
					mboDestino.setValue("MSNUNUMVALORUNITARIOALTERADO", 0);
				}
				super.save();
			} else {
				MboRemote mbo;
				for (int i = 0; ((mbo=getMbo().getMboSet("MSTBITENSTERMOADITIVO").getMbo(i)) !=null); i++) {
					if (mbo.getBoolean("MSNUFLGSELECAO")) {
						if((mbo.getDouble("MSNUNUMPORCENTAGEMADITIVADA") > 0) || (mbo.getDouble("MSNUNUMQUANTIDADEADITIVADA") > 0)) {
							mbo.setValue("MSNUNUMVALORTOTAL", mbo.getDouble("MSNUNUMQUANTIDADEADITIVADA") * mbo.getMboSet("MSVWITENSNETERMOADITIVO").getMbo(0).getDouble("UNITCOST"));
							super.save();
						} else if ((mbo.getDouble("MSNUNUMVALORUNITARIOALTERADO") > 0)) {
							mbo.setValue("MSNUNUMVALORTOTAL", mbo.getDouble("MSNUNUMVALORUNITARIOALTERADO") * mbo.getMboSet("MSVWITENSNETERMOADITIVO").getMbo(0).getDouble("ORDERQTY"));
							super.save();
						} else {
							throw new MXApplicationException("termoaditivo", "NaoAditivado");
						}
					} else {
						super.save();
					}
				}
			}
			//mboDestino.getThisMboSet().save();
					
			
			/*WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
			Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));*/
		
		} catch (RemoteException ex) {
            Logger.getLogger(MsTbPregao.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
