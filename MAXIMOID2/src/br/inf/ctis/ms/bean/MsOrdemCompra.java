/**
 * 
 */
package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author willians.andrade
 * @author Eduardo.Assis
 */
public class MsOrdemCompra extends DataBean {

	public MsOrdemCompra() {
		System.out.println("CTIS # --- Entrou em MsOrdemCompra(1)");
	}

	public void GerarOrdem() throws MXException {
		try {

			Integer novoIdOC = 0;
			Date dataOrdemCompra = null;

			System.out.println("CTIS # --- Entrou no GerarOrdem 1");
			MboSet mboMedicamentos = (MboSet) app.getDataBean("MAINRECORD")
					.getMbo().getMboSet("MSTBMEDICAMENTO");
			if (getMbo().getString("MSFORNEC005").equals("S")){
				
				
				if (getMbo().getString("MSFORNEC001").equals("S") 
					|| getMbo().getString("MSFORNEC002").equals("S")
					|| getMbo().getString("MSFORNEC003").equals("S")
					|| getMbo().getString("MSFORNEC004").equals("S")){
					throw new MXApplicationException("msco", "informeSomenteExclusivo");
				}
				
				
			} else if (getMbo().getString("MSFORNEC001").equals("N")
					&& getMbo().getString("MSFORNEC002").equals("N")
					&& getMbo().getString("MSFORNEC003").equals("N")
					&& getMbo().getString("MSFORNEC004").equals("N")) {
				throw new MXApplicationException("msco", "informeFornecedor");
			}

			/*
			 * Verificar se validade informada
			 */

			String validade = getMbo().getString("MSVALIDADE");

			if (validade == "") {
				throw new MXApplicationException("msco", "informeValidade");
			}

			boolean itemMarcado = false;

			int flagUm = 0;

			MboRemote mboOrdemCompra = null;
			MboRemote mboSuporteOC = null;

			for (int i = 0; i < mboMedicamentos.count(); i++) {

				Mbo mboMed = (Mbo) mboMedicamentos.getMbo(i);

				if (mboMed.getBoolean("SELECIONADO")) {
					if (flagUm == 0) {
						mboOrdemCompra = getMbo().getMboSet("MSTBOC").add();
						mboOrdemCompra.setValue("msdtvalidade", getMbo()
								.getString("MSVALIDADE"), 2L);
						dataOrdemCompra = new Date();
						mboOrdemCompra.setValue("msdata", dataOrdemCompra, 2L);
						novoIdOC = mboOrdemCompra.getInt("mstbocid");

						/*
						 * Tabela de Suporte da OC
						 */
						mboSuporteOC = getMbo().getMboSet("MSTBSUPOC").add();

						mboSuporteOC.setValue("mssismat",
								mboMed.getString("mssismat"), 2L);
						mboSuporteOC.setValue("mstbmedicamentoid",
								mboMed.getInt("mstbmedicamentoid"), 2L);
						mboSuporteOC.setValue("mstbocid", novoIdOC, 2L);

						mboSuporteOC.setValue("msfornec001", getMbo()
								.getBoolean("msfornec001"), 2L);
						mboSuporteOC.setValue("msfornec002", getMbo()
								.getBoolean("msfornec002"), 2L);
						mboSuporteOC.setValue("msfornec003", getMbo()
								.getBoolean("msfornec003"), 2L);
						mboSuporteOC.setValue("msfornec004", getMbo()
								.getBoolean("msfornec004"), 2L);
						mboSuporteOC.setValue("msfornec005", getMbo()
								.getBoolean("msfornec005"), 2L);

						flagUm = 1;
					}
					System.out.println("CTIS # --- novoIdOC: (" + novoIdOC
							+ ")");
					mboMed.setValue("MSTBOCID", novoIdOC);
					mboMed.setValue("STATUSCOT", "AG. COTACAO");
					mboMed.setValue("SELECIONADO", false);

					itemMarcado = true;
					System.out.println("CTIS # --- SetValue(" + i + ")");
				}
			}

			if (!itemMarcado) {
				throw new MXApplicationException("msco",
						"SelecioneUmMedicamento");
			}

			mboOrdemCompra.getThisMboSet().save();

			/*
			 * Salva objetos
			 */
			mboMedicamentos.save();
			mboSuporteOC.getThisMboSet().save();

			super.save();

			/*
			 * Fechar Dialog
			 */
			WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.sendEvent(new WebClientEvent("dialogclose", event
					.getTargetId(), event.getValue(), sessionContext));

			/*
			 * Atualizar
			 */
			app.getDataBean("MAINRECORD").refreshTable();
			app.getDataBean("MAINRECORD").reloadTable();

			if (itemMarcado) {
				throw new MXApplicationException("system", "novaOrdemCompra");
			}
			
		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}
}
