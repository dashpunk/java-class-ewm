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
		System.out.println("############ --- MsGerOC");
	}

	@Override
	public void save() throws MXException {
		try {
			
			super.save();
			
			System.out.println("################# Entrou na Classe MsGerOC");
			MboSet mboSetVenc;
			mboSetVenc = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MSTBCOTCDJU", getMbo().getUserInfo());
			mboSetVenc.setWhere("MSTBOCID = '" + getMbo().getString("MSTBOCID") + "' and msflvenc = '1'");
			mboSetVenc.reset();
			
			float Total = 0;
			System.out.println("################# Total = 0");
			
			for (int i = 0; mboSetVenc.count() > i ; i++){
				System.out.println("################# Entrou no for I: " + i);
				String medicamento = mboSetVenc.getMbo(i).getString("MSSISMAT");
				
				System.out.println("################# Medicamento: " + medicamento);
				
				int qtnSismat = 0;
				
				for (int j = 0; mboSetVenc.count() > j; j++){
					if ( mboSetVenc.getMbo(j).getString("MSSISMAT").equals(medicamento)){
						qtnSismat++;
						System.out.println("################# Sismat QTD: " + qtnSismat);
					}
					
					if (qtnSismat > 1){
						//error
						throw new MXApplicationException("msgeroc", "MaisDeUmSismatSelecionado");
					}
				} 
				
				if (mboSetVenc.getMbo(i).getBoolean("MSFLVENC")){
					System.out.println("################# Soma Total");
					Total += mboSetVenc.getMbo(i).getFloat("MSNUMPREC");
				}
			}
			System.out.println("################# Setou Valor Total : " + Total);
			getMbo().setValue("MSNUMTOTAL", Total);
			
		
			super.save();
			
			refreshTable();
			reloadTable();
			
		} catch (RemoteException ex) {
			Logger.getLogger(MsGerOC.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}
}
