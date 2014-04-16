package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Date;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

public class DataEntregaParcela extends MboValueAdapter {

	public DataEntregaParcela(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();

		Date dataInicial = getMboValue().getMbo().getMboSet("PURCHVIEW").getMbo(0).getDate("STARTDATE");
						
		System.out.println("########## DATA = " + getMboValue().getMbo().getDate("MSDTDTAENTREGA"));
		System.out.println("########## QUANTIDADE DE DIAS DATA= " + Data.recuperaDiasEntreDatas(dataInicial, getMboValue().getMbo().getDate("MSDTDTAENTREGA")));
		getMboValue("MSNUNUMQUANTIDADEDIAS").setValue(Data.recuperaDiasEntreDatas(dataInicial, getMboValue().getMbo().getDate("MSDTDTAENTREGA")));
	}
}