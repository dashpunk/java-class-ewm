package br.inf.ctis.ms.wf;

import java.rmi.RemoteException;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class AlteraFlagTarefaAgAjusteFalse implements ActionCustomClass {

	public AlteraFlagTarefaAgAjusteFalse() {
		super();
		System.out.println("########## Flag MSNUFLGAGUARDANDOAJUSTE de WO Activity com mesmo pai para false");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, RemoteException {
		
		System.out.println("########## applyCustomAction");
		
		System.out.println("########## WONUM PAI: " + mbo.getString("WONUM"));
		MboRemote mbo2;
		
		for (int i = 0; ((mbo2 = mbo.getMboSet("WOACTIVITY").getMbo(i)) != null); i++){
			
			System.out.println("########## WOACTIVITY ID: " + mbo2.getString("WONUM") + " WONUM PAI: " + mbo2.getString("PARENT"));
						   
			mbo2.setValue("MSNUFLGAGUARDANDOAJUSTE", false, MboConstants.NOACCESSCHECK);
			if(mbo2.getString("STATUS").equalsIgnoreCase("AG. AJUSTES")) {
				mbo2.setValue("STATUS", "ANALISANDO PROC", MboConstants.NOACCESSCHECK);
			}
			
		}	
		
		mbo.getMboSet("WOACTIVITY").save(MboConstants.NOACCESSCHECK);
		
	}

}
