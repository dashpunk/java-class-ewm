package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
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
			if(getMbo().getMboSet("MSTBITENSNOTAEMPENHO").isEmpty()){
			
				MboRemote mbo;
				MboRemote mbo1;
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
									System.out.println("############ add() na itens da nota de empenho - PREGÃO");
									
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
									System.out.println("############ add() na itens da nota de empenho - INEXIGIBILIDADE");
									
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
							} else if (getMbo().getString("MSALCODMODALIDADE").equals("DISPENSA")) {
								//DISPENSA
								
								System.out.println("########## MS_CREDORC" + getMbo().getMboSet("MSTBDISPENSA").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_CREDORC"));
								getMbo().setValue("MS_CREDORC", getMbo().getMboSet("MSTBDISPENSA").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_CREDORC"));
								System.out.println("########## MS_ELEDESP" + getMbo().getMboSet("MSTBDISPENSA").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_ELEDESP"));
								getMbo().setValue("MS_ELEDESP", getMbo().getMboSet("MSTBDISPENSA").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_ELEDESP"));
								
								for (int i = 0; ((mbo= getMbo().getMboSet("MSTBITENSDISPENSA").getMbo(i)) !=null); i++) {
									mboDestino = getMbo().getMboSet("MSTBITENSNOTAEMPENHO").add();
									System.out.println("############ add() na itens da nota de empenho - DISPENSA");
									
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
									
									mboDestino.setValue("MSNUNUMTABELAORIGEMID", mbo.getInt("MSTBITENSDISPENSAID"));
									System.out.println("############ MSNUNUMTABELAORIGEMID = " + mbo.getInt("MSTBITENSDISPENSAID"));
									
									mboDestino.setValue("COMPANY", mbo.getString("COMPANY"));
									System.out.println("############ COMPANY = " + mbo.getString("COMPANY"));
									
									mboDestino.setValue("PERSONID", mbo.getString("MSALCODREPRESENTANTE"));
									System.out.println("############ PERSONID = " + mbo.getString("MSALCODREPRESENTANTE"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEREGISTRADA", mbo.getDouble("MSNUNUMQUANTIDADECONTRATADA"));
									System.out.println("############ MSNUNUMQUANTIDADEREGISTRADA = " + mbo.getDouble("MSNUNUMQUANTIDADECONTRATADA"));
									
									mboDestino.setValue("MSNUNUMVALORUNITARIOREGISTRADO", mbo.getDouble("MSNUNUMVALORUNITARIOCONTRATADO"));
									System.out.println("############ MSNUNUMVALORUNITARIOREGISTRADO = " + mbo.getDouble("MSNUNUMVALORUNITARIOCONTRATADO"));
									
									mboDestino.setValue("MSNUNUMVALORTOTALREGISTRADO", (mbo.getDouble("MSNUNUMVALORUNITARIOCONTRATADO") * mbo.getDouble("MSNUNUMQUANTIDADECONTRATADA")));
									System.out.println("############ MSNUNUMVALORTOTALREGISTRADO = " + (mbo.getDouble("MSNUNUMVALORUNITARIOCONTRATADO") * mbo.getDouble("MSNUNUMQUANTIDADECONTRATADA")));
									
									mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDA"));
									System.out.println("############ MSALCODMOEDA = " + mbo.getString("MSALCODMOEDA"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEEMPENHADA", 0);
								}
								//DISPENSA
							} else if (getMbo().getString("MSALCODMODALIDADE").equals("OC")) {
								//ORDEM DE COMPRA
									
								
							
								for (int i = 0; ((mbo= getMbo().getMboSet("MSTBCOTCDJU").getMbo(i)) !=null); i++) {
									mboDestino = getMbo().getMboSet("MSTBITENSNOTAEMPENHO").add();
									System.out.println("############ add() na itens da nota de empenho - ORDEM DE COMPRA");
									
									
									// Setando Status
									for (int j = 0; ((mbo1 = getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(j)) !=null); j++) {
										System.out.println("############ Entrou no for de medicamentos");
										mbo1.setValue("STATUS", "AG.AGENDAMENTO", MboConstants.NOACCESSCHECK);
										System.out.println("########### STATUS = " + mbo1.getString("STATUS"));
									}
									/// Fim	
																				
									mboDestino.setValue("MSTBNOTAEMPENHOID", getMbo().getInt("MSTBNOTAEMPENHOID"));
									System.out.println("########### MSTBNOTAEMPENHOID = " + getMbo().getInt("MSTBNOTAEMPENHOID"));
									
									mboDestino.setValue("MSALCODMODALIDADE", getMbo().getString("MSALCODMODALIDADE"));
									System.out.println("########### MSALCODMODALIDADE = " + getMbo().getString("MSALCODMODALIDADE"));
									
									mboDestino.setValue("MSALNUMMODALIDADE", getMbo().getString("MSALNUMMODALIDADE"));
									System.out.println("########### MSALNUMMODALIDADE = " + getMbo().getString("MSALNUMMODALIDADE"));
									
									mboDestino.setValue("ID2ITEMNUM", mbo.getString("MSSISMAT"));
									System.out.println("########### ID2ITEMNUM = " + mbo.getString("MSSISMAT"));
									
									mboDestino.setValue("POLINEID", mbo.getInt("MSTBCOTCDJUID"));
									System.out.println("############ POLINEID = " + mbo.getInt("MSTBCOTCDJUID"));
									
									mboDestino.setValue("MSNUNUMQUANTIDADEREGISTRADA", mbo.getDouble("MSNUMQNT"));
									System.out.println("############ MSNUNUMQUANTIDADEREGISTRADA = " + mbo.getDouble("MSNUMQNT"));
									
									mboDestino.setValue("MSNUNUMVALORUNITARIOREGISTRADO", mbo.getDouble("MSNUMPREC"));
									System.out.println("############ MSNUNUMVALORUNITARIOREGISTRADO = " + mbo.getDouble("MSNUMPREC"));
									
									mboDestino.setValue("MSNUNUMVALORTOTALREGISTRADO", (mbo.getDouble("MSNUMPREC") * mbo.getDouble("MSNUMQNT")));
									System.out.println("############ MSNUNUMVALORTOTALREGISTRADO = " + (mbo.getDouble("MSNUMPREC") * mbo.getDouble("MSNUMQNT")));
									
									mboDestino.setValue("MSALCODMOEDA", "REAL");
									System.out.println("############ MSALCODMOEDA = REAL");
									
									mboDestino.setValue("MSALNFORNECEDOR", mbo.getString("MSFORNECEDOR"));
									System.out.println("############ MSALNFORNECEDOR = " + mbo.getString("MSFORNECEDOR"));
									
									mboDestino.setValue("MSALNFORNECEDOR", mbo.getString("MSFORNECEDOR"));
									System.out.println("############ MSALNFORNECEDOR = " + mbo.getString("MSFORNECEDOR"));									
																	
									mboDestino.setValue("MSNUNUMQUANTIDADEEMPENHADA", 0);
								}
								//ORDEM DE COMPRA
							} else if (getMbo().getString("MSALCODMODALIDADE").equals("ARP")) {
								//ARP
								
								System.out.println("########## MS_CREDORC" + getMbo().getMboSet("MSTBARP").getMbo(0).getMboSet("MSTBPREGAO").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_CREDORC"));
								getMbo().setValue("MS_CREDORC", getMbo().getMboSet("MSTBARP").getMbo(0).getMboSet("MSTBPREGAO").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_CREDORC"));
								System.out.println("########## MS_ELEDESP" + getMbo().getMboSet("MSTBARP").getMbo(0).getMboSet("MSTBPREGAO").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_ELEDESP"));
								getMbo().setValue("MS_ELEDESP", getMbo().getMboSet("MSTBARP").getMbo(0).getMboSet("MSTBPREGAO").getMbo(0).getMboSet("PO").getMbo(0).getString("MS_ELEDESP"));
								
								for (int i = 0; ((mbo= getMbo().getMboSet("MSTBITENSARP").getMbo(i)) !=null); i++) {
									mboDestino = getMbo().getMboSet("MSTBITENSNOTAEMPENHO").add();
									System.out.println("############ add() na itens da nota de empenho - ARP");
									
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
									System.out.println("############ add() na itens da nota de empenho - TERMO ADITIVO");
									
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
									
									mboDestino.setValue("MSNUNUMQUANTIDADEEMPENHADA", mbo.getDouble("ORDERQTY"));
									System.out.println("############ MSNUNUMQUANTIDADEEMPENHADA = " + mbo.getDouble("ORDERQTY"));
									
									mboDestino.setValue("MSNUNUMVALORTOTALEMPENHADO", mbo.getDouble("LINECOST"));
									System.out.println("############ MSNUNUMVALORTOTALEMPENHADO = " + mbo.getDouble("LINECOST"));
									
									mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDA"));
									System.out.println("############ MSALCODMOEDA = " + mbo.getString("MSALCODMOEDA"));
									
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
									System.out.println("############ add() na itens da nota de empenho - NE");
									
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
									
									mboDestino.setValue("MSNUNUMQUANTIDADEEMPENHADA", mbo.getDouble("MSNUNUMQUANTIDADEEMPENHADA"));
									System.out.println("############ MSNUNUMQUANTIDADEEMPENHADA = " + mbo.getDouble("MSNUNUMQUANTIDADEEMPENHADA"));
									
									mboDestino.setValue("MSNUNUMVALORTOTALEMPENHADO", mbo.getDouble("MSNUNUMVALORTOTALEMPENHADO"));
									System.out.println("############ MSNUNUMVALORTOTALEMPENHADO = " + mbo.getDouble("MSNUNUMVALORTOTALEMPENHADO"));
									
									mboDestino.setValue("MSNUNUMVALORTOTALREGISTRADO", mbo.getDouble("MSNUNUMVALORTOTALREGISTRADO"));
									System.out.println("############ MSNUNUMVALORTOTALREGISTRADO = " + mbo.getDouble("MSNUNUMVALORTOTALREGISTRADO"));
									
									mboDestino.setValue("MSALCODMOEDA", mbo.getString("MSALCODMOEDA"));
									System.out.println("############ MSALCODMOEDA = " + mbo.getString("MSALCODMOEDA"));
									
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
							System.out.println("############ add() na itens da nota de empenho - REFORÇO");
							
							mboDestino.setValue("MSTBNOTAEMPENHOID", getMbo().getInt("MSTBNOTAEMPENHOID"));
							System.out.println("########### MSTBNOTAEMPENHOID = " + getMbo().getInt("MSTBNOTAEMPENHOID"));
							
							mboDestino.setValue("MSNUCODNOTAEMPENHOORIGINAL", getMbo().getInt("MSNUCODNOTAEMPENHOORIGINAL"));
							System.out.println("########### MSNUCODNOTAEMPENHOORIGINAL = " + getMbo().getInt("MSNUCODNOTAEMPENHOORIGINAL"));
							
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
							System.out.println("############ add() na itens da nota de empenho - ANULAÇÃO");
							
							mboDestino.setValue("MSTBNOTAEMPENHOID", getMbo().getInt("MSTBNOTAEMPENHOID"));
							System.out.println("########### MSTBNOTAEMPENHOID = " + getMbo().getInt("MSTBNOTAEMPENHOID"));
							
							mboDestino.setValue("MSNUCODNOTAEMPENHOORIGINAL", getMbo().getInt("MSNUCODNOTAEMPENHOORIGINAL"));
							System.out.println("########### MSNUCODNOTAEMPENHOORIGINAL = " + getMbo().getInt("MSNUCODNOTAEMPENHOORIGINAL"));
							
							
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
					
					if (mbo.getDouble("MSNUNUMQUANTIDADEREGISTRADA") < mbo.getDouble("MSNUNUMQUANTIDADEEMPENHADA")) {
						mbo.setValue("MSNUNUMQUANTIDADEEMPENHADA", 0);
						mbo.setValue("MSNUNUMVALORTOTALEMPENHADO", 0);
						super.save();
						throw new MXApplicationException("notaempenho", "QuantidadeExcedente");
					}
					
					double quantidadeitem = 0d;
					MboRemote mboItensMesmaOrigem;
					
					for (int j = 0; ((mboItensMesmaOrigem= mbo.getMboSet("MSTBITENSNOTAEMPENHOMESMAMODALIDADE").getMbo(j)) !=null); j++) {
						System.out.println("############ MSNUNUMQUANTIDADEEMPENHADA = " + mboItensMesmaOrigem.getDouble("MSNUNUMQUANTIDADEEMPENHADA"));
						System.out.println("############ MSTBITENSNOTAEMPENHOID = " + mboItensMesmaOrigem.getInt("MSTBITENSNOTAEMPENHOID"));
						quantidadeitem += mboItensMesmaOrigem.getDouble("MSNUNUMQUANTIDADEEMPENHADA");
						System.out.println("############ quantidadeitem = " + quantidadeitem);
					}
					
					for (int j = 0; ((mboItensMesmaOrigem= mbo.getMboSet("MSTBITENSNOTAEMPENHOMESMAMODALIDADEANULACAO").getMbo(j)) !=null); j++) {
						System.out.println("############ MSNUNUMQUANTIDADEEMPENHADA = " + mboItensMesmaOrigem.getDouble("MSNUNUMQUANTIDADEEMPENHADA"));
						System.out.println("############ MSTBITENSNOTAEMPENHOID = " + mboItensMesmaOrigem.getInt("MSTBITENSNOTAEMPENHOID"));
						quantidadeitem -= mboItensMesmaOrigem.getDouble("MSNUNUMQUANTIDADEEMPENHADA");
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
				
				if (getMbo().getString("MSALCODMODALIDADE").equals("OC")) {
					for (int i = 0; ((mbo= getMbo().getMboSet("MSTBCOTCDJU").getMbo(i)) !=null); i++) {
						System.out.println("########## Entrou : " + getMbo().getMboSet("MSTBCOTCDJU").getMbo(i));
						System.out.println("########## Entrou 2: " + mbo.getInt("MSTBOCID"));
						//INICIO: MUDAR STATUS DA ORDEM DE COMPRA SE TODOS ITENS TIVEREM NOTA DE EMPENHO	
						MboSet mboSetMedicamento;
						mboSetMedicamento = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MSTBMEDICAMENTO", sessionContext.getUserInfo());

						mboSetMedicamento.setWhere("MSTBOCID = '" + mbo.getInt("MSTBOCID") + "'");
						mboSetMedicamento.reset();
						
						MboSet mboSetNotaEmp;
						mboSetNotaEmp = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MSTBITENSNOTAEMPENHO", sessionContext.getUserInfo());

						mboSetNotaEmp.setWhere("POLINEID = '" + mbo.getInt("MSTBOCID") + "'");
						mboSetNotaEmp.reset();	
						
						MboRemote mboOc = getMbo().getMboSet("MSTBOC").getMbo();
								
								
				        if (mboSetMedicamento.count() == mboSetNotaEmp.count() ) {
				        	mboOc.setValue("MSSTATUS", "FINALIZADA");
				        }
						//FIM: MUDAR STATUS DA ORDEM DE COMPRA SE TODOS ITENS TIVEREM NOTA DE EMPENHO
					}
					
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
