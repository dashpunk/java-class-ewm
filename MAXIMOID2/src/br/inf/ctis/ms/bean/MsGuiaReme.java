package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class MsGuiaReme extends AppBean {

	/**
	 * @author Marcelo
	 */
	public MsGuiaReme() {
	}
	
	int ultimoNumero=0;
	
	@Override
	public void save() throws MXException {
		Properties prop;
		Connection conexao = null;
		Statement st2 = null;
		ResultSet idMax = null;
		
		try {
			if(getMbo().isNew()){
				prop = MXServer.getMXServer().getConfig();
				
				String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
	            String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@srvoradf0.saude.gov:1521/DFPO1.SAUDE.GOV");
	            String username = prop.getProperty("mxe.db.user", "dbmaximo");
	            String password = prop.getProperty("mxe.db.password", "max894512");
				Class.forName(driver).newInstance();
				
				conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
				
				st2 = conexao.createStatement();
				idMax = st2.executeQuery("select nvl(max(TO_NUMBER(ltrim(MSCODREMESSA))),0) from MSREMESSA");
				
				
				if (idMax.next()) {
					ultimoNumero = idMax.getInt(1);
				}
				
				System.out.println("########## ultimoNumero = " + ultimoNumero);
				
				ultimoNumero += 1;
				System.out.println("########## ultimoNumero += 1 = " + ultimoNumero);
				
				String codRemessa = String.format("%05d", ultimoNumero);
				System.out.println("########## String.format = " + codRemessa);
				
				System.out.println("########## MSCODREMESSA = " + codRemessa);
				getMbo().setValue("MSCODREMESSA", codRemessa);
				
				super.save();
			} 
			//mboDestino.getThisMboSet().save();
			super.save();
			
			/*WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
			Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));*/
		
		} catch (RemoteException ex) {
            Logger.getLogger(MsTbArp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { conexao.close(); } catch (Exception e) { /**/ }
		    try { st2.close(); } catch (Exception e) { /**/ }
		    try { idMax.close(); } catch (Exception e) { /**/ }
		}
	}
}