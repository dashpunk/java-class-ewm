package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author willians.andrade
 * 
 */

public class MsNumQtd extends MboValueAdapter {

	public MsNumQtd(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		
		System.out.print("********** Entrou na classe MsNumQtd.");
		super.validate();

		MboRemote MSTBINSUMOS = getMboValue().getMbo();

		// PUFOB(USD)
		if (!MSTBINSUMOS.isNull("MSNUMQNT")) {	
			
			double TotReacao = MSTBINSUMOS.getFloat("MSINTREACAO") * MSTBINSUMOS.getFloat("MSNUMQNT");
			
			MSTBINSUMOS.setValue("MSINTTOTALREAC", TotReacao);
			
			System.out.print("CTIS # " + TotReacao);						
			
		}			

	}

}
