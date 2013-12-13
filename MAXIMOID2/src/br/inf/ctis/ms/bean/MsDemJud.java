/**
 * 
 */
package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 * @author willians.andrade
 * 
 */
public class MsDemJud extends
		psdi.plusp.webclient.beans.pluspwo.PlusPWorkorderAppBean {

	public MsDemJud() {
		System.out.print("CTIS # --- Entrou na Classe AppBean MsDemJud");
	}

	public void save() throws MXException {

		try {
			System.out.print("CTIS # --- Entrou no Save()");
			MboRemote mboMedicamentos = null;
			
			for (int i = 0; ((mboMedicamentos = getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(i)) != null); i++) {
				mboMedicamentos.setValue("SELECIONADO", false);
			}
			super.save();
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
