package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsDtDtaAtualizacao extends MboValueAdapter{
	
	public MsDtDtaAtualizacao(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();

		Date data = getMboValue().getDate();
		Calendar dataAtualizacao = Calendar.getInstance(); 
		dataAtualizacao.setTime(data);
		String mesAno;
		
		System.out.println("########## dataAtesto = " + data);
		System.out.println("########## dataAtesto.MONTH = " + dataAtualizacao.get(Calendar.MONTH)+1);
		System.out.println("########## dataAtesto.YEAR = " + dataAtualizacao.get(Calendar.YEAR));
		if(dataAtualizacao.get(Calendar.MONTH) < 10) {
			mesAno = "0"+(dataAtualizacao.get(Calendar.MONTH)+1)+"/"+dataAtualizacao.get(Calendar.YEAR);
		} else {
			mesAno = (dataAtualizacao.get(Calendar.MONTH)+1)+"/"+dataAtualizacao.get(Calendar.YEAR);
		}
		System.out.println("########## mesAno = " + mesAno);
		getMboValue("MSALCODMESANO").setValue(mesAno);
		
	}

}
