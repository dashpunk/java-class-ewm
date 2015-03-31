package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import psdi.mbo.MboRemote;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.pr.PRAppBean;
import psdi.webclient.system.beans.DataBean;
import br.inf.id2.common.util.Uteis;

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
    	
    	try {
    		
    		//PREVISAO DE ENTREGA
    		
    		MboRemote entrega;
    		MboRemote itensEntrega;
    	    	
			for (int i = 0; ((entrega = getMbo().getMboSet("MSTBPREVISAOENTREGA").getMbo(i)) != null); i++) {
				
				double totalVolume = 0d;
				double totalPeso = 0d;
				double totalQuantidade = 0d;
				
				for (int j = 0; ((itensEntrega = entrega.getMboSet("MSTBITENSENTREGA").getMbo(j)) != null); j++) {
					
					System.out.println("######### CATMAT: " + itensEntrega.getString("ID2ITEMNUM"));
					
					System.out.println("######### Volume: " + ((itensEntrega.getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMAQUISICAOVOLUME") / itensEntrega.getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) * itensEntrega.getDouble("MSNUNUMQUANTIDADE")));					
					totalVolume += ((itensEntrega.getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMAQUISICAOVOLUME") / itensEntrega.getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) * itensEntrega.getDouble("MSNUNUMQUANTIDADE"));
					
					System.out.println("######### Peso: " + ((itensEntrega.getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMAQUISICAOPESO") / itensEntrega.getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) * itensEntrega.getDouble("MSNUNUMQUANTIDADE")));
					totalPeso += ((itensEntrega.getMboSet("PRLINE").getMbo(0).getDouble("MSNUNUMAQUISICAOPESO") / itensEntrega.getMboSet("PRLINE").getMbo(0).getDouble("ORDERQTY")) * itensEntrega.getDouble("MSNUNUMQUANTIDADE"));
					
					System.out.println("######### Quantidade: " + itensEntrega.getDouble("MSNUNUMQUANTIDADE"));
					totalQuantidade += itensEntrega.getDouble("MSNUNUMQUANTIDADE");
				}
				
				entrega.setValue("MSNUNUMVOLUME", totalVolume);
				entrega.setValue("MSNUNUMPESO", totalPeso);
				entrega.setValue("MSNUNUMQUANTIDADE", totalQuantidade);
								
			}
			
			//PREVISAO DE ENTREGA
			
			
			
			super.save();
			//NUMERO DA PARCELA DE ENTREGA
			
			MboRemote entrega2;
			ArrayList<Integer> parcelas = new ArrayList<Integer>();
			
			prop = MXServer.getMXServer().getConfig();
			
			String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
            String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@srvoradf0.saude.gov:1521/DFPO1.SAUDE.GOV");
            String username = prop.getProperty("mxe.db.user", "dbmaximo");
            String password = prop.getProperty("mxe.db.password", "max894512");
			Class.forName(driver).newInstance();
			
			conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
			
			stmt = conexao.createStatement();
			rs = stmt.executeQuery("select MSTBPREVISAOENTREGAID from MSTBPREVISAOENTREGA where prnum = " + getMbo().getString("PRNUM") + " order by to_date('01/'||MSALDTAENTREGA, 'DD/MM/YYYY'), MSTBPREVISAOENTREGAID");
			
			while (rs.next()) {
				System.out.println("########## rs.getInt(MSTBPREVISAOENTREGAID)" + rs.getInt("MSTBPREVISAOENTREGAID"));
				for (int i = 0; ((entrega2 = getMbo().getMboSet("MSTBPREVISAOENTREGA").getMbo(i)) != null); i++) {
					if ((rs.getInt("MSTBPREVISAOENTREGAID") == entrega2.getInt("MSTBPREVISAOENTREGAID")) && !entrega2.toBeDeleted()) {
						parcelas.add(rs.getInt("MSTBPREVISAOENTREGAID"));
						System.out.println("########## Adicionado o ID: " + rs.getInt("MSTBPREVISAOENTREGAID"));
					}
				}
			}
			
			MboRemote entrega3;
			
			for (int j = 0; ((entrega3 = getMbo().getMboSet("MSTBPREVISAOENTREGA").getMbo(j)) != null); j++) {
				
				System.out.println("########## ID da linha: " + entrega3.getInt("MSTBPREVISAOENTREGAID"));
				System.out.println("########## parcelas.indexOf()" + parcelas.indexOf(entrega3.getInt("MSTBPREVISAOENTREGAID")));
				
				entrega3.setValue("MSNUCODPARCELA", parcelas.indexOf(entrega3.getInt("MSTBPREVISAOENTREGAID")));
			}
			
			//NUMERO DA PARCELA DE ENTREGA
			
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
    
    public int SAVE() throws MXException, RemoteException {

        Uteis.espera("*************** SAVE()");

        if (getMbo().getString("ID2CODCOO") == null || getMbo().getString("ID2CODCOO").equals("")) {
            throw new MXApplicationException("demanda", "ID2CDCOOEstaNuloOuVazio");
        }
        if (getMbo().getString("ID2SEC") == null || getMbo().getString("ID2SEC").equals("")) {
            throw new MXApplicationException("demanda", "ID2SECEstaNuloOuVazio");
        }
                
        return super.SAVE();

    }

}
