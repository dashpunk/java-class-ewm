package br.inf.id2.mapa.cron;

import psdi.security.UserInfo;
import psdi.server.SimpleCronTask;
import java.rmi.RemoteException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 */
public class ExecutaProcedureWSCron extends SimpleCronTask {

	private boolean ready;
	UserInfo userInfo;

	public ExecutaProcedureWSCron() {

		System.out.println("-------------- ExecutaProcedureWSCron");
	}

	public void start() {

		System.out.println("-------------- start");
	}

	public void cronAction() {
		System.out.println("-------------- do action INIT");
		Properties prop;
		try {
			prop = MXServer.getMXServer().getConfig();

			String driver = prop.getProperty("mxe.db.driver",
					"com.inet.tds.TdsDriver");
			String url = prop
					.getProperty(
							"mxe.db.url",
							"jdbc:inetdae7:192.168.1.6:1433?database=MAXDB72&language=us_english&nowarnings=true&mars=false&charset=8859_1");
			String username = prop.getProperty("mxe.db.user", "maximo");
			String password = prop.getProperty("mxe.db.password", "id2maximo");
			Class.forName(driver).newInstance();
			java.sql.Connection conexao = DriverManager.getConnection(url,
					username, password);
			Statement stmt = conexao.createStatement();
			PreparedStatement ps = conexao
					.prepareStatement("call WS_EXECUTA_PROCEDURES()");
			// java.sql.Connection conexao = DBConnect.getConnection(url,
			// username, password, prop.getProperty("mxe.db.schemaowner",
			// "dbo"));
			// CallableStatement ps = conexao.prepareCall("{EXEC " + PROCEDURE +
			// " ?,?,?}");
			// PreparedStatement ps = conexao.prepareStatement("EXEC " +
			// PROCEDURE + " ?,?,?");

			// ps.setString(1, getMbo().getString("CO_CNPJ"));
			// ps.setInt(2, getMbo().getInt("MESBOH"));
			// ps.setInt(3, getMbo().getInt("ANOBOH"));
			ps.execute();
			ps.close();
		} catch (RemoteException e) {
			System.out.println("...e1 "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("...e2 "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("...e3 "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("...e4 "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("...e5 "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("-------------- do action END");
	}
}
