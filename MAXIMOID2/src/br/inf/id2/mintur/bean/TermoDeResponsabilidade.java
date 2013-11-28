package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;

import psdi.mbo.*;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import br.inf.id2.common.util.Executa;

/**
 * @author Dyogo Dantas
 */

public class TermoDeResponsabilidade extends AppBean {

	public TermoDeResponsabilidade() {
	}
	
	public void carregaPatrimonios() throws RemoteException, MXException {
		
		try {
//			System.out.println("carregaPatrimonios - TermoDeResponsabilidade");
//			System.out.println("*** depois mboSetAsset");
//			System.out.println("*** MXTIPTER - "+ getMbo().getString("MXTIPTER"));

			if (getMbo().getString("MXTIPTER").equals("UO")) {
				MboSet mboSetAsset = (MboSet) getMbo().getMboSet("MXRL01ASSET");
//				System.out.println("*** IF UO");
				mboSetAsset.setWhere("id2tipass in ('01','03')");
				Executa.adiciona(mboSetAsset, (MboSet) getMbo().getMboSet(
						"MXRL01TERPAT"), new Object[] { "ASSETNUM" },new String[] { "ASSETNUM" });
				for (int i = 0; i < getMbo().getMboSet("MXRL01TERPAT").count(); i++) {
					getMbo().getMboSet("MXRL01TERPAT").getMbo(i).setValue("MXUNIORG", getMbo().getString("MXUNIORG"));
					getMbo().getMboSet("MXRL01TERPAT").getMbo(i).setValue("MXTBTERRESID", getMbo().getString("MXTBTERRESID"));
				}
				app.getDataBean("MAINRECORD").refreshTable();
				app.getDataBean("MAINRECORD").reloadTable();
			}
			if (getMbo().getString("MXTIPTER").equals("IF")) {
				MboSet mboSetOrigem = (MboSet) getMbo().getMboSet("MXRL01INVFIS").getMbo(0).getMboSet("MXRL01INVPAT");
//				System.out.println("*** IF IF");
				// System.out.println("carregaPatrimonios - TermoDeResponsabilidade");
				// MboSet mboSetAsset = (MboSet)
				// getMbo().getMboSet("MXRL01INVPAT");
				Executa.adiciona(mboSetOrigem, (MboSet) getMbo().getMboSet("MXRL01TERPAT"), new Object[] { "ASSETNUM" },new String[] { "ASSETNUM" });
				for (int i = 0; i < getMbo().getMboSet("MXRL01TERPAT").count(); i++) {
					getMbo().getMboSet("MXRL01TERPAT").getMbo(i).setValue("MXUNIORG", getMbo().getString("MXUNIORG"));
					getMbo().getMboSet("MXRL01TERPAT").getMbo(i).setValue("MXTBTERRESID", getMbo().getString("MXTBTERRESID"));
				}
				app.getDataBean("MAINRECORD").refreshTable();
				app.getDataBean("MAINRECORD").reloadTable();
			}
			if(getMbo().getString("MXTIPTER").equals("CD")) {
				MboSet mboSetOrigem = (MboSet) getMbo().getMboSet("MXRL01AUC");
				Executa.adiciona(mboSetOrigem, (MboSet) getMbo().getMboSet("MXRL01TERPAT"), new Object[] { "ASSETNUM" },new String[] { "ASSETNUM" });
				for (int i = 0; i < getMbo().getMboSet("MXRL01TERPAT").count(); i++) {
					getMbo().getMboSet("MXRL01TERPAT").getMbo(i).setValue("MXTBTERRESID", getMbo().getString("MXTBTERRESID"));
				}
				app.getDataBean("MAINRECORD").refreshTable();
				app.getDataBean("MAINRECORD").reloadTable();
			}
		} catch (Exception e) {
//			System.out.println("*** RichJhones " + e.getMessage());
		}
	
	
	}
	/*
	@Override
	public int SAVE() throws MXException, RemoteException {
		getMbo().setValue("MXSTATERRES", "FECHADO");
		return super.SAVE();
	}
	*/
}