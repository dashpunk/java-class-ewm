package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import br.inf.id2.common.util.Data;
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

		Calendar dataAtualizacao = Calendar.getInstance(); 
		dataAtualizacao.setTime(getMboValue().getDate());
		
		System.out.println("########## dataAtesto = " + dataAtualizacao);
		System.out.println("########## dataAtesto.MONTH = " + dataAtualizacao.get(Calendar.MONTH));
		System.out.println("########## dataAtesto.YEAR = " + dataAtualizacao.get(Calendar.YEAR));
		String mesAno = dataAtualizacao.get(Calendar.MONTH)+"/"+dataAtualizacao.get(Calendar.YEAR);
		System.out.println("########## mesAno = " + mesAno);
		getMboValue("MSALCODMESANO").setValue(mesAno);
		
	}

}
