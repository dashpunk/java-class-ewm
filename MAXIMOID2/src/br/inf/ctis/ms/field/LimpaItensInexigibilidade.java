package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class LimpaItensInexigibilidade extends MboValueAdapter {

	public LimpaItensInexigibilidade(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
	
		if(!getMboValue().getMbo().isNew()){
			getMboValue().getMbo().getMboSet("MSTBITENSINEXIGIBILIDADE").deleteAll();
			getMboValue().getMbo().getMboSet("MSTBITENSINEXIGIBILIDADE").save();
		}
		
	}
}
