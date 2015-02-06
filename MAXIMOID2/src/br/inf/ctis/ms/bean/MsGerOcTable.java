/**
 * 
 */
package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author willians.andrade
 *
 */
public class MsGerOcTable extends DataBean {
	 
	public MsGerOcTable() {
	 
	}
	 
	 public int excluirMedic() throws MXException, RemoteException {
		 // Mensagem de Alerta
		 if(!getMbo().getString("MSSTATUS").equalsIgnoreCase("APROVADA")) {
		
			 String yesNoId = getClass().getName();
			 int userInput = MXApplicationYesNoCancelException.getUserInput(yesNoId, MXServer.getMXServer(), getMbo().getUserInfo());
			 switch (userInput) {
			 	case MXApplicationYesNoCancelException.NULL:
			 		Object params[] = { null };
			 		throw new MXApplicationYesNoCancelException(yesNoId, "msgeroc","ExcluirRegistrosYorN", params);
			 	case MXApplicationYesNoCancelException.YES:
			 		// Seleciona a Posição da Tabela de Medicamentos
			 		
			 		int OrdemCompra = getMboSet().getMbo(getMboSet().getCurrentPosition()).getInt("MSTBOCID");
			 		String MsSismat = getMboSet().getMbo(getMboSet().getCurrentPosition()).getString("MSSISMAT");
			 		
			 		System.out.println(">>>>>> OC: " + OrdemCompra + " SISMAT: " + MsSismat);
			 		
			 		//Excluindo Cotações
			 		
			 		MboSetRemote mboSetCotacoes;
			 		mboSetCotacoes = getMbo().getMboSet("MSTBCOTCDJUALL");
					
			 		mboSetCotacoes.deleteAll();
			 		
			 		// Excluir Medicamento
			 		  
			 		MboSetRemote mboSetMedic;
			 		mboSetMedic = getMbo().getMboSet("MSTBLOGMEDICAMENTO");
					
			 		mboSetMedic.deleteAll();
					
					// Retornando Medicamento para Lista
			 	
					
					MboRemote mboDestino;
					
					for (int i = 0; ((mboDestino= getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(i)) !=null); i++) {
						mboDestino.setValue("STATUSCOT", "RETORNO");
						mboDestino.setValue("MSTBOCID", "");
						mboDestino.setValue("SELECIONADO", "0");	
						if (mboDestino.isNull("MSALNRETORNO")){
							mboDestino.setValue("MSALNRETORNO", 1);
						} else {
							mboDestino.setValue("MSALNRETORNO", mboDestino.getInt("MSALNRETORNO") + 1);
						}
			 		}
			 						
			 		// Atualiza Tabelas
					save();
			 		refreshTable();
			 		reloadTable();
			 		
			 		
			 		break;
			 	case MXApplicationYesNoCancelException.NO:
			 		break;
			 	default:
			 		break;
			 }
			 
			 return EVENT_HANDLED;
		 } else {
			 throw new MXApplicationException("msgeroc", "NaoPodeExcluirMedicamentoOCAprovada");
		 }
	 }
}