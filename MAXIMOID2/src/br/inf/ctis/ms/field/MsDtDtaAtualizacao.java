package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
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

		Date dataAtualizacao = getMboValue().getDate();
		System.out.println("########## dataAtesto = " + dataAtualizacao);
		String mesAno = dataAtualizacao.getMonth()+"/"+dataAtualizacao.getYear();
		System.out.println("########## mesAno = " + mesAno);
		getMboValue("MSALCODMESANO").setValue(mesAno);
		
	}

}
