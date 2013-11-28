package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author Patrick L. Silva
 */
public class NomeacaoServidores extends psdi.webclient.system.beans.AppBean {

	String pessoa;

	public NomeacaoServidores() {
		System.out.println("*** NomeacaoServidores ***");
	}

	@Override
	public synchronized void dataChangedEvent(DataBean speaker) {
		System.out.println("*************************************************************************************");
		System.out.println("*** dataChangedEvent");
		try {
			pessoa = (String) (getMbo().getDatabaseValue("RHSTCODSERV") == null ? "" : getMbo().getDatabaseValue("RHSTCODSERV"));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		System.out.println("*** pessoaOld " + pessoa);
	}

	@Override
	public int SAVE() throws RemoteException, MXException {

		System.out.println("*** SAVE() MARCELO 12/06");
		
		int id = getMbo().getInt("RHTBUOID");
		
		Date dtNomeacao = (Date) getMbo().getDatabaseValue("RHSTDTANOMEACAO");
		Date dtPosse = (Date) getMbo().getDatabaseValue("RHSTDTAPOSSE");
		Date dtExercicio = (Date) getMbo().getDatabaseValue("RHSTDTAENTRAD");
		String atoNormativo = (String) getMbo().getDatabaseValue("RHSTDSCATONOR");

		System.out.println("*** SAVE() pessoaOld " + pessoa);
		System.out.println("*** SAVE() pessoa "+ getMbo().getString("RHSTCODSERV"));
		System.out.println("*** pesso = pessoaOld - "+pessoa.equals(getMbo().getString("RHSTCODSERV")));
		System.out.println("*** SAVE() pessoa getDatabaseValue "+ (String) getMbo().getDatabaseValue("RHSTCODSERV"));
		
		if (!pessoa.equals(getMbo().getString("RHSTCODSERV"))) {
			System.out.println("*** SAVE() if");
			muda(getMbo().getString("RHSTCODSERV"), pessoa, id);
			System.out.println("*** SAVE() if FIM");

		}else if((dtNomeacao != null && dtNomeacao.equals(getMbo().getString("RHSTDTANOMEACAO"))) || 
				(dtPosse  != null && dtPosse.equals(getMbo().getString("RHSTDTAPOSSE"))) ||
				(dtExercicio != null && dtExercicio.equals(getMbo().getString("RHSTDTAENTRAD"))) ||
				(atoNormativo != null && atoNormativo.equals(getMbo().getString("RHSTDSCATONOR")))){
			
			System.out.println("*** else if");
			
			getMbo().getMboSet("RHRLHULE01").getMbo(0).setValue("RHSTDTANOMEACAO", getMbo().getString("RHSTDTANOMEACAO"));
			getMbo().getMboSet("RHRLHULE01").getMbo(0).setValue("RHSTDTAPOSSE", getMbo().getString("RHSTDTAPOSSE"));
			getMbo().getMboSet("RHRLHULE01").getMbo(0).setValue("RHSTDTAENTRAD", getMbo().getString("RHSTDTAENTRAD"));
			getMbo().getMboSet("RHRLHULE01").getMbo(0).setValue("RHSTDSCATONOR", getMbo().getString("RHSTDSCATONOR"));
		}
		
		pessoa = (String) (getMbo().getDatabaseValue("RHSTCODSERV") == null ? "" : getMbo().getDatabaseValue("RHSTCODSERV"));
		int retorno = super.SAVE();

		return retorno;
	}

	private void muda(String pessoa, String pessoaOld, int RHTBUOID)
			throws MXException, RemoteException {
		System.out.println("*** muda");
		MboSet mboSet;
		mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("RHTBHULE01", sessionContext.getUserInfo());
		MboSet mboSet2;
		mboSet2 = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("RHTBCASE01", sessionContext.getUserInfo());
		System.out.println("*** muda OLD");
		if (pessoaOld != null) {
			System.out.println("*** muda OLD pessoaOld != null");
			
			mboSet2.setWhere("PERSONID = \'" + pessoaOld + "\' ");
			mboSet2.reset();

			if (mboSet2.count() > 0) {
				System.out.println("*** muda OLD v1");
				mboSet2.getMbo(0).setValueNull("RHSTCODUOLOT");
				System.out.println("*** muda OLD v2");
				mboSet2.getMbo(0).setValueNull("RHNUCODUOLOTID");
				System.out.println("*** muda OLD save2");
				mboSet2.save();
				System.out.println("*** muda OLD save2 FIM");
			}
		}

		if (pessoa.length() > 0) {
			System.out.println("*** muda OLD pessoa != null");

			System.out.println("*** lot add");
			MboRemote mbo = mboSet.add();
			System.out.println("*** lot vals");
			mbo.setValue("PERSONID", pessoa);
			mbo.setValue("RHTBUOID", RHTBUOID);
			mbo.setValue("RHSTCODTIPO", "LOT");
			System.out.println("*** Novos campos: "+ getMbo().getDate("RHSTDTAENTRAD"));
			mbo.setValue("RHSTDTAENTRAD", getMbo().getDate("RHSTDTAENTRAD"));
			mbo.setValue("RHSTDTAPOSSE", getMbo().getDate("RHSTDTAPOSSE"));
			mbo.setValue("RHSTDSCATONOR", getMbo().getString("RHSTDSCATONOR"));
			mbo.setValue("RHTBVAGA01ID", getMbo().getString("RHTBVAGA01ID"));
                        
			mbo.setValue("RHTBCARGO01ID", getMbo().getString("RHTBCARGO01ID"));
			mbo.setValue("RHSTCODTIPCAR", getMbo().getString("RHSTCODTIPCAR"));
			mbo.setValue("RHSTCODFUNCAO", getMbo().getString("RHSTCODFUNCAO"));
			mbo.setValue("RHNUVLRCARHOR", getMbo().getString("RHNUVLRCARHOR"));
			mbo.setValue("RHSTNUMSIAPE", getMbo().getString("RHSTNUMSIAPE"));
			
			System.out.println("*** lot save");
			mboSet.save();
			System.out.println("*** lot save FIM");
			
			System.out.println("*** Nome= " + getMbo().getName());
			System.out.println(getMbo().getInt("RHTBUOID"));
			MboRemote mboRH;

			mboRH = getMbo().getMboSet("RHRLCASE01").getMbo(0);

			mboRH.setValue("RHSTCODUOLOT", getMbo().getMboSet("RHRLUO0001").getMbo(0).getString("RHSTSGLUO"));
			System.out.println("*** lot save v1");
			mboRH.setValue("RHNUCODUOLOTID", getMbo().getInt("RHTBUOID"));
			System.out.println("*** lot save save rel");
			getMbo().getMboSet("RHRLCASE01").save();
			System.out.println("*** lot save save rel fim");

		}
	}
	
	public void EXONERAR() throws RemoteException, MXException{
		System.out.println("*** EXONERAR");
		
		String pessoa = getMbo().getString("RHSTCODSERV");
		
		MboSet mboSet;
		mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("RHTBCASE01", sessionContext.getUserInfo());

		if (pessoa != null) {
			System.out.println("*** EXONERAR pessoa != null");
			
			mboSet.setWhere("PERSONID = \'" + pessoa + "\' ");
			mboSet.reset();

			if (mboSet.count() > 0) {
				System.out.println("*** RHNUFLGINATIVAR "+getMbo().getBoolean("RHNUFLGINATIVAR"));
				if(getMbo().getBoolean("RHNUFLGINATIVAR") == true){
					mboSet.getMbo(0).setValue("RHSTCODSITUAC", "INATIVO");
				}
				System.out.println("*** ANTES SET NULL");
				mboSet.getMbo(0).setValueNull("RHSTCODUOLOT");
				mboSet.getMbo(0).setValueNull("RHNUCODUOLOTID");
				mboSet.save();
				System.out.println("*** salvou o mboSet");
			}
			
			
			getMbo().getMboSet("RHRLHULE01").getMbo(0).setValue("RHSTDTASAIDA", getMbo().getString("RHSTDTASAIDA"));
			getMbo().getMboSet("RHRLHULE01").getMbo(0).setValue("RHSTDSCATONOREXO", getMbo().getString("RHSTDSCATONOREXO"));
			getMbo().setValue("RHNUFLGEXONERAR", false);
			
			getMbo().setValueNull("RHSTCODSERV");
			save();
			getMbo().setValueNull("RHSTDTANOMEACAO");
			getMbo().setValueNull("RHSTDTAPOSSE");
			getMbo().setValueNull("RHSTDTAENTRAD");
			getMbo().setValueNull("RHSTDSCATONOR");
			
			save();
		}
	}
}
