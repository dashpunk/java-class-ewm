package br.inf.id2.mintur.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class QuilometragemSGAP01 extends MboValueAdapter {
	
	
	public QuilometragemSGAP01(MboValue mbv) {
        super(mbv);
    }

	@Override
	public void validate() throws MXException, RemoteException {
		
		if((getMboValue().getMbo().getDate("SGHORSAIDA").after(getMboValue().getMbo().getDate("SGHORCHE")))) {
			throw new MXApplicationException("horario", "horarioChegadaMenorSaida");
		}

		if ((getMboValue().getMbo().getInt("SGKMSA") > getMboValue().getMbo().getInt("SGKMCHE"))) {
			throw new MXApplicationException("quilometragem", "quilChegadaMenorQuilSaida");
		}
		
		super.validate();
	}

}
