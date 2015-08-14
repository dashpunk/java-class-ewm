package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MsTotalEstimadoCompra extends MboValueAdapter {

	public MsTotalEstimadoCompra(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	public void validate() throws MXException, RemoteException {
		super.validate();
		
		if(!getMboValue().isNull()) {
			
			float qtdTotal =0;
			for (int i = 0; i < getMboValue().getMbo().getThisMboSet().count();i++){
				
				qtdTotal= getMboValue().getMbo().getFloat("MSVALEST") * getMboValue().getMbo().getFloat("ORDERQTY") ;
				
			}
			
			getMboValue().getMbo().getMboSet("PO").getMbo(0).setValue("MSVLRTOTALCOMPRA", qtdTotal);
			 
			} else {
			throw new MXApplicationException("poline", "PreencherTotalEstimadoCompraPO");
		}
	}
}
