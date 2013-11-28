/**
 * @author Dyogo
 */
package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.util.Properties;

import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ExecutaProcedureAtividadeCargo extends AppBean {

	private static final String PROCEDURE = "ATIVIDADECARGO";
	
    public ExecutaProcedureAtividadeCargo() {
    }

    public void executaProcedure() throws MXException, RemoteException {
    	
    	SAVE();
    	
    	Properties prop;
        
        try {
            prop = MXServer.getMXServer().getConfig();
            System.out.println(" ################### ExecutaProcedureAtividadeCargo ");
            String param1 = getMbo().getString("MAFUNCAR");
            String param2 = getMbo().getString("MADEP");
            int param3 = getMbo().getInt("MATBGESATIID");
            System.out.println("############## Parâmetro = " + param1);
            System.out.println("############## Parâmetro = " + param2);
            System.out.println("############## Parâmetro = " + param3);
            String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
            String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@10.102.1.193:1521:sifimp");
            String username = prop.getProperty("mxe.db.user", "sifimp");
            String password = prop.getProperty("mxe.db.password", "id2maximo");
            System.out.println("############## Propriedades=" + driver + "|" + url + "|" + username + "|" + password);
            Class.forName(driver).newInstance();
            java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbo"));
            System.out.println("############## Conexão = " + conexao);
            CallableStatement cs = conexao.prepareCall("{call " + PROCEDURE + "(?,?,?)}");
            cs.setString(1, param1);
            cs.setString(2, param2);
            cs.setInt(3, param3);
            cs.execute();
            cs.close();
            System.out.println("############## Procedure executada! Fechando tela!");
            WebClientEvent event = sessionContext.getCurrentEvent();
            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
            
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
