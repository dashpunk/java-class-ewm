package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class LimpaItensPregao extends MboValueAdapter {

	public LimpaItensPregao(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		
		if(!getMboValue().getMbo().isNew()){
		
			getMboValue().getMbo().getMboSet("MSTBITENSPREGAO").deleteAll();
			getMboValue().getMbo().getMboSet("MSTBFORNECEDORESITEMPREGAO").deleteAll();
			getMboValue().getMbo().getMboSet("MSTBITENSPREGAO").save();
			getMboValue().getMbo().getMboSet("MSTBFORNECEDORESITEMPREGAO").save();
		
		}
	}
}
