package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Willians L Andrade
 * 
 */
public class MSCatMat extends MboValueAdapter {

	public MSCatMat(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {

		super.validate();
		
		System.out.println("Entrou no Validade de CATMAT");
		
		System.out.println("TIPO DE ATENDIMENTO: " + getMboValue().getMbo().getString("MSALNTIPOATENDIMENTO") + 
						   "MSMEDPAI: " + getMboValue().getMbo().getInt("MSMEDPAI"));
				
		if (!getMboValue().getMbo().getString("MSALNTIPOATENDIMENTO").equals("ESTOQUE") && getMboValue().getMbo().isNull("MSMEDPAI")){
				
				System.out.println("#### CTIS - Entrou na MSCatMat");
						
				MboRemote WORKORDER;
				WORKORDER = getMboValue().getMbo().getMboSet("WORKORDER").getMbo(0);
		
				System.out.println("CTIS ### CatMat:" + getMboValue().getMbo().getString("CATMAT") + "    -     " + getMboValue().getMbo().isNull("CATMAT") );
				
				if (verificaSeExiste(getMboValue().getMbo().getString("CATMAT")) && !getMboValue().getMbo().isNull("CATMAT")) {
					if (!WORKORDER.isNull("STATUS")) {
						if (WORKORDER.getString("STATUS").equals("ESTADO/MUNICIPIO")) {
							novaLinha("ESTADO/MUNICIPIO");
						} else if (WORKORDER.getString("STATUS").equals("VER. PROGRAMA")) {
							novaLinha("VER. PROGRAMA");
						}
					}
				} else {
					novaLinha("");
				}
				
		}
	}

	/**
	 * Verifica se existe CATMAT está inserido.
	 */
	private boolean verificaSeExiste(String CatMat) throws MXException,RemoteException {

		System.out.println("#### CTIS - MSCatMat / verificaSeExiste CatMat");
		MboSetRemote TabelaMedicamentos;
		TabelaMedicamentos = getMboValue().getMbo().getMboSet("MSTBMEDICAMENTOS");

		MboRemote mboa = null;

		if (CatMat != "") {
			for (int i = 0; ((mboa = TabelaMedicamentos.getMbo(i)) != null); i++) {
				System.out.println("#### CTIS - MSCatMat / verificaSeExiste CatMat For" + mboa.getString("CATMAT") + "     =    " + CatMat + " ? " );
				System.out.println("CTIS ### CatMat MBOA:" + mboa.getString("CATMAT").equals(CatMat));
				if (mboa.getString("CATMAT").equals(CatMat) && (getMboValue().getMbo().getInt("MSTBMEDICAMENTOID") != mboa.getInt("MSTBMEDICAMENTOID"))) {
					System.out.println("#### CTIS - MSCatMat / verificaSeExiste CatMat Return FALSE");
					throw new MXApplicationException("MsCatMat", "ValorCatmatJaExiste");
				}
			}
		} 
		return true;
	}

	/**
	 * Adiciona nova linha
	 */
	private void novaLinha(String status) throws RemoteException, MXException {
		// TODO Auto-generated method stub

		MboRemote mbo = getMboValue().getMbo();

		if (status == "ESTADO/MUNICIPIO") {
			System.out.println("#### CTIS - MSCatMat / Nova Linha: ESTADO/MUNICIPIO");
			mbo.setValue("MSALNTIPOATENDIMENTO", status, MboConstants.NOACCESSCHECK);
			mbo.setValue("STATUS", "ATEND.EST/MUN", MboConstants.NOACCESSCHECK);
		} else if (status == "VER. PROGRAMA") {
			System.out.println("#### CTIS - MSCatMat / Nova Linha: VER. PROGRAMA");
			MboSet mboSetMedicamentosVW;
			mboSetMedicamentosVW = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("VW_MEDICAMENTO_ESTOQUE", getMboValue().getMbo().getUserInfo());
			mboSetMedicamentosVW.setWhere("CO_CATMAT = '" + getMboValue().getMbo().getString("CATMAT") + "' and QT_SALDO_ATUAL > 0");
			mboSetMedicamentosVW.reset();

			if (mboSetMedicamentosVW.count() > 0) {
				System.out.println("#### CTIS - MSCatMat / Nova Linha: > 0");
				mbo.setValue("MSALNTIPOATENDIMENTO", "PROGRAMA", MboConstants.NOACCESSCHECK);
				mbo.setValue("STATUS", "RET.MED", MboConstants.NOACCESSCHECK);
			} else {
				System.out.println("#### CTIS - MSCatMat / Nova Linha: <= 0");
				mbo.setValue("MSALNTIPOATENDIMENTO", "COMPRA", MboConstants.NOACCESSCHECK);
				mbo.setValue("STATUS", "VER.DEP.JUD", MboConstants.NOACCESSCHECK);
			}
		}
		if (status == "") {
			mbo.setValue("MSALNTIPOATENDIMENTO", "", MboConstants.NOACCESSCHECK);
			mbo.setValue("DESCRIPTION", "", MboConstants.NOACCESSCHECK);
			mbo.setValue("STATUS", "", MboConstants.NOACCESSCHECK);
		}
	}
}
