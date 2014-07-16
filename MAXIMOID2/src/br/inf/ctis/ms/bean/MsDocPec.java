package br.inf.ctis.ms.bean;

import java.beans.Statement;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboConstants;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * @author andrel.almeida
 */

public class MsDocPec extends AppBean {
	
	public MsDocPec() {
		System.out.println(">>>>>>>>> Dentro da classe: br.inf.ctis.ms.bean.MsDocPec");
	}
	
	@Override
	public void save() throws MXException {
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
            Statement stmt = (Statement) conexao.createStatement();
            System.out.println(">>>>>>>>> Antes de montar o INSERT");
            PreparedStatement ps = conexao.prepareStatement("INSERT INTO MSTBCONTE(MSTBCONTEID,DESCRIPTION,HASLD,MSTBCLAPARID,ROWSTAMP,MSBLOQUEADO,MSPOSICAO) VALUES (mstbconteidseq.NEXTVAL,?,0,null,'123456',0,?) ");
            //ps.setString(1, getMbo().getString("DESCRIPTION"));
            //ps.setString(2, getMbo().getString("MSPOSICAO"));
            System.out.println(">>>>>>>>> Antes dos parametros para o INSERT");
            ps.setString(1,getMbo().getMboSet("MSTBMOD").getMbo(0).getMboSet("MSTBCLACAP").getMbo(0).getString("DESCRIPTION"));
            ps.setString(2,getMbo().getMboSet("MSTBMOD").getMbo(0).getMboSet("MSTBCLACAP").getMbo(0).getString("MSPOSICAO"));
            System.out.println(">>>>>>>>> Depois dos parametros para o INSERT");           
            
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
		super.save();
	}
}
	
	
	
	

