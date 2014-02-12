package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsYnDemJud extends MboValueAdapter {
	
	public MsYnDemJud(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {

		System.out.print("********** Entrou na classe MsYnDemJud.");
		super.validate();
		
		if (getMboValue().getBoolean()) {
            getMboValue().getMbo().setValue("MSALCODMODALIDADE", "OC");
            getMboValue().getMbo().setValue("MSALCODINSTRUMENTOCONTRATACAO", "NE");
		} else if (!getMboValue().getBoolean()) {
			getMboValue().getMbo().setValueNull("MSALCODMODALIDADE");
			getMboValue().getMbo().setValueNull("MSALCODINSTRUMENTOCONTRATACAO");
			getMboValue().getMbo().setValueNull("MSALNUMMODALIDADE");
		}
	}
}
