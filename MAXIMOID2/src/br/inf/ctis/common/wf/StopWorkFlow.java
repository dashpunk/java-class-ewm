package br.inf.ctis.common.wf;

import java.rmi.RemoteException; 
import psdi.mbo.MboRemote;
import psdi.mbo.SqlFormat;
import psdi.util.MXException;
import psdi.workflow.WFInstanceSetRemote;
import psdi.workflow.WFInstance;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;
import psdi.workflow.WFProcess;
import psdi.workflow.WFProcessSetRemote;




public class StopWorkFlow implements psdi.common.action.ActionCustomClass {

	private static final MXLogger log = MXLoggerFactory.getLogger("maximo");
	
	public void applyCustomAction(MboRemote mbo, Object[] arg) throws MXException, RemoteException {
		
		//log.debug("Entered applyCustomAction of StopWorkFlow");
		WFInstanceSetRemote wfInstanceSet=(WFInstanceSetRemote) mbo.getMboSet("ACTIVEWFINSTANCE");
		
		System.out.println("########## WfInstance " + (WFInstanceSetRemote) mbo.getMboSet("ACTIVEWFINSTANCE"));
		System.out.println("########## WfInstance vazia?" + wfInstanceSet.isEmpty());
		if(!wfInstanceSet.isEmpty()) {
			System.out.println("########## WfInstance está vazia!");
			//log.debug("WfInstance is not empty");
			
			for(int wfInstance=0; wfInstance <wfInstanceSet.count();wfInstance++) {
				WFInstance wfInst=(WFInstance) wfInstanceSet.getMbo(wfInstance);
				String processName = wfInst.getString("processname");
				System.out.println("########## processName: " + processName);
				log.debug("processName: "+ processName); 
				WFProcessSetRemote wfProcessSet = (WFProcessSetRemote) mbo.getMboSet("WFPROCESS");
				SqlFormat sqf1 = new SqlFormat(mbo.getUserInfo(), "processname = :1 and active = 1");
				sqf1.setObject(1,"WFPROCESS","PROCESSNAME", processName);
				wfProcessSet.setWhere(sqf1.format());
				int matchingProcess = wfProcessSet.count();
				log.debug("matchingProcess: " + matchingProcess);
				WFProcess wfProcess = null;
				
				if (matchingProcess==1) {
					wfProcess = (WFProcess)wfProcessSet.getMbo();
				}
				
				// Enter memo for the closed transaction
				wfInst.stopWorkflow("Distribuido para o advogado / assessor.");
				log.debug("After stopping workflow");
				// Enter memo for restarted transaction
				wfInst.initiateWorkflow("Distribuido para o advogado / assessor.",wfProcess);
				log.debug("After initiating workflow");
				wfInstanceSet.save();
		
			} //end of For
		} //End of if(!wfInstanceSet.isEmpty())
	} //End of public void applyCustomAction
} //End of stopWorkFlow 
