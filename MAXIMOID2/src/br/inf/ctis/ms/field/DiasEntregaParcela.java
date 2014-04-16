package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

public class DiasEntregaParcela extends MboValueAdapter {

	public DiasEntregaParcela(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();

		Date dataInicial = getMboValue().getMbo().getMboSet("PURCHVIEW").getMbo(0).getDate("STARTDATE");
						
		System.out.println("########## QUANTIDADE DE DIAS = " + getMboValue().getMbo().getInt("MSNUNUMQUANTIDADEDIAS"));
		System.out.println("########## DATA ACRESCIMO = " + Data.getDataAcrescimo(dataInicial, getMboValue().getMbo().getInt("MSNUNUMQUANTIDADEDIAS")));
		getMboValue("MSDTDTAENTREGA").setValue(Data.getDataAcrescimo(dataInicial, getMboValue().getMbo().getInt("MSNUNUMQUANTIDADEDIAS")));			
	}
}