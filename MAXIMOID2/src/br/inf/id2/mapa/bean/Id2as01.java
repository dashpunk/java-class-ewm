package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *
 */
public class Id2as01 extends psdi.webclient.beans.asset.AssetAppBean {

	public Id2as01() {
		System.out.println("---- Id2as01");
	}
	
	public int receberTodosAnimais() throws MXException, RemoteException {
		System.out.println("--- receberTodosAnimais");
		MboRemote mbo;
		
		for (int i = 0; ((mbo = app.getDataBean("1351782959567").getMboSet().getMbo(i)) != null); i++) {
			mbo.setValue("STATUS", "EM USO" , MboConstants.NOACCESSCHECK);
			mbo.setValueNull("STATUS2", MboConstants.NOACCESSCHECK);
			mbo.setValue("LOCATION", mbo.getString("MACODLOC"), MboConstants.NOACCESSCHECK);
			mbo.setValueNull("MACODLOC", MboConstants.NOACCESSCHECK);
			mbo.setValue("SELECIONADO", "N", MboConstants.NOACCESSCHECK);
		}
		
		app.getDataBean("1351782959567").save();
		
		throw new MXApplicationException("id2as01", "sucessoReceberTodosAnimais");
		
//		return EVENT_HANDLED;
	}

	public int receberTodosAnimaisSelecionados() throws MXException, RemoteException {
		System.out.println("---- receberTodosAnimaisSelecionados");
		MboRemote mbo;
		
		
		
		for (int i = 0; ((mbo = app.getDataBean("1351782959567").getMboSet().getMbo(i)) != null); i++) {
			System.out.println("nr animal "+ mbo.getString("ASSETNUM"));
			System.out.println("selecionado "+ mbo.getString("SELECIONADO"));
			if (mbo.getBoolean("SELECIONADO")) {
				mbo.setValue("STATUS", "EM USO" , MboConstants.NOACCESSCHECK);
				mbo.setValueNull("STATUS2", MboConstants.NOACCESSCHECK);
				mbo.setValue("LOCATION", mbo.getString("MACODLOC"), MboConstants.NOACCESSCHECK);
				mbo.setValueNull("MACODLOC", MboConstants.NOACCESSCHECK);
				mbo.setValue("SELECIONADO", false, MboConstants.NOACCESSCHECK);
			}
		}
		
		
		
		app.getDataBean("1351782959567").save();
		
		throw new MXApplicationException("id2as01", "sucessoReceberTodosAnimaisSelecionados");
		
//		return EVENT_HANDLED;
	}


}