package br.inf.id2.mapa.bean;

import psdi.webclient.beans.id2.mapa.*;
import java.rmi.RemoteException;
import psdi.id2.Uteis;
import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.util.MXApplicationException;

/**
 * 
 * @author Ricardo Gomes
 */
public class ID2AnimalRecebimentoTableBean extends
		psdi.webclient.beans.po.POLineTableBean {

	public ID2AnimalRecebimentoTableBean() {
		System.out.println("*** ID2AnimalRecebimentoTableBean ***");
	}

	public int carregarItens() throws MXException {
		int ret = -1;
		boolean adicionou = false;

		try {
			int onde = 0;
			// Uteis.espera("Antes--------");
			// //System.out.println("---------------------------------- Inicio");

			MboSet linhas = (MboSet) parent.getMbo().getMboSet("POLINE");
			// linhas.clear();
			// linhas.deleteAll();
			linhas.deleteAndRemoveAll();

			// Uteis.espera("Depois-------");
			Mbo item;
			// System.out.println("---------------------------------- apos Mbos e MboSets");
			// Uteis.espera("Depois 1-------");
			parent.getMbo().setFieldFlag("STORELOC", psdi.mbo.MboConstants.READONLY, false);
			parent.getMbo().setFieldFlag("ID2PROABA", psdi.mbo.MboConstants.READONLY, true);
			parent.getMbo().setFieldFlag("ID2PROAGL", psdi.mbo.MboConstants.READONLY, true);
			parent.getMbo().setFieldFlag("ID2PROEXP", psdi.mbo.MboConstants.READONLY, true);
			parent.getMbo().setFieldFlag("ID2DESABA", psdi.mbo.MboConstants.READONLY, true);
			parent.getMbo().setFieldFlag("ID2DESAGL", psdi.mbo.MboConstants.READONLY, true);
			parent.getMbo().setFieldFlag("ID2DESEXP", psdi.mbo.MboConstants.READONLY, true);
			// Uteis.espera("Depois 2-------");
			// System.out.println("Tipo do local Proced?ncia " +
			// parent.getMbo().getString("id2tipolocal"));
			// System.out.println("Tipo do local Destino " +
			// parent.getMbo().getString("id2tipolocaldest"));

			if (parent.getMbo().getString("ID2TIPOLOCAL") == null) {
				throw new MXApplicationException("company", "ID2TIPOLOCALInvalido");
			}
			if (parent.getMbo().getString("ID2TIPOLOCALDEST") == null) {
				throw new MXApplicationException("company", "ID2TIPOLOCALDESTnvalido");
			}
			// Uteis.espera("*********** ID2TIPOLOCAL = " +
			// parent.getMbo().getString("ID2TIPOLOCAL"));

			// Uteis.espera("*********** ID2TIPOLOCALDEST = " +
			// parent.getMbo().getString("ID2TIPOLOCALDEST"));

			if (parent.getMbo().getString("ID2TIPOLOCAL").equals("02")) {
				// System.out.println("local Proced?ncia " +
				// parent.getMbo().getString("ID2PROABA"));
				parent.getMbo().setValue("STORELOC",
						parent.getMbo().getString("ID2PROABA")); // TODO ver
																	// novo
																	// valor
			} else if (parent.getMbo().getString("ID2TIPOLOCAL").equals("03")) {
				// System.out.println("local Procedência " +
				// parent.getMbo().getString("ID2PROAGL"));
				// parent.getMbo().setValue("STORELOC",
				// parent.getMbo().getString("ID2PROAGL")); //TODO ver novo
				// valor
				parent.getMbo().setValue("STORELOC", parent.getMbo().getString("ID2VWLOC03PRO.LOCATION")); // TODO
																				// ver
																				// novo
																				// valor
			} else if (parent.getMbo().getString("ID2TIPOLOCAL").equals("04")) {
				parent.getMbo().setValue("STORELOC", parent.getMbo().getString("ID2PROEXP"));
			}

			// Uteis.espera("*********** após storeloc = " +
			// parent.getMbo().getString("STORELOC"));

			if (parent.getMbo().getString("ID2TIPOLOCALDEST").equals("02")) {
				parent.getMbo().setValue("ID2STORELOCDEST", parent.getMbo().getString("ID2DESABA")); // TODO ver
																	// novo
																	// valor
			} else if (parent.getMbo().getString("ID2TIPOLOCALDEST").equals("03")) {
				parent.getMbo().setValue("ID2STORELOCDEST", parent.getMbo().getString("ID2VWLOC03DES.LOCATION")); // TODO
																				// ver
																				// novo
																				// valor
			} else if (parent.getMbo().getString("ID2TIPOLOCALDEST").equals("04")) {
				parent.getMbo().setValue("ID2STORELOCDEST", parent.getMbo().getString("ID2DESEXP"));
			}

			// Uteis.espera("*********** após storeloc destino = " +
			// parent.getMbo().getString("ID2DESAGL"));
			// Uteis.espera("*********** storeloc destino relacionamento = " +
			// parent.getMbo().getString("ID2VWLOC03DES.LOCATION"));
			// Uteis.espera("*********** ID2STORELOCDEST = " +
			// parent.getMbo().getString("ID2STORELOCDEST"));

			MboSet itens = (MboSet) parent.getMbo().getMboSet("ID2PROEXP_SALDO");

			if (itens != null) {
				// Uteis.espera("Total de itens ------------- " +
				// itens.count());

				for (int i = 0; i < itens.count(); i++) {

					item = (Mbo) itens.getMbo(i);

					// caso seja da mesma commodity
					// Uteis.espera("Det. Item-------: " +
					// item.getString("ITEMNUM"));
					// Uteis.espera("Commodity-------: " +
					// parent.getMbo().getString("COMMODITY"));

					// if ((item.getString("ITEMNUM").substring(0,
					// 1).equals(parent.getMbo().getString("COMMODITYGROUP")))
					// && (Validar.marcado((MboSet)
					// parent.getMbo().getMboSet("ID2LISESP"), null,
					// "COMMODITY", item.getString("ITEMNUM").substring(0, 3))))
					// {
					// if (item.getString("ITEMNUM").substring(0,
					// 1).equals(parent.getMbo().getString("COMMODITYGROUP"))) {
					if (item.getString("ITEMNUM").substring(0, 3).equals(parent.getMbo().getString("COMMODITY"))) {

						// Uteis.espera("Adicionou--------");
						// System.out.println("antes do add");
						ret = super.addrow();

						adicionou = true;

						MboRemote linhaAnimal = getMbo();
						// System.out.println("apos do add");

						/*
						 * if
						 * (parent.getMbo().getString("id2tipolocaldest").equals
						 * ("02")) { //System.out.println("local destino " +
						 * parent.getMbo().getString("ID2DESABA"));
						 * linhaAnimal.setValue("STORELOC",
						 * parent.getMbo().getString("ID2DESABA")); } else if
						 * (parent
						 * .getMbo().getString("id2tipolocaldest").equals("03"))
						 * { //System.out.println("local destino " +
						 * parent.getMbo().getString("ID2DESAGL"));
						 * linhaAnimal.setValue("STORELOC",
						 * parent.getMbo().getString("ID2DESAGL")); } else if
						 * (parent
						 * .getMbo().getString("id2tipolocaldest").equals("04"))
						 * { //System.out.println("local destino " +
						 * parent.getMbo().getString("ID2DESEXP"));
						 * linhaAnimal.setValue("STORELOC",
						 * parent.getMbo().getString("ID2DESEXP")); }
						 */
						// System.out.println("---------------------------------- sets "
						// + ++onde);
						// Quantidade Emitida
						linhaAnimal.setValue("ORDERQTY", 0);

						// Uteis.espera("*************////////-------------- qty = "
						// + linhaAnimal.getString("ORDERQTY"));

						// System.out.println("---------------------------------- sets "
						// + ++onde);
						linhaAnimal.setValue("ITEMNUM", item.getString("ITEMNUM"));

						// System.out.println("---------------------------------- sets "
						// + ++onde);
						linhaAnimal.setValue("POLINENUM", i);
						// System.out.println("---------------------------------- sets "
						// + ++onde);
						// Uteis.espera(linhaAnimal.getString("STORELOC"));
						// System.out.println("---------------------------------- sets "
						// + ++onde);
						// Código da Exploração
						linhaAnimal.setValue("VENDORWAREHOUSE", ".");
						// System.out.println("---------------------------------- sets "
						// + ++onde);

						// Código da Exploração

						linhaAnimal.setValue("STORELOC", parent.getMbo().getString("ID2STORELOCDEST"));

						// System.out.println("---------------------------------- sets "
						// + ++onde);

						// Uteis.espera("------------------ ANTES ééééé read only");

						if (linhaAnimal.getMboValueData("ORDERUNIT").isReadOnly()) {
							// Uteis.espera("------------------ ééééé read only");
						} else {
							linhaAnimal.setValue("ORDERUNIT", "CADA");
						}

						System.out.println("*** RECEIVEDQTY - Antes");
						linhaAnimal.setFieldFlag("RECEIVEDQTY", MboConstants.READONLY, false);
						System.out.println("*** RECEIVEDQTY - Depois");
						// System.out.println("---------------------------------- DEPOIS");

						// linhaAnimal.setValue("STORELOC",
						// parent.getMbo().getString("STORELOC"));
						// //System.out.println("---------------------------------- APÓS STORELOC DE CARREGAR ITENS ******************************");

						/*
						 * //Código da Exploração
						 * linhaAnimal.setValue("STORELOC",
						 * parent.getMbo().getString("ID2PROEXP")); /*
						 * //System.out
						 * .println("---------------------------------- sets");
						 * linhaAnimal.setValue("PONUM",
						 * parent.getMbo().getString("PONUM"));
						 * //System.out.println
						 * ("---------------------------------- sets " +
						 * ++onde);
						 * 
						 * //System.out.println(
						 * "---------------------------------- sets " + ++onde);
						 * 
						 * //Unidade linhaAnimal.setValue("ORDERUNIT", "CADA");
						 * //System.out.println(
						 * "---------------------------------- sets " + ++onde);
						 * //Custo //linhaAnimal.setValue("UNITCOST", 0);
						 * //System
						 * .out.println("---------------------------------- sets "
						 * + ++onde); linhaAnimal.setValue("RECEIVEDUNITCOST",
						 * 0);//System.out.println(
						 * "---------------------------------- sets " + ++onde);
						 * linhaAnimal.setValue("RECEIVEDTOTALCOST", 0);
						 * //System
						 * .out.println("---------------------------------- sets "
						 * + ++onde); linhaAnimal.setValue("REJECTEDQTY", 0);
						 * //System
						 * .out.println("---------------------------------- sets "
						 * + ++onde); linhaAnimal.setValue("ENTERDATE", new
						 * Date());//System.out.println(
						 * "---------------------------------- sets " + ++onde);
						 * //TODO Buscarquem ta logado
						 * linhaAnimal.setValue("ENTERBY", "MAXADMIN");
						 * //System.
						 * out.println("---------------------------------- sets "
						 * + ++onde); //TODO fata esse
						 * linhaAnimal.setValue("DESCRIPTION", "...");
						 * //System.out
						 * .println("---------------------------------- sets " +
						 * ++onde); //TODO Buscarquem ta logado
						 * linhaAnimal.setValue("REQUESTEDBY", "MAXADMIN");
						 * //System
						 * .out.println("---------------------------------- sets "
						 * + ++onde); linhaAnimal.setValue("ISSUE", 0);
						 * //System.
						 * out.println("---------------------------------- sets "
						 * + ++onde); linhaAnimal.setValue("TAXED", 0);
						 * //System.
						 * out.println("---------------------------------- sets "
						 * + ++onde); linhaAnimal.setValue("CHARGESTORE", 0);
						 * //System
						 * .out.println("---------------------------------- sets "
						 * + ++onde); linhaAnimal.setValue("LINECOST", 0);
						 * //System
						 * .out.println("---------------------------------- sets "
						 * + ++onde); linhaAnimal.setValue("TAX1", 0);
						 * //System.out
						 * .println("---------------------------------- sets " +
						 * ++onde); linhaAnimal.setValue("TAX2", 0);
						 * //System.out
						 * .println("---------------------------------- sets " +
						 * ++onde); linhaAnimal.setValue("TAX3", 0);
						 * //System.out
						 * .println("---------------------------------- sets " +
						 * ++onde); linhaAnimal.setValue("RECEIPTREQD", 0);
						 * //System
						 * .out.println("---------------------------------- sets "
						 * + ++onde); linhaAnimal.setValue("TAX4", 0);
						 * //System.out
						 * .println("---------------------------------- sets " +
						 * ++onde); linhaAnimal.setValue("TAX5", 0);
						 * //System.out
						 * .println("---------------------------------- sets " +
						 * ++onde); linhaAnimal.setValue("CATEGORY", "ESTOC");
						 * //System.out.println(
						 * "---------------------------------- sets " + ++onde);
						 * linhaAnimal.setValue("LOADEDCOST", 0);
						 * //System.out.println
						 * ("---------------------------------- sets " +
						 * ++onde); linhaAnimal.setValue("PRORATESERVICE", 0);
						 * //System.out.println(
						 * "---------------------------------- sets " + ++onde);
						 * linhaAnimal.setValue("RECEIPTSCOMPLETE", 0);
						 * //System.
						 * out.println("---------------------------------- sets "
						 * + ++onde); linhaAnimal.setValue("INSPECTIONREQUIRED",
						 * 0);//System.out.println(
						 * "---------------------------------- sets " + ++onde);
						 * linhaAnimal.setValue("PRORATECOST", 0);
						 * //System.out.println
						 * ("---------------------------------- sets " +
						 * ++onde); //TODO proximo id //TODO Relacionamento
						 * POLINE_PROXIMO linhaAnimal.setValue("POLINEID",
						 * parent
						 * .getMbo().getMboSet("POLINE_PROXIMO").getMbo(0).
						 * getInt("POLINEID") + 1);
						 * //System.out.println(String.valueOf
						 * (parent.getMbo().getMboSet
						 * ("POLINE_PROXIMO").getMbo(0).getInt("POLINEID")));
						 * linhaAnimal.setValue("LINECOST2", 0);
						 * linhaAnimal.setValue("SITEID", "SITE_01");
						 * linhaAnimal.setValue("ORGID", "ORG_01");
						 * linhaAnimal.setValue("ISDISTRIBUTED", 0); //somente
						 * leitura? //linhaAnimal.setValue("ENTEREDASTASK", 0);
						 * linhaAnimal.setValue("LINETYPE", "ITEM");
						 * linhaAnimal.setValue("ITEMSETID", "CI_01"); //TODO
						 * melhorar isso - fazer um método para buscar os
						 * valores antes dos "."
						 * linhaAnimal.setFieldFlag("COMMODITYGROUP",
						 * psdi.mbo.MboConstants.CHANGEDBY_USER, true);
						 * linhaAnimal.setValue("COMMODITYGROUP",
						 * item.getString("ITEMNUM").substring(0, 1));
						 * linhaAnimal.setFieldFlag("COMMODITY",
						 * psdi.mbo.MboConstants.CHANGEDBY_USER, true);
						 * linhaAnimal.setValue("COMMODITY",
						 * item.getString("ITEMNUM").substring(0, 3));
						 * linhaAnimal.setFieldFlag("COMMODITYGROUP",
						 * psdi.mbo.MboConstants.READONLY, false);
						 * linhaAnimal.setFieldFlag("COMMODITY",
						 * psdi.mbo.MboConstants.READONLY, false); //comentado
						 * pois esta somente leitura
						 * linhaAnimal.setValue("TOSITEID", "SITE_01");
						 * linhaAnimal.setValue("LANGCODE", "PT");
						 * linhaAnimal.setValue("CONVERSION", 1);
						 * linhaAnimal.setValue("HASLD", 0);
						 * linhaAnimal.setValue("MKTPLCITEM", 0);
						 */

						// System.out.println("---------------------------------- FIM");
					}
				}
				// Uteis.espera("Fim-------");
			}

		} catch (Exception e) {
			String params[] = { new String() };

			// System.out.println(e.getMessage());

			throw new MXApplicationException("PO", "AnimalInvalido", params);
		}

		if (!adicionou) {
			throw new MXApplicationException("company", "ProcedenciaSemItens");
		}
		return ret;
	}

	public void delete() throws MXException, java.rmi.RemoteException {
		super.delete();

		try {
			if (count() == 0) {
				parent.getMbo().setFieldFlag("ID2PROABA", psdi.mbo.MboConstants.READONLY, false);
				parent.getMbo().setFieldFlag("ID2PROAGL", psdi.mbo.MboConstants.READONLY, false);
				parent.getMbo().setFieldFlag("ID2PROEXP", psdi.mbo.MboConstants.READONLY, false);
				parent.getMbo().setFieldFlag("ID2DESABA", psdi.mbo.MboConstants.READONLY, false);
				parent.getMbo().setFieldFlag("ID2DESAGL", psdi.mbo.MboConstants.READONLY, false);
				parent.getMbo().setFieldFlag("ID2DESEXP", psdi.mbo.MboConstants.READONLY, false);
			}
		} catch (Exception e) {
			String params[] = { new String() };
			// System.out.println(e.getMessage());
		}
	}
}
