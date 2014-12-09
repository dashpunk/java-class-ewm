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
		
		if(getMboValue().getMbo().getMboSet("WOACTIVITY").getMbo(0).getString("STATUS").equalsIgnoreCase("VER. PROGRAMA") 
				|| getMboValue().getMbo().getMboSet("WOACTIVITY").getMbo(0).getString("STATUS").equalsIgnoreCase("EMAND")
				|| getMboValue().getMbo().getMboSet("WOACTIVITY").getMbo(0).getString("STATUS").equalsIgnoreCase("SOL. AUT. COMP.")
				|| getMboValue().getMbo().getMboSet("WOACTIVITY").getMbo(0).getString("STATUS").equalsIgnoreCase("AUT. COMPRA")) {
			
			if(getMboValue().getString().equalsIgnoreCase("COMPRA")){
				getMboValue().getMbo().setValue("STATUS", "VER.DEP.JUD");
			} else if(getMboValue().getString().equalsIgnoreCase("PROGRAMA")) {
				getMboValue().getMbo().setValue("STATUS", "RET.MED");
			} else if(getMboValue().getString().equalsIgnoreCase("ESTADO/MUNICIPIO")) {
				getMboValue().getMbo().setValue("STATUS", "ATEND.EST/MUN");
			}
		} else if(getMboValue().getMbo().getMboSet("WOACTIVITY").getMbo(0).getString("STATUS").equalsIgnoreCase("REAL. QUANT.")) {
			
			if(getMboValue().getString().equalsIgnoreCase("COMPRA")){
				getMboValue().getMbo().setValue("STATUS", "REAL. QUANT.");
			} else if(getMboValue().getString().equalsIgnoreCase("PROGRAMA")) {
				getMboValue().getMbo().setValue("STATUS", "RET.MED");
			} else if(getMboValue().getString().equalsIgnoreCase("ESTADO/MUNICIPIO")) {
				getMboValue().getMbo().setValue("STATUS", "ATEND.EST/MUN");
			}
		} else if(getMboValue().getMbo().getMboSet("WOACTIVITY").getMbo(0).getString("STATUS").equalsIgnoreCase("AG. OC")) {
			
			if(getMboValue().getString().equalsIgnoreCase("COMPRA")){
				getMboValue().getMbo().setValue("STATUS", "AG. OC");
			} else if(getMboValue().getString().equalsIgnoreCase("PROGRAMA")) {
				getMboValue().getMbo().setValue("STATUS", "RET.MED");
			} else if(getMboValue().getString().equalsIgnoreCase("ESTADO/MUNICIPIO")) {
				getMboValue().getMbo().setValue("STATUS", "ATEND.EST/MUN");
			}
		} 
	}
}