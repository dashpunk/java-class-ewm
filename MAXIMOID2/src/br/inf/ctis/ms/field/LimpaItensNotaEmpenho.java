package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class LimpaItensNotaEmpenho extends MboValueAdapter {

	public LimpaItensNotaEmpenho(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
	
		if(!getMboValue().getMbo().isNew()){
			
			getMboValue().getMbo().getMboSet("MSTBITENSNOTAEMPENHO").deleteAll();
			getMboValue().getMbo().getMboSet("MSTBITENSNOTAEMPENHO").save();
		}
		
	}
}
