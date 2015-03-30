package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class Id2UltimaModalidade extends MboValueAdapter {

	public Id2UltimaModalidade(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		
		//AUDIENCIA PUBLICA
		if (getMboValue().getName().equals("ID2ULTIMAMODALIDADE")) {
			if(getMboValue().getString().equalsIgnoreCase("08") && (getMboValue("LINECOST").getDouble() >= 150000000)){
				getMboValue("MSALCODAUDIENCIAPUBLICA").setValue("SIM");
			} else {
				getMboValue("MSALCODAUDIENCIAPUBLICA").setValue("NAO");
			}
		} else if (getMboValue().getName().equals("LINECOST")) {
			if(getMboValue("ID2ULTIMAMODALIDADE").getString().equalsIgnoreCase("08") && (getMboValue().getDouble() >= 150000000)){
				getMboValue("MSALCODAUDIENCIAPUBLICA").setValue("SIM");
			} else {
				getMboValue("MSALCODAUDIENCIAPUBLICA").setValue("NAO");
			}
		}
		//AUDIENCIA PUBLICA
		
		//IMPORTADO
		if (getMboValue().getString().equalsIgnoreCase("10") || getMboValue().getString().equalsIgnoreCase("13")) {
			getMboValue("MSNUFLGIMPORTADO").setValue("SIM");
			getMboValue("MSALCODIMPORTADOMINISTERIO").setValue("SIM");
		}
		//IMPORTADO
		
		//VALOR UNITARIO DA ULTIMA AQUISICAO
		if (getMboValue().getString().equalsIgnoreCase("06") || getMboValue().getString().equalsIgnoreCase("07") || getMboValue().getString().equalsIgnoreCase("08")
				|| getMboValue().getString().equalsIgnoreCase("14") || getMboValue().getString().equalsIgnoreCase("15")) {
			
			//MSVWVALORUNITARIODEMANDA
			int ano = 0;
			Double valorUnitario = 999999999999999d;
			MboRemote mbo1;
			
			//Verifica maior ano entre as ultimas aquisicoes
			for (int i = 0; ((mbo1 = getMboValue().getMbo().getMboSet("MSVWVALORUNITARIODEMANDA").getMbo(i)) !=null); i++) {
				
				System.out.println("########## Ano: " + mbo1.getString("ANO"));
							
				if (ano < Integer.valueOf(mbo1.getString("ANO")).intValue()) {
					System.out.println("########## ano: " + ano + " < " + mbo1.getString("ANO"));
					ano = Integer.valueOf(mbo1.getString("ANO")).intValue();
				} else {
					System.out.println("########## ano: " + ano + " > " + mbo1.getString("ANO"));
				}
				
				System.out.println("########## ano: " + ano);
		
			}
			//Verifica maior ano entre as ultimas aquisicoes
			
			//Verifica valor unitario da ARP ou menor valor unitario entre as outras modalidades de aquisicao do ultimo ano
			for (int i = 0; ((mbo1 = getMboValue().getMbo().getMboSet("MSVWVALORUNITARIODEMANDA").getMbo(i)) !=null); i++) {
				
				boolean temArp = false;
								
				//Testa ano
				if (ano == Integer.valueOf(mbo1.getString("ANO")).intValue()) {
					
					//Testa ARP
					if (mbo1.getString("MODALIDADE").equalsIgnoreCase("ARP")) {
						System.out.println("########## Modalidade: " + mbo1.getString("MODALIDADE"));
						
						//Testa valor unitario
						System.out.println("########## Ano: " + ano + " == " + mbo1.getString("ANO"));
						System.out.println("########## Valor Unitario: " + mbo1.getDouble("VALOR_UNITARIO"));
						
						if (valorUnitario > mbo1.getDouble("VALOR_UNITARIO")) {
							System.out.println("########## valorUnitario: " + valorUnitario + " > " + mbo1.getDouble("VALOR_UNITARIO"));
							valorUnitario = mbo1.getDouble("VALOR_UNITARIO");
						} else {
							System.out.println("########## valorUnitario: " + valorUnitario + " < " + mbo1.getDouble("VALOR_UNITARIO"));
						}
						
						System.out.println("########## valorUnitario: " + valorUnitario);
						//Testa valor unitario
						
						temArp = true;
						
					} else if (!temArp) {
						System.out.println("########## temArp: " + temArp);
						System.out.println("########## Modalidade: " + mbo1.getString("MODALIDADE"));

						//Testa valor unitario
						System.out.println("########## Ano: " + ano + " == " + mbo1.getString("ANO"));
						System.out.println("########## Valor Unitario: " + mbo1.getDouble("VALOR_UNITARIO"));
						
						if (valorUnitario > mbo1.getDouble("VALOR_UNITARIO")) {
							System.out.println("########## valorUnitario: " + valorUnitario + " > " + mbo1.getDouble("VALOR_UNITARIO"));
							valorUnitario = mbo1.getDouble("VALOR_UNITARIO");
						} else {
							System.out.println("########## valorUnitario: " + valorUnitario + " < " + mbo1.getDouble("VALOR_UNITARIO"));
						}
						
						System.out.println("########## valorUnitario: " + valorUnitario);
						//Testa valor unitario
						
					}
					//Testa ARP
					
				} else {
					System.out.println("########## Ano: " + ano + " != " + mbo1.getString("ANO"));
				}
								
				System.out.println("########## valorUnitario: " + valorUnitario + " ###### Ano: " + ano);
				getMboValue("UNITCOST").setValue(valorUnitario);
				
			}
			//Verifica valor unitario da ARP ou menor valor unitario entre as outras modalidades de aquisicao do ultimo ano
			
		}
		//VALOR UNITARIO DA ULTIMA AQUISICAO
	}
}




