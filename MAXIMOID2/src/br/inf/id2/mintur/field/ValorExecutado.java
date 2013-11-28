package br.inf.id2.mintur.field;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class ValorExecutado extends MboValueAdapter {

	public ValorExecutado(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, java.rmi.RemoteException {
		System.out.println("*** ValorExecutado ***");

		for (int i=0; i< getMboValue().getMbo().getOwner().getMboSet("MTVW01EVOSER").count(); i++) {
			System.out.println("##################### Quantidade das tabelas");
			// MTVW01EVOSER.MTVLRTOTITEM * (MTVW01EVOSER.MTPEREXEC/100)
			double valor1 = getMboValue().getMbo().getOwner().getMboSet("MTVW01EVOSER").getMbo(i).getDouble("MTVLRTOTITEM");
			double valor2 = getMboValue().getMbo().getOwner().getMboSet("MTVW01EVOSER").getMbo(i).getDouble("MTPEREXEC");

			System.out.println("*** MTVW01EVOSER.MTVLRTOTITEM "+valor1);
			System.out.println("*** MTVW01EVOSER.MTPEREXEC "+valor2);

			//MTVW01EVOSER.MTVLREXEC
			//getMboValue().getMbo().setValue("",valor1 * (valor2 / 100));
			getMboValue().getMbo().getOwner().getMboSet("MTVW01EVOSER").getMbo(i).setValue("MTVLREXEC", valor1 * (valor2 / 100));
			
		}
		super.validate();
	}
}
