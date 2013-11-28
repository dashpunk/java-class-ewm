/*
 * Esta classe é o servlet que efetivamente cria o relatório, passando os parâmetros do Maximo para o Jasper.
 * Para que essa classe funcione, deve-se criar as variáveis no Maximo.properties que apontam o logo.gif e o nome do relatório em questão.
 */
/**
 *
 * @author Jessé Rovira
 */
package br.inf.id2.ms.bean.report;

import psdi.webclient.beans.report.*;
import java.io.*;
import java.sql.Connection;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.*;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.view.JasperViewer;

import psdi.webclient.system.controller.SessionContext;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.servlet.*;
import psdi.server.*;
import psdi.util.DBConnect;

public class JasperRPT extends WebClientServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, java.io.IOException {
        Properties prop;
        try {
        	System.out.println("*** JasperRPT - service ***");
        	
            prop = MXServer.getMXServer().getConfig();
            File reportFile = new File(prop.getProperty("mxe.jasper.reports.local") + req.getParameter("relatorio"));
            
            HashMap map = new HashMap();
            Locale locale = new Locale("pt", "BR");
            map.put(JRParameter.REPORT_LOCALE, locale);
            for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
                String attrbName = (String) e.nextElement();
                map.put(attrbName, req.getParameter(attrbName));
            }
            
            for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
                String attrbName = (String) e.nextElement();
                map.put(attrbName, req.getParameter(attrbName));
            }
            
            map.put("IMAGE_PATH", prop.getProperty("mxe.jasper.reports.local") + "logo.gif");

            String reporttype = (String)map.get("REPORTTYPE");

            String driver = prop.getProperty("mxe.db.driver", "net.sourceforge.jtds.jdbc.Driver");
            String url = prop.getProperty("mxe.db.url", "jdbc:jtds:sqlserver://192.168.1.211:1433/MAXDB76");
            String username = prop.getProperty("mxe.db.user", "maximome");
            String password = prop.getProperty("mxe.db.password", "id2maximo");
			Class.forName(driver).newInstance();

            Connection conexao;
			conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
            
			if(reporttype == null || reporttype.equals("PDF")){
				geraPDF(reportFile, map, conexao, resp);
			} else if(reporttype.equals("XLS")){
				geraXLS(reportFile, map, conexao, resp);
			}/*else if(reporttype.equals("HTML")){
				geraHTML(reportFile, map, conexao, resp, req);
			}*/

			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
    }
    
    public void geraPDF(File reportFile, HashMap map, Connection conexao, HttpServletResponse resp) throws JRException, IOException{
    	
    	byte[] bytes = null;
    	bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), map, conexao);
    	
        if (bytes != null && bytes.length > 0) {
            
        	resp.setContentType("application/pdf");
            resp.setContentLength(bytes.length);
            
            ServletOutputStream ouputStream = resp.getOutputStream();
            ouputStream.write(bytes, 0, bytes.length);
            ouputStream.flush();
            ouputStream.close();
        }
    }
    
    public void geraXLS(File reportFile, HashMap map, Connection conexao, HttpServletResponse resp) throws JRException, IOException{
    	
    	byte bts[] = null;
    	JasperPrint jasperPrint = JasperManager.fillReport ( reportFile.getPath(),map, conexao);   
        String result = JasperRunManager.runReportToHtmlFile(reportFile.getPath() , map, conexao);  
        JRXlsExporter exporter = new JRXlsExporter();  
        
        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();  
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);  
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE, "C:JSP");  
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "Relatorio.xls");
        exporter.exportReport();
        
        
        bts = xlsReport.toByteArray();  
        resp.setContentType("application/vnd.ms-excel");
        
        System.out.println("*** After JasperPrint = "+result);  
        resp.setContentLength(bts.length);
        xlsReport.close();
        OutputStream ouputStream = resp.getOutputStream();  
        ouputStream.write(bts, 0, bts.length);
        ouputStream.flush();  
        ouputStream.close();
    }
    
    //TODO - Concluir a implementação para geração de HTML
    public void geraHTML(File reportFile, HashMap map, Connection conexao, HttpServletResponse resp, HttpServletRequest req ) throws JRException, IOException{
    	
    	PrintWriter printWriter = resp.getWriter();
        try {  
            JasperReport relCompilado = (JasperReport) JRLoader.loadObject(reportFile.getPath());
              
            JasperPrint result = JasperFillManager.fillReport(relCompilado, map, conexao);
  
            JRHtmlExporter htmlExporter = new JRHtmlExporter();  
  
            resp.setContentType("text/html");
  
            resp.setCharacterEncoding("ISO-8859-1");
  
            req.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,result);
            htmlExporter.setParameter(JRExporterParameter.JASPER_PRINT,result);  
            htmlExporter.setParameter(JRExporterParameter.OUTPUT_WRITER,printWriter);
            htmlExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,"ISO-8859-1");  
            /* TENTEI FAZER COMO NO EXEMPLO, MAS AINDA NÃO FUNCIONOU.
             * TAMBÉM NÃO FUNCIONOU COM RELATÓRIOS COM SUB RELTÓRIOS
             * 
             * aqui é mapeado para o servlet do JasperReport, para que ao gerar o  
             * html não renderize as imagens em branco, pois os espaços em branco, 
             * são imagens em branco que ele adiciona! 
             * Basta adicionar no web.xml a chamada ao servlet que existe no pacote: 
             *     
                <servlet> 
                  <servlet-name>ImageServlet</servlet-name> 
                  <servlet-class>net.sf.jasperreports.j2ee.servlets.ImageServlet</servlet-class> 
               </servlet>  
             
               <servlet-mapping> 
                  <servlet-name>ImageServlet</servlet-name> 
                  <url-pattern>/image.servlet</url-pattern> 
               </servlet-mapping> 
              *   
             */  
            htmlExporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, req.getContextPath() + "/image.servlet?image=");  
            htmlExporter.exportReport();  
        } catch (JRException e) {  
            e.printStackTrace(printWriter);  
        }  

    }
}
