package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 
 * @author Leysson Barbosa Moreira
 * 
 */
public class ID2Map08 extends AppBean {

	public ID2Map08() {

	}
	
	
	@Override
 	public  void save() throws MXException {

	try {
		if(getMbo() != null) {
			validarSalvar();
		}
	} catch (RemoteException e) {
		e.printStackTrace();
	}
 		super.save();
 	}

	
	private void validarSalvar() throws MXException, RemoteException {
		 MboSet mboMaProcon = (MboSet) getMbo().getMboSet("MARL01IMP");
		if(getMbo().getBoolean("ID2SEMCON48") == false) {
			if(mboMaProcon.count() <= 0 && getMbo().getString("ID2STAMAP48").equalsIgnoreCase("CONCLUÍDO")) {
				throw new MXApplicationException("id2mapa08", "condProdutoNull");
			}
		}
		
		for (int i = 0; ((mboMaProcon.getMbo(i)) != null); i++) {
			if(!getMbo().isNew() && mboMaProcon.getMbo(i).toBeDeleted()) {
				mboMaProcon.getMbo().getThisMboSet().save();
				if(mboMaProcon.count() <= 0 && getMbo().getString("ID2STAMAP48").equalsIgnoreCase("CONCLUÍDO")) {
					throw new MXApplicationException("id2mapa08", "condProdutoNull");
				}
			}
		}
		super.save();
	}
}
