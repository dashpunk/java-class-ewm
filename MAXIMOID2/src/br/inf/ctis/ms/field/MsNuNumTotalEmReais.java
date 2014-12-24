package br.inf.ctis.ms.field;
import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsNuNumTotalEmReais extends MboValueAdapter {

	/*
	 * Victor Cajá
	 */
	
  public MsNuNumTotalEmReais(MboValue mbv) throws MXException {
    super(mbv);
  }
  
  @Override
  public void validate() throws MXException, RemoteException {
    super.validate();
    
    double taxaCambio = getMboValue().getDouble();
  
    double totalReal = taxaCambio * getMboValue().getMbo().getDouble("MsNuNumTotalEmReais");
    
    getMboValue().getMbo().setValue("MsNuNumTotalEmReais", totalReal); 
  }
  
}
