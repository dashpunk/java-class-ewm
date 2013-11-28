package br.inf.id2.valec.field;

import java.rmi.RemoteException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class GotbmedcontitemGonuqutcont extends MboValueAdapter {

    public GotbmedcontitemGonuqutcont(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        Properties prop;
        prop = MXServer.getMXServer().getConfig();
        byte[] bytes = null;
        String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
        String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@192.168.1.215:1521:dftop1");
        String username = prop.getProperty("mxe.db.user", "dbmaximo");
        String password = prop.getProperty("mxe.db.password", "id2maximo");
        try {
            Class.forName(driver).newInstance();
            java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
            Statement stmt = conexao.createStatement();
            PreparedStatement ps = conexao.prepareStatement("SELECT GO_QUANT_ATUAL_ITEM(?,?,?,?) VALOR FROM DUAL");

            ps.setLong(1, getMboValue("FKGOCONTRACTNUM").getLong());
            ps.setLong(2, getMboValue("FKGOITEMNUM").getLong());
            ps.setLong(3, getMboValue("FKGONUNUMMEDICAO").getLong());
            ps.setLong(4, getMboValue("FKGOASSETNUM").getLong());
            

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Double valor = rs.getDouble("VALOR");
                getMboValue("GONUQUTCONT").setValue(valor, MboConstants.NOACCESSCHECK);
            }


        } catch (Exception e) {
            e.getStackTrace();
        }
        super.initValue();

    }
}
