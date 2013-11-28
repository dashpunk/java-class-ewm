/**
 * Calcula o Quantidade Disponivel para o Almoxarifado.
 */
package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author willians.andrade
 * @author eduardo.assis
 */
public class MsNumQntDisp extends MboValueAdapter {

	public MsNumQntDisp(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {

		System.out.print("********** Entrou na classe MsNumQntDisp.");
		super.validate();

		MboRemote MSTBMEDALMOX = getMboValue().getMbo();

		// PUFOB(USD)
		if (!MSTBMEDALMOX.isNull("MSNUMQNTTOTAL")) {

			double Total = MSTBMEDALMOX.getFloat("MSNUMQNTTOTAL")
					- MSTBMEDALMOX.getFloat("MSNUMQNTRESERV");

			MSTBMEDALMOX.setValue("MSNUMQNTDISP", Total);

			System.out.print("CTIS # " + Total);

		}

	}

}
