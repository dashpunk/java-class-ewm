package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Date;
import br.inf.id2.common.util.Data;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class PaidDate extends MboValueAdapter{
	
	public PaidDate(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();

		Date dataAtesto = getMboValue().getDate();
		System.out.println("########## dataAtesto = " + dataAtesto);
		String mesAno = dataAtesto.getMonth()+"/"+dataAtesto.getYear();
		System.out.println("########## mesAno = " + mesAno);
		getMboValue("MSALCODMESANO").setValue(mesAno);
		
	}

}
