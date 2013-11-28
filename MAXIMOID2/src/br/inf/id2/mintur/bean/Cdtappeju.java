package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author ricardo s gomes
 */
public class Cdtappeju extends psdi.webclient.system.beans.AppBean {

    public Cdtappeju() {
    }

    public int BOHVIZ() throws MXException, RemoteException {
        if (getMbo().isNull("MESBOH") || getMbo().isNull("ANOBOH")) {
            throw new MXApplicationException("boh", "mesOuAnoVazio");
        }
        try {
            Properties prop;
            prop = MXServer.getMXServer().getConfig();
            String driver = prop.getProperty("mxe.db.driver", "com.inet.tds.TdsDriver");
            String url = prop.getProperty("mxe.db.url", "jdbc:inetdae7:192.168.1.6:1433?database=MAXDB72&language=us_english&nowarnings=true&mars=false&charset=8859_1");
            String username = prop.getProperty("mxe.db.user", "maximo");
            String password = prop.getProperty("mxe.db.password", "id2maximo");
            Class.forName(driver).newInstance();
            java.sql.Connection conexao = DriverManager.getConnection(url, username, password);
            Statement stmt = conexao.createStatement();
            PreparedStatement ps = conexao.prepareStatement("EXEC FNRH_GERA_BOH_10 ?,?,?");
//            java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbo"));
//            CallableStatement ps = conexao.prepareCall("{EXEC " + PROCEDURE + " ?,?,?}");
//            PreparedStatement ps = conexao.prepareStatement("EXEC " + PROCEDURE + " ?,?,?");

            ps.setString(1, getMbo().getString("CO_CNPJ"));
            ps.setInt(2, getMbo().getInt("MESBOH"));
            ps.setInt(3, getMbo().getInt("ANOBOH"));
            ps.execute();
            ps.close();
//            conexao.commit();
            refreshTable();
            reloadTable();
        } catch (Exception e) {
            System.out.println("---eprocedure ex " + e.getMessage());
            e.printStackTrace();
            return EVENT_STOP_ALL;
        }
        return EVENT_HANDLED;
    }
}
