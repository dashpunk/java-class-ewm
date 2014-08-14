package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
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

		Date data = getMboValue().getDate();
		Calendar dataAtesto = Calendar.getInstance(); 
		dataAtesto.setTime(data);
		String mesAno;
		
		System.out.println("########## dataAtesto = " + data);
		System.out.println("########## dataAtesto.MONTH = " + dataAtesto.get(Calendar.MONTH)+1);
		System.out.println("########## dataAtesto.YEAR = " + dataAtesto.get(Calendar.YEAR));
		if(dataAtesto.get(Calendar.MONTH) < 10) {
			mesAno = "0"+(dataAtesto.get(Calendar.MONTH)+1)+"/"+dataAtesto.get(Calendar.YEAR);
		} else {
			mesAno = (dataAtesto.get(Calendar.MONTH)+1)+"/"+dataAtesto.get(Calendar.YEAR);
		}
		System.out.println("########## mesAno = " + mesAno);
		getMboValue("MSALCODMESANO").setValue(mesAno);
		
	}

}
