/*
 * Classe de SVS para auditoria dos Status por Data para Relatórios
 */
package br.inf.ctis.common.wf;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

public class MsLogSVS implements ActionCustomClass {

	public MsLogSVS() {
		super();
		System.out.println("* Classe de LOG da SVS *");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, java.rmi.RemoteException {
		System.out.println("applyCustomAction");

		
		MboSetRemote Log = mbo.getMboSet("MSLOGSVS");
		
		Log.add();
		
		Log.setValue("STATUS", mbo.getString("STATUS"));
		Log.setValue("ID", mbo.getString("MSTBCADDEMSVSID"));
		
		Log.save(MboConstants.NOACCESSCHECK);
	}
}
