package br.inf.id2.valec.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import br.inf.id2.common.util.Executa;

/**
 * @author Patrick
 */

public class TermoDeResponsabilidade extends AppBean
{

	public TermoDeResponsabilidade() {

	}
	
	public void carregaPatrimonios() throws RemoteException, MXException {
		
		try {
			System.out.println("carregaPatrimonios - TermoDeResponsabilidade");
			MboSet mboSetAsset = (MboSet) getMbo().getMboSet("MXRL01ASSET");
			System.out.println("############# Sem o setWhere = " + mboSetAsset.count());
			mboSetAsset.setWhere("id2tipass in ('01','03') and STATUS <> 'BAIXADO'");
			System.out.println("############# Com o SetWhere = " + mboSetAsset.count());
			Executa.adiciona(mboSetAsset, (MboSet)getMbo().getMboSet("MXRL01TERPAT"), new Object[]{"ASSETNUM"}, new String[]{"ASSETNUM"});
			for (int i=0; i < getMbo().getMboSet("MXRL01TERPAT").count(); i++) {
				getMbo().getMboSet("MXRL01TERPAT").getMbo(i).setValue("MXUNIORG", getMbo().getString("MXUNIORG"));
				getMbo().getMboSet("MXRL01TERPAT").getMbo(i).setValue("MXTBTERRESID", getMbo().getString("MXTBTERRESID"));
			}
			app.getDataBean("MAINRECORD").refreshTable();
			app.getDataBean("MAINRECORD").reloadTable();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}