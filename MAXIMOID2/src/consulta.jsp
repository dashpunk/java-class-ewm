<%-- 
  Document : index
  Created on : 22/06/2012, 13:32:13
  Author   : ID2maximo
--%>

<%@page import="java.sql.DriverManager"%>
<%@page import="java.util.*" %>
<%@page import="javax.sql.*" %>
<%@page import="java.sql.ResultSetMetaData"%>
<html>
    <style type="text/css">
        /* 
        TagBox table styles
        written by TagBox http://www.tagbox.de
        */
        table {
            border-collapse: collapse;
            border: 1px solid #03476F;
            font: normal 11px verdana, arial, helvetica, sans-serif;
            color: #363636;
            background: #92C428;
        }
        caption {
            text-align: center;
            font: bold 18px arial, helvetica, sans-serif;
            background: transparent;
            padding:6px 4px 8px 0px;
            color: #03476F;
            text-transform: uppercase;
        }
        td, th {
            border: 1px dotted #03476F;
            padding: .4em;
            color: #363636;
        }

        thead th, tfoot th {
            font: bold 11px verdana, arial, helvetica, sans-serif;
            border: 1px solid #03476F;;
            text-align: left;
            background: #4591AD;
            color: #FFFFFF;
            padding-top:3px;
        }
        tbody td a {
            background: transparent;
            text-decoration: none;
            color: #363636;
        }
        tbody td a:hover {
            background: #C2F64D;
            color: #363636;
        }
        tbody th a {
            font: normal 11px verdana, arial, helvetica, sans-serif;
            background: transparent;
            text-decoration: none;
            font-weight:normal;
            color: #363636;
        }
        tbody th a:hover {
            background: transparent;
            color: #363636;
        }
        tbody th, tbody td {
            vertical-align: top;
            text-align: left;
        }
        tfoot td {
            border: 1px solid #03476F;
            background: #4591AD;
            padding-top:3px;
            color: #FFFFFF;
        }
        .odd {
            background: #AEE239;
        }
        tbody tr:hover {
            background: #FFD800;
            border: 1px solid #03476F;
            color: #FFFFFF;
        }
        tbody tr:hover th,
        tbody tr.odd:hover th {
            background: #FFD800;
            color: #FFFFFF;
        }
    </style>
    <head><title>Mnt Maximo</title></head>


    <body onload="executa()">
        <%	String valor = request.getParameter("edValor");
            if (valor != null) {%>
        <%
            int cont = 0;
            if (valor.toUpperCase().indexOf("INSERT") != -1
                    || valor.toUpperCase().indexOf("INTO") != -1
                    || valor.toUpperCase().indexOf("UPDATE") != -1
                    || valor.toUpperCase().indexOf("DELETE") != -1
                    || valor.toUpperCase().indexOf("CREATE") != -1
                    || valor.toUpperCase().indexOf("ALTER") != -1
                    || valor.toUpperCase().indexOf("DROP") != -1
                    || valor.toUpperCase().indexOf("RENAME") != -1
                    || valor.toUpperCase().indexOf("TRUNCATE") != -1
                    || valor.toUpperCase().indexOf("COMMIT") != -1
                    || valor.toUpperCase().indexOf("ROLLBACK") != -1
                    || valor.toUpperCase().indexOf("SAVEPOINT") != -1
                    || valor.toUpperCase().indexOf("GRANT") != -1
                    || valor.toUpperCase().indexOf("REVOKE") != -1) {
        %>Comando inválido!<%                } else {

            java.sql.Connection con;
            java.sql.Statement s;
            java.sql.ResultSet rs;
            java.sql.PreparedStatement pst;

            con = null;
            s = null;
            pst = null;
            rs = null;

            String user = request.getParameter("edUsuario");
            String pass = request.getParameter("edSenha");
            String aurl = request.getServerName();
            try {

                Class.forName("oracle.jdbc.OracleDriver");
                if (aurl.equalsIgnoreCase("localhost") || aurl.equalsIgnoreCase("10.102.1.193") || aurl.equalsIgnoreCase("10.102.5.104")) {
                    //System.out.println("___local");
                    con = DriverManager.getConnection("jdbc:oracle:thin:@10.102.1.193:1521:oramapa", user, pass);
                } else if (aurl.equalsIgnoreCase("pgahom.agricultura.gov.br")) {
                    con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = mavrt515.agricultura.gov.br)(PORT = 1521)) (CONNECT_DATA = (SERVER = DEDICATED) (SERVICE_NAME = cnahom)))", "gta_pga", "id2maximo");
                } else if (aurl.equalsIgnoreCase("pga.agricultura.gov.br")) {
                    con = DriverManager.getConnection("mxe.db.url=jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = masrv371-vip.agricultura.gov.br)(PORT = 1521)) (ADDRESS = (PROTOCOL = TCP)(HOST = masrv372-vip.agricultura.gov.br)(PORT = 1521)) (LOAD_BALANCE = YES) (FAILOVER = ON) (CONNECT_DATA = (SERVER = DEDICATED) (SERVICE_NAME = prodpga.agricultura.gov.br) (FAILOVER_MODE = (TYPE = SELECT) (METHOD = BASIC) (RETRIES = 180) (DELAY = 5))))", "gta_pga", "id2maximo");
                } else {

                    con = DriverManager.getConnection("jdbc:oracle:thin:@10.102.1.193:1521:oramapa", user, pass);
                }

            } catch (ClassNotFoundException cnfex) {
                cnfex.printStackTrace();

            }
        %>

        <%
            String sql = "select * from APP.CUSTOMER";
            sql = "select PURCHASE_ORDER.*,  CUSTOMER.NAME from APP.PURCHASE_ORDER, CUSTOMER "
                    + "WHERE APP.PURCHASE_ORDER.CUSTOMER_ID = CUSTOMER.CUSTOMER_ID";
            sql = valor;
            try {
                s = con.createStatement();
                rs = s.executeQuery(sql);
                ResultSetMetaData meta = rs.getMetaData();
        %><table border="1"><thead><tr><%
            for (int cab = 1; cab <= meta.getColumnCount(); cab++) {

                    %><th><%= meta.getColumnName(cab)%></th><%
                        }
                    %></tr></thead><tbody>
                    <%
                        while (rs.next()) {
                        %><tr><%
                            for (int cab = 1; cab <= meta.getColumnCount(); cab++) {

                    %><td><%= rs.getString(cab)%></td><%

                        }
                    %></tr><%
                        }
                    %></tbody></table>

        <%

        } catch (Exception e) {
        %><%=e.getMessage()%><%
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (s != null) {
                            s.close();
                        }
                        if (con != null) {
                            con.close();
                        }
                    }

        %>



        <%}
            }%>


    </body>
</html>