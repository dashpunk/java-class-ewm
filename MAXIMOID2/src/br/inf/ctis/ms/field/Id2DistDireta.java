package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class Id2DistDireta extends MboValueAdapter{
	
	public Id2DistDireta(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();

		System.out.println("######### ID2DISTDIRETA = " + getMboValue().getMbo().getString("ID2DISTDIRETA"));
		if(getMboValue().getMbo().getString("ID2DISTDIRETA").equalsIgnoreCase("SIM")){
			
			getMboValue().getMbo().setValueNull("ID2ESTOQUEESTRATEGICO");
			getMboValue().getMbo().setValueNull("MSNUNUMCUBAGEMALTURA");
			getMboValue().getMbo().setValueNull("MSNUNUMCUBAGEMLARGURA");
			getMboValue().getMbo().setValueNull("MSNUNUMCUBAGEMPROFUNDIDADE");
			getMboValue().getMbo().setValueNull("MSNUNUMCUBAGEMVOLUME");
			getMboValue().getMbo().setValueNull("MSALCODTIPOEMBALAGEM");
			getMboValue().getMbo().setValueNull("MSNUNUMEMBALAGEMPESO");
			getMboValue().getMbo().setValueNull("MSNUNUMQTDPOREMBALAGEM");
			getMboValue().getMbo().setValueNull("MSNUNUMQTDEMBALAGENS");
			
			System.out.println("########## TABELA: " + getMboValue().getMbo().getName());
			System.out.println("########## ID: " + getMboValue().getMbo().getInt("PRLINEID"));
			getMboValue().getMbo().getMboSet("MSTBPREVISAOENTREGA").deleteAll();
		
		}
		
		super.validate();
	}
}
