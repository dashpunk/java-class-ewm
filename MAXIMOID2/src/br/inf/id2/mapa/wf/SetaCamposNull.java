package br.inf.id2.mapa.wf;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class SetaCamposNull implements ActionCustomClass {

	public SetaCamposNull() {
		super();
		System.out.println("*** SetaCamposNull ***");
	}

	/**
	 * 
	 * @param mbo
	 * @param params
	 *            Primeiro parametro nome do aplicativo
	 * @throws MXException
	 * @throws java.rmi.RemoteException
	 */
	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, java.rmi.RemoteException {

		updateCamposNull(mbo);
	}

	private void updateCamposNull(MboRemote mbo) throws RemoteException,
			MXException {

		 System.out.println("*** updateCamposNull");
		try {
			Properties prop;
			prop = MXServer.getMXServer().getConfig();
			String driver = prop.getProperty("mxe.db.driver","oracle.jdbc.OracleDriver");
			String url = prop.getProperty("mxe.db.url");
			String username = prop.getProperty("mxe.db.user", "sifimp");
			String password = prop.getProperty("mxe.db.password", "id2maximo");

			Class.forName(driver).newInstance();
			java.sql.Connection conexao = DBConnect.getConnection(url,username, password, prop.getProperty("mxe.db.schemaowner","dbmaximo"));
			Statement stmt = conexao.createStatement();
			PreparedStatement ps = conexao.prepareStatement(montaSql());
			 System.out.println("*** LOCATION "+mbo.getString("LOCATION"));
			ps.setString(1, mbo.getString("LOCATION"));

			int r = ps.executeUpdate();

			conexao.commit();
			 System.out.println("*** commit");

			conexao.close();
		} catch (Exception e) {
			 System.out.println("*** e = " + e.getMessage());
		}
	}

	private String montaSql() {

    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("UPDATE LOCATIONS SET ")
    	
    	.append("ID2STAPARMEL = null, ")
    	.append("ID2STAPAROVO = null, ")
    	.append("ID2STAPARPES = null, ")
    	.append("ID2STAPARLEI = null, ")
    	.append("ID2STAPARTECDIPPROJ = null, ")
    	.append("ID2STAAPRPES = null, ")
    	.append("ID2STAAPROVO = null, ")
    	.append("ID2STAAPRDIPPROJ = null, ")
    	.append("ID2STAAPRLEI = null, ")
    	.append("ID2STAAPRMEL = null, ")
    	.append("ID2JUSRESLEG = null, ")
    	.append("ID2JUSPARTECSIPPROJ = null, ")
    	.append("ID2JUSAPRPARTECSIPPROJ = null, ")
    	.append("ID2JUSPARTECDIPPROJ = null, ")
    	.append("ID2OBSPARLEI = null, ")
    	.append("ID2OBSPARMEL = null, ")
    	.append("ID2OBSPARPES = null, ")
    	.append("ID2OBSPAROVO = null, ")
    	.append("ID2JUSAPRDIPPROJ = null, ")
    	.append("ID2OBSAPRLEI = null, ")
    	.append("ID2OBSAPRMEL = null, ")
    	.append("ID2OBSAPRPES = null, ")
    	.append("ID2OBSAPROVO = null, ")
    	.append("ID2RESLEG = null, ")
    	.append("ID2RESPARSIP = null, ")
    	.append("ID2RESAPRSIP = null, ")
    	.append("ID2RESPARTECDIPRPOJ = null, ")
    	.append("ID2RESPARLEI = null, ")
    	.append("ID2RESPARMEL = null, ")
    	.append("ID2RESPARPES = null, ")
    	.append("ID2RESPAROVO = null, ")
    	.append("ID2RESAPRDIPPROJ = null, ")
    	.append("ID2RESAPRLEI = null, ")
    	.append("ID2RESAPRMEL = null, ")
    	.append("ID2RESAPRPES = null, ")
    	.append("ID2RESAPROVO = null, ")
    	
    	.append("id2ret = 0, ")
    	.append("id2ckbox05car = 0, ")
    	.append("id2ckbox06car = 0, ")
    	.append("id2ckbox05lei = 0, ")
    	.append("id2ckbox06lei = 0, ")
    	.append("id2ckbox05mel = 0, ")
    	.append("id2ckbox06mel = 0, ")
    	.append("id2ckbox05ovo = 0, ")
    	.append("id2ckbox06ovo = 0, ")
    	.append("id2ckbox05pes = 0, ")
    	.append("id2ckbox06pes = 0, ")
    	.append("id2retsip = 0, ")
    	.append("id2revapr = 0 ")
    	
    	.append("WHERE LOCATION = ?");
    	
    	return sql.toString();
	}

}
