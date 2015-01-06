package br.inf.ctis.ms.wf;

import java.rmi.RemoteException;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class AlteraTarefaCompraAut implements ActionCustomClass {

	public AlteraTarefaCompraAut() {
		super();
		System.out.println("########## Flag MSNUFLGAGUARDANDOAJUSTE de WO Activity com mesmo pai para true");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, RemoteException {
		
		System.out.println("########## applyCustomAction");
		
		System.out.println("########## WONUM REGISTRO: " + mbo.getString("WONUM"));
		MboRemote mbo1 = mbo.getMboSet("WORKORDER").getMbo(0);
		System.out.println("########## WONUM PAI: " + mbo1.getString("WONUM"));
		MboRemote mbo2;
		
		for (int i = 0; ((mbo2 = mbo1.getMboSet("WOACTIVITY").getMbo(i)) != null); i++){
			
			System.out.println("########## WOACTIVITY ID: " + mbo2.getString("WONUM") + " WONUM PAI: " + mbo2.getString("PARENT"));
			
			mbo2.setValue("MSNUFLGAPROVADO", true, MboConstants.NOACCESSCHECK);
			mbo2.setValue("STATUS", "COMPRA AUT.", MboConstants.NOACCESSCHECK);
		}	
		
		mbo1.setValue("STATUS", "REAL. QUANT.", MboConstants.NOACCESSCHECK);
		mbo1.getMboSet("WOACTIVITY").save(MboConstants.NOACCESSCHECK);
		
	}

}
