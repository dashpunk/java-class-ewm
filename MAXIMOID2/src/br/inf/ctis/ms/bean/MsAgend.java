package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class MsAgend extends AppBean {

	/**
	 * @author marcelosydney.lima
	 */
	
	public MsAgend() {
	}
	
	@Override
	public void save() throws MXException {
		try {
			
			MboRemote mbo1;
			MboRemote mbo2;
					
			for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBITENSNOTAEMPENHO").getMbo(i)) != null); i++) {
				if (mbo1.getMboSet("MSLOTEAGEND").count() > 0) {
				
					double somalotes = 0d;
					System.out.println("########## somalotes iniciado: " + somalotes);				
					
					for (int j = 0; ((mbo2 = mbo1.getMboSet("MSLOTEAGEND").getMbo(j)) != null); j++) {
						if (!mbo2.toBeDeleted()) {
							System.out.println("########## Quantidade do lote = " + mbo2.getDouble("MSNUNUMQUANTIDADE"));
							somalotes += mbo2.getDouble("MSNUNUMQUANTIDADE");
							System.out.println("########## somalotes somado: " + somalotes);
						}
					}
					
					System.out.println("########## somalotes total: " + somalotes);
					System.out.println("########## Quantidade Empenhada: " + mbo1.getDouble("MSNUNUMQUANTIDADEEMPENHADA"));
					
					if (somalotes > mbo1.getDouble("MSNUNUMQUANTIDADEEMPENHADA")) {
						throw new MXApplicationException("msagend", "QuantidadeExcedenteLotes");
					}
				}
			}
			super.save();
		} catch (RemoteException e) {
			Logger.getLogger(MsAgend.class.getName()).log(Level.SEVERE, null, e);
		}
		
	}
}