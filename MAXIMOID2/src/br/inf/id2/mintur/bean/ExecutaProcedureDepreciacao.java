/**
 * @author Dyogo
 */
package br.inf.id2.mintur.bean;

import java.sql.CallableStatement;
import java.util.Properties;

import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ExecutaProcedureDepreciacao extends DataBean {

	private static final String PROCEDURE = "DEPRECIACAO_1";
	
    public ExecutaProcedureDepreciacao() {
    }

    public void executaProcedure() {
    	
    	Properties prop;
        
        try {
            prop = MXServer.getMXServer().getConfig();
            System.out.println(" ################### ExecutaProcedureDepreciacao ");
            int parametro = parent.getMbo().getInt("ASSETNUM");
            System.out.println("############## Parâmetro = " + parametro);
            String driver = prop.getProperty("mxe.db.driver", "com.inet.tds.TdsDriver");
            String url = prop.getProperty("mxe.db.url", "jdbc:inetdae7:192.168.1.196:1433?database=MT_IMP&language=us_english&nowarnings=true&mars=false&charset=8859_1");
            String username = prop.getProperty("mxe.db.user", "mtimp");
            String password = prop.getProperty("mxe.db.password", "id2maximo");
            System.out.println("############## Propriedades=" + driver + "|" + url + "|" + username + "|" + password);
            Class.forName(driver).newInstance();
            java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbo"));
            System.out.println("############## Conexão = " + conexao);
            CallableStatement cs = conexao.prepareCall("{call " + PROCEDURE + "(?)}");
            cs.setInt(1, parametro);
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
