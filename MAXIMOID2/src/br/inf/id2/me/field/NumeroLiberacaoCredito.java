package br.inf.id2.me.field;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Dyogo
 */
public class NumeroLiberacaoCredito extends MboValueAdapter {

	public NumeroLiberacaoCredito(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void initValue() throws MXException, RemoteException {
		super.initValue();

		int numero = getMboValue().getMbo().getInt("MXTBSOLICREID");
		String mxnumslc = getMboValue().getMbo().getString("MXNUMSLC");
		System.out.println("######### Numero = " + numero + "| mxnumslc = " + mxnumslc);
		if (mxnumslc == null || mxnumslc.indexOf("/") == -1) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			int anoCorrente = calendar.get(Calendar.YEAR);

			getMboValue().getMbo().setValue("MXNUMSLC", numero + "/" + anoCorrente);
		}
	}
}
