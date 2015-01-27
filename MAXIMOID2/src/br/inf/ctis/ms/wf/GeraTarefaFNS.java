package br.inf.ctis.ms.wf;

import java.rmi.RemoteException;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class GeraTarefaFNS implements ActionCustomClass {
	
	public GeraTarefaFNS() {
		super();
		System.out.println("########## Criar Tarefa para o Fluxo da FNS");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params) throws MXException, RemoteException {

		System.out.println("########## applyCustomAction");
		
		System.out.println("########## WONUM PAI: " + mbo.getString("WONUM"));
		
		if(mbo.getMboSet("WOACTIVITYFNS").isEmpty()){
			
			System.out.println("########## Não há tarefas para o FNS neste processo ainda.");
			
			MboRemote mboDestino = null;
			mboDestino = mbo.getMboSet("WOACTIVITYFNS").add();
			System.out.println("########## Tarefa criada. WONUM: " + mboDestino.getString("WONUM"));
			
			mboDestino.setValue("OWNERGROUP", "GRP_FNS", MboConstants.NOACCESSCHECK);
			System.out.println("########## OWNERGROUP: " + mboDestino.getString("OWNERGROUP"));
			
		}
		
		mbo.getMboSet("WOACTIVITYFNS").save(MboConstants.NOACCESSCHECK);
		
	}
	
}
