package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class QuantidadeRemessa extends MboValueAdapter {

	public QuantidadeRemessa(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		//MSNUNUMQUANTIDADEREMESSA
		//Relacionamento com Remessa: MSREMESSA
		//Relacionamento com Lote: MSLOTEAGEND
		//Relacionamento com Med. Paciente: MSMEDPAC
		//Relacionamento com Med. Guia mesmo lote: MSTBMEDGUIAMESMOLOTE
		
		if(getMboValue().isNull()) {
			throw new MXApplicationException("msguiareme", "QuantidadeNula");
		} else {
			//VERIFICACAO DO LOTE
			double quantidadesomadalote = 0d;
			
			System.out.println("########## Quantidade total do item no LOTE: " + getMboValue().getMbo().getMboSet("MSLOTEAGEND").getMbo(0).getDouble("MSNUNUMQUANTIDADE"));
			double quantidadelote = getMboValue().getMbo().getMboSet("MSLOTEAGEND").getMbo(0).getDouble("MSNUNUMQUANTIDADE");
			
			MboRemote mbo1;
			
			for (int i = 0; ((mbo1 = getMboValue().getMbo().getMboSet("MSTBMEDGUIAMESMOLOTE").getMbo(i)) !=null); i++) {
				if (mbo1.getInt("MSTBMEDGUIAID") != getMboValue("MSTBMEDGUIAID").getInt()) {
					System.out.println("########## Quantidade do item: " + mbo1.getDouble("MSQTD"));
					quantidadesomadalote += mbo1.getDouble("MSQTD");
					System.out.println("########## Quantidade somada lote atual: " + quantidadesomadalote);
				}
				
			}
			
			quantidadesomadalote += getMboValue().getDouble();
			System.out.println("########## Quantidade somada lote final: " + quantidadesomadalote);
			
			if(quantidadesomadalote > quantidadelote) {
				getMboValue().setValue(0);
				throw new MXApplicationException("msguiareme", "QuantidadeExcedenteParaLote");
			} else {
				System.out.println("########## Valor nao excede");
			}
			//VERIFICACAO DO LOTE
			
			//VERIFICACAO DO PACIENTE
			double quantidadesomadapaciente = 0d;
			
			System.out.println("########## Quantidade total do item para o PACIENTE na DEMANDA: " + getMboValue().getMbo().getMboSet("MSMEDPAC").getMbo(0).getDouble("MSNUNUMQUANTIDADE"));
			double quantidadepaciente = getMboValue().getMbo().getMboSet("MSMEDPAC").getMbo(0).getDouble("MSNUNUMQUANTIDADE");
			
			MboRemote mbo2;
			
			for (int j = 0; ((mbo2 = getMboValue().getMbo().getMboSet("MSTBMEDGUIAMESMOPACIENTE").getMbo(j)) !=null); j++) {
				if (mbo2.getInt("MSTBMEDGUIAID") != getMboValue("MSTBMEDGUIAID").getInt()) {
					System.out.println("########## Quantidade do item: " + mbo2.getDouble("MSQTD"));
					quantidadesomadapaciente += mbo2.getDouble("MSQTD");
					System.out.println("########## Quantidade somada paciente atual: " + quantidadesomadapaciente);
				}
				
			}
			
			quantidadesomadapaciente += getMboValue().getDouble();
			System.out.println("########## Quantidade somada paciente final: " + quantidadesomadapaciente);
			
			if(quantidadesomadapaciente > quantidadepaciente) {
				getMboValue().setValue(0);
				throw new MXApplicationException("msguiareme", "QuantidadeExcedenteParaPaciente");
			} else {
				System.out.println("########## Valor nao excede");
			}
			
		}
	}
}