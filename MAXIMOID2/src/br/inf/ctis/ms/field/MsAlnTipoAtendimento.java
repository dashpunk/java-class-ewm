package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsAlnTipoAtendimento extends MboValueAdapter{
	public MsAlnTipoAtendimento(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
	
		if(getMboValue().getString().equalsIgnoreCase("COMPRA") && !getMboValue().getMbo().getMboSet("WORKORDER").getMbo(0).getString("STATUS").equalsIgnoreCase("NOVA")){
			getMboValue().getMbo().setValue("STATUS", "AG. OC");
		}
	}
}
	