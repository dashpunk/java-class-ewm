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
import psdi.webclient.system.beans.DataBean;
import br.inf.id2.common.util.Executa;

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
    public synchronized void fireDataChangedEvent(DataBean speaker) {
        super.fireDataChangedEvent(speaker);
    }

    @Override
	public void save() throws MXException {
    	   	 	
    	Properties prop;
		Connection conexao = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
    	
    	try {
    		
    		getMbo().setValue("SITEID", getMbo().getString("MSALCODSITEID"), MboConstants.NOACCESSCHECK);  
    		    		
    		//-------------------------------------------------------------------PREVISAO DE ENTREGA
    		
			MboRemote itemEntrega;
    		    		
    		for (int i = 0; ((itemEntrega = getMbo().getMboSet("PRLINEENTREGA").getMbo(i)) != null); i++) {
				
    			if (itemEntrega.getString("ID2DISTDIRETA").equalsIgnoreCase("AMBOS")) {
					if (itemEntrega.getMboSet("MSTBPREVISAOENTREGA").count() < 1) {
						throw new MXApplicationException("ambos", "NecessarioCadastroDeUmaEntrega");
					}
				} else if (itemEntrega.getString("ID2DISTDIRETA").equalsIgnoreCase("NAO")) {
	    					
					Double valor = Executa.somaValor("MSNUNUMQUANTIDADE", itemEntrega.getMboSet("MSTBPREVISAOENTREGA"));
			        System.out.println("########## valorEntrega = " + valor);
			        
			        if (valor != itemEntrega.getDouble("ORDERQTY")) {
			        	throw new MXApplicationException("entrega", "FaltaEntregas");
			        }

				} 
    		}
			
			//-------------------------------------------------------------------PREVISAO DE ENTREGA
			
			super.save();
			
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
							
				rs = stmt.executeQuery("select MSTBPREVISAOENTREGAID from MSTBPREVISAOENTREGA where prnum = " + prlineentrega.getString("PRNUM") + "prlineid = " + prlineentrega.getInt("PRLINEID") + " order by to_date('01/'||MSALDTAENTREGA, 'DD/MM/YYYY'), MSTBPREVISAOENTREGAID");
				
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
							
				rs2 = stmt.executeQuery("select MSTBPREVISAODISTRIBUICAOID from MSTBPREVISAODISTRIBUICAO where prnum = " + prlinedistribuicao.getString("PRNUM") + "prlineid = " + prlinedistribuicao.getInt("PRLINEID") + " order by to_date('01/'||MSALDTADISTRIBUICAO, 'DD/MM/YYYY'), MSTBPREVISAODISTRIBUICAOID");
				
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
