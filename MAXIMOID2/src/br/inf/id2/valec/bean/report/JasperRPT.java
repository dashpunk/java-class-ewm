/*
 * Esta classe é o servlet que efetivamente cria o relatório, passando os parâmetros do Maximo para o Jasper.
 * Para que essa classe funcione, deve-se criar as variáveis no Maximo.properties que apontam o logo.gif e o nome do relatório em questão.
 */
/**
 *
 * @author Jessé Rovira
 */
package br.inf.id2.valec.bean.report;



import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperRunManager;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.webclient.servlet.WebClientServlet;

public class JasperRPT extends WebClientServlet {

    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, java.io.IOException {
        Properties prop;
        try {
            int aonde = 0;
            System.out.println(" >>>------> JasperRPT " + ++aonde);
            prop = MXServer.getMXServer().getConfig();
            File reportFile = new File(prop.getProperty("mxe.jasper.reports.local") + req.getParameter("relatorio"));
            byte[] bytes = null;
            HashMap map = new HashMap();
            System.out.println(" ------> JasperRPT " + ++aonde);
            for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {

                String attrbName = (String) e.nextElement();
                map.put(attrbName, req.getParameter(attrbName));
                System.out.println(req.getParameter(attrbName));
            }
            System.out.println(" ------> JasperRPT " + ++aonde);
            System.out.println("---- antes ");
            map.put("IMAGE_PATH", prop.getProperty("mxe.jasper.reports.local") + "logo.gif");
            System.out.println("---- depois ");
            System.out.println(" ------> JasperRPT " + ++aonde);
            try {
                /*
                java.io.InputStream in = getClass().getResourceAsStream("/maximo.properties");
                Properties properties = MXProperties.loadProperties(in, true);
                String driver = properties.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
                String url = properties.getProperty("mxe.db.url", "jdbc:oracle:thin:");
                String username = properties.getProperty("mxe.db.user", "maximo");
                String password = properties.getProperty("mxe.db.password", "maximo");
                 */
                System.out.println(" ------> JasperRPT " + ++aonde);
                String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
                String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@192.168.1.215:1521:dftop1");
                String username = prop.getProperty("mxe.db.user", "dbmaximo");
                String password = prop.getProperty("mxe.db.password", "id2maximo");
                Class.forName(driver).newInstance();
                java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
                System.out.println(" ------> JasperRPT " + ++aonde);
                /*
                Statement stmt = con.createStatement();
                HttpSession session = req.getSession();
                SessionContext sc = SessionContext.getSessionContext(req);
                java.sql.Connection Conexao = MXServer.getMXServer().getDBManager().getConnection(sc.getMXSession().getMboSet("JASPERREPORT").getUserInfo().getConnectionKey());
                //JasperRunManager.runReportToHtmlFile(reportFile.getPath(), Parameters, Conexao);
                 */
                bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), map, conexao);
                System.out.println(" ------> JasperRPT " + ++aonde);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            System.out.println(" ------> JasperRPT " + ++aonde);
            if (bytes != null && bytes.length > 0) {
                System.out.println(" ------> JasperRPT if " + ++aonde);
                resp.setContentType("application/pdf");
                resp.setContentLength(bytes.length);
                ServletOutputStream ouputStream = resp.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();
                System.out.println(" ------> JasperRPT if " + ++aonde);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //out.close();
        }
    }
}
