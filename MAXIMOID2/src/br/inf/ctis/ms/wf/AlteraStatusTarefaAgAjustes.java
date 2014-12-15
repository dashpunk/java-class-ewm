package br.inf.ctis.ms.wf;

import java.rmi.RemoteException;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class AlteraStatusTarefaAgAjustes implements ActionCustomClass {

	public AlteraStatusTarefaAgAjustes() {
		super();
		System.out.println("########## Status WO Activity de mesmo pai para 'AG. AJUSTES'");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, RemoteException {
		
		System.out.println("########## applyCustomAction");
		
		MboRemote mbo1 = mbo.getMboSet("PARENTPROCESS").getMbo();
		System.out.println("########## WONUM PAI: " + mbo1.getString("WONUM"));
		MboRemote mbo2;
		
		for (int i = 0; ((mbo2 = mbo1.getMboSet("WOACTIVITY").getMbo(i)) != null); i++){
			
			System.out.println("########## WOACTIVITY ID: " + mbo2.getString("WONUM") + " WONUM PAI: " + mbo2.getString("PARENT"));
			
			mbo2.setValue("STATUS", "AG. AJUSTES");
			mbo2.getString("STATUS");
		}	
		
	}

}
