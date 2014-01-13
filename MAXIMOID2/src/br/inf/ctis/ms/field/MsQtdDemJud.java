/**
 * Solicitado por Glaucio.Fernandes
 * Ao informar a quantidade desejada verifica se existe estoque
 * se não existe dá a opção de incluir uma nova linha com a
 * quantidade para compra.
 */

package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.server.MXServer;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;

/**
 * @author willians.andrade
 */
public class MsQtdDemJud extends MboValueAdapter {
	
	public MsQtdDemJud(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		
		double QtdSolic = getMboValue().getMbo().getDouble("MSQTD");
		int IdEstoque = getMboValue().getMbo().getInt("MSIDESTOQUE");
		
		System.out.println(">>>>>>>>> CTIS - MSIDESTOQUE: " + IdEstoque);
		
		if (!getMboValue().getMbo().isNull("MSIDESTOQUE")){
			MboSet mboSetEstoqueMedic;
			mboSetEstoqueMedic = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MSTBMEDALMOX", getMboValue().getMbo().getUserInfo());
			mboSetEstoqueMedic.setWhere("MSTBMEDALMOXID = '" + IdEstoque + "'");
			mboSetEstoqueMedic.reset();
			
			if (mboSetEstoqueMedic.count() > 0){
				System.out.println(">>>>>>>>> CTIS - " + mboSetEstoqueMedic.count());					
				
				double QtdDisp = mboSetEstoqueMedic.getMbo(0).getDouble("MSNUMQNTDISP");
				
				if (QtdDisp < QtdSolic){
					System.out.println(">>>>>>>>> CTIS - " + QtdDisp + " > " + QtdSolic);
					
					// Alerta
					String yesNoId = getClass().getName();
					int userInput = MXApplicationYesNoCancelException.getUserInput(yesNoId, MXServer.getMXServer(), getMboValue().getMbo().getUserInfo());
					System.out.println(">>>>> retorno = " + userInput);
					
					switch (userInput) {
					case MXApplicationYesNoCancelException.NULL:
						System.out.println(">>> userImput null");
						Object params[] = { QtdDisp, QtdSolic };
						throw new MXApplicationYesNoCancelException(yesNoId, "msdemjud","MsQtd", params);
					case MXApplicationYesNoCancelException.YES:
						System.out.println(">>> Usuario clicou em SIM");
						
						int Wonum = getMboValue().getMbo().getInt("WONUM");
						double QtdCompra = QtdSolic - QtdDisp;
						
						MboSetRemote mboSetMedicamento;
						mboSetMedicamento = getMboValue().getMbo().getMboSet("MSTBMEDICAMENTOS");
						
						MboRemote mboDestinoMed;
						mboDestinoMed = mboSetMedicamento.add();

						System.out.println(">>>>> WONUM : " + Wonum);
						mboDestinoMed.setValue("WONUM", Wonum,  MboConstants.NOACCESSCHECK);
						System.out.println(">>>>> MSQTD : " + QtdCompra);
						mboDestinoMed.setValue("MSQTD", QtdCompra,  MboConstants.NOACCESSCHECK);
						System.out.println(">>>>> MSALNTIPOATENDIMENTO : " + "ESTOQUE");
						mboDestinoMed.setValue("MSALNTIPOATENDIMENTO", "ESTOQUE", MboConstants.NOACCESSCHECK);
						System.out.println(">>>>> MSIDESTOQUE : " + IdEstoque);
						mboDestinoMed.setValue("MSIDESTOQUE", IdEstoque, MboConstants.NOACCESSCHECK);
						
						getMboValue().getMbo().getThisMboSet().save();

						break;
					case MXApplicationYesNoCancelException.NO:
						System.out.println(">>> Usuario clicou em NAO");
						getMboValue().getMbo().setValueNull("MSQTD");
						break;
					default:
						System.out.println(">>> userImpot DEFAULT");
						break;
					}
				}
			}
		}
		
		
	}
}
