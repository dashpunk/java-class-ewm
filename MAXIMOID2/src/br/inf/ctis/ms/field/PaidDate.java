package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Calendar;
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

		Calendar dataAtesto = Calendar.getInstance(); 
		dataAtesto.setTime(getMboValue().getDate());
		
		System.out.println("########## dataAtesto = " + dataAtesto);
		System.out.println("########## dataAtesto.MONTH = " + dataAtesto.get(Calendar.MONTH));
		System.out.println("########## dataAtesto.YEAR = " + dataAtesto.get(Calendar.YEAR));
		String mesAno = dataAtesto.get(Calendar.MONTH)+"/"+dataAtesto.get(Calendar.YEAR);
		System.out.println("########## mesAno = " + mesAno);
		getMboValue("MSALCODMESANO").setValue(mesAno);
		
	}

}
