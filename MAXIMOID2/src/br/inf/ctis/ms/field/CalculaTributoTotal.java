package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author marcelosydney.lima
 * 
 */

public class CalculaTributoTotal extends MboValueAdapter {

	public CalculaTributoTotal(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		
		System.out.println("########## AL_IR = " + getMboValue().getMbo().getDouble("AL_IR"));
		System.out.println("########## AL_CSLL = " + getMboValue().getMbo().getDouble("AL_CSLL"));
		System.out.println("########## AL_CONFINS = " + getMboValue().getMbo().getDouble("AL_CONFINS"));
		System.out.println("########## AL_PIS_PASEP = " + getMboValue().getMbo().getDouble("AL_PIS_PASEP"));
		System.out.println("########## AL_ISS = " + getMboValue().getMbo().getDouble("AL_ISS"));
		System.out.println("########## AL_INSS = " + getMboValue().getMbo().getDouble("AL_INSS"));
	
		double totalTributo = getMboValue().getMbo().getDouble("AL_IR") 
				+ getMboValue().getMbo().getDouble("AL_CSLL") 
				+ getMboValue().getMbo().getDouble("AL_CONFINS") 
				+ getMboValue().getMbo().getDouble("AL_PIS_PASEP")
				+ getMboValue().getMbo().getDouble("AL_ISS") 
				+ getMboValue().getMbo().getDouble("AL_INSS");
		
		System.out.println("########## somatorio valores = " + (getMboValue().getMbo().getDouble("AL_IR") 
				+ getMboValue().getMbo().getDouble("AL_CSLL") 
				+ getMboValue().getMbo().getDouble("AL_CONFINS") 
				+ getMboValue().getMbo().getDouble("AL_PIS_PASEP")
				+ getMboValue().getMbo().getDouble("AL_ISS") 
				+ getMboValue().getMbo().getDouble("AL_INSS")));
		System.out.println("########## totalTributo = " + totalTributo);
		
		getMboValue().getMbo().setValue("TAXRATE", totalTributo);
		
		System.out.println("########## TAXRATE PREENCHIDO = " + getMboValue().getMbo().getDouble("TAXRATE"));
		
	}
	
}
