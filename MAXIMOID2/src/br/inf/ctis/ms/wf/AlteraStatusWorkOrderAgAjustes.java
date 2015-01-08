package br.inf.ctis.ms.wf;

import java.rmi.RemoteException;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class AlteraStatusWorkOrderAgAjustes implements ActionCustomClass {

	public AlteraStatusWorkOrderAgAjustes() {
		super();
		System.out.println("########## Status WOSTATUS de WORKORDER pai e de suas atividades para SUSPENSO");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, RemoteException {
		
		System.out.println("########## applyCustomAction");
		
		System.out.println("########## WONUM REGISTRO: " + mbo.getString("WONUM"));
		MboRemote mbo1 = mbo.getMboSet("PARENT").getMbo(0);
		System.out.println("########## WONUM PAI: " + mbo1.getString("WONUM"));
		mbo1.setValue("STATUS", "AG. AJUSTES", MboConstants.NOACCESSCHECK);
		MboRemote mbo2;
		
		for (int i = 0; ((mbo2 = mbo1.getMboSet("WOACTIVITY").getMbo(i)) != null); i++){
			
			System.out.println("########## WOACTIVITY ID: " + mbo2.getString("WONUM") + " WONUM PAI: " + mbo2.getString("PARENT"));

			mbo2.setValue("STATUS", "AG. AJUSTES", MboConstants.NOACCESSCHECK);
		}	
		
		
		mbo1.getMboSet("WOACTIVITY").save(MboConstants.NOACCESSCHECK);
		mbo.getMboSet("PARENT").save(MboConstants.NOACCESSCHECK);
		
	}

}
