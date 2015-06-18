package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class LimpaItensArp extends MboValueAdapter {

	public LimpaItensArp(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
	
		getMboValue().getMbo().getMboSet("MSTBITENSARP").deleteAll();
		getMboValue().getMbo().getMboSet("MSTBITENSARP").save();
	}
}
