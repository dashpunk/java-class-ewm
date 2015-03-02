/*
 * Alterar Status dos Medicamentos para Demandas Judiciais
 */
package br.inf.ctis.common.wf;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class AlterarStatusMedicamentos implements ActionCustomClass {

	public AlterarStatusMedicamentos() {
		super();
		System.out.println("* Altera Status dos Medicamentos *");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, java.rmi.RemoteException {
		System.out.println("applyCustomAction");

		MboRemote aMbo;

		if (mbo.getString("STATUS").equals("EMAND")) {
			for (int i = 0; ((aMbo = mbo.getMboSet("MSTBMEDICAMENTO").getMbo(i)) != null); i++) {

				if (aMbo.getString("MSALNTIPOATENDIMENTO").equals("COMPRA") && !aMbo.getString("STATUS").equalsIgnoreCase("ATEND.DEP.JUD")) {
					aMbo.setValue("STATUS", "SOL.AUT.COMPRA", MboConstants.NOACCESSCHECK);
					aMbo.setValue("MSFLWORKFLOW", "03", MboConstants.NOACCESSCHECK);					
				}

			}
			mbo.setValue("STATUS", "SOL. AUT. COMP.", MboConstants.NOACCESSCHECK);
		} else if (mbo.getString("STATUS").equals("SOL. AUT. COMP.")) {
			for (int i = 0; ((aMbo = mbo.getMboSet("MSTBMEDICAMENTO").getMbo(i)) != null); i++) {

				if (aMbo.getString("MSALNTIPOATENDIMENTO").equals("COMPRA")) {
					aMbo.setValue("STATUS", "AUT. COMPRA", MboConstants.NOACCESSCHECK);
					aMbo.setValue("MSFLWORKFLOW", "03", MboConstants.NOACCESSCHECK);										
				}

			}
			mbo.setValue("STATUS", "AUT. COMPRA", MboConstants.NOACCESSCHECK);
		} else if (mbo.getString("STATUS").equals("AUT. COMPRA")) {
			mbo.setValue("STATUS", "REAL. QUANT.", MboConstants.NOACCESSCHECK);	
			for (int i = 0; ((aMbo = mbo.getMboSet("MSTBMEDICAMENTO").getMbo(i)) != null); i++) {

				if (aMbo.getString("MSALNTIPOATENDIMENTO").equals("COMPRA")) {
					aMbo.setValue("STATUS", "REAL. QUANT.", MboConstants.NOACCESSCHECK);
					aMbo.setValue("MSFLWORKFLOW", "03", MboConstants.NOACCESSCHECK);					
				}

			}
		} else if (mbo.getString("STATUS").equals("REAL. QUANT.")) {
			mbo.setValue("STATUS", "AG. OC", MboConstants.NOACCESSCHECK);	
			for (int i = 0; ((aMbo = mbo.getMboSet("MSTBMEDICAMENTO").getMbo(i)) != null); i++) {

				if (aMbo.getString("MSALNTIPOATENDIMENTO").equals("COMPRA")) {
					aMbo.setValue("STATUS", "AG. OC", MboConstants.NOACCESSCHECK);
					aMbo.setValue("MSFLWORKFLOW", "03", MboConstants.NOACCESSCHECK);					
				}

			}
		}
		// Salva
		mbo.getMboSet("MSTBMEDICAMENTO").save(MboConstants.NOACCESSCHECK);
	}
}
