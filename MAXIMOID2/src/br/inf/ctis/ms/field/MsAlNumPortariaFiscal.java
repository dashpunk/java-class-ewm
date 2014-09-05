package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsAlNumPortariaFiscal extends MboValueAdapter{
	public MsAlNumPortariaFiscal(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
		
		getMboValue().getMbo().setValueNull("MSALNOMFISCALCONTRATO");
		getMboValue().getMbo().setValueNull("MSALNOMFISCALCONTRATOSUB");
		getMboValue().getMbo().setValueNull("MSALNUMSIAPEFISCALCONTRATO");
		getMboValue().getMbo().setValueNull("MSALNUMSIAPEFISCALCONTRATOSUB");
		getMboValue().getMbo().setValueNull("MSALNUMBSEFISCAL");
		getMboValue().getMbo().setValueNull("MSDTDTAPORTARIAFISCAL");
		
		try {
			getMboValue().getMbo().setValueNull("MSDTDTAPUBLICACAOPORTARIAFISCAL");
		} catch (Exception e) {
			getMboValue().getMbo().setValueNull("MSDTDTAPUBLICACAOPORTARIA");
		}
	}
}
