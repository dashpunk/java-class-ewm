/*
 * Gerar Total de Item Confirmados
 */

package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MsGerOC extends psdi.webclient.system.beans.AppBean {

	public MsGerOC() {
		System.out.print("############ --- MsGerOC");
	}

	@Override
	public void save() throws MXException {
		try {
			
			MboSet mboSetVenc;
			mboSetVenc = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MSTBCOTCDJU", getMbo().getUserInfo());
			mboSetVenc.setWhere("MSTBOCID = '" + getMbo().getString("MSTBOCID") + "' and msflvenc = '1'");
			mboSetVenc.reset();
			
			float Total = 0;
			
			for (int i = 1; mboSetVenc.count() > i ; i++){
				String medicamento = mboSetVenc.getString("MSSISMAT");
				int qtdSismat = 0;
				for (int j = 1; mboSetVenc.count() > j; j++){
					if (medicamento == mboSetVenc.getString("MSSISMAT")){
						qtdSismat++;
					}
					if (qtdSismat > 1){
						throw new MXApplicationException("msgeroc","SismatMaiorQueUm");
					}
					qtdSismat = 0;
				}
				
				if (mboSetVenc.getBoolean("MSFLVENC")){
					Total += mboSetVenc.getFloat("MSNUMPREC");
				}
			}
			
			getMbo().setValue("MSNUMTOTAL", Total);
			
			refreshTable();
			reloadTable();

		} catch (RemoteException ex) {
			Logger.getLogger(MsAlmox.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}
}
