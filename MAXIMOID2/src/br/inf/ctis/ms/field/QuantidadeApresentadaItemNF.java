package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class QuantidadeApresentadaItemNF extends MboValueAdapter {

	public QuantidadeApresentadaItemNF(MboValue mbv) throws MXException {
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
		
		if(!getMboValue().isNull() && !getMboValue("MSNUNUMMULTIPLICADOR").isNull()) {
			getMboValue("QTYFORUI").setValue(getMboValue().getDouble() * getMboValue("MSNUNUMMULTIPLICADOR").getDouble());
			getMboValue("MSNUNUMVALORUNITARIOAPRES").setValue(getMboValue("MSNUNUMMULTIPLICADOR").getDouble() * getMboValue("UNITCOST").getDouble() );
		} else {
			throw new MXApplicationException("invoiceline", "PreencherQtdMultiplicador");
		}
		
	}
	
}
