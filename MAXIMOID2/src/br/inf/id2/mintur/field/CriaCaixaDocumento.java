package br.inf.id2.mintur.field;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.server.MXServer;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;

/**
 * 
 * @author Patrick Silva
 */

public class CriaCaixaDocumento extends MAXTableDomain {

	public CriaCaixaDocumento(MboValue mbv) throws MXException {

		super(mbv);

		System.out.println("*** CriaCaixaDocumento 1848***");

		setRelationship("MXTBCAI", "mxnumcai = :mxnumcai");// and mxdscanopra = :mxdscanodoc and mxcodpra= :mxcodpra and bgstcodalfa2 = :mxdscestfedori");
		setErrorMessage("mxtbcai", "InvalidMxtbcai");
//		setListCriteria((new StringBuilder()).append( "mxnumcai not in (select mxnumcai from mxtbcaidoc)").toString());

	}

	@Override
	public void action() throws MXException, RemoteException {
		// Precisa ser comentado
		System.out.println("*** action");
		// super.action();
	}

	public void validate() throws MXException, RemoteException {

		System.out.println("*** validate ");

		if (getMboValue().isNull()) {
			System.out.println("*** is null");
			return;
		}

		String nrCaixa = getMboValue().getString();
		String codigoCaixa = getMboValue().getMbo().getString("MXCODPRA");
		String anoCaixa = getMboValue().getMbo().getString("MXDSCANODOC");
		String estadoCaixa = getMboValue().getMbo().getString("MXDSCESTFEDORI");
		String soma = getMboValue().getMbo().getMboSet("MXRLVW01").getMbo(0).getString("MXSOMA");
		
//		System.out.println("*** nrCaixa "+nrCaixa);
//		System.out.println("*** anoCaixa "+anoCaixa+" *** "+gMbo.getString("MXDSCANODOC"));
//		System.out.println("*** estadoCaixa "+estadoCaixa+" *** "+gMbo.getString("MXDSCESTFEDORI"));
//		System.out.println("*** codigoCaixa " +codigoCaixa+" *** "+gMbo.getString("MXCODPRA"));

		MboRemote mbo = getMboValue().getMbo();
		MboSet mboSetCaixa = (MboSet) getMboValue().getMbo().getMboSet("MXRLTBCAI01");

		MboRemote gMbo;
		boolean encontrado = false;

		for (int i = 0; ((gMbo = mboSetCaixa.getMbo(i)) != null); i++) {

//			System.out.println("*** i " + i + " caixa " + gMbo.getInt("MXTBCAIID"));
			System.out.println("*** i " + i + " caixa " + gMbo.getInt("MXNUMCAI"));
			System.out.println("*** nrCaixa "+nrCaixa+" --- "+gMbo.getString("MXNUMCAI").equalsIgnoreCase(nrCaixa));
			System.out.println("*** anoCaixa "+anoCaixa+" *** "+gMbo.getString("MXDSCANOPRA")+" --- "+gMbo.getString("MXDSCANOPRA").equalsIgnoreCase(anoCaixa));
			System.out.println("*** estadoCaixa "+estadoCaixa+" *** "+gMbo.getString("BGSTCODALFA2")+" --- "+gMbo.getString("BGSTCODALFA2").equalsIgnoreCase(estadoCaixa));
			System.out.println("*** codigoCaixa " +codigoCaixa+" *** "+gMbo.getString("MXCODPRA")+" --- "+gMbo.getString("MXCODPRA").equalsIgnoreCase(codigoCaixa));

			System.out.println("*** nrDocumento " + nrCaixa);
//			encontrado = ((gMbo.getString("MXNUMCAI") == nrCaixa) && (gMbo.getString("MXDSCANOPRA").equalsIgnoreCase(anoCaixa)) && (gMbo.getString("BGSTCODALFA2").equalsIgnoreCase(estadoCaixa)) && (gMbo.getString("MXCODPRA").equalsIgnoreCase(codigoCaixa)));
			if((gMbo.getString("MXNUMCAI").equalsIgnoreCase(nrCaixa)) && 
					(gMbo.getString("MXDSCANOPRA").equalsIgnoreCase(anoCaixa)) && 
					(gMbo.getString("BGSTCODALFA2").equalsIgnoreCase(estadoCaixa)) && 
					(gMbo.getString("MXCODPRA").equalsIgnoreCase(codigoCaixa))){
				
				encontrado = true;
			}
			System.out.println("*** encontrado " + encontrado);

			if (encontrado) {

				System.out.println("*** encontrou");
				getMboValue().getMbo().setValue("MXNUMCAI", gMbo.getString("MXNUMCAI"), MboConstants.NOVALIDATION_AND_NOACTION);

				break;
			}
		}

		if (!encontrado) {

			System.out.println("*** !encontrado ");
			String yesNoId = getClass().getName();

			int userInput = MXApplicationYesNoCancelException.getUserInput(yesNoId, MXServer.getMXServer(), getMboValue().getMbo().getUserInfo());

			System.out.println("*** retorno = " + userInput);

			switch (userInput) {

			case MXApplicationYesNoCancelException.NULL:
				System.out.println("*** userImput null");
				Object params[] = { getMboValue().getString() };
				throw new MXApplicationYesNoCancelException(yesNoId, "SACaixa","novaCaixa", params);

			case MXApplicationYesNoCancelException.YES:
				System.out.println("*** userImpot YES");
				MboSet caixas = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MXTBCAI", mbo.getUserInfo());

				MboRemote caixa = caixas.add();

				caixa.setFieldFlag("MXDSCTIPCAI", MboConstants.READONLY, false);
				caixa.setFieldFlag("MXDSCANOPRA", MboConstants.READONLY, false);
				caixa.setFieldFlag("MXCODPRA", MboConstants.READONLY, false);
				caixa.setFieldFlag("BGSTCODALFA2", MboConstants.READONLY, false);
				caixa.setFieldFlag("MXNUMCAI", MboConstants.READONLY, false);
				caixa.setFieldFlag("MXDSCPRA", MboConstants.READONLY, false);
				caixa.setFieldFlag("MXDTACON", MboConstants.READONLY, false);
				caixa.setFieldFlag("MXDTAARQ", MboConstants.READONLY, false);
				caixa.setFieldFlag("MXDTAELI", MboConstants.READONLY, false);
				caixa.setFieldFlag("MXFLGCHE", MboConstants.READONLY, false);

				System.out.println("*** caixas save");

				caixa.setValue("MXNUMCAI", nrCaixa, MboConstants.NOVALIDATION_AND_NOACTION);
				caixa.setValue("MXCODPRA", codigoCaixa, MboConstants.NOVALIDATION_AND_NOACTION);
				caixa.setValue("MXDSCANOPRA", anoCaixa, MboConstants.NOVALIDATION_AND_NOACTION);
				caixa.setValue("BGSTCODALFA2", estadoCaixa, MboConstants.NOVALIDATION_AND_NOACTION);
				caixa.setValue("MXDTAELI", soma, MboConstants.NOVALIDATION_AND_NOACTION);
				
				caixas.save();

				System.out.println("*** caixas save a");

				break;

			case MXApplicationYesNoCancelException.NO: // '\020'

				System.out.println("*** userImpot NO");

//				getList();
				getMboValue().setValueNull();
				// throw new MXApplicationException("system", "null");

//				getMboValue().setValueNull();

			default:
				System.out.println("*** userImpot DEFAULT");
				break;
			}
		}
	}
}