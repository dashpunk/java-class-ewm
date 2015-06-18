package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class LimpaItensContrato extends MboValueAdapter {

	public LimpaItensContrato(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
	
		getMboValue().getMbo().getMboSet("CONTRACTLINE").deleteAll();
		getMboValue().getMbo().getMboSet("MSTBPARCELASCONTRATO").deleteAll();
		getMboValue().getMbo().getMboSet("MSTBNECONTRATO").deleteAll();
		getMboValue().getMbo().getMboSet("CONTRACTLINE").save();
		getMboValue().getMbo().getMboSet("MSTBPARCELASCONTRATO").save();
		getMboValue().getMbo().getMboSet("MSTBNECONTRATO").save();
	}
}
