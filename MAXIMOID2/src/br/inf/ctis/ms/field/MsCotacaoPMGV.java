/**
 * Classe solicitada por Glaucio
 * Calcular o valor do PGMV Unitário
 */
package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author willians.andrade
 * 
 */
public class MsCotacaoPMGV extends MboValueAdapter {
	
	public MsCotacaoPMGV(MboValue mbv) {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		System.out.println("#### CTIS - Entrou na MsCotacaoPMGV");
		super.validate();
		
		/*
		 * Fator de Embalagem
		 */
		Double Fator = getMboValue().getMbo().getDouble("MSNUMFATEMB");
		Double Qntd = getMboValue().getMbo().getDouble("MSNUMQNT");
		
		MboSetRemote Medicamento;
		Medicamento = getMboValue().getMbo().getMboSet("MSTBCOTCDJU");
		
		Double MedicamentoQntd = Medicamento.getDouble("MSQTD");
		
		if(!getMboValue().getMbo().isNull("MSNUMFATEMB") && !getMboValue().getMbo().isNull("MSNUMQNT")){
			Double Total = Qntd / Fator;
			System.out.println("##### CTIS MsCotacaoPMGV -  Total: " + Total);
			
			if (Total < MedicamentoQntd){
				System.out.println("##### CTIS MsCotacaoPMGV -  Verifica: " + Total + " < "+ MedicamentoQntd);
				throw new MXApplicationException("MsCotacao", "TotalMenorQuantidadeMed");
			}
		}
		
		
		/*
		 * PMGV
		 */
		MboSet mboSetPMVG;
		mboSetPMVG = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MSTBGGREM", getMboValue().getMbo().getUserInfo());
		mboSetPMVG.setWhere("MSTBGGREMID = '" + getMboValue().getMbo().getString("MSTBGGREMID"));
		mboSetPMVG.reset();
		
		Double PMGV_0 = mboSetPMVG.getMbo().getDouble("PMVG_0");
		Double PMGV_12 = mboSetPMVG.getMbo().getDouble("PMVG_12");
		Double PMGV_17 = mboSetPMVG.getMbo().getDouble("PMVG_17");
		Double PMGV_18 = mboSetPMVG.getMbo().getDouble("PMVG_18");
		Double PMGV_19 = mboSetPMVG.getMbo().getDouble("PMVG_19");		
		/*
		 * Pessoa
		 */
		MboSetRemote Person;
		Person = getMboValue().getMbo().getMboSet("PERSON");
		
		String StateProvince = Person.getString("STATEPROVINCE");
		
		if (getMboValue().getMbo().getString("MSALNCMED").equals("SIM")){
			/*
			 * Se Campo Convênio <NAO> preencher PMGV Unitário igual ol PMVG_X da MSTBGGREM.
			 */
			if (getMboValue().getMbo().getString("MSALNCONV").equals("NÃO")){
				
				System.out.println("#### CTIS - PMGV: " + PMGV_0);
				getMboValue().getMbo().setValue("MSNUMPMVG",  PMGV_0, MboConstants.NOACCESSCHECK);
			} else if (getMboValue().getMbo().getString("MSALNGENERICO").equals("SIM") && (StateProvince.equals("MG") || StateProvince.equals("PR"))){
				System.out.println("#### CTIS - PMGV: " + PMGV_12);
				getMboValue().getMbo().setValue("MSNUMPMVG",  PMGV_12, MboConstants.NOACCESSCHECK);
			} else {
				if(StateProvince.equals("RJ")){
					System.out.println("#### CTIS - PMGV: " + PMGV_19);
					getMboValue().getMbo().setValue("MSNUMPMVG",  PMGV_19, MboConstants.NOACCESSCHECK);
				} else if (StateProvince.equals("SP")){
					System.out.println("#### CTIS - PMGV: " + PMGV_18);
					getMboValue().getMbo().setValue("MSNUMPMVG",  PMGV_18, MboConstants.NOACCESSCHECK);
				} else {
					System.out.println("#### CTIS - PMGV: " + PMGV_17);
					getMboValue().getMbo().setValue("MSNUMPMVG",  PMGV_17, MboConstants.NOACCESSCHECK);
				}
			}
		}
		
	}
}
