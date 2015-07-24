package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class MsFinanceImport extends AppBean {

	/**
	 * @author marcelosydney.lima
	 * psdi.webclient.system.beans.AppBean
	 */
	public MsFinanceImport() {
	}
	
	@Override
	public void save() throws MXException {
		Properties prop;
		Connection conexao = null;
		Statement st2 = null;
		ResultSet idMax = null;
//		HashMap<String, String> message = new HashMap<String, String>();
		boolean blnPossuiFaturaDiLi = false;
		
		try {
				MboRemote mbo1;
				MboRemote mbo2;
				MboRemote mbo3;
				MboRemote mbo4;
				double valorglobal = 0d;
				double saldoinstrumento = 0d;
				double valortotalap = 0d;
				double valortotalapst = 0d;
				int ultimoNumero = 0;
				
				for (int i = 0; ((mbo1 = getMbo().getMboSet("INVOICE").getMbo(i)) != null); i++) {					
					System.out.println("########## Count dos   Objetos da Nota Fiscal (INVOICELINE) = " + mbo1.getMboSet("INVOICELINE").count());
					if (!mbo1.toBeDeleted()) {
						if (mbo1.getMboSet("INVOICELINE").count() > 0) {
							
//							double valortotalnf = 0d;
							double valortotalnfst = 0d;
//							blnPossuiFaturaDiLi = true;
							
							for (int j = 0; ((mbo2 = mbo1.getMboSet("INVOICELINE").getMbo(j)) != null); j++) {
//								double valor = mbo2.getDouble("LINECOSTFORUI") - mbo2.getDouble("TAX1FORUI");
								
//								mbo2.setValue("MSNUNUMTOTALCOMTRIBUTO", valor);
//								System.out.println("########## Valor = " + valor);
								
								if (!mbo2.toBeDeleted()) {
//									valortotalnf += valor;
									valortotalnfst += mbo2.getDouble("LINECOSTFORUI");
								}
							}
//							System.out.println("########## Valor Total = " + valortotalnf);
//							mbo1.setValue("MSNUNUMVALORTOTAL", valortotalnf);
							System.out.println("########## Valor Total ST = " + valortotalnfst);
							mbo1.setValue("MSNUNUMVALORTOTALSEMTRIBUTO", valortotalnfst);
							mbo1.setValue("MSNUNUMVALORTOTAL", valortotalnfst);
							
							valorglobal += mbo1.getDouble("MSNUNUMVALORTOTALSEMTRIBUTO");
							System.out.println("########## Valor Global ST = " + valorglobal);
								
						}else{
//							message.put("invoiceline", "SemInsumos");
//							throw new MXApplicationException("invoiceline", "SemInsumos");
						}
					}
				}
				
				prop = MXServer.getMXServer().getConfig();
				
				String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
	            String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@srvoradf0.saude.gov:1521/DFPO1.SAUDE.GOV");
	            String username = prop.getProperty("mxe.db.user", "dbmaximo");
	            String password = prop.getProperty("mxe.db.password", "max894512");
				Class.forName(driver).newInstance();
				
				conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
				
				st2 = conexao.createStatement();
				idMax = st2.executeQuery("select nvl(max(TO_NUMBER(ltrim(substr(MSALNUMAUTORIZACAOPAGAMENTO,7)))),0) from MSTBAUTORIZACAOPAGAMENTO where substr(MSALNUMAUTORIZACAOPAGAMENTO,0,4)=EXTRACT(YEAR FROM SYSDATE)");
				
				
				if (idMax.next()) {
					ultimoNumero = idMax.getInt(1);
				}	
				
				System.out.println("########## ultimoNumero = " + ultimoNumero);
										
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBAUTORIZACAOPAGAMENTO").getMbo(i)) != null); i++) {
					
					//###########################################################NUMERO DA AP#################################################################
					if(mbo1.getString("MSALNUMAUTORIZACAOPAGAMENTO") == null || mbo1.getString("MSALNUMAUTORIZACAOPAGAMENTO") == ""){
						ultimoNumero += 1;
						System.out.println("########## ultimoNumero += 1 = " + ultimoNumero);
						
						String numeroAp = String.format("%06d", ultimoNumero);
						System.out.println("########## String.format = " + numeroAp);
						
						numeroAp = Integer.toString(Calendar.getInstance().get(Calendar.YEAR))+"AP"+numeroAp;
						
						System.out.println("########## MSALNUMAUTORIZACAOPAGAMENTO = " + numeroAp);
						mbo1.setValue("MSALNUMAUTORIZACAOPAGAMENTO", numeroAp);
					}
					//###########################################################NUMERO DA AP#################################################################
					
					System.out.println("########## Count Notas Fiscais = " + mbo1.getMboSet("MSTBAPNOTAFISCAL").count());
					if (!mbo1.toBeDeleted()) {
						if (mbo1.getMboSet("MSTBAPNOTAFISCAL").count() > 0) {
							blnPossuiFaturaDiLi = true;
							for (int j = 0; ((mbo2 = mbo1.getMboSet("MSTBAPNOTAFISCAL").getMbo(j)) != null); j++) {
								if (!mbo2.toBeDeleted()) {
//									System.out.println("########## Valor total da NF = " + mbo2.getMboSet("INVOICE").getMbo(0).getDouble("MSNUNUMVALORTOTAL"));
									System.out.println("########## Valor total da NF = " + mbo2.getMboSet("INVOICE").getMbo(0).getDouble("MSNUNUMVALORTOTALSEMTRIBUTO"));
//									valortotalap += mbo2.getMboSet("INVOICE").getMbo(0).getDouble("MSNUNUMVALORTOTAL");
									valortotalap += mbo2.getMboSet("INVOICE").getMbo(0).getDouble("MSNUNUMVALORTOTALSEMTRIBUTO");
									valortotalapst += mbo2.getMboSet("INVOICE").getMbo(0).getDouble("MSNUNUMVALORTOTALSEMTRIBUTO");
									System.out.println("########## Valor Total da AP (PARCIAL) = " + valortotalap);
									System.out.println("########## Valor Total da AP ST (PARCIAL) = " + valortotalapst);
								}
							}
							
							System.out.println("########## Valor Total da AP = " + valortotalap);
							System.out.println("########## Valor Total da AP ST = " + valortotalapst);
							mbo1.setValue("MSNUNUMVALORTOTAL", valortotalap);
							mbo1.setValue("MSNUNUMVALORTOTALSEMTRIBUTO", valortotalapst);
							
						}else{
//							throw new MXApplicationException("autorizacaopagamento", "SemNotasFiscais");
						}
					}
				}
				
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBAUTORIZACAOPAGAMENTO").getMbo(i)) != null); i++) {
					System.out.println("########## Count Notas de Empenho = " + mbo1.getMboSet("MSTBAPNOTAEMPENHO").count());
					if (!mbo1.toBeDeleted()) {
						if (mbo1.getMboSet("MSTBAPNOTAEMPENHO").count() > 0) {
													
							double valortotalne = 0d;
							
							for (int j = 0; ((mbo3 = mbo1.getMboSet("MSTBAPNOTAEMPENHO").getMbo(j)) != null); j++) {
								if (!mbo3.toBeDeleted()) {
									System.out.println("########## Valor total da NE = " + mbo3.getMboSet("MSTBNOTAEMPENHO").getMbo(0).getDouble("MSNUNUMVALOREMPENHO"));
									valortotalne += mbo3.getMboSet("MSTBNOTAEMPENHO").getMbo(0).getDouble("MSNUNUMVALOREMPENHO");
									System.out.println("########## Valor Total das NEs (PARCIAL) = " + valortotalne);
								}
							}
							
							System.out.println("########## Valor Total das NEs = " + valortotalne);
							
							if (valortotalapst > valortotalne){
//								message.put("autorizacaopagamento", "ValorAPsuperiorNE");
								throw new MXApplicationException("", "Não é possível salvar uma AP sem uma NE!");
							}
							
							
						} else {
							blnPossuiFaturaDiLi = false;
							throw new MXApplicationException("autorizacaopagamento", "SemNotasEmpenho");
						}
					}
				}
	
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBAUTORIZACAOPAGAMENTO").getMbo(i)) != null); i++) {		
															
					System.out.println("########## Count Ordens Bancarias = " + mbo1.getMboSet("MSTBORDEMBANCARIA").count());
					if (mbo1.getMboSet("MSTBORDEMBANCARIA").count() > 0) {
											
						double valortotalob = 0d;
						double saldoap = 0d;
						
						for (int j = 0; ((mbo4 = mbo1.getMboSet("MSTBORDEMBANCARIA").getMbo(j)) != null); j++) {
							if (!mbo4.toBeDeleted()) {
								System.out.println("########## Valor da OB = " + mbo4.getDouble("MSNUNUMVALOR"));
								valortotalob += mbo4.getDouble("MSNUNUMVALOR");
								System.out.println("########## Valor total das OBs (PARCIAL) = " + valortotalob);
							}
						}
						
						saldoap = mbo1.getDouble("MSNUNUMVALORTOTAL") - valortotalob;
						if(saldoap >= 0){
							System.out.println("########## Saldo da AP = " + saldoap);
							mbo1.setValue("MSNUNUMSALDO", saldoap);
						} else if (saldoap < 0){
//							message.put("ordensbancarias", "SaldoInsuficiente");
							throw new MXApplicationException("ordensbancarias", "SaldoInsuficiente");
						}
					}
				}
				
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBAUTORIZACAOPAGAMENTO").getMbo(i)) != null); i++) {
					if (!mbo1.toBeDeleted()) {
						if (mbo1.getMboSet("MSTBAPLI").count() > 0) {
							blnPossuiFaturaDiLi = true;
						}
					}
				}
				
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBAUTORIZACAOPAGAMENTO").getMbo(i)) != null); i++) {
					if (!mbo1.toBeDeleted()) {
						if (mbo1.getMboSet("MSTBAPDI").count() > 0) {
							blnPossuiFaturaDiLi = true;
						}
					}
				}
				
				idMax = st2.executeQuery("select nvl(max(TO_NUMBER(ltrim(substr(MSALNNUMTERMOACEITE,7)))),0) from MSTBORDEMBANCARIA where substr(MSALNNUMTERMOACEITE,0,4)=EXTRACT(YEAR FROM SYSDATE)");
				
				ultimoNumero = 0;

				if (idMax.next()) {
					ultimoNumero = idMax.getInt(1);
				}	
				
				System.out.println("########## ultimoNumero = " + ultimoNumero);
				
				
				for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBORDEMBANCARIA").getMbo(i)) != null); i++) {
					
					//###########################################################NUMERO DO TERMO DE ACEITE#################################################################
					if(mbo1.getString("MSALNNUMTERMOACEITE") == null || mbo1.getString("MSALNNUMTERMOACEITE") == ""){
						ultimoNumero += 1;
						System.out.println("########## ultimoNumero += 1 = " + ultimoNumero);
						
						String numeroTA = String.format("%06d", ultimoNumero);
						System.out.println("########## String.format = " + numeroTA);
						
						numeroTA = Integer.toString(Calendar.getInstance().get(Calendar.YEAR))+"TA"+numeroTA;
						
						System.out.println("########## MSALNNUMTERMOACEITE = " + numeroTA);
						mbo1.setValue("MSALNNUMTERMOACEITE", numeroTA);
					}
					//###########################################################NUMERO DO TERMO DE ACEITE#################################################################
					
					if((mbo1.getString("MSALNNUMTERMOACEITE") != null && !mbo1.getString("MSALNNUMTERMOACEITE").equalsIgnoreCase("")) &&
							(mbo1.getString("MSALNUMORDEMBANCARIA") != null && !mbo1.getString("MSALNUMORDEMBANCARIA").equalsIgnoreCase("")) &&
							(mbo1.getString("MSNUNUMCARTACREDITO") != null && mbo1.getInt("MSNUNUMCARTACREDITO") != 0) && 
							(mbo1.getString("MSALNDESCRICAOPRODUTO") != null && !mbo1.getString("MSALNDESCRICAOPRODUTO").equalsIgnoreCase("")) &&
							(mbo1.getString("MSALNBANCONEGOCIADOR") != null && !mbo1.getString("MSALNBANCONEGOCIADOR").equalsIgnoreCase("")) &&
							(mbo1.getString("MSNUNUMBLEMBARQUE") != null && !mbo1.getString("MSNUNUMBLEMBARQUE").equalsIgnoreCase("")) &&
							(mbo1.getString("MSALNNUMEMBARQUE") != null && !mbo1.getString("MSALNNUMEMBARQUE").equalsIgnoreCase("")) &&
							(mbo1.getString("MSNUNUMUNIDADES") != null && mbo1.getInt("MSNUNUMUNIDADES") != 0) &&
							(mbo1.getString("MSNUNUMPORCENTAGEM") != null && mbo1.getInt("MSNUNUMPORCENTAGEM") > 0) &&
							(mbo1.getString("MSNUNUMVALORPAGAR") != null && mbo1.getFloat("MSNUNUMVALORPAGAR") > new Float(0))){
						
						DateFormat formatter1;
						formatter1 = new SimpleDateFormat("dd/MM/yyyy");
						
						mbo1.setValue("MSDTDTATERMOACEITE", formatter1.format(new Date()));
					}
					
				}
			
			if(blnPossuiFaturaDiLi==false){
				throw new MXApplicationException("", "Inclua pelo menos uma Fatura e/ou Declaração de Importação(DI) e/ou Licença de Importação(LI) a uma autorização de pagamento.");
			}
				
			System.out.println("########## Valor Global Final = " + valorglobal);
			saldoinstrumento = getMbo().getDouble("VALORTOTALINSTRUMENTO") - valorglobal;
			System.out.println("########## Saldo do Instrumento = " + saldoinstrumento);
			
			if(saldoinstrumento >= 0){
				super.save();
			} else if (saldoinstrumento <0){
//				message.put("invoice", "SaldoInsuficiente");
				throw new MXApplicationException("invoice", "SaldoInsuficiente");
			}
				
			//mboDestino.getThisMboSet().save();
			
			
			/*WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
			Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));*/
		
		}catch (RemoteException ex) {
            Logger.getLogger(MsFinance.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception e) {       	
        	Logger.getLogger(MsFinance.class.getName()).log(Level.SEVERE, null, e);
    		throw new MXApplicationException("", e.getMessage());
        	
/*        	if(!message.isEmpty()){
        		if(message.get("invoiceline").equalsIgnoreCase("SemInsumos")){
            		Logger.getLogger(MsFinance.class.getName()).log(Level.SEVERE, null, e);
            		throw new MXApplicationException("invoiceline", message.get("invoiceline"));
            	}else if(message.get("autorizacaopagamento").equalsIgnoreCase("ValorAPsuperiorNE")){ //"autorizacaopagamento", "ValorAPsuperiorNE"
            		Logger.getLogger(MsFinance.class.getName()).log(Level.SEVERE, null, e);
            		throw new MXApplicationException("autorizacaopagamento", message.get("ValorAPsuperiorNE"));
            	}else if(message.get("ordensbancarias").equalsIgnoreCase("SaldoInsuficiente")){ //"ordensbancarias", "SaldoInsuficiente"
            		Logger.getLogger(MsFinance.class.getName()).log(Level.SEVERE, null, e);
            		throw new MXApplicationException("ordensbancarias", message.get("SaldoInsuficiente"));
            	}else if(message.get("invoice").equalsIgnoreCase("SaldoInsuficiente")){ //"invoice", "SaldoInsuficiente"
            		Logger.getLogger(MsFinance.class.getName()).log(Level.SEVERE, null, e);
            		throw new MXApplicationException("invoice", message.get("SaldoInsuficiente"));
            	}
        	}else{
        		Logger.getLogger(MsFinance.class.getName()).log(Level.SEVERE, null, e);
        		throw new MXApplicationException("", e.getMessage());
        	}*/
        	
		} finally {
		    try { conexao.close(); } catch (Exception e) { /**/ }
		    try { st2.close(); } catch (Exception e) { /**/ }
		    try { idMax.close(); } catch (Exception e) { /**/ }
		}
	}
}