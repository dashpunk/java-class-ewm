package br.inf.ctis.ms.bean;


import psdi.mbo.MboRemote;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;

public class MsFinance extends AppBean {

	/**
	 * @author marcelosydney.lima
	 * psdi.webclient.system.beans.AppBean
	 */
	public MsFinance() {
		System.out.println("########## Versão 2.0");
	}
	
	@Override
	public void save() throws MXException {
				
		try {
			MboRemote mbo1;
			MboRemote mbo2;
			MboRemote mbo3;
			MboRemote mbo4;
			double valorglobal = 0d;
			double saldoinstrumento = 0d;
			
			
			for (int i = 0; ((mbo1 = getMbo().getMboSet("INVOICE").getMbo(i)) != null); i++) {					
				System.out.println("########## Count dos Objetos da Nota Fiscal (INVOICELINE) = " + mbo1.getMboSet("INVOICELINE").count());
				if (!mbo1.toBeDeleted()) {
					if (mbo1.getMboSet("INVOICELINE").count() > 0) {
						
						double valortotalnf = 0d;
						double valortotalnfst = 0d;
						
						for (int j = 0; ((mbo2 = mbo1.getMboSet("INVOICELINE").getMbo(j)) != null); j++) {
							double valor = mbo2.getDouble("LINECOSTFORUI") - mbo2.getDouble("TAX1FORUI");
							mbo2.setValue("MSNUNUMTOTALCOMTRIBUTO", valor);
							System.out.println("########## Valor = " + valor);
							if (!mbo2.toBeDeleted()) {
								valortotalnf += valor;
								valortotalnfst += mbo2.getDouble("LINECOSTFORUI");
							}
						}
						System.out.println("########## Valor Total = " + valortotalnf);
						mbo1.setValue("MSNUNUMVALORTOTAL", valortotalnf);
						System.out.println("########## Valor Total ST = " + valortotalnfst);
						mbo1.setValue("MSNUNUMVALORTOTALSEMTRIBUTO", valortotalnfst);
						
						valorglobal += mbo1.getDouble("MSNUNUMVALORTOTALSEMTRIBUTO");
						System.out.println("########## Valor Global ST = " + valorglobal);
							
					} else {
						throw new MXApplicationException("invoiceline", "SemInsumos");
					}
				}
			}
			
			for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBAUTORIZACAOPAGAMENTO").getMbo(i)) != null); i++) {
				
				double valortotalap = 0d;
				double valortotalapst = 0d;
				
				System.out.println("########## Count Notas Fiscais = " + mbo1.getMboSet("MSTBAPNOTAFISCAL").count());
				if (!mbo1.toBeDeleted()) {
					if (mbo1.getMboSet("MSTBAPNOTAFISCAL").count() > 0) {
										
						for (int j = 0; ((mbo2 = mbo1.getMboSet("MSTBAPNOTAFISCAL").getMbo(j)) != null); j++) {
							if (!mbo2.toBeDeleted()) {
								System.out.println("########## Valor total da NF = " + mbo2.getMboSet("INVOICE").getMbo(0).getDouble("MSNUNUMVALORTOTAL"));
								System.out.println("########## Valor total da NF = " + mbo2.getMboSet("INVOICE").getMbo(0).getDouble("MSNUNUMVALORTOTALSEMTRIBUTO"));
								valortotalap += mbo2.getMboSet("INVOICE").getMbo(0).getDouble("MSNUNUMVALORTOTAL");
								valortotalapst += mbo2.getMboSet("INVOICE").getMbo(0).getDouble("MSNUNUMVALORTOTALSEMTRIBUTO");
								System.out.println("########## Valor Total da AP (PARCIAL) = " + valortotalap);
								System.out.println("########## Valor Total da AP ST (PARCIAL) = " + valortotalapst);
							}
						}
						
						System.out.println("########## Valor Total da AP = " + valortotalap);
						System.out.println("########## Valor Total da AP ST = " + valortotalapst);
						mbo1.setValue("MSNUNUMVALORTOTAL", valortotalap);
						mbo1.setValue("MSNUNUMVALORTOTALSEMTRIBUTO", valortotalapst);
						
					} else {
						throw new MXApplicationException("autorizacaopagamento", "SemNotasFiscais");
					}
				}
			}
			
			for (int i = 0; ((mbo1 = getMbo().getMboSet("MSTBAUTORIZACAOPAGAMENTO").getMbo(i)) != null); i++) {
				
				double valortotalap = mbo1.getDouble("MSNUNUMVALORTOTAL");
				double valortotalapst = mbo1.getDouble("MSNUNUMVALORTOTALSEMTRIBUTO");
				
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
							throw new MXApplicationException("autorizacaopagamento", "ValorAPsuperiorNE");
						}
						
						
					} else {
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
						throw new MXApplicationException("ordensbancarias", "SaldoInsuficiente");
					}
				}
			}
		
			System.out.println("########## Valor Global Final = " + valorglobal);
			saldoinstrumento = getMbo().getDouble("VALOR_TOTAL_INSTRUMENTO") - valorglobal;
			System.out.println("########## Saldo do Instrumento = " + saldoinstrumento);
			if (saldoinstrumento <0) {
				throw new MXApplicationException("invoice", "SaldoInsuficiente");
			}
	
		} catch (RemoteException e) {
			e.printStackTrace();
        }
		
		Properties prop;
		Connection conexao = null;
		Statement st2 = null;
		ResultSet idMax = null;
		
		try {
			
			super.save();
			int ultimoNumero = 0;
			MboRemote mbo6;
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
									
			for (int i = 0; ((mbo6 = getMbo().getMboSet("MSTBAUTORIZACAOPAGAMENTO").getMbo(i)) != null); i++) {
										
				//###########################################################NUMERO DA AP#################################################################
				if(mbo6.getString("MSALNUMAUTORIZACAOPAGAMENTO") == null || mbo6.getString("MSALNUMAUTORIZACAOPAGAMENTO") == ""){
					ultimoNumero += 1;
					System.out.println("########## ultimoNumero += 1 = " + ultimoNumero);
					
					String numeroAp = String.format("%06d", ultimoNumero);
					System.out.println("########## String.format = " + numeroAp);
					
					numeroAp = Integer.toString(Calendar.getInstance().get(Calendar.YEAR))+"AP"+numeroAp;
					
					System.out.println("########## MSALNUMAUTORIZACAOPAGAMENTO = " + numeroAp);
					mbo6.setValue("MSALNUMAUTORIZACAOPAGAMENTO", numeroAp);
				}
				//###########################################################NUMERO DA AP#################################################################
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { conexao.close(); } catch (Exception e) { /**/ }
		    try { st2.close(); } catch (Exception e) { /**/ }
		    try { idMax.close(); } catch (Exception e) { /**/ }
		}
		
		super.save();
		
		try {
			MboRemote mboAp;
		
		
			for (int i = 0; ((mboAp = getMbo().getMboSet("MSTBAUTORIZACAOPAGAMENTO").getMbo(i)) != null); i++) {
				System.out.println("ID AP" + mboAp.getInt("MSTBAUTORIZACAOPAGAMENTOID"));
				if (mboAp.toBeDeleted()) {
					System.out.println("ID AP toBeDeleted()" + mboAp.getInt("MSTBAUTORIZACAOPAGAMENTOID"));
					mboAp.getMboSet("MSTBAPNOTAFISCAL").deleteAll();
					mboAp.getMboSet("").deleteAll();
					mboAp.getMboSet("MSTBORDEMBANCARIA").deleteAll();
				}
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		super.save();
	}
}