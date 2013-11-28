/**
 * Notas e Ordens da SVS
 * app: msnotord
 */
package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author willians.andrade
 * 
 */
public class MsNotasOrdens extends psdi.webclient.system.beans.AppBean {

	public MsNotasOrdens() {
		System.out.print("CTIS # --- MsNotasOrdens");
	}

	@Override
	public synchronized void dataChangedEvent(DataBean speaker) {

		try {
			MboRemote mboNotas = null;
			MboRemote mboOrdens = null;

			Float totalNotas = (float) 0;
			Float Cancelados = (float) 0;

			for (int i = 0; ((mboNotas = getMbo().getMboSet("MSTBNOTAORDEMNOTAS").getMbo(i)) != null); i++) {
				totalNotas += mboNotas.getFloat("MSINTVALOR");
			}				

			Float totalOrdens = (float) 0;

			for (int i = 0; ((mboOrdens = getMbo().getMboSet("MSTBNOTAORDEMBANCARIAS").getMbo(i)) != null); i++) {
				totalOrdens += mboOrdens.getFloat("MSINTVALOR");
				
				if(mboOrdens.getString("MSNALNTIPO") == "CANCELADO" ){
					Cancelados += mboOrdens.getFloat("MSINTVALOR");
				}
				
			}
			
			getMbo().setValue("MSNUMTOTALNOTAS", totalNotas);
			
			getMbo().setValue("MSNUMTOTALOB", totalOrdens);
			
			getMbo().setValue("MSNUMSALDO", totalNotas - totalOrdens);
			
			getMbo().setValue("MSNUMCANC", Cancelados);

		} catch (RemoteException ex) {
			Logger.getLogger(MsNotasOrdens.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MXException ex) {
			Logger.getLogger(MsNotasOrdens.class.getName()).log(Level.SEVERE, null, ex);
		}

		super.dataChangedEvent(speaker);
		System.out.println("CTIS # --- MsNotasOrdens Super()");
	}
}
