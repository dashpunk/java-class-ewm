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
						
		if (getMboValue().getName().equals("MSNUNUMQUANTIDADEDIAS")) {
			getMboValue("MSDTDTAENTREGA").setValue(Data.getDataAcrescimo(dataInicial, getMboValue().getMbo().getInt("MSNUNUMQUANTIDADEDIAS")));			
		} else if (getMboValue().getName().equals("MSDTDTAENTREGA")) {
			getMboValue("MSNUNUMQUANTIDADEDIAS").setValue(Data.recuperaDiasEntreDatas(dataInicial, getMboValue().getMbo().getDate("MSDTDTAENTREGA")));
		}
	}
}