/**
 * Solicitado por Glaucio.Fernandes
 * Ao informar a quantidade desejada verifica se existe estoque
 * se não existe dá a opção de incluir uma nova linha com a
 * quantidade para compra.
 */

package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

import psdi.id2.Uteis;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.server.MXServer;
import psdi.util.DBConnect;
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
				
					if (QtdDisp > 0 && getMboValue().getMbo().getString("MSALNTIPOATENDIMENTO").equals("COMPRA")){
						System.out.println(">>>>>>>>> CTIS - " + QtdSolic  + " > " + QtdDisp);
	
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
							
							String Catmat = getMboValue().getMbo().getString("CATMAT");
							String DescCatmat = getMboValue().getMbo().getString("DESCRIPTION");
							String Wonum = getMboValue().getMbo().getString("WONUM");
							double QtdCompra = QtdSolic - QtdDisp;
							
							MboSetRemote mboSetMedicamento;
							mboSetMedicamento = getMboValue().getMbo().getMboSet("MSTBMEDICAMENTOS");
							
							MboRemote mboDestinoMed;
							mboDestinoMed = mboSetMedicamento.add();
							
							int ID_do_Pai = mboDestinoMed.getInt("MSTBMEDICAMENTOID");
							
							if (QtdDisp == QtdSolic){
												
								getMboValue().getMbo().setValue("MSALNTIPOATENDIMENTO", "ESTOQUE");
								getMboValue().getMbo().setValue("MSQTD", QtdDisp);
								getMboValue().getMbo().setValue("MSMEDPAI", ID_do_Pai);
								
								mboDestinoMed.setValue("CATMAT", Catmat, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("DESCRIPTION", DescCatmat, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("WONUM", Wonum, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("MSQTD", 0, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("MSALNTIPOATENDIMENTO", "COMPRA", MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("MSIDESTOQUE", IdEstoque, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("MSMEDPAI", 0, MboConstants.NOACCESSCHECK);
								
								getMboValue().getMbo().getThisMboSet().save();
						
							} else if (QtdSolic < QtdDisp){
								getMboValue().getMbo().setValue("MSALNTIPOATENDIMENTO", "ESTOQUE");
								getMboValue().getMbo().setValue("MSQTD", QtdSolic);
								getMboValue().getMbo().setValue("MSMEDPAI", ID_do_Pai);
								
								mboDestinoMed.setValue("CATMAT", Catmat, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("DESCRIPTION", DescCatmat, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("WONUM", Wonum, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("MSQTD", 0, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("MSALNTIPOATENDIMENTO", "COMPRA", MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("MSIDESTOQUE", IdEstoque, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("MSMEDPAI", ID_do_Pai, MboConstants.NOACCESSCHECK);
								
								getMboValue().getMbo().getThisMboSet().save();
								
							} else if (QtdSolic > QtdDisp){
								getMboValue().getMbo().setValue("MSALNTIPOATENDIMENTO", "ESTOQUE");
								getMboValue().getMbo().setValue("MSQTD", QtdDisp);
								getMboValue().getMbo().setValue("MSMEDPAI", ID_do_Pai);
								
								mboDestinoMed.setValue("CATMAT", Catmat, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("DESCRIPTION", DescCatmat, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("WONUM", Wonum, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("MSQTD", QtdCompra, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("MSALNTIPOATENDIMENTO", "COMPRA", MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("MSIDESTOQUE", IdEstoque, MboConstants.NOACCESSCHECK);
								mboDestinoMed.setValue("MSMEDPAI", 0, MboConstants.NOACCESSCHECK);
								
								getMboValue().getMbo().getThisMboSet().save();
							}
														
							// UPDATE ALMOXARIFADO DE MEDICAMENTO
							
							MboSetRemote mboSetAlmoxMed;
							mboSetAlmoxMed = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MSTBMEDALMOX", getMboValue().getMbo().getUserInfo());
							mboSetAlmoxMed.setWhere("MSTBMEDALMOXID = '"+ IdEstoque + "'");
							mboSetAlmoxMed.reset();
						
							double NovoTotal_Reserva = 0;
							
							if (QtdSolic > QtdDisp){
								NovoTotal_Reserva = mboSetAlmoxMed.getMbo(0).getDouble("MSNUMQNTRESERV") + QtdDisp;
							} else {
								NovoTotal_Reserva = mboSetAlmoxMed.getMbo(0).getDouble("MSNUMQNTRESERV") + QtdSolic;
							}
							
							mboSetAlmoxMed.getMbo(0).setValue("MSNUMQNTRESERV", NovoTotal_Reserva, MboConstants.NOACCESSCHECK);
							mboSetAlmoxMed.save();
					        
							break;
						case MXApplicationYesNoCancelException.NO:
							System.out.println(">>> Usuario clicou em NAO");
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
