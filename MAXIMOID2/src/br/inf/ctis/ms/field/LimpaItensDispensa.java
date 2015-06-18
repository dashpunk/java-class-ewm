package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class LimpaItensDispensa extends MboValueAdapter {

	public LimpaItensDispensa(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
	
		getMboValue().getMbo().getMboSet("MSTBITENSDISPENSA").deleteAll();
		getMboValue().getMbo().getMboSet("MSTBITENSDISPENSA").save();
	}
}
