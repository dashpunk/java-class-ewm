package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MsNuNumValorNEUtilizado extends MboValueAdapter{
	
	public MsNuNumValorNEUtilizado(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
	
		MboRemote mbo;
		Double valorUtilizado = getMboValue().getDouble();
		System.out.println("########## MSNUNUMVALORNEUTILIZADOPREENCHIDO = " + getMboValue().getDouble());
		Double valorNE = getMboValue().getMbo().getMboSet("MSTBNOTAEMPENHO").getMbo(0).getDouble("MSNUNUMVALOREMPENHO");
		System.out.println("########## VALOR NE = " + valorNE);
		
		for (int i = 0; ((mbo = getMboValue().getMbo().getMboSet("MSTBAPNOTAEMPENHO").getMbo(i)) != null); i++) {
			
			if(mbo.getInt("MSTBAPNOTAEMPENHOID") != getMboValue("MSTBAPNOTAEMPENHOID").getInt()){
				if(!mbo.toBeDeleted()){
					System.out.println("########## MSNUNUMVALORNEUTILIZADO = " + mbo.getDouble("MSNUNUMVALORNEUTILIZADO"));
					valorUtilizado += mbo.getDouble("MSNUNUMVALORNEUTILIZADO");
					System.out.println("########## ValorUtilizado Atual = " + valorUtilizado);
				}
				
			}
		}
		
		if (valorNE < valorUtilizado){
			throw new MXApplicationException("mstbapnotaempenho", "SaldoNEInsuficiente");
		}
		
	}
}
