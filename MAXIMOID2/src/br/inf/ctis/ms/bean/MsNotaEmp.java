package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class MsNotaEmp extends AppBean {

	/**
	 * @author marcelosydney.lima
	 */
	public MsNotaEmp() {
	}
	
	@Override
	public void save() throws MXException {
		try {
			if(getMbo().isNew()){
			
				MboRemote mbo;
				MboRemote mboDestino = null;
				
				System.out.println("############ MODALIDADE: "+ getMbo().getString("MSALCODMODALIDADE"));
				
				if (!getMbo().getBoolean("MSNUFLGTERMOADITIVO")) {
					System.out.println("############ TERMO ADITIVO: " + getMbo().getBoolean("MSNUFLGTERMOADITIVO"));
					//NOTAS DE EMPENHOS NORMAIS (COPIAR MSTBFORNECEDORESITEMPREGAO, MSTBITENSINEXIGIBILIDADE E MSTBITENSARP)
					if (getMbo().getString("MSALCODMODALIDADE").equals("PREGAO")) { 
						for (int i = 0; ((mbo= getMbo().getMboSet("MSTBFORNECEDORESITEMPREGAO").getMbo(i)) !=null); i++) {
							mboDestino = getMbo().getMboSet("MSTBITENSNOTAEMPENHO").add();
							System.out.println("############ add() na itens da nota de empenho");
							
							mboDestino.setValue("MSTBNOTAEMPENHOID", getMbo().getInt("MSTBNOTAEMPENHOID"));
							System.out.println("########### MSTBNOTAEMPENHOID = " + getMbo().getInt("MSTBNOTAEMPENHOID"));
							
							mboDestino.setValue("MSALCODMODALIDADE", getMbo().getString("MSALCODMODALIDADE"));
							System.out.println("########### MSALCODMODALIDADE = " + getMbo().getString("MSALCODMODALIDADE"));
							
							mboDestino.setValue("MSALNUMMODALIDADE", getMbo().getString("MSALNUMMODALIDADE"));
							System.out.println("########### MSALNUMMODALIDADE = " + getMbo().getString("MSALNUMMODALIDADE"));
							
							mboDestino.setValue("ID2ITEMNUM", mbo.getMboSet("POLINE").getMbo(0).getString("ID2ITEMNUM"));
							System.out.println("########### ID2ITEMNUM = " + mbo.getMboSet("POLINE").getMbo(0).getString("ID2ITEMNUM"));
							
							mboDestino.setValue("POLINEID", mbo.getInt("POLINEID"));
							System.out.println("############ POLINEID = " + mbo.getInt("POLINEID"));
							
							mboDestino.setValue("MSNUNUMTABELAORIGEMID", mbo.getInt("MSTBFORNECEDORESITEMPREGAOID"));
							System.out.println("############ MSNUNUMTABELAORIGEMID = " + mbo.getInt("MSTBFORNECEDORESITEMPREGAOID"));
						}
					} else if (getMbo().getString("MSALCODMODALIDADE").equals("INEXIGIBILIDADE")) {
						for (int i = 0; ((mbo= getMbo().getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(i)) !=null); i++) {
							mboDestino = getMbo().getMboSet("MSTBITENSNOTAEMPENHO").add();
							System.out.println("############ add() na itens da nota de empenho");
							
							mboDestino.setValue("MSTBNOTAEMPENHOID", getMbo().getInt("MSTBNOTAEMPENHOID"));
							System.out.println("########### MSTBNOTAEMPENHOID = " + getMbo().getInt("MSTBNOTAEMPENHOID"));
							
							mboDestino.setValue("MSALCODMODALIDADE", getMbo().getString("MSALCODMODALIDADE"));
							System.out.println("########### MSALCODMODALIDADE = " + getMbo().getString("MSALCODMODALIDADE"));
							
							mboDestino.setValue("MSALNUMMODALIDADE", getMbo().getString("MSALNUMMODALIDADE"));
							System.out.println("########### MSALNUMMODALIDADE = " + getMbo().getString("MSALNUMMODALIDADE"));
							
							mboDestino.setValue("ID2ITEMNUM", mbo.getMboSet("POLINE").getMbo(0).getString("ID2ITEMNUM"));
							System.out.println("########### ID2ITEMNUM = " + mbo.getMboSet("POLINE").getMbo(0).getString("ID2ITEMNUM"));
							
							mboDestino.setValue("POLINEID", mbo.getString("POLINEID"));
							System.out.println("############ POLINEID = " + mbo.getInt("POLINEID"));
							
							mboDestino.setValue("MSNUNUMTABELAORIGEMID", mbo.getInt("MSTBITENSINEXIGIBILIDADEID"));
							System.out.println("############ MSNUNUMTABELAORIGEMID = " + mbo.getInt("MSTBITENSINEXIGIBILIDADEID"));
						}
					} else if (getMbo().getString("MSALCODMODALIDADE").equals("ARP")) {
						for (int i = 0; ((mbo= getMbo().getMboSet("MSTBITENSARP").getMbo(i)) !=null); i++) {
							mboDestino = getMbo().getMboSet("MSTBITENSNOTAEMPENHO").add();
							System.out.println("############ add() na itens da nota de empenho");
							
							mboDestino.setValue("MSTBNOTAEMPENHOID", getMbo().getInt("MSTBNOTAEMPENHOID"));
							System.out.println("########### MSTBNOTAEMPENHOID = " + getMbo().getInt("MSTBNOTAEMPENHOID"));
							
							mboDestino.setValue("MSALCODMODALIDADE", getMbo().getString("MSALCODMODALIDADE"));
							System.out.println("########### MSALCODMODALIDADE = " + getMbo().getString("MSALCODMODALIDADE"));
							
							mboDestino.setValue("MSALNUMMODALIDADE", getMbo().getString("MSALNUMMODALIDADE"));
							System.out.println("########### MSALNUMMODALIDADE = " + getMbo().getString("MSALNUMMODALIDADE"));
							
							mboDestino.setValue("ID2ITEMNUM", mbo.getMboSet("POLINE").getMbo(0).getString("ID2ITEMNUM"));
							System.out.println("########### ID2ITEMNUM = " + mbo.getMboSet("POLINE").getMbo(0).getString("ID2ITEMNUM"));
							
							mboDestino.setValue("POLINEID", mbo.getInt("POLINEID"));
							System.out.println("############ POLINEID = " + mbo.getInt("POLINEID"));
							
							mboDestino.setValue("MSNUNUMTABELAORIGEMID", mbo.getInt("MSTBITENSARPID"));
							System.out.println("############ MSNUNUMTABELAORIGEMID = " + mbo.getInt("MSTBITENSARPID"));
						}
					}
				} else if (getMbo().getBoolean("MSNUFLGTERMOADITIVO")) {
					System.out.println("############ TERMO ADITIVO: " + getMbo().getBoolean("MSNUFLGTERMOADITIVO"));
					//NOTAS DE EMPENHO DE TERMO ADITIVO (COPIAR CONTRACTLINE E MSTBITENSNOTAEMPENHO)
					if (getMbo().getString("MSALCODINSTRUMENTOCONTRATACAO").equals("CONTRATO")) { 
						for (int i = 0; ((mbo= getMbo().getMboSet("CONTRACTLINE").getMbo(i)) !=null); i++) {
							mboDestino = getMbo().getMboSet("MSTBITENSNOTAEMPENHO").add();
							System.out.println("############ add() na itens da nota de empenho");
							
							mboDestino.setValue("MSTBNOTAEMPENHOID", getMbo().getInt("MSTBNOTAEMPENHOID"));
							System.out.println("########### MSTBNOTAEMPENHOID = " + getMbo().getInt("MSTBNOTAEMPENHOID"));
							
							mboDestino.setValue("MSALCODINSTRUMENTOCONTRATACAO", getMbo().getString("MSALCODINSTRUMENTOCONTRATACAO"));
							System.out.println("########### MSALCODINSTRUMENTOCONTRATACAO = " + getMbo().getString("MSALCODINSTRUMENTOCONTRATACAO"));
							
							mboDestino.setValue("MSALNUMINSTRUMENTOCONTRATACAO", getMbo().getString("MSALNUMINSTRUMENTOCONTRATACAO"));
							System.out.println("########### MSALNUMINSTRUMENTOCONTRATACAO = " + getMbo().getString("MSALNUMINSTRUMENTOCONTRATACAO"));
							
							mboDestino.setValue("ID2ITEMNUM", mbo.getString("ID2ITEMNUM"));
							System.out.println("########### ID2ITEMNUM = " + mbo.getString("ID2ITEMNUM"));
							
							mboDestino.setValue("POLINEID", mbo.getInt("POLINEID"));
							System.out.println("############ POLINEID = " + mbo.getInt("POLINEID"));
							
							mboDestino.setValue("MSNUNUMTABELAORIGEMID", mbo.getInt("CONTRACTLINEID"));
							System.out.println("############ MSNUNUMTABELAORIGEMID = " + mbo.getInt("CONTRACTLINEID"));
						} 
					} else if (getMbo().getString("MSALCODINSTRUMENTOCONTRATACAO").equals("NE")) {
						for (int i = 0; ((mbo= getMbo().getMboSet("MSTBITENSNOTAEMPENHOADITIVO").getMbo(i)) !=null); i++) {
							mboDestino = getMbo().getMboSet("MSTBITENSNOTAEMPENHO").add();
							System.out.println("############ add() na itens da nota de empenho");
							
							mboDestino.setValue("MSTBNOTAEMPENHOID", getMbo().getInt("MSTBNOTAEMPENHOID"));
							System.out.println("########### MSTBNOTAEMPENHOID = " + getMbo().getInt("MSTBNOTAEMPENHOID"));
							
							mboDestino.setValue("MSALCODINSTRUMENTOCONTRATACAO", getMbo().getString("MSALCODINSTRUMENTOCONTRATACAO"));
							System.out.println("########### MSALCODINSTRUMENTOCONTRATACAO = " + getMbo().getString("MSALCODINSTRUMENTOCONTRATACAO"));
							
							mboDestino.setValue("MSALNUMINSTRUMENTOCONTRATACAO", getMbo().getString("MSALNUMINSTRUMENTOCONTRATACAO"));
							System.out.println("########### MSALNUMINSTRUMENTOCONTRATACAO = " + getMbo().getString("MSALNUMINSTRUMENTOCONTRATACAO"));
							
							mboDestino.setValue("ID2ITEMNUM", mbo.getString("ID2ITEMNUM"));
							System.out.println("########### ID2ITEMNUM = " + mbo.getString("ID2ITEMNUM"));
							
							mboDestino.setValue("POLINEID", mbo.getInt("POLINEID"));
							System.out.println("############ POLINEID = " + mbo.getInt("POLINEID"));
							
							mboDestino.setValue("MSNUNUMTABELAORIGEMID", mbo.getInt("MSTBITENSNOTAEMPENHOID"));
							System.out.println("############ MSNUNUMTABELAORIGEMID = " + mbo.getInt("MSTBITENSNOTAEMPENHOID"));
						}
					} 
				}
			}
			//mboDestino.getThisMboSet().save();
			super.save();
			
			/*WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
			Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));*/
		
		} catch (RemoteException ex) {
            Logger.getLogger(MsTbPregao.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

}
