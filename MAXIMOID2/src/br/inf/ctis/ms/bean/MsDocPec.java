package br.inf.ctis.ms.bean;

import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author andrel.almeida
 */

public class MsDocPec extends DataBean {
	
	public MsDocPec (){
		
	}
	
	protected void SAVE() throws MXException, RemoteException {
    	super.save();
		
    	System.out.println(">>>>>>>>> Dentro do metodo Save, classe: br.inf.ctis.ms.bean.MsDocPec");
    	try {
            Properties prop;
            prop = MXServer.getMXServer().getConfig();
            String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
            String url = prop.getProperty("mxe.db.url");
            String username = prop.getProperty("mxe.db.user", "dbmaximo");
            String password = prop.getProperty("mxe.db.password", "max894512");

            Class.forName(driver).newInstance();
            java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
            Statement stmt = conexao.createStatement();
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO MSTBCONTE(DESCRIPTION,MSPOSICAO) VALUES () ");
            ps.setString(1, getMbo().getString("DESCRIPTION"));
            ps.setString(2, getMbo().getString("MSPOSICAO"));


            System.out.println(">>>>>>>>> Insert MSTBCONTE execute b");
            int r = ps.executeUpdate();
            System.out.println(">>>>>>>>> Insert MSTBCONTE execute a " + r);
            conexao.commit();
            System.out.println(">>>>>>>>> commit");
            
            super.save();
            

            conexao.close();
        } catch (Exception e) {
            System.out.println(">>>>>>>>> e = " + e.getMessage());
        }
	}

}
