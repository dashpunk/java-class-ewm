/**
 * Aplicação de Almoxarifado da CDJU
 */
package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.inf.id2.common.util.Data;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author willians.andrade
 * @author eduardo.assis
 */
public class MsAlmox extends psdi.webclient.system.beans.AppBean {
	public MsAlmox() {
		System.out.print("CTIS # --- MsAlmox");
	}

	int selecionar = 0;

	@Override
	public void save() throws MXException {
		try {
			MboSet mboMedic = (MboSet) getMbo().getMboSet("MSTBMEDALMOX");

			int iTamanho = mboMedic.count();
			int Valid = 1;
			if (iTamanho > 0) {
				for (int i = 0; i < iTamanho; i++) {
					if (mboMedic.getMbo(i).getString("MSALNSTATUS")
							.equals("Aguardando Reserva")
							&& (mboMedic.getMbo(i).isNull("MSNUMQNTRESERV") || mboMedic
									.getMbo(i).getInt("MSNUMQNTRESERV") == 0)) {
						Valid = 0;
					}
					if (mboMedic.getMbo(i).getString("MSALNSTATUS")
							.equals("Aguardando Reserva")
							&& (mboMedic.getMbo(i).getInt("MSNUMQNTRESERV") > mboMedic
									.getMbo(i).getInt("MSNUMQNTTOTAL"))) {
						throw new MXApplicationException("msalmox",
								"InformeQtdReservadaMenor");
					}
				}
				if (Valid == 0) {
					throw new MXApplicationException("msalmox",
							"InformeQuantidadeParaReserva");
				} else {
					for (int i = 0; i < iTamanho; i++) {
						if (mboMedic.getMbo(i).getString("MSALNSTATUS")
								.equals("Aguardando Reserva")
								&& (mboMedic.getMbo(i).isNull("MSNUMQNTRESERV") || mboMedic
										.getMbo(i).getInt("MSNUMQNTRESERV") > 0)) {
							mboMedic.getMbo(i).setValue("MSALNSTATUS", "Ativo");
							mboMedic.getMbo(i).setValue("MSDATEALTERACAO",
									new Date());
						}
					}
					super.save();
				}
			}
		} catch (RemoteException ex) {
			Logger.getLogger(MsAlmox.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	public int Novo() throws MXException, RemoteException {
		System.out.print("CTIS # --- Novo");
		MboRemote Medic = app.getDataBean().getMboSet().getMbo()
				.getMboSet("MSTBMEDALMOX").add();

		String status = "Novo";
		System.out.print("CTIS # --- Setar Status e ID");
		Medic.setValue("MSALNSTATUS", status, MboConstants.NOACCESSCHECK);
		Medic.setValue("MSTBALMOXERIFADOID",
				getMbo().getInt("MSTBALMOXERIFADOID"),
				MboConstants.NOACCESSCHECK);

		Medic.setValue("MSNUMQNTTOTAL", 0, MboConstants.NOACCESSCHECK);
		Medic.setValue("MSNUMQNTRESERV", 0, MboConstants.NOACCESSCHECK);
		Medic.setValue("MSNUMQNTDISP", 0, MboConstants.NOACCESSCHECK);
		Medic.setValue("MSDATEALTERACAO", new Date());

		reloadTable();
		refreshTable();

		return EVENT_HANDLED;
	}

	public int Ativar() throws MXException, RemoteException {

		MboSet mboMedic = (MboSet) getMbo().getMboSet("MSTBMEDALMOX");

		// / Passo 1

		int iTamanho = mboMedic.count();
		if (iTamanho > 0) {
			for (int i = 0; i < iTamanho; i++) {
				String Selecionado = mboMedic.getMbo(i).getString(
						"MSSELECIONADO");
				if (Selecionado.equals("S") || Selecionado.equals("Y")
						|| Selecionado == "S" || Selecionado == "Y") {
					Date DataValidade = mboMedic.getMbo(i).getDate(
							"MSDTVALIDADE");
					if (mboMedic.getMbo(i).isNull("MSDTVALIDADE")) {
						throw new MXApplicationException("msalmox",
								"InformeDataValidadeMedicamento");
					}
					/*
					 * Atributo Removido. Solicitado pelo Glaucio 21/11/2013
					 * 10:00 if (mboMedic.getMbo(i).isNull("CATMAT") ||
					 * mboMedic.getMbo(i).getFloat("CATMAT") == 0) { throw new
					 * MXApplicationException("msalmox",
					 * "InformeQuantidadeTotal"); }
					 */
					if (mboMedic.getMbo(i).isNull("MSALNPRINCIPIO")) {
						throw new MXApplicationException("msalmox",
								"InformePrincipioAtivo");
					}
					if (mboMedic.getMbo(i).isNull("MSALNNOME")) {
						throw new MXApplicationException("msalmox",
								"InformeNomeComercial");
					}
					if (mboMedic.getMbo(i).isNull("MSALNCONCENTRACAO")) {
						throw new MXApplicationException("msalmox",
								"InformeConcentracao");
					}
					if (mboMedic.getMbo(i).isNull("MSALNUNIDADEMEDIDA")) {
						throw new MXApplicationException("msalmox",
								"InformeUnidMedida");
					}
					if (mboMedic.getMbo(i).isNull("MSNUMFATOREMB")
							|| mboMedic.getMbo(i).getInt("MSNUMFATOREMB") == 0) {
						throw new MXApplicationException("msalmox",
								"InformeFatorEmbalagem");
					}
					if (mboMedic.getMbo(i).isNull("MSALNLOTE")) {
						throw new MXApplicationException("msalmox",
								"InformeLote");
					}
					if (mboMedic.getMbo(i).isNull("MSALNLOCAL")) {
						throw new MXApplicationException("msalmox",
								"InformeLocal");
					}
					if (mboMedic.getMbo(i).isNull("MSNUMQNTTOTAL")
							|| mboMedic.getMbo(i).getFloat("MSNUMQNTTOTAL") == 0) {
						throw new MXApplicationException("msalmox",
								"InformeQuantidadeTotal");
					}
					if (Data.dataInicialMenorFinal(DataValidade, new Date())) {
						throw new MXApplicationException("msalmox",
								"ExitemMedicamentosVencidos");
					}
				}
			}
		}

		// // Passo 2
		Integer QtdMedic = 0;
		if (iTamanho > 0) {
			for (int i = 0; i < iTamanho; i++) {

				String Selecionado = mboMedic.getMbo(i).getString(
						"MSSELECIONADO");
				System.out.print("CTIS # --- Selecionado: " + Selecionado
						+ " --");
				if (Selecionado.equals("S") || Selecionado.equals("Y")
						|| Selecionado == "S" || Selecionado == "Y") {
					mboMedic.getMbo(i).setValue("MSALNSTATUS", "Ativo");
					mboMedic.getMbo(i).setValue("MSDATEALTERACAO", new Date());

					QtdMedic++;
					System.out.print("CTIS # --- Saiu Selecionado");
				}
			}
		}
		if (QtdMedic == 0) {
			throw new MXApplicationException("msalmox",
					"NenhumMedicamentoSelecionado");
		} else {
			reloadTable();
			refreshTable();
			throw new MXApplicationException("msalmox", "StatusAlteradoAtivos");
		}
	}

	public int Excluir() throws MXException, RemoteException {
		MboSet mboMedic = (MboSet) getMbo().getMboSet("MSTBMEDALMOX");
		int iTamanho = mboMedic.count();
		Integer QtdMedic = 0;
		if (iTamanho > 0) {
			for (int i = 0; i < iTamanho; i++) {
				String Selecionado = mboMedic.getMbo(i).getString(
						"MSSELECIONADO");
				if (Selecionado.equals("S") || Selecionado.equals("Y")
						|| Selecionado == "S" || Selecionado == "Y") {
					mboMedic.getMbo(i).setValue("MSALNSTATUS", "Excluído");
					mboMedic.getMbo(i).setValue("MSDATEALTERACAO", new Date());
					QtdMedic++;
				}
			}
		}
		if (QtdMedic == 0) {
			throw new MXApplicationException("msalmox",
					"NenhumMedicamentoSelecionado");
		} else {
			reloadTable();
			refreshTable();
			throw new MXApplicationException("msalmox",
					"StatusAlteradoExcluido");
		}
	}

	public void Reservar() throws MXException, RemoteException {
		try {
			MboSet mboMedic = (MboSet) getMbo().getMboSet("MSTBMEDALMOX");
			/*
			 * Se Status = Novo, Alerta: Ativar Medicamentos
			 */

			int iTamanho = mboMedic.count();
			int count = 0;
			if (iTamanho > 0) {
				for (int i = 0; i < iTamanho; i++) {
					String Selecionado = mboMedic.getMbo(i).getString(
							"MSSELECIONADO");
					if (Selecionado.equals("S") || Selecionado.equals("Y")
							|| Selecionado == "S" || Selecionado == "Y") {
						if (mboMedic.getMbo(i).getString("MSALNSTATUS")
								.equals("Novo")) {
							count++;
						}
					}
				}
				if (count > 0) {
					throw new MXApplicationException("msalmox",
							"AtivarMedicamentos");
				}
			}

			/*
			 * Reservar Medicamentos Selecionados
			 */

			iTamanho = mboMedic.count();
			Integer QtdMedic = 0;
			if (iTamanho > 0) {
				for (int i = 0; i < iTamanho; i++) {
					String Selecionado = mboMedic.getMbo(i).getString(
							"MSSELECIONADO");
					if (Selecionado.equals("S") || Selecionado.equals("Y")
							|| Selecionado == "S" || Selecionado == "Y") {
						mboMedic.getMbo(i).setValue("MSALNSTATUS",
								"Aguardando Reserva");
						mboMedic.getMbo(i).setValue("MSDATEALTERACAO",
								new Date());
						QtdMedic++;
					}
				}
			}
			if (QtdMedic == 0) {
				throw new MXApplicationException("msalmox",
						"NenhumMedicamentoSelecionado");
			} else {
				reloadTable();
				refreshTable();
				throw new MXApplicationException("msalmox",
						"InformeQuantidadeParaReserva");
			}
		} catch (RemoteException ex) {
			Logger.getLogger(MsAlmox.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	public int marcarTodos() throws MXException, RemoteException {
		selecionar = 1;
		MboSet mboMedic = (MboSet) getMbo().getMboSet("MSTBMEDALMOX");
		int iTamanho = mboMedic.count();
		if (iTamanho > 0) {
			for (int i = 0; i < iTamanho; i++) {
				mboMedic.getMbo(i).setValue("MSSELECIONADO", "S");
			}
		}
		reloadTable();

		return EVENT_HANDLED;
	}

	public int desmarcarTodos() throws MXException, RemoteException {
		selecionar = 1;
		MboSet mboMedic = (MboSet) getMbo().getMboSet("MSTBMEDALMOX");
		int iTamanho = mboMedic.count();
		if (iTamanho > 0) {
			for (int i = 0; i < iTamanho; i++) {
				mboMedic.getMbo(i).setValue("MSSELECIONADO", "N");
			}
		}
		reloadTable();

		return EVENT_HANDLED;
	}

	@Override
	public synchronized void dataChangedEvent(DataBean speaker) {

		System.out.println("CTIS # --- MsAlmox dataChangedEvent()");

		if (selecionar == 0) {
			// reloadTable();
			refreshTable();

			super.dataChangedEvent(speaker);
		}

	}
}