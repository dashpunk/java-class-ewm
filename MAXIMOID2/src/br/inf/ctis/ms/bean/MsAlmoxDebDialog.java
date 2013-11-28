/**
 * Classe para a Dialog de Almoxarifado.
 * Realiza o saldo para o medicamento selecionado.
 * br.inf.ctis.ms.bean.MsAlmoxDebDialog
 */
package br.inf.ctis.ms.bean;

/**
 * @author willians.andrade
 * @author eduardo.assis
 */
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class MsAlmoxDebDialog extends DataBean {

	public void debitar() throws MXException, RemoteException {
		//int valor = app.getDataBean("MAINRECORD").getMbo().getInt("MSNPBAIXA");
		int valor = getMbo().getInt("MSNPBAIXA");
		
		if (valor == 0
				|| getMbo().isNull("MSNPBAIXA")) {
			throw new MXApplicationException("msalmoxDialog",
					"InformeValorParaDebitar");
		}
		System.out.println("################ debitarValor(debitar)");
		debitarValor(valor);
	}

	private void debitarValor(int valor) throws MXException, RemoteException {

		System.out.println("################ debitarValor()");

		/*
		 * Todos os Medicamentos
		 */
		MboSet mboMedic = (MboSet) app.getDataBean("MAINRECORD").getMbo()
				.getMboSet("MSTBMEDALMOX");
		int iTamanho = mboMedic.count();
		int count = 0;
		int mboMedicamento = 0;

		/*
		 * Verificar qual esta selecionado
		 */
		if (iTamanho > 0) {
			for (int i = 0; i < iTamanho; i++) {
				String Selecionado = mboMedic.getMbo(i).getString(
						"MSSELECIONADO");
				if (Selecionado.equals("S") || Selecionado.equals("Y")
						|| Selecionado == "S" || Selecionado == "Y") {
					count++;
					mboMedicamento = i;
				}
			}

			if (count == 1) {
				System.out.println("################ MsAlmoxDebDialog (count == 1)");
				int qntTotal = mboMedic.getMbo(mboMedicamento).getInt(
						"MSNUMQNTTOTAL")
						- valor;
				int qntReservada = mboMedic.getMbo(mboMedicamento).getInt(
						"MSNUMQNTRESERV")
						- valor;

				if (valor <= mboMedic.getMbo(mboMedicamento).getInt(
						"MSNUMQNTRESERV")) {

					mboMedic.getMbo(mboMedicamento).setValue("MSNUMQNTTOTAL",
							qntTotal);
					mboMedic.getMbo(mboMedicamento).setValue("MSNUMQNTRESERV",
							qntReservada);
					mboMedic.getMbo(mboMedicamento).setValue("MSALNSTATUS",
							"Debitado");
				} else {

					throw new MXApplicationException("msalmoxDialog",
							"InformeValorInferiorReserv");

				}
			}
			if (count > 1) {
				throw new MXApplicationException("msalmoxDialog",
						"InformeSomenteUmValor");
			}
			if (count <= 0) {
				throw new MXApplicationException("msalmoxDialog",
						"InformeQualMedicamentoDebitar");
			}
		}
		/*
		 * Fechar Dialog
		 */
		WebClientEvent event = sessionContext.getCurrentEvent();
		Utility.sendEvent(new WebClientEvent("dialogclose",
				event.getTargetId(), event.getValue(), sessionContext));

		/*
		 * Atualizar
		 */
		app.getDataBean("MAINRECORD").refreshTable();
		app.getDataBean("MAINRECORD").reloadTable();
	}

}