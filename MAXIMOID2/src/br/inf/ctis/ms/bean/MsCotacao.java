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
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 * @author DUDU
 */
public class MsCotacao extends psdi.webclient.system.beans.AppBean {
	public MsCotacao() {
		System.out.print("CTIS # --- Entrou na Classe AppBean MsCotacao");
	}
	
	
	 public int SAVE() throws MXException, RemoteException {
		 System.out.print("CTIS # --- Entrou na Classe AppBean MsCotacao SAVE()");
		 
		tempMedicamentos();
		 
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

				mboDestino.deleteAndRemoveAll();

				refreshTable();
				mboDestino.save();
				
				for (String key : map.keySet()) {
					Component c = map.get(key);

					/*
					 * Adicionar Linhas Novas
					 */
					
					System.out.print("#####  Setando Valores  ------  MSSISMAT: " + c.getId() +  " --- MSQTD: " + c.getQuantity() + " --- MSTBOCID: " + getMbo().getInt("MSTBOCID"));
					
					mboDestino.add();

					mboDestino.setValue("MSSISMAT", c.getId());
					mboDestino.setValue("MSQTD", c.getQuantity());
					mboDestino.setValue("MSTBOCID", getMbo().getInt("MSTBOCID"));
					
					
				}
				refreshTable();
				mboDestino.save();
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
				String mssismat = Integer.toString(mboOrigem.getMbo(i).getInt("MSSISMAT"));
				Integer qntd = mboOrigem.getMbo(i).getInt("MSQTD");
				System.out.print("##### adicionarComponentes  ------   MSSISMAT: " + mboOrigem.getMbo(i).getInt("MSSISMAT") +  " --- MSQTD: " + mboOrigem.getMbo(i).getInt("MSQTD") + " --- MSTBOCID: " + getMbo().getInt("MSTBOCID"));
				components.add(new Component(mssismat, mssismat, qntd));
			}
		}

	}
}
