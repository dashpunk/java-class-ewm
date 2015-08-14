package br.inf.ctis.ms.wf;

import java.rmi.RemoteException;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class AlteraFlagTarefaAgAjusteTrue implements ActionCustomClass {

	public AlteraFlagTarefaAgAjusteTrue() {
		super();
		System.out.println("########## Flag MSNUFLGAGUARDANDOAJUSTE de WO Activity com mesmo pai para true");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, RemoteException {
		
		System.out.println("########## applyCustomAction");
		
		System.out.println("########## WONUM REGISTRO: " + mbo.getString("WONUM"));
		MboRemote mbo1 = mbo.getMboSet("WORKORDER").getMbo(0);
		System.out.println("########## WONUM PAI: " + mbo1.getString("WONUM"));
		
		System.out.println("########## WOACTIVITY ID: " + mbo.getString("WONUM") + " WONUM PAI: " + mbo.getString("PARENT"));
			
		mbo.setValue("MSNUFLGAGUARDANDOAJUSTE", true, MboConstants.NOACCESSCHECK);
		mbo.setValue("STATUS", "AG. AJUSTES", MboConstants.NOACCESSCHECK);
				
		mbo1.setValue("STATUS", "ANALISANDO PROC", MboConstants.NOACCESSCHECK);
		mbo1.getMboSet("WOACTIVITY").save(MboConstants.NOACCESSCHECK);
		
	}

}
