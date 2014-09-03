/**
 * Criação de Classe App Bean para preencher relacionamento MSTBMEDPERSISTENTE unificando o SISMAT dos registro da tabela MSTBMEDICAMENTO.
 */
package br.inf.ctis.ms.bean;


import br.inf.ctis.ms.bean.Component;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author Willians Andrade 1
 */
public class MsCotacao extends psdi.webclient.system.beans.AppBean {
	public MsCotacao() {
		System.out.print("CTIS # --- Entrou na Classe AppBean MsCotacao");
	}

	public int SAVE() throws MXException, RemoteException {
		System.out.print("CTIS # --- Entrou na Classe AppBean MsCotacao SAVE()");
		
		MboRemote mbo1;
		MboRemote mbo2;
		int contador = 0;
		
		for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBMEDICPERSISTENTE").getMbo(i)) != null); i++) {
						
			System.out.println("########## Count Cotacoes do Medicamento = " + mbo1.getMboSet("MSTBCOTCDJU").count());
			if (mbo1.getMboSet("MSTBCOTCDJU").count() > 0) {
				
				for(int j = 0; ((mbo2 = mbo1.getMboSet("MSTBCOTCDJU").getMbo(j)) != null); j++) {
					System.out.println("########## Posicao  = " + j);
					if (!mbo2.toBeDeleted()) {
						System.out.println("########## Posicao !toBeDeleted() = " + j);
						contador++;
					}
				}
			}
									
			if (contador > 1) {
				throw new MXApplicationException("cotacao", "MaisQueUmaCotacaoPorMedicamento");
			}
		}
		
		if(getMbo().isNew()){
			tempMedicamentos();
		}
		

		super.save();
		return currentRow;

	}

	public void tempMedicamentos() throws MXException, RemoteException {

		try {

			if (!getMbo().isNull("MSTBOCID")) {
				/*
				 * Cria ArrayList e Adiciona Valores do MboSetRemote
				 */

				ArrayList<Component> components = new ArrayList<Component>();
				adicionarComponentes(components);
				Collections.sort(components, new Component());

				Map<String, Component> map = new HashMap<String, Component>();
				for (Component c : components) {
					String key = c.getPartNumber();
					if (!map.containsKey(key)) {
						map.put(c.getPartNumber(), c);
					} else {
						Component comp = map.get(key);
						Integer quantity = comp.getQuantity() + c.getQuantity();
						comp.setQuantity(quantity);
					}
				}

				MboSet mboDestino = (MboSet) getMbo().getMboSet(
						"MSTBMEDICPERSISTENTE");

				int iTamanho = mboDestino.count();
				for (int i = 0; i < iTamanho; i++) {
					mboDestino.getMbo(i).delete();
				}

				//mboDestino.save();

				for (String key : map.keySet()) {
					Component c = map.get(key);

					/*
					 * Adicionar Linhas Novas
					 */

					System.out
							.print("#####  Setando Valores  ------  MSSISMAT: "
									+ c.getId() + " --- MSQTD: "
									+ c.getQuantity() + " --- MSTBOCID: "
									+ getMbo().getInt("MSTBOCID"));

					mboDestino.add();

					mboDestino.setValue("MSSISMAT", c.getId());
					mboDestino.setValue("MSQTD", c.getQuantity());
					mboDestino.setValue("MSTBCADCOTID",
							getMbo().getInt("MSTBCADCOTID"));
					mboDestino
							.setValue("MSTBOCID", getMbo().getInt("MSTBOCID"));

				}

				//mboDestino.save();
				refreshTable();
				reloadTable();
			}

			// reloadTable();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void adicionarComponentes(ArrayList<Component> components)
			throws MXException, RemoteException {

		MboSet mboOrigem = (MboSet) getMbo().getMboSet("MSTBMEDICAMENTO");

		int iTamanho = mboOrigem.count();
		if (iTamanho > 0) {
			for (int i = 0; i < iTamanho; i++) {
				String mssismat = Integer.toString(mboOrigem.getMbo(i).getInt(
						"MSSISMAT"));
				Integer qntd = mboOrigem.getMbo(i).getInt("MSQTD");
				System.out
						.print("##### adicionarComponentes  ------   MSSISMAT: "
								+ mboOrigem.getMbo(i).getInt("MSSISMAT")
								+ " --- MSQTD: "
								+ mboOrigem.getMbo(i).getInt("MSQTD")
								+ " --- MSTBOCID: "
								+ getMbo().getInt("MSTBOCID"));
				components.add(new Component(mssismat, mssismat, qntd));
			}
		}

	}
}
