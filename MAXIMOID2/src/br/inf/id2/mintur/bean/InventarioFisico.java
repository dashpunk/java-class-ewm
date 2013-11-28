package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;

import psdi.mbo.*;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import br.inf.id2.common.util.Executa;

/**
 * 
 * @author Jessé Rovira
 *
 */

public class InventarioFisico extends AppBean
{

	public InventarioFisico() {
		// TODO Auto-generated constructor stub
	}
	
	public void carregaPatrimonios() throws RemoteException, MXException {
		
		try
		{
			System.out.println("Entrou no metodo");
			MboSet mboSetAsset = (MboSet) getMbo().getMboSet("MXRL01ASSET");
			System.out.println("############# Sem o setWhere = " + mboSetAsset.count());
			mboSetAsset.setWhere("id2tipass = '01'");
			System.out.println("############# Com o SetWhere = " + mboSetAsset.count());
			
			Executa.adiciona(mboSetAsset, (MboSet)getMbo().getMboSet("MXRL01INVPAT"), new Object[]{"ASSETNUM", getMbo().getInt("MXTBINVFISID"),"#"+getMbo().getString("MXUNIORG")}, new String[]{"ASSETNUM","MXTBINVFISID","MXUNIORG"});
			app.getDataBean("1311777725267").refreshTable();
			app.getDataBean("1311777725267").reloadTable();
			System.out.println("Executou o metodo");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	
	}	
	
}