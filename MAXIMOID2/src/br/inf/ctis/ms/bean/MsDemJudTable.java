/**
 * Classe de Table para ação excluir.
 * Quando excluir um medicamento verifica se existe filhos de estoque para o mesmo.
 */
package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author willians.andrade
 *
 */
public class MsDemJudTable extends DataBean {
	
	public MsDemJudTable (){
		
	}
	
	public int excluirMedic() throws MXException, RemoteException {
	
		int Id_Medic = getMboSet().getMbo(getMboSet().getCurrentPosition()).getInt("MSTBMEDICAMENTOID");
		int Id_Medic_Estoque = getMboSet().getMbo(getMboSet().getCurrentPosition()).getInt("MSIDESTOQUE");
		String Status = getMboSet().getMbo(getMboSet().getCurrentPosition()).getString("MSALNTIPOATENDIMENTO");
		double Qntd =  getMboSet().getMbo(getMboSet().getCurrentPosition()).getDouble("MSQTD");
	
		System.out.println (" >>>>>> Status do Medicamento: " + Status);
		
		if (Status.equals("COMPRA")){
			System.out.println (" >>>>>> Status é " + Status);
			
			MboSet mboSetMedicamentos = (MboSet) MXServer.getMXServer().getMboSet("MSTBMEDICAMENTO", sessionContext.getUserInfo());
			mboSetMedicamentos.setWhere("MSIDESTOQUE = '" + Id_Medic_Estoque + "' and MSMEDPAI = '" + Id_Medic + "' and MSALNTIPOATENDIMENTO = 'ESTOQUE'");
			mboSetMedicamentos.reset();
			
			System.out.println (" >>>>>> Qtd Medicamentos Filhos: " + mboSetMedicamentos.count());
			if (mboSetMedicamentos.count() > 0){
				throw new MXApplicationException("MsDemJud", "MedicamentoExisteEstoque");
			} else {
				getMboSet().getMbo(getMboSet().getCurrentPosition()).delete();
			}
		} else if (Status.equals("ESTOQUE")){
			MboRemote mboAlmoxMedic;
			mboAlmoxMedic= getMbo().getMboSet("MSTBMEDALMOX").getMbo(0);
			
			double Qntd_Reserv = mboAlmoxMedic.getDouble("MSNUMQNTRESERV");
			mboAlmoxMedic.setValue("MSNUMQNTRESERV",  Qntd - Qntd_Reserv);
			
			getMboSet().getMbo(getMboSet().getCurrentPosition()).delete();
			
		} else {
			getMboSet().getMbo(getMboSet().getCurrentPosition()).delete();
		}
		
		refreshTable();
		reloadTable();
		save();
				
		return EVENT_HANDLED;
	}
}
