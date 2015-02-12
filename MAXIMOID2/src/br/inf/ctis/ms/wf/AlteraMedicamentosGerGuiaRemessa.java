package br.inf.ctis.ms.wf;

import java.rmi.RemoteException;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class AlteraMedicamentosGerGuiaRemessa implements ActionCustomClass {

	public AlteraMedicamentosGerGuiaRemessa() {
		super();
		System.out.println("########## Status Medicamentos = 'GER.GUIA.REMESSA'");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, RemoteException {
		
		System.out.println("########## applyCustomAction");
		
		System.out.println("########## AGENDAMENTO ID: " + mbo.getString("MSAGENDAMENTOID"));
		MboRemote mbo2;
		
		for (int i = 0; ((mbo2 = mbo.getMboSet("MSTBMEDICAMENTO").getMbo(i)) != null); i++){
			
			System.out.println("########## MSTBMEDICAMENTOID: " + mbo2.getString("MSTBMEDICAMENTOID"));
									   
			mbo2.setValue("STATUS", "GER.GUIA.REMESSA", MboConstants.NOACCESSCHECK);
			
		}	
		
		mbo.getMboSet("MSTBMEDICAMENTO").save(MboConstants.NOACCESSCHECK);
		
	}

}
