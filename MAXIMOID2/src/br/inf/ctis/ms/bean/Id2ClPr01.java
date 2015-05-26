package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.pr.PRAppBean;

public class Id2ClPr01 extends PRAppBean {

    /**
     * @author marcelosydney.lima
     * 
     * Copia da classe psdi.webclient.beans.id2.mapa.ID2ClPRAppBean
     * 
     * Realizada limpeza de codigo comentado e inclusao de novas regras de negocio
     * 
     * 
     */
    public Id2ClPr01() {
    }

    @Override
	public void save() throws MXException {
    	    	   	
    	try {
		    		   		
    		if (!getMbo().isNull("MSALCODSITEID")){
				getMbo().setValue("SITEID", getMbo().getString("MSALCODSITEID"), MboConstants.NOACCESSCHECK);
			}
    		
			//-------------------------------------------------------------------PREVISAO DE ENTREGA
			
			MboRemote itemEntrega;
			    		
			for (int i = 0; ((itemEntrega = getMbo().getMboSet("PRLINEENTREGA").getMbo(i)) != null); i++) {
				
				if (itemEntrega.getString("ID2DISTDIRETA").equalsIgnoreCase("AMBOS")) {
					
					int countEntregas = itemEntrega.getMboSet("MSTBPREVISAOENTREGA").count();
					System.out.println("########## countEntregas: " + countEntregas);
					
					if (countEntregas < 1) {
						throw new MXApplicationException("ambos", "NecessarioCadastroDeUmaEntrega");
					} else {
					
						MboRemote mbo;
						int contador = 0;
						
						for (int j = 0; ((mbo = itemEntrega.getMboSet("MSTBPREVISAOENTREGA").getMbo(j)) != null); j++) {
							if (!mbo.toBeDeleted()) {
								contador++;
							}
						}
						System.out.println("########## contador: " + contador);
						
						if (contador < 1) {
							app.gotoTab("tab_entrega");
							throw new MXApplicationException("ambos", "NecessarioCadastroDeUmaEntrega");
						}
						
					}
				} else if (itemEntrega.getString("ID2DISTDIRETA").equalsIgnoreCase("NAO")) {
	    					
					Double valor = 0d;
					MboRemote mbo;
					
					for (int j = 0; ((mbo = itemEntrega.getMboSet("MSTBPREVISAOENTREGA").getMbo(j)) != null); j++) {
						
						System.out.println("########## Data: " + mbo.getString("MSALDTAENTREGA") + " ########## Quantidade: " + mbo.getDouble("MSNUNUMQUANTIDADE"));
						valor += mbo.getDouble("MSNUNUMQUANTIDADE");
						
						System.out.println("########## valor: " + valor);
					}
								        
			        if (valor != itemEntrega.getDouble("ORDERQTY")) {
			        	throw new MXApplicationException("entrega", "FaltaEntregas");
			        }
	
				} 
			}
			//-------------------------------------------------------------------PREVISAO DE ENTREGA
			
			//-------------------------------------------------------------------PREVISAO DE DISTRIBUICAO
			
			MboRemote itemDistribuicao;
			    		
			for (int i = 0; ((itemDistribuicao = getMbo().getMboSet("PRLINEDISTRIBUICAO").getMbo(i)) != null); i++) {
				
				/*if (itemDistribuicao.getString("ID2DISTDIRETA").equalsIgnoreCase("AMBOS")) {
					if (itemDistribuicao.getMboSet("MSTBPREVISAODISTRIBUICAO").count() < 1) {
						throw new MXApplicationException("ambos", "NecessarioCadastroDeUmaDistribuicao");
					}
				} else */
				if (itemDistribuicao.getString("ID2DISTDIRETA").equalsIgnoreCase("SIM")) {
	    					
					Double valor = 0d;
					MboRemote mbo;
					
					for (int j = 0; ((mbo = itemDistribuicao.getMboSet("MSTBPREVISAODISTRIBUICAO").getMbo(j)) != null); j++) {
						
						System.out.println("########## Data: " + mbo.getString("MSALDTADISTRIBUICAO") + " ########## Quantidade: " + mbo.getDouble("MSNUNUMQUANTIDADE"));
						valor += mbo.getDouble("MSNUNUMQUANTIDADE");
						
						System.out.println("########## valor: " + valor);
					}
			        
			        if (valor != itemDistribuicao.getDouble("ORDERQTY")) {
			        	throw new MXApplicationException("distribuicao", "FaltaDistribuicoes");
			        }
	
				} 
			}
			//-------------------------------------------------------------------PREVISAO DE DISTRIBUICAO
			
    	} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		super.save();
    	
    	Properties prop;
		Connection conexao = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
    	
    	try {
    					
			//-------------------------------------------------------------------CONEXAO
    		prop = MXServer.getMXServer().getConfig();
			
			String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
            String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@srvoradf0.saude.gov:1521/DFPO1.SAUDE.GOV");
            String username = prop.getProperty("mxe.db.user", "dbmaximo");
            String password = prop.getProperty("mxe.db.password", "max894512");
			Class.forName(driver).newInstance();
			
			conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
			
			stmt = conexao.createStatement();
			
			//-------------------------------------------------------------------CONEXAO
			
			//-------------------------------------------------------------------NUMERO DA PARCELA DE ENTREGA
			MboRemote prlineentrega;
			
			for (int i = 0; ((prlineentrega = getMbo().getMboSet("PRLINEENTREGA").getMbo(i)) != null); i++) {
				MboRemote entrega2;
				ArrayList<Integer> parcelas = new ArrayList<Integer>();
							
				rs = stmt.executeQuery("select MSTBPREVISAOENTREGAID from MSTBPREVISAOENTREGA where prnum = " + 
											prlineentrega.getString("PRNUM") + " and prlineid = " + 
												prlineentrega.getInt("PRLINEID") + " order by to_date('01/'||MSALDTAENTREGA, 'DD/MM/YYYY'), MSTBPREVISAOENTREGAID");
				
				while (rs.next()) {
					System.out.println("########## rs.getInt(MSTBPREVISAOENTREGAID)" + rs.getInt("MSTBPREVISAOENTREGAID"));
					for (int j = 0; ((entrega2 = prlineentrega.getMboSet("MSTBPREVISAOENTREGA").getMbo(j)) != null); j++) {
						if ((rs.getInt("MSTBPREVISAOENTREGAID") == entrega2.getInt("MSTBPREVISAOENTREGAID")) && !entrega2.toBeDeleted()) {
							parcelas.add(rs.getInt("MSTBPREVISAOENTREGAID"));
							System.out.println("########## Adicionado o ID: " + rs.getInt("MSTBPREVISAOENTREGAID"));
						}
					}
				}
				
				MboRemote entrega3;
				
				for (int j = 0; ((entrega3 = prlineentrega.getMboSet("MSTBPREVISAOENTREGA").getMbo(j)) != null); j++) {
					
					System.out.println("########## ID da linha: " + entrega3.getInt("MSTBPREVISAOENTREGAID"));
					System.out.println("########## parcelas.indexOf()" + parcelas.indexOf(entrega3.getInt("MSTBPREVISAOENTREGAID")));
					
					entrega3.setValue("MSNUCODPARCELA", parcelas.indexOf(entrega3.getInt("MSTBPREVISAOENTREGAID"))+1);
				}
    		}
			
			//-------------------------------------------------------------------NUMERO DA PARCELA DE ENTREGA
			
			//-------------------------------------------------------------------NUMERO DA PARCELA DE DISTRIBUICAO
			MboRemote prlinedistribuicao;
			
			for (int i = 0; ((prlinedistribuicao = getMbo().getMboSet("PRLINEDISTRIBUICAO").getMbo(i)) != null); i++) {
				MboRemote distribuicao2;
				ArrayList<Integer> parcelasDist = new ArrayList<Integer>();
							
				rs2 = stmt.executeQuery("select MSTBPREVISAODISTRIBUICAOID from MSTBPREVISAODISTRIBUICAO where prnum = " + 
											prlinedistribuicao.getString("PRNUM") + " and prlineid = " + 
												prlinedistribuicao.getInt("PRLINEID") + " order by to_date('01/'||MSALDTADISTRIBUICAO, 'DD/MM/YYYY'), MSTBPREVISAODISTRIBUICAOID");
				
				while (rs2.next()) {
					System.out.println("########## rs2.getInt(MSTBPREVISAODISTRIBUICAOID)" + rs2.getInt("MSTBPREVISAODISTRIBUICAOID"));
					for (int j = 0; ((distribuicao2 = prlinedistribuicao.getMboSet("MSTBPREVISAODISTRIBUICAO").getMbo(j)) != null); j++) {
						if ((rs2.getInt("MSTBPREVISAODISTRIBUICAOID") == distribuicao2.getInt("MSTBPREVISAODISTRIBUICAOID")) && !distribuicao2.toBeDeleted()) {
							parcelasDist.add(rs2.getInt("MSTBPREVISAODISTRIBUICAOID"));
							System.out.println("########## Adicionado o ID: " + rs2.getInt("MSTBPREVISAODISTRIBUICAOID"));
						}
					}
				}
				
				MboRemote distribuicao3;
				
				for (int j = 0; ((distribuicao3 = prlinedistribuicao.getMboSet("MSTBPREVISAODISTRIBUICAO").getMbo(j)) != null); j++) {
					
					System.out.println("########## ID da linha: " + distribuicao3.getInt("MSTBPREVISAODISTRIBUICAOID"));
					System.out.println("########## parcelasDist.indexOf()" + parcelasDist.indexOf(distribuicao3.getInt("MSTBPREVISAODISTRIBUICAOID")));
					
					distribuicao3.setValue("MSNUCODPARCELA", parcelasDist.indexOf(distribuicao3.getInt("MSTBPREVISAODISTRIBUICAOID"))+1);
				}
			}
			//-------------------------------------------------------------------NUMERO DA PARCELA DE DISTRIBUICAO
			
			super.save();
			
    	} catch (RemoteException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    try { conexao.close(); } catch (Exception e) { /**/ }
		    try { stmt.close(); } catch (Exception e) { /**/ }
		    try { rs.close(); } catch (Exception e) { /**/ }
		}
    	
    	super.save();
    }
    
}
