package br.inf.ctis.common.wf;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.workflow.WFInstanceRemote;
import psdi.workflow.WFInstanceSetRemote;

public class StopWorkFlow implements psdi.common.action.ActionCustomClass {
	public void applyCustomAction(MboRemote mbo, Object[] arg1)
			throws MXException, RemoteException {
		// TODO Auto-generated method stub
		WFInstanceSetRemote wfInstanceSet = (WFInstanceSetRemote) mbo.getMboSet("ACTIVEWORKFLOW");
		if (!wfInstanceSet.isEmpty()) {
			for (int i = 0; i < wfInstanceSet.count(); i++) {
				WFInstanceRemote wfInst = (WFInstanceRemote) wfInstanceSet.getMbo(i);
				wfInst.stopWorkflow("Auto Stop"); // Memo of the transaction...
				wfInstanceSet.save();
			}
		}
	}
}