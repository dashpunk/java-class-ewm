package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class MsTbDispensa extends AppBean {

	/**
	 * @author marcelosydney.lima 
	 */
	public MsTbDispensa() {
	}
	
	@Override
	public void save() throws MXException {
		try {
			if(getMbo().isNew()){
			
				MboRemote mbo;
				MboRemote mboDestino = null;
				
				System.out.println("########## ID2PERIODO: " + getMbo().getMboSet("PO").getMbo(0).getMboSet("ID2RELPR").getMbo(0).getString("ID2PERIODO"));
				getMbo().setValue("ID2PERIODO", getMbo().getMboSet("PO").getMbo(0).getMboSet("ID2RELPR").getMbo(0).getString("ID2PERIODO"));
				
				if (getMbo().getMboSet("MSTBITENSDISPENSA").isEmpty()){
					for (int i = 0; ((mbo= getMbo().getMboSet("POLINE").getMbo(i)) !=null); i++) {
						mboDestino = getMbo().getMboSet("MSTBITENSDISPENSA").add();
						System.out.println("############ add() na itens dispensa");
						mboDestino.setValue("POLINEID", mbo.getInt("POLINEID"));
						System.out.println("############ POLINEID = " + mbo.getInt("POLINEID"));
						mboDestino.setValue("MSTBDISPENSAID", getMbo().getInt("MSTBDISPENSAID"));
						System.out.println("########### MSTBDISPENSAID" + getMbo().getInt("MSTBDISPENSAID"));
					}
				}
				super.save();
				
				MboRemote mbo1;
				
				
				double valorglobal = 0d;
				
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBITENSDISPENSA").getMbo(i)) != null); i++) {
					super.save();
					if (!mbo1.getBoolean("MSNUFLGCANCELADO")) {
						mbo1.setValue("MSNUNUMVALORTOTALCONTRATADO", (mbo1.getDouble("MSNUNUMVALORUNITARIOCONTRATADO") * mbo1.getDouble("MSNUNUMQUANTIDADECONTRATADA")));
						super.save();
						valorglobal += (mbo1.getDouble("MSNUNUMVALORUNITARIOCONTRATADO") * mbo1.getDouble("MSNUNUMQUANTIDADECONTRATADA"));
					}
				}
				
				System.out.println("########## Valor Global = " + valorglobal);
				getMbo().setValue("MSNUNUMVALORTOTAL", valorglobal);
								
				super.save();
				
			} else {
				
				MboRemote mbo1;
				
				
				double valorglobal = 0d;
				
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBITENSDISPENSA").getMbo(i)) != null); i++) {
					super.save();
					if (!mbo1.getBoolean("MSNUFLGCANCELADO")) {
						mbo1.setValue("MSNUNUMVALORTOTALCONTRATADO", (mbo1.getDouble("MSNUNUMVALORUNITARIOCONTRATADO") * mbo1.getDouble("MSNUNUMQUANTIDADECONTRATADA")));
						super.save();
						valorglobal += (mbo1.getDouble("MSNUNUMVALORUNITARIOCONTRATADO") * mbo1.getDouble("MSNUNUMQUANTIDADECONTRATADA"));
					}
				}
				
				System.out.println("########## Valor Global = " + valorglobal);
				getMbo().setValue("MSNUNUMVALORTOTAL", valorglobal);
				super.save();
			}
			//mboDestino.getThisMboSet().save();
			super.save();
			
			/*WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
			Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));*/
		
		} catch (RemoteException ex) {
            Logger.getLogger(MsTbInexigibilidade.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}