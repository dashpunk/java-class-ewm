package br.inf.ctis.ms.wf;

import java.rmi.RemoteException;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class AlteraMedicamentosEntregue implements ActionCustomClass {

	public AlteraMedicamentosEntregue() {
		super();
		System.out.println("########## Status Medicamentos = 'ENTREGUE'");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, RemoteException {
		
		System.out.println("########## applyCustomAction");
		
		System.out.println("########## REMESSA: " + mbo.getString("MSCODREMESSA"));
		System.out.println("########## REMESSA ID: " + mbo.getString("MSREMESSAID"));
		MboRemote mbo2;
		
		for (int i = 0; ((mbo2 = mbo.getMboSet("MSTBMEDICAMENTO").getMbo(i)) != null); i++){
			
			System.out.println("########## MSTBMEDICAMENTOID: " + mbo2.getString("MSTBMEDICAMENTOID"));
									   
			mbo2.setValue("STATUS", "ENTREGUE", MboConstants.NOACCESSCHECK);
			
		}	
		
		mbo.getMboSet("MSTBMEDICAMENTO").save(MboConstants.NOACCESSCHECK);
		
	}

}