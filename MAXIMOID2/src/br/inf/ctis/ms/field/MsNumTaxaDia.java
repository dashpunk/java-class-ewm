/**
 * Preenche os campos totalizados baseado no valor da Taxa de Câmbio do Dia;
 */
package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author willians.andrade
 * 
 */
public class MsNumTaxaDia extends MboValueAdapter {

	public MsNumTaxaDia(MboValue mbv) {
		super(mbv);
	}

	public void validate() throws MXException, RemoteException {

		System.out.print("********** Entrou na classe MsNumTaxaDia.");
		super.validate();

		if (!getMboValue().getMbo().isNull("MSNUMTXDIA")) {
			Float TaxaCambioDia = getMboValue().getMbo().getFloat("MSNUMTXDIA");

			Float CifUSD = getMboValue().getMbo().getFloat("MSNUMTOTALCIF");
			Float CifPorcentUSD = getMboValue().getMbo().getFloat(
					"MSNUMTOTALCIF35");
			Float PorcentUSD = getMboValue().getMbo().getFloat("MSNUMTOTAL35");

			getMboValue().getMbo().setValue("MSNUMTOTALCIFREAIS",
					CifUSD * TaxaCambioDia, MboConstants.NOACCESSCHECK);

			getMboValue().getMbo().setValue("MSNUMTOTALCIF35REAIS",
					CifPorcentUSD * TaxaCambioDia, MboConstants.NOACCESSCHECK);

			getMboValue().getMbo().setValue("MSNUNTOTALIZ35RS",
					PorcentUSD * TaxaCambioDia, MboConstants.NOACCESSCHECK);
		}
	}
}