package br.inf.ctis.ms.app;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
*
* @author Marcelo Lima
*/

public class PurchView extends psdi.app.contract.purch.PurchView {
	
	public PurchView(MboSet ms) throws MXException, RemoteException {
		super(ms);
		// TODO Auto-generated constructor stub
	}
	
	public void copyPOLineToContract(MboRemote poLineRemote) throws MXException, RemoteException {
	    MboSetRemote contractLineSetRemote = getMboSet("CONTRACTLINE");
	    MboRemote contractLineRemote = contractLineSetRemote.add();
	    contractLineRemote.setValue("polineid", poLineRemote.getString("polineid"), 2L);
	    contractLineRemote.setValue("id2itemnum", poLineRemote.getString("id2itemnum"), 2L);
	    contractLineRemote.setValue("linetype", poLineRemote.getString("linetype"), 2L);
	    contractLineRemote.setValue("itemnum", poLineRemote.getString("itemnum"), 11L);
	    contractLineRemote.setValue("itemsetid", poLineRemote.getString("itemsetid"), 11L);
	    contractLineRemote.setValue("conditioncode", poLineRemote.getString("conditioncode"), 11L);
	    contractLineRemote.setValue("description", poLineRemote.getString("description"), 11L);
	    contractLineRemote.setValue("manufacturer", poLineRemote.getString("manufacturer"), 11L);
	    contractLineRemote.setValue("modelnum", poLineRemote.getString("modelnum"), 11L);
	    contractLineRemote.setValue("catalogcode", poLineRemote.getString("catalogcode"), 11L);
	    contractLineRemote.setValue("orderunit", poLineRemote.getString("orderunit"), 11L);
	    contractLineRemote.setValue("orderqty", poLineRemote.getString("orderqty"), 11L);
	    contractLineRemote.setValue("unitcost", poLineRemote.getString("unitcost"), 11L);
	    contractLineRemote.setValue("linecost", poLineRemote.getString("linecost"), 2L);
	    contractLineRemote.setValue("inspectionrequired", poLineRemote.getString("inspectionrequired"), 11L);
	    contractLineRemote.setValue("commodity", poLineRemote.getString("commodity"), 11L);
	    contractLineRemote.setValue("commoditygroup", poLineRemote.getString("commoditygroup"), 11L);		
	}

}
