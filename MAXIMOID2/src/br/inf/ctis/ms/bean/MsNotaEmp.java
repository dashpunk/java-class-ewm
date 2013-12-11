package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
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
				
				if (getMbo().getMboSet("MSTBITENSNOTAEMPENHO").isEmpty()){
					if (getMbo().getString("MSALCODTIPO").equalsIgnoreCase("ORIGINAL")) {
						if (!getMbo().getBoolean("MSNUFLGTERMOADITIVO")) {
							System.out.println("############ TERMO ADITIVO: " + getMbo().getBoolean("MSNUFLGTERMOADITIVO"));
							//NOTAS DE EMPENHOS NORMAIS (COPIAR MSTBFORNECEDORESITEMPREGAO, MSTBITENSINEXIGIBILIDADE E MSTBITENSARP)
							if (getMbo().getString("MSALCODMODALIDADE").equals("PREGAO")) { 
								//PREGAO
								
								System.out.println("########## MS_CREDORC" + getMbo().getMboSet("MSTBPREGAO").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_CREDORC"));
								getMbo().setValue("MS_CREDORC", getMbo().getMboSet("MSTBPREGAO").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_CREDORC"));
								System.out.println("########## MS_ELEDESP" + getMbo().getMboSet("MSTBPREGAO").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_ELEDESP"));
								getMbo().setValue("MS_ELEDESP", getMbo().getMboSet("MSTBPREGAO").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_ELEDESP"));		
								
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
									
									mboDestino.setValue("COMPANY", mbo.getString("COMPANY"));
									System.out.println("############ COMPANY = " + mbo.getString("COMPANY"));
									
									mboDestino.setValue("PERSONID", mbo.getString("PERSONID"));
									System.out.println("############ PERSONID = " + mbo.getString("PERSONID"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEREGISTRADA", mbo.getDouble("MSNUNUMQUANTIDADE"));
									System.out.println("############ MSNUNUMQUANTIDADEREGISTRADA = " + mbo.getDouble("MSNUNUMQUANTIDADE"));
									
									mboDestino.setValue("MSNUNUMVALORUNITARIOREGISTRADO", mbo.getDouble("MSNUNUMVALORUNITARIO"));
									System.out.println("############ MSNUNUMVALORUNITARIOREGISTRADO = " + mbo.getDouble("MSNUNUMVALORUNITARIO"));
									
									mboDestino.setValue("MSNUNUMVALORTOTALREGISTRADO", mbo.getDouble("MSNUNUMVALORTOTAL"));
									System.out.println("############ MSNUNUMVALORTOTALREGISTRADO = " + mbo.getDouble("MSNUNUMVALORTOTAL"));
									
									mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDA"));
									System.out.println("############ MSALCODMOEDA = " + mbo.getString("MSALCODMOEDA"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEEMPENHADA", 0);
								}
								//PREGAO
							} else if (getMbo().getString("MSALCODMODALIDADE").equals("INEXIGIBILIDADE")) {
								//INEXIGIBILIDADE
								
								System.out.println("########## MS_CREDORC" + getMbo().getMboSet("MSTBINEXIGIBILIDADE").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_CREDORC"));
								getMbo().setValue("MS_CREDORC", getMbo().getMboSet("MSTBINEXIGIBILIDADE").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_CREDORC"));
								System.out.println("########## MS_ELEDESP" + getMbo().getMboSet("MSTBINEXIGIBILIDADE").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_ELEDESP"));
								getMbo().setValue("MS_ELEDESP", getMbo().getMboSet("MSTBINEXIGIBILIDADE").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_ELEDESP"));
								
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
									
									mboDestino.setValue("COMPANY", mbo.getString("MSNUCODFORNECEDORINEX"));
									System.out.println("############ COMPANY = " + mbo.getString("MSNUCODFORNECEDORINEX"));
									
									mboDestino.setValue("PERSONID", mbo.getString("MSALCODREPRESENTANTEINEX"));
									System.out.println("############ PERSONID = " + mbo.getString("MSALCODREPRESENTANTEINEX"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEREGISTRADA", mbo.getDouble("MSNUNUMQTDCONTRATADAINEX"));
									System.out.println("############ MSNUNUMQUANTIDADEREGISTRADA = " + mbo.getDouble("MSNUNUMQTDCONTRATADAINEX"));
									
									mboDestino.setValue("MSNUNUMVALORUNITARIOREGISTRADO", mbo.getDouble("MSNUNUMVALUNICONTRATADOINEX"));
									System.out.println("############ MSNUNUMVALORUNITARIOREGISTRADO = " + mbo.getDouble("MSNUNUMVALUNICONTRATADOINEX"));
									
									mboDestino.setValue("MSNUNUMVALORTOTALREGISTRADO", (mbo.getDouble("MSNUNUMVALUNICONTRATADOINEX") * mbo.getDouble("MSNUNUMQTDCONTRATADAINEX")));
									System.out.println("############ MSNUNUMVALORTOTALREGISTRADO = " + (mbo.getDouble("MSNUNUMVALUNICONTRATADOINEX") * mbo.getDouble("MSNUNUMQTDCONTRATADAINEX")));
									
									mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDAINEX"));
									System.out.println("############ MSALCODMOEDA = " + mbo.getString("MSALCODMOEDAINEX"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEEMPENHADA", 0);
								}
								//INEXIGIBILIDADE
							} else if (getMbo().getString("MSALCODMODALIDADE").equals("ARP")) {
								//ARP
								
								System.out.println("########## MS_CREDORC" + getMbo().getMboSet("MSTBARP").getMbo(0).getMboSet("MSTBPREGAO").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_CREDORC"));
								getMbo().setValue("MS_CREDORC", getMbo().getMboSet("MSTBARP").getMbo(0).getMboSet("MSTBPREGAO").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_CREDORC"));
								System.out.println("########## MS_ELEDESP" + getMbo().getMboSet("MSTBARP").getMbo(0).getMboSet("MSTBPREGAO").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_ELEDESP"));
								getMbo().setValue("MS_ELEDESP", getMbo().getMboSet("MSTBARP").getMbo(0).getMboSet("MSTBPREGAO").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_ELEDESP"));
								
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
									
									mboDestino.setValue("COMPANY", mbo.getMboSet("MSTBARP").getMbo(0).getString("COMPANY"));
									System.out.println("############ COMPANY = " + mbo.getMboSet("MSTBARP").getMbo(0).getString("COMPANY"));
									
									mboDestino.setValue("PERSONID", mbo.getMboSet("MSTBARP").getMbo(0).getString("PERSONID"));
									System.out.println("############ PERSONID = " + mbo.getMboSet("MSTBARP").getMbo(0).getString("PERSONID"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEREGISTRADA", mbo.getDouble("MSNUNUMQUANTIDADEREGISTRADA"));
									System.out.println("############ MSNUNUMQUANTIDADEREGISTRADA = " + mbo.getDouble("MSNUNUMQUANTIDADEREGISTRADA"));
									
									mboDestino.setValue("MSNUNUMVALORUNITARIOREGISTRADO", mbo.getDouble("MSNUNUMVALORUNITARIO"));
									System.out.println("############ MSNUNUMVALORUNITARIOREGISTRADO = " + mbo.getDouble("MSNUNUMVALORUNITARIO"));
									
									mboDestino.setValue("MSNUNUMVALORTOTALREGISTRADO", mbo.getDouble("MSNUNUMVALORTOTAL"));
									System.out.println("############ MSNUNUMVALORTOTALREGISTRADO = " + mbo.getDouble("MSNUNUMVALORTOTAL"));
									
									mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDA"));
									System.out.println("############ MSALCODMOEDA = " + mbo.getString("MSALCODMOEDA"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEEMPENHADA", 0);
								}
								//ARP
							}
						} else if (getMbo().getBoolean("MSNUFLGTERMOADITIVO")) {
							System.out.println("############ TERMO ADITIVO: " + getMbo().getBoolean("MSNUFLGTERMOADITIVO"));
							//NOTAS DE EMPENHO DE TERMO ADITIVO (COPIAR CONTRACTLINE E MSTBITENSNOTAEMPENHO)
							if (getMbo().getString("MSALCODINSTRUMENTOCONTRATACAO").equals("CONTRATO")) { 
								//CONTRATO
														
								System.out.println("########## MS_CREDORC" + getMbo().getMboSet("MSTBNOTAEMPENHO").getMbo(0).getString("MS_CREDORC"));
								getMbo().setValue("MS_CREDORC", getMbo().getMboSet("MSTBNOTAEMPENHO").getMbo(0).getString("MS_CREDORC"));
								System.out.println("########## MS_ELEDESP" + getMbo().getMboSet("MSTBNOTAEMPENHO").getMbo(0).getString("MS_ELEDESP"));
								getMbo().setValue("MS_ELEDESP", getMbo().getMboSet("MSTBNOTAEMPENHO").getMbo(0).getString("MS_ELEDESP"));
								 
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
									
									mboDestino.setValue("COMPANY", mbo.getString("COMPANY"));
									System.out.println("############ COMPANY = " + mbo.getString("COMPANY"));
									
									mboDestino.setValue("PERSONID", mbo.getString("PERSONID"));
									System.out.println("############ PERSONID = " + mbo.getString("PERSONID"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEREGISTRADA", mbo.getDouble("ORDERQTY"));
									System.out.println("############ MSNUNUMQUANTIDADEREGISTRADA = " + mbo.getDouble("ORDERQTY"));
									
									mboDestino.setValue("MSNUNUMVALORUNITARIOREGISTRADO", mbo.getDouble("UNITCOST"));
									System.out.println("############ MSNUNUMVALORUNITARIOREGISTRADO = " + mbo.getDouble("UNITCOST"));
									
									mboDestino.setValue("MSNUNUMVALORTOTALREGISTRADO", mbo.getDouble("LINECOST"));
									System.out.println("############ MSNUNUMVALORTOTALREGISTRADO = " + mbo.getDouble("LINECOST"));
									
									mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDA"));
									System.out.println("############ MSALCODMOEDA = " + mbo.getString("MSALCODMOEDA"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEEMPENHADA", 0);
								}
								//CONTRATO
							} else if (getMbo().getString("MSALCODINSTRUMENTOCONTRATACAO").equals("NE")) {
								//NOTA DE EMPENHO
								
								System.out.println("########## MS_CREDORC" + getMbo().getMboSet("MSTBNOTAEMPENHO").getMbo(0).getString("MS_CREDORC"));
								getMbo().setValue("MS_CREDORC", getMbo().getMboSet("MSTBNOTAEMPENHO").getMbo(0).getString("MS_CREDORC"));
								System.out.println("########## MS_ELEDESP" + getMbo().getMboSet("MSTBNOTAEMPENHO").getMbo(0).getString("MS_ELEDESP"));
								getMbo().setValue("MS_ELEDESP", getMbo().getMboSet("MSTBNOTAEMPENHO").getMbo(0).getString("MS_ELEDESP"));
								
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
									
									mboDestino.setValue("COMPANY", mbo.getString("COMPANY"));
									System.out.println("############ COMPANY = " + mbo.getString("COMPANY"));
									
									mboDestino.setValue("PERSONID", mbo.getString("PERSONID"));
									System.out.println("############ PERSONID = " + mbo.getString("PERSONID"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEREGISTRADA", mbo.getDouble("MSNUNUMQUANTIDADEREGISTRADA"));
									System.out.println("############ MSNUNUMQUANTIDADEREGISTRADA = " + mbo.getDouble("MSNUNUMQUANTIDADEREGISTRADA"));
									
									mboDestino.setValue("MSNUNUMVALORUNITARIOREGISTRADO", mbo.getDouble("MSNUNUMVALORUNITARIOREGISTRADO"));
									System.out.println("############ MSNUNUMVALORUNITARIOREGISTRADO = " + mbo.getDouble("MSNUNUMVALORUNITARIOREGISTRADO"));
									
									mboDestino.setValue("MSNUNUMVALORTOTALREGISTRADO", mbo.getDouble("MSNUNUMVALORTOTALREGISTRADO"));
									System.out.println("############ MSNUNUMVALORTOTALREGISTRADO = " + mbo.getDouble("MSNUNUMVALORTOTALREGISTRADO"));
									
									mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDA"));
									System.out.println("############ MSALCODMOEDA = " + mbo.getString("MSALCODMOEDA"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEEMPENHADA", 0);
								}
								//NOTA DE EMPENHO
							} 
						}
					} else if (getMbo().getString("MSALCODTIPO").equalsIgnoreCase("REFORCO")) {
						//REFORCO
						
						System.out.println("########## MS_CREDORC" + getMbo().getMboSet("MSTBNOTAEMPENHOORIGINAL").getMbo(0).getString("MS_CREDORC"));
						getMbo().setValue("MS_CREDORC", getMbo().getMboSet("MSTBNOTAEMPENHOORIGINAL").getMbo(0).getString("MS_CREDORC"));
						System.out.println("########## MS_ELEDESP" + getMbo().getMboSet("MSTBNOTAEMPENHOORIGINAL").getMbo(0).getString("MS_ELEDESP"));
						getMbo().setValue("MS_ELEDESP", getMbo().getMboSet("MSTBNOTAEMPENHOORIGINAL").getMbo(0).getString("MS_ELEDESP"));
						
						for (int i = 0; ((mbo= getMbo().getMboSet("MSTBITENSNOTAEMPENHOORIGINAL").getMbo(i)) !=null); i++) {
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
							
							mboDestino.setValue("COMPANY", mbo.getString("COMPANY"));
							System.out.println("############ COMPANY = " + mbo.getString("COMPANY"));
							
							mboDestino.setValue("PERSONID", mbo.getString("PERSONID"));
							System.out.println("############ PERSONID = " + mbo.getString("PERSONID"));
							
							mboDestino.setValue("MSNUNUMQUANTIDADEREGISTRADA", mbo.getDouble("MSNUNUMQUANTIDADEEMPENHADA"));
							System.out.println("############ MSNUNUMQUANTIDADEREGISTRADA = " + mbo.getDouble("MSNUNUMQUANTIDADEEMPENHADA"));
							
							mboDestino.setValue("MSNUNUMVALORUNITARIOREGISTRADO", mbo.getDouble("MSNUNUMVALORUNITARIOREGISTRADO"));
							System.out.println("############ MSNUNUMVALORUNITARIOREGISTRADO = " + mbo.getDouble("MSNUNUMVALORUNITARIOREGISTRADO"));
							
							mboDestino.setValue("MSNUNUMVALORTOTALREGISTRADO", mbo.getDouble("MSNUNUMVALORTOTALEMPENHADO"));
							System.out.println("############ MSNUNUMVALORTOTALREGISTRADO = " + mbo.getDouble("MSNUNUMVALORTOTALEMPENHADO"));
							
							mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDA"));
							System.out.println("############ MSALCODMOEDA = " + mbo.getString("MSALCODMOEDA"));
							
							mboDestino.setValue("MSNUNUMQUANTIDADEEMPENHADA", 0);
						}
						//REFORCO
					} else if (getMbo().getString("MSALCODTIPO").equalsIgnoreCase("ANULACAO")) {
						//ANULACAO
						
						System.out.println("########## MS_CREDORC" + getMbo().getMboSet("MSTBNOTAEMPENHOORIGINAL").getMbo(0).getString("MS_CREDORC"));
						getMbo().setValue("MS_CREDORC", getMbo().getMboSet("MSTBNOTAEMPENHOORIGINAL").getMbo(0).getString("MS_CREDORC"));
						System.out.println("########## MS_ELEDESP" + getMbo().getMboSet("MSTBNOTAEMPENHOORIGINAL").getMbo(0).getString("MS_ELEDESP"));
						getMbo().setValue("MS_ELEDESP", getMbo().getMboSet("MSTBNOTAEMPENHOORIGINAL").getMbo(0).getString("MS_ELEDESP"));
						
						for (int i = 0; ((mbo= getMbo().getMboSet("MSTBITENSNOTAEMPENHOORIGINAL").getMbo(i)) !=null); i++) {
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
							
							mboDestino.setValue("COMPANY", mbo.getString("COMPANY"));
							System.out.println("############ COMPANY = " + mbo.getString("COMPANY"));
							
							mboDestino.setValue("PERSONID", mbo.getString("PERSONID"));
							System.out.println("############ PERSONID = " + mbo.getString("PERSONID"));
							
							mboDestino.setValue("MSNUNUMQUANTIDADEREGISTRADA", mbo.getDouble("MSNUNUMQUANTIDADEEMPENHADA"));
							System.out.println("############ MSNUNUMQUANTIDADEREGISTRADA = " + mbo.getDouble("MSNUNUMQUANTIDADEEMPENHADA"));
							
							mboDestino.setValue("MSNUNUMVALORUNITARIOREGISTRADO", mbo.getDouble("MSNUNUMVALORUNITARIOREGISTRADO"));
							System.out.println("############ MSNUNUMVALORUNITARIOREGISTRADO = " + mbo.getDouble("MSNUNUMVALORUNITARIOREGISTRADO"));
							
							mboDestino.setValue("MSNUNUMVALORTOTALREGISTRADO", mbo.getDouble("MSNUNUMVALORTOTALEMPENHADO"));
							System.out.println("############ MSNUNUMVALORTOTALREGISTRADO = " + mbo.getDouble("MSNUNUMVALORTOTALEMPENHADO"));
							
							mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDA"));
							System.out.println("############ MSALCODMOEDA = " + mbo.getString("MSALCODMOEDA"));
							
							mboDestino.setValue("MSNUNUMQUANTIDADEEMPENHADA", 0);
						}
						//ANULACAO
					}
				}
			
				super.save();
			
			} else {
				
				MboRemote mbo;
				double valorglobal = 0d;
				
				for (int i = 0; ((mbo= getMbo().getMboSet("MSTBITENSNOTAEMPENHO").getMbo(i)) !=null); i++) {
					
					double quantidadeitem = 0d;
					MboRemote mboItensMesmaOrigem;
					
					for (int j = 0; ((mboItensMesmaOrigem= mbo.getMboSet("MSTBITENSNOTAEMPENHOMESMAMODALIDADE").getMbo(j)) !=null); j++) {
						System.out.println("############ MSNUNUMQUANTIDADEEMPENHADA = " + mboItensMesmaOrigem.getDouble("MSNUNUMQUANTIDADEEMPENHADA"));
						System.out.println("############ MSTBITENSNOTAEMPENHOID = " + mboItensMesmaOrigem.getInt("MSTBITENSNOTAEMPENHOID"));
						quantidadeitem += mboItensMesmaOrigem.getDouble("MSNUNUMQUANTIDADEEMPENHADA");
						System.out.println("############ quantidadeitem = " + quantidadeitem);
					}
					
					System.out.println("############ quantidadeitem(FINAL) = " + quantidadeitem);
					//super.save();
					
					System.out.println("############ MSNUNUMQUANTIDADEREGISTRADA = " + mbo.getDouble("MSNUNUMQUANTIDADEREGISTRADA"));
					System.out.println("############ quantidadeitem + MSNUNUMQUANTIDADEEMPENHADA = " + quantidadeitem + mbo.getDouble("MSNUNUMQUANTIDADEEMPENHADA"));
					
					if ((mbo.getDouble("MSNUNUMQUANTIDADEREGISTRADA") >= (quantidadeitem + mbo.getDouble("MSNUNUMQUANTIDADEEMPENHADA"))) || (getMbo().getString("MSALCODTIPO").equalsIgnoreCase("REFORCO"))) {
						if (mbo.getBoolean("MSNUFLGSELECAO")){
							valorglobal += mbo.getDouble("MSNUNUMQUANTIDADEEMPENHADA") * mbo.getDouble("MSNUNUMVALORUNITARIOREGISTRADO");
						}
					} else if (mbo.getDouble("MSNUNUMQUANTIDADEREGISTRADA") < (quantidadeitem + mbo.getDouble("MSNUNUMQUANTIDADEEMPENHADA"))) {
						mbo.setValue("MSNUNUMQUANTIDADEEMPENHADA", 0);
						mbo.setValue("MSNUNUMVALORTOTALEMPENHADO", 0);
						super.save();
						throw new MXApplicationException("notaempenho", "QuantidadeExcedente");
					}
					
					mbo.setValue("MSNUNUMVALORTOTALEMPENHADO", (mbo.getDouble("MSNUNUMQUANTIDADEEMPENHADA") * mbo.getDouble("MSNUNUMVALORUNITARIOREGISTRADO")));
					
				}
				
				getMbo().setValue("MSNUNUMVALOREMPENHO", valorglobal);
				super.save();
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
