/**
 * Classe solicitada por Glaucio
 * Calcular o valor do PGMV Unitário
 */
package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.id2.Uteis;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
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
		
		Double MedicamentoQntd = getMboValue().getMbo().getMboSet("MSTBCOTCDJU").getMbo(0).getDouble("MSQTD");
		
		if(!getMboValue().getMbo().isNull("MSNUMFATEMB") && !getMboValue().getMbo().isNull("MSNUMQNT")){
			double Total = Qntd / Fator;
			
			if ((int) Total != Total){
				//não inteiro
				throw new MXApplicationException("MsCotacao", "TotalNaoInteiro");
			}
		
			System.out.println("##### CTIS MsCotacaoPMGV -  Total: " + Total);
			
			if (Qntd < MedicamentoQntd){
				System.out.println("##### CTIS MsCotacaoPMGV -  Verifica: " + Qntd + " < "+ MedicamentoQntd);
				throw new MXApplicationException("MsCotacao", "TotalMenorQuantidadeMed");
			}
		}
		
		
		/*
		 * PMGV
		 */
		if (!getMboValue().getMbo().isNull("MSTBGGREMID")){
			MboSet mboSetPMVG;
			mboSetPMVG = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MSTBGGREM", getMboValue().getMbo().getUserInfo());
			System.out.println("##### CTIS MsCotacaoPMGV -  MSTBGGREMID: " + Uteis.getApenasNumeros(getMboValue().getMbo().getString("MSTBGGREMID")));
			mboSetPMVG.setWhere("MSTBGGREMID = '" + Uteis.getApenasNumeros(getMboValue().getMbo().getString("MSTBGGREMID")) + "'");
			mboSetPMVG.reset();
			
			Double PMGV_0 = mboSetPMVG.getMbo(0).getDouble("PMVG_0");
			Double PMGV_12 = mboSetPMVG.getMbo(0).getDouble("PMVG_12");
			Double PMGV_17 = mboSetPMVG.getMbo(0).getDouble("PMVG_17");
			Double PMGV_18 = mboSetPMVG.getMbo(0).getDouble("PMVG_18");
			Double PMGV_19 = mboSetPMVG.getMbo(0).getDouble("PMVG_19");	
			
			/*
			 * Pessoa
			 */

			String StateProvince = getMboValue().getMbo().getMboSet("PERSON").getMbo(0).getString("STATEPROVINCE");
			
			if (getMboValue().getMbo().getString("MSALNCMED").equals("SIM") && !getMboValue().getMbo().isNull("MSNUMFATEMB")){
				/*
				 * Se Campo Convênio <NAO> preencher PMGV Unitário igual ol PMVG_X da MSTBGGREM.
				 */
				if (getMboValue().getMbo().getString("MSALNCONV").equals("SIM")){
					
					System.out.println("#### CTIS - PMGV: " + PMGV_0);
					getMboValue().getMbo().setValue("MSNUMPMVG",  PMGV_0 / Fator, MboConstants.NOACCESSCHECK);
				} else if (getMboValue().getMbo().getString("MSALNGENERICO").equals("SIM") && (StateProvince.equals("MG") || StateProvince.equals("PR"))){
					System.out.println("#### CTIS - PMGV: " + PMGV_12);
					getMboValue().getMbo().setValue("MSNUMPMVG",  PMGV_12 / Fator, MboConstants.NOACCESSCHECK);
				} else {
					if(StateProvince.equals("RJ")){
						System.out.println("#### CTIS - PMGV: " + PMGV_19);
						getMboValue().getMbo().setValue("MSNUMPMVG",  PMGV_19 / Fator, MboConstants.NOACCESSCHECK);
					} else if (StateProvince.equals("SP")){
						System.out.println("#### CTIS - PMGV: " + PMGV_18);
						getMboValue().getMbo().setValue("MSNUMPMVG",  PMGV_18 / Fator, MboConstants.NOACCESSCHECK);
					} else {
						System.out.println("#### CTIS - PMGV: " + PMGV_17);
						getMboValue().getMbo().setValue("MSNUMPMVG",  PMGV_17 / Fator, MboConstants.NOACCESSCHECK);
					}
				}
			}
		}				
	}
}
