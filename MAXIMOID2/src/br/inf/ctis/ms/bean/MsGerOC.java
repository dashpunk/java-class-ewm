/*
 * Gerar Total de Item Confirmados
 */

package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
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
			
			double Total = 0;
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
			}
			
			MboRemote mbo;
			MboRemote mbo1;
			
			for (int i = 0;  ((mbo = getMbo().getMboSet("MSTBMEDICAMENTO02").getMbo(i)) !=null); i++){
				System.out.println("################# Entrou Medicamento: " + i);
				for (int j = 0;  ((mbo1 = mbo.getMboSet("MSTBCOTCDJUALL").getMbo(j)) !=null); j++){
					System.out.println("################# Entrou Cotacao: " + i + "-" + j);
					if(mbo1.getBoolean("MSFLVENC")){
						System.out.println("################# Entrou Vencedor: " + mbo1.getString("MSSISMAT"));
						
						System.out.println("################# Soma Total Linha: " + mbo1.getDouble("MSNUMQNT") * mbo1.getDouble("MSNUMPREC"));
						mbo1.setValue("MSPRECTOTAL", mbo1.getDouble("MSNUMQNT") * mbo1.getDouble("MSNUMPREC"));
						System.out.println("################# MSPRECTOTAL: " + mbo1.getDouble("MSPRECTOTAL"));
						Total += mbo1.getDouble("MSNUMQNT") * mbo1.getDouble("MSNUMPREC");
						System.out.println("################# Soma Total Parcial: " + Total);
						
						mbo.setValue("MSNUMVALUNIT", mbo1.getDouble("MSNUMPREC"));
						mbo.setValue("MSNUMTOTAL", mbo1.getDouble("MSNUMPREC") * mbo1.getDouble("MSNUMQNT"));
					} else if(!mbo1.getBoolean("MSFLVENC")){
						System.out.println("################# Entrou Perdedor: " + mbo1.getString("MSSISMAT"));
					}
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
