package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class CalculaValorTotalItensFornPregaoSAA extends MboValueAdapter {

	public CalculaValorTotalItensFornPregaoSAA(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
				
		getMboValue("MSNUNUMVALORTOTAL").setValue(getMboValue("MSNUNUMQUANTIDADE").getDouble() * getMboValue("MSNUNUMVALORUNITARIO").getDouble());
				
	}
}
