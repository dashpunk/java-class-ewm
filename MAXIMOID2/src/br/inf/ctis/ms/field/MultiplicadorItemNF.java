package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MultiplicadorItemNF extends MboValueAdapter {

	public MultiplicadorItemNF(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	public void initValue() throws MXException, RemoteException {
		super.initValue();
		if(getMboValue().getMbo().isNew()){
			getMboValue().setValue(1);
		}
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		
		if(!getMboValue().isNull() && !getMboValue("MSNUNUMQUANTIDADEAPRESENTADA").isNull()) {
			getMboValue("QTYFORUI").setValue(getMboValue().getDouble() * getMboValue("MSNUNUMQUANTIDADEAPRESENTADA").getDouble());
			getMboValue("MSNUNUMVALORUNITARIOAPRES").setValue(getMboValue().getDouble() * getMboValue("UNITCOST").getDouble() );
		} else {
			throw new MXApplicationException("invoiceline", "PreencherQtdMultiplicador");
		}
		
	}
	
}
