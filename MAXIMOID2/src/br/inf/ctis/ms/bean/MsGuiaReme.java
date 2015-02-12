package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class MsGuiaReme extends AppBean {

	/**
	 * @author Marcelo
	 */
	public MsGuiaReme() {
		System.out.println("########## Entrou em MsGuiaReme()");
	}
	
	int ultimoNumero=0;
	
	@Override
	public void save() throws MXException {
		System.out.println("########## Entrou em MsGuiaReme.save()");
		Properties prop;
		Connection conexao = null;
		Statement st2 = null;
		ResultSet idMax = null;
		
		try {
			if(getMbo().isNew()) {
				System.out.println("########## Entrou em isNew()");
				prop = MXServer.getMXServer().getConfig();
				
				String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
	            String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@srvoradf0.saude.gov:1521/DFPO1.SAUDE.GOV");
	            String username = prop.getProperty("mxe.db.user", "dbmaximo");
	            String password = prop.getProperty("mxe.db.password", "max894512");
				Class.forName(driver).newInstance();
				
				conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
				
				st2 = conexao.createStatement();
				idMax = st2.executeQuery("select TO_NUMBER(nvl(max(TO_NUMBER(ltrim(substr(MSCODREMESSA,0,5)))),0)) from MSREMESSA where substr(MSCODREMESSA,7,4)=EXTRACT(YEAR FROM SYSDATE)");
				
				
				if (idMax.next()) {
					System.out.println("########## Entrou no ResultSet!");
					System.out.println("########## idMax = " + idMax.getInt(1));
					ultimoNumero = idMax.getInt(1);
				}
				
				System.out.println("########## ultimoNumero = " + ultimoNumero);
				
				if(getMbo().getString("MSCODREMESSA") == null || getMbo().getString("MSCODREMESSA") == ""){
					ultimoNumero += 1;
					System.out.println("########## ultimoNumero += 1 = " + ultimoNumero);
					
					String codRemessa = String.format("%05d", ultimoNumero);
					System.out.println("########## String.format = " + codRemessa);
					
					codRemessa = codRemessa+"/"+Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
					
					System.out.println("########## MSCODREMESSA = " + codRemessa);
					getMbo().setValue("MSCODREMESSA", codRemessa);
				}
				
				// Setando Status Medicamentos
				MboRemote mbo1;
				
				for (int j = 0; ((mbo1 = getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(j)) !=null); j++) {
					System.out.println("############ Entrou no for de medicamentos");
					mbo1.setValue("STATUS", "AG.REMESSA", MboConstants.NOACCESSCHECK);
					System.out.println("########### STATUS = " + mbo1.getString("STATUS"));
				}
				/// Fim	
				
				super.save();
			} 
			//mboDestino.getThisMboSet().save();
			super.save();
			
			/*WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
			Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));*/
		
		} catch (RemoteException ex) {
            Logger.getLogger(MsGuiaReme.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
		    try { conexao.close(); } catch (Exception e) { /**/ }
		    try { st2.close(); } catch (Exception e) { /**/ }
		    try { idMax.close(); } catch (Exception e) { /**/ }
		}
	}
}