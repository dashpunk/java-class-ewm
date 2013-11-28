package psdi.webclient.beans.report;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.webclient.servlet.WebClientServlet;

public class JasperRPT extends WebClientServlet
{

    public JasperRPT()
    {
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        int aonde;
        byte bytes[];
        JRExporter exporter;
        String contentType;
        aonde = 0;
        System.out.println((new StringBuilder(" ------> JasperRPT ")).append(++aonde).toString());
        Properties prop = MXServer.getMXServer().getConfig();
        File reportFile = new File((new StringBuilder(String.valueOf(prop.getProperty("mxe.jasper.reports.local")))).append(req.getParameter("relatorio")).toString());
        bytes = (byte[])null;
        String html = null;
        exporter = new JRHtmlExporter();
        contentType = req.getParameter("contentType");
        Map map = new HashMap();
        System.out.println((new StringBuilder(" ------> JasperRPT ")).append(++aonde).toString());
        String attrbName;
        for(Enumeration e = req.getParameterNames(); e.hasMoreElements(); System.out.println(req.getParameter(attrbName)))
        {
            attrbName = (String)e.nextElement();
            if(attrbName.equals("CLAUSULA"))
                map.put(attrbName, req.getParameter(attrbName).replaceAll("#", "'"));
            else
                map.put(attrbName, req.getParameter(attrbName));
        }

        System.out.println((new StringBuilder(" ------> JasperRPT ")).append(++aonde).toString());
        System.out.println("---- antes ");
        System.out.println("---- depois ");
        System.out.println((new StringBuilder(" ------> JasperRPT ")).append(++aonde).toString());
        try
        {
            System.out.println((new StringBuilder(" ------> JasperRPT Conexão i ")).append(++aonde).toString());
            String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
            String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@192.168.1.215:1521:dftop1");
            String username = prop.getProperty("mxe.db.user", "dbmaximo");
            String password = prop.getProperty("mxe.db.password", "id2maximo");
            Class.forName(driver).newInstance();
            System.out.println((new StringBuilder("--- url = ")).append(url).toString());
            System.out.println((new StringBuilder("--- drive = ")).append(driver).toString());
            java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
            System.out.println((new StringBuilder(" ------> JasperRPT conexao f ")).append(++aonde).toString());
            System.out.println((new StringBuilder("----- reportFile.getPath() ")).append(reportFile.getPath()).toString());
            if(map == null)
                System.out.println("***************** map null");
            else
                System.out.println("------------- map not null");
            if(conexao == null)
                System.out.println("---- Conexão nula");
            else
                System.out.println("---- Conexão nao he nula");
            System.out.println((new StringBuilder(" ------> JasperRPT ")).append(++aonde).toString());
            if(contentType != null && !contentType.equals(""))
            {
                net.sf.jasperreports.engine.JasperPrint print = JasperFillManager.fillReport(reportFile.getPath(), map, conexao);
                System.out.println((new StringBuilder("############## Print = ")).append(print).toString());
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            } else
            {
                bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), map, conexao);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return;
        }
        try
        {
            System.out.println((new StringBuilder(" ------> JasperRPT ")).append(++aonde).toString());
            if(bytes != null && bytes.length > 0)
            {
                System.out.println((new StringBuilder(" ------> JasperRPT if ")).append(++aonde).toString());
                resp.setContentType("application/pdf");
                resp.setContentLength(bytes.length);
                ServletOutputStream ouputStream = resp.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();
                System.out.println((new StringBuilder(" ------> JasperRPT if ")).append(++aonde).toString());
            } else
            {
                System.out.println((new StringBuilder(" ------> JasperRPT HTML ")).append(++aonde).toString());
                ServletOutputStream ouputStream = resp.getOutputStream();
                StringWriter destination = new StringWriter();
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, destination);
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
                exporter.exportReport();
                resp.setContentType(contentType);
                resp.setContentLength(destination.getBuffer().length());
                System.out.println((new StringBuilder("########## Buffer =")).append(destination.getBuffer().toString()).toString());
                ouputStream.write(destination.getBuffer().toString().getBytes(Charset.forName("UTF-8").name()), 0, destination.getBuffer().length());
                System.out.println((new StringBuilder(" ------> JasperRPT if ")).append(++aonde).toString());
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return;
    }
}
