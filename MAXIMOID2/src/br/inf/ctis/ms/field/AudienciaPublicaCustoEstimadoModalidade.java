package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class AudienciaPublicaCustoEstimadoModalidade extends MboValueAdapter {

	public AudienciaPublicaCustoEstimadoModalidade(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		
		if (getMboValue().getName().equals("ID2ULTIMAMODALIDADE")) {
			if(getMboValue().getString().equalsIgnoreCase("08") && (getMboValue("LINECOST").getDouble() >= 150000000)){
				getMboValue("MSALCODAUDIENCIAPUBLICA").setValue("SIM");
			} else {
				getMboValue("MSALCODAUDIENCIAPUBLICA").setValue("NAO");
			}
		} else if (getMboValue().getName().equals("LINECOST")) {
			if(getMboValue("ID2ULTIMAMODALIDADE").getString().equalsIgnoreCase("08") && (getMboValue().getDouble() >= 150000000)){
				getMboValue("MSALCODAUDIENCIAPUBLICA").setValue("SIM");
			} else {
				getMboValue("MSALCODAUDIENCIAPUBLICA").setValue("NAO");
			}
		}			
	}
}