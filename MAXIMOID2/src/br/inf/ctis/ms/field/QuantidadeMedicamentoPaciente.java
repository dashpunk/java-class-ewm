package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class QuantidadeMedicamentoPaciente extends MboValueAdapter {

	public QuantidadeMedicamentoPaciente(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		
		if(getMboValue().isNull()) {
			throw new MXApplicationException("msmedpac", "QuantidadeNula");
		} else {
			
			double quantidadesomada = 0d;
			System.out.println("########## Quantidade total do item na demanda: " + getMboValue().getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getDouble("MSQTD"));
			double quantidadetotalitem = getMboValue().getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getDouble("MSQTD");
			
			MboRemote mbo1;
			
			for (int j = 0; ((mbo1 = getMboValue().getMbo().getMboSet("MSMEDPAC").getMbo(j)) !=null); j++) {
				if (mbo1.getInt("MSMEDPACID") != getMboValue("MSMEDPACID").getInt()) {
					System.out.println("########## Quantidade do item para o paciente: " + mbo1.getDouble("MSNUNUMQUANTIDADE"));
					quantidadesomada += mbo1.getDouble("MSNUNUMQUANTIDADE");
					System.out.println("########## Quantidade somada atual: " + quantidadesomada);
				}
				
			}
			
			quantidadesomada += getMboValue().getDouble();
			System.out.println("########## Quantidade somada final: " + quantidadesomada);
			
			if(quantidadesomada > quantidadetotalitem) {
				getMboValue().setValue(0);
				throw new MXApplicationException("msmedpac", "QuantidadeExcedente");
			} else {
				System.out.println("########## Valor nao excede");
			}
			
		}
		
	}
	
}
