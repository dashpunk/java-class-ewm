package br.inf.ctis.ms.wf;

import java.rmi.RemoteException;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class AlteraTarefaCancelado implements ActionCustomClass {

	public AlteraTarefaCancelado() {
		super();
		System.out.println("########## Status da Tarefa para CANCELADO");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, RemoteException {
		
		System.out.println("########## applyCustomAction");
		
		System.out.println("########## WONUM REGISTRO: " + mbo.getString("WONUM"));
		MboRemote mbo1 = mbo.getMboSet("WORKORDER").getMbo(0);
		System.out.println("########## WONUM PAI: " + mbo1.getString("WONUM"));
		MboRemote mbo2;
		int concluido = 1;
		
		mbo.setValue("MSNUFLGAPROVADO", true, MboConstants.NOACCESSCHECK);
		mbo.setValue("STATUS", "CANCELADO", MboConstants.NOACCESSCHECK);
						
		mbo1.getMboSet("WOACTIVITY").save(MboConstants.NOACCESSCHECK);
		
		for (int i = 0; ((mbo2 = mbo1.getMboSet("WOACTIVITY").getMbo(i)) != null); i++){
			
			System.out.println("########## WOACTIVITY ID: " + mbo2.getString("WONUM") + " WONUM PAI: " + mbo2.getString("PARENT"));
			System.out.println("########## Status: " + mbo2.getString("STATUS"));
			
			if(!mbo2.getString("STATUS").equalsIgnoreCase("COMPRA AUT.") && !mbo2.getString("STATUS").equalsIgnoreCase("CANCELADO") && !mbo2.getString("STATUS").equalsIgnoreCase("CONCLUIDO")){
				concluido = 0;
			}
			
			System.out.println("########## concluido: " + concluido);
		}	
		
		if(concluido == 1){
			mbo1.setValue("STATUS", "AUT. COMPRA", MboConstants.NOACCESSCHECK);
			mbo.getMboSet("WORKORDER").save(MboConstants.NOACCESSCHECK);
		}
		
		mbo1.getMboSet("WOACTIVITY").save(MboConstants.NOACCESSCHECK);
		mbo.getMboSet("WORKORDER").save(MboConstants.NOACCESSCHECK);
	}

}
