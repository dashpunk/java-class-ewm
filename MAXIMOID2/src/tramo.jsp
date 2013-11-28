<%--
    Document   : Tramo.jsp
    Created on : 23/08/2011, 15:23:55
    Author     : id2-ricardo
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" import="java.io.*,java.sql.*,javax.sql.*,javax.naming.*,java.util.*" %>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="java.io.*,java.sql.*,javax.sql.*,javax.naming.*,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
    <style type="text/css">
        @import url("http://www.google.com/uds/css/gsearch.css");
        @import url("http://www.google.com/uds/solutions/localsearch/gmlocalsearch.css");
        html {
            background-color: ;
            padding: 10px;
            background-image: url(../images/bg_navbarxxx.jpg);
            background-repeat: repeat;

        }
        html, body {
            height: 100%;
        }
        body {
            margin: 0;
        }
        .toolbar {
            background-image: url(../images/bg_toolbar.gif);
        }

        div#GQueryControl {
            background-color: #F5EFD5;
            background-image: url(../images/bg_toolbar.gif);
            background-repeat: repeat-x;
            width: 155;
            border: 2px;
            font-family:arial,sans serif,verdana;
            font-size:11px;
            padding: 1px;
        }
        div#Title {
            padding-top: 4px;
            padding-bottom: 10px;
            font-family:arial,sans serif,verdana;
            font-size: 70px;
        }
        especificacao {
            padding-top: 4px;
            padding-bottom: 10px;
            font-family:arial,sans serif,verdana;
            font-size: 12px;
        }
        #caixaGrafico {
            border:1px solid #e7e7e7;
            padding:20px 20px;
            width:400px;
            background-color:#f8f8f8;
        }
        #caixaGrafico h2{
            color:#666666;
            font-family:Arial, sans-serif;
            font-size:1.1em;
            padding-bottom:0.3em;
            font-weight:bold;
            text-align:center;
        }
        #caixaGrafico p {
            color:#888888;
            font-family:Arial, sans-serif;
            font-size:0.8em;
            padding-top: 0.3em;
            text-align:left;
        }
        .grafico {
            background-color: #F0EFEF;
            border:1px solid #cccccc;
            padding:2px 2px;
        }
        .grafico .barra, .grafico .barra2, .grafico .barra3 {
            text-align:left;
            color:#ffffff;
            height:1.8em;
            line-height:1.8em;
            font-family:Arial, sans-serif;
            display:block;
        }
        .grafico .barra {
            background-color:#ff6600;
        }
        .grafico .barra2 {
            background-color:#66CC33;
        }
        .grafico .barra3 {
            background-color:#3399CC;
        }
        .grafico .barra4 {
            background-color:#add8e6;
        }

        .content {
            font-family:arial,sans serif,verdana;
            margin-left:13.5em;
            margin-right:13.5em;
            margin-bottom:20px;
            height:85%;
            color:#333;
            background:#ffc;
            border:1px solid #333;
            padding:0 10px;}

    </style>
    <head>
        <script type="text/javascript">
            <%
            System.out.println("____init()");
                        String user = "MAXIMO";
                        String pass = "id2maximo";
                int contadorCPE = 0;
                String titulo = "";
                String especificacoesAtivo = "";
                String aurl = request.getServerName();
                String percentual = "";
                java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("0,000.00");
                int coluna = 0;
                int lineCounter = 0;
                String pontos = "";
                int modoExibicao = 0; //0 = linha e 1 = poligono

                //modoExibicao(aurl);
                String chave = "";
                Connection connection = null;
                Class.forName("oracle.jdbc.OracleDriver");
                //TODO Informa??es de conex?o com o banco
                 System.out.println("___local == "+aurl);
                if (aurl.equalsIgnoreCase("10.102.5.92") || aurl.equalsIgnoreCase("10.102.1.242") || aurl.equalsIgnoreCase("localhost") || aurl.equalsIgnoreCase("201.86.128.94") || url.equalsIgnoreCase("10.102.5.9")) {
                    System.out.println("___local");
                    user = "valecimp";
                    pass = "id2maximo";
                    connection = DriverManager.getConnection("jdbc:oracle:thin:@10.102.1.193:1521:valecimp", user, pass);
                    chave = "https://www.google.com/jsapi?key=ABQIAAAAw-yqRVh0fXt4BVMn2fazxRQ7YNF5N61Ft6oZUsVVqOd0EfP-chTEGn0serPdYfTLgS4nzjNKn4ElpA";
                } else {
                                                connection = DriverManager.getConnection("jdbc:oracle:thin:@10.61.0.253:1521:MAXDES", user, pass);
                                                chave = "https://www.google.com/jsapi?key=ABQIAAAAw-yqRVh0fXt4BVMn2fazxRSTYIXEnZRlGaGnBqD-ddlwdLTW9BSqPmqwl0mLUd6GjYQlczAB3DmbGA";
}

                //////////System.out.println(aurl);
                ////////////System.out.println(chave);
                ResultSet rs;
                ResultSet rs2;
                ResultSet rs3;
                ResultSet rs4;
                String sql;
                String sql2;
                String sql3;
                String sql4;
                PreparedStatement instrucaoSql;
                PreparedStatement instrucaoSql2;
                PreparedStatement instrucaoSql3;
                PreparedStatement instrucaoSql4;

                String assetNum = (request.getParameter("assetnum") == null ? "" : request.getParameter("assetnum"));



                Statement stmt = null;
                Statement stmt2 = null;
                Statement stmt3 = null;
                Statement stmt4 = null;

                if (connection != null) {

                    //stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt = connection.createStatement();
                    stmt2 = connection.createStatement();
                    stmt3 = connection.createStatement();
                    stmt4 = connection.createStatement();
                } else {
                    //////////System.out.println("---- connection null");
                    //TODO avisar e fechar
                }

                sql = "SELECT assetnum, parent FROM asset CONNECT BY PRIOR assetnum = parent START WITH assetnum = \'" + assetNum + "\' order by SEQCHILDREN";
                ////////System.out.println(sql);

                rs = stmt.executeQuery(sql);
                String assets = "";
                boolean primeiro = true;
                while (rs.next()) {
                    if (!primeiro) {
                        assets += ", ";

                    } else {
                        primeiro = false;
                    }
                    assets += "\'" + rs.getString("ASSETNUM") + "\'";
                }
                ////System.out.println(assets );
                rs.close();
                //sql = "select ASSETNUM, NUSEQ, UTMLAT,UTMLON from MXTBASSGEO where ASSETNUM in (select assetnum from asset where parent = \'" + assetNum + "\') or assetnum = \'" + assetNum + "\' order by ASSETNUM, NUSEQ";
                //sql = "select ASSETNUM, STRING_LATLNG(''+assetnum+'') valor from ASSET where ASSET.ASSETNUM IN (" + assets + ")";
                sql = "select a.ASSETNUM, "+
                        "(select g.utmlat from MXTBASSGEO g where g.assetnum = a.assetnum and g.nuseq = (select min(m.nuseq) from MXTBASSGEO m where m.assetnum = g.assetnum)) utmlat, "+
                        "(select g.utmlon from MXTBASSGEO g where g.assetnum = a.assetnum and g.nuseq = (select min(m.nuseq) from MXTBASSGEO m where m.assetnum = g.assetnum)) utmlon, "+
                        "STRING_LATLNG(ASSETNUM) valor from ASSET a where ASSETNUM IN ("+assets+") "
                        + "and (select g.utmlat from MXTBASSGEO g where g.assetnum = a.assetnum and g.nuseq = (select min(m.nuseq) from MXTBASSGEO m where m.assetnum = g.assetnum)) is not null";

                sql2 = "select * from ASSET where ASSETNUM = \'" + assetNum + "\'";

                //////////System.out.println("sql = " + sql);
                //////////System.out.println("sql2 = " + sql2);
                rs2 = stmt2.executeQuery(sql2);
                rs2.next();
                int classStructureId = 0;
                try {
                    classStructureId = rs2.getInt("CLASSSTRUCTUREID");
                } catch (SQLException e) {
                }
                rs.close();
                rs = stmt.executeQuery(sql);
                String assetCurrent = null;
                int totalLinhas = 0;
                while (rs.next()) {
                    totalLinhas++;
                }

                rs.close();
                //System.out.println("tot lin " + totalLinhas);


                if (classStructureId > 0 && totalLinhas > 0) {
                    sql3 = "select DESCRIPTION from CLASSSTRUCTURE where CLASSSTRUCTUREID = " + rs2.getInt("CLASSSTRUCTUREID");
                    rs3 = stmt3.executeQuery(sql3);
                    rs3.next();
                    String descricaoDeClasse = rs3.getString("DESCRIPTION");
                    ////////System.out.println("desc a " + descricaoDeClasse);

                    //informações de ordem de serviço
                    rs = stmt.executeQuery(sql);
                    rs.next();
                    String latInicial = rs.getString("UTMLAT");
                    String lonInicial = rs.getString("UTMLON");


            %>

        </script>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <title>Ativos VALEC</title>
        <script src= type="text/javascript"></script>
        <link href="http://code.google.com/apis/maps/documentation/javascript/examples/default.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?v=3.1&sensor=false&region=BR"></script>
        <script type="text/javascript">
            function ListagemTrechosControl(controlDiv, map) {

                // Set CSS styles for the DIV containing the control
                // Setting padding to 5 px will offset the control
                // from the edge of the map
                controlDiv.style.padding = '5px';

                // Set CSS for the control border
                var controlUI = document.createElement('DIV');
                controlUI.style.backgroundColor = 'white';
                controlUI.style.borderStyle = 'solid';
                controlUI.style.borderWidth = '2px';
                controlUI.style.cursor = 'pointer';
                controlUI.style.textAlign = 'center';
                controlUI.title = 'Click to set the map to Home';
                controlDiv.appendChild(controlUI);

                // Set CSS for the control interior
                var controlText = document.createElement('DIV');
                controlText.style.fontFamily = 'Arial,sans-serif';
                controlText.style.fontSize = '12px';
                controlText.style.paddingLeft = '4px';
                controlText.style.paddingRight = '4px';
                controlText.innerHTML = 'Início';
                controlUI.appendChild(controlText);

                // Setup the click event listeners: simply set the map to
                // Chicago
                google.maps.event.addDomListener(controlUI, 'click', function() {
                    //alert("click");
                });
            }
            function initialize() {

                var myLatLng = new google.maps.LatLng(  <%=latInicial%>, <%=lonInicial%>);
                var myOptions = {
                    zoom: 11,
                    center: myLatLng,
                    mapTypeId: google.maps.MapTypeId.TERRAIN
                };

                var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
                var homeControlDiv = document.createElement('div');
                homeControlDiv.id = "Title";
                //homeControlDiv.appendChild(document.createTextNode("Trechos"));
                var homeControl = new ListagemTrechosControl(homeControlDiv, map);
                homeControl.id = "Title";

                homeControlDiv.index = 1;
                //map.controls[google.maps.ControlPosition.TOP_LEFT].push(homeControlDiv);

            <%
                rs.close();
                rs = stmt.executeQuery(sql);
                assetCurrent = null;
                totalLinhas = 0;
                System.out.println("Total linhas a " + totalLinhas);
                System.out.println("Total linhas a " + new java.util.Date());
                while (rs.next()) {
                    totalLinhas++;
                }

                System.out.println("Total linhas a FIM " + totalLinhas);
                System.out.println("Total linhas a FIM " + new java.util.Date());
                rs.close();
                rs = stmt.executeQuery(sql);
                ////////System.out.println("------ totalLinhas " + totalLinhas);
                int linhaAtual = 0;
                ////System.out.println("la " + linhaAtual);
                while (rs.next()) {
                    linhaAtual++;
                    modoExibicao = 0;
                    ////////System.out.println("linecount" + lineCounter);
                    if (assetCurrent == null) {
                        assetCurrent = rs.getString("ASSETNUM");
                        pontos = "";
                        latInicial = rs.getString("UTMLAT");
                        lonInicial = rs.getString("UTMLON");
                        sql2 = "select * from ASSET where ASSETNUM = \'" + rs.getString("ASSETNUM") + "\'";

                        //////////System.out.println("----sql2 " + sql2);
                        rs2 = stmt2.executeQuery(sql2);
                        rs2.next();

                        sql3 = "select contratado, planejado, efetivo from (select assetnum, sum(linecost) contratado from contractline group by assetnum) contratado, (select assetnum, sum(estmatcost) planejado, sum(actmatcost) efetivo from workorder group by assetnum) os where contratado.assetnum = os.assetnum and contratado.assetnum = \'" + assetCurrent + "\'";
                        rs3 = stmt3.executeQuery(sql3);

                        contadorCPE = 0;
                        while (rs3.next()) {
                            contadorCPE++;
                        }
                        rs3 = stmt3.executeQuery(sql3);
                        rs3.next();
                        especificacoesAtivo = "";
                        if (contadorCPE > 0) {
                            especificacoesAtivo = "<table class=\"especificacao\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\">";
                            especificacoesAtivo += "<tr><td bgcolor=\"#cccccc\"><b>Contratado:</b> " + (rs3.getDouble("contratado") > 0 ? decimalFormat.format(rs3.getDouble("contratado")) : "0,00") + "</td>"
                                    + "<td bgcolor=\"#cccccc\"><b>Planejado:</b> " + (rs3.getDouble("planejado") > 0 ? decimalFormat.format(rs3.getDouble("planejado")) : "0,00") + "</td>"
                                    + "<td bgcolor=\"#cccccc\"><b>Efetivo:</b> " + (rs3.getDouble("efetivo") > 0 ? decimalFormat.format(rs3.getDouble("efetivo")) : "0,00") + "</td>"
                                    + "<tr></table>";
                        }

                        sql3 = "select DESCRIPTION from CLASSSTRUCTURE where CLASSSTRUCTUREID = " + rs2.getInt("CLASSSTRUCTUREID");
                        rs3 = stmt3.executeQuery(sql3);
                        rs3.next();
                        descricaoDeClasse = rs3.getString("DESCRIPTION");
                        ////////System.out.println("desc b " + descricaoDeClasse);

                        sql3 = "select ASSETNUM, B.DESCRIPTION, (CASE WHEN ALNVALUE IS NULL THEN TO_CHAR(NUMVALUE) ELSE ALNVALUE END) VALOR, DATATYPE from assetspec A, ASSETATTRIBUTE B where assetnum = \'" + rs.getInt("ASSETNUM") + "\' AND A.ASSETATTRID = B.ASSETATTRID";
                        //////////System.out.println("---- sql3 " + sql3);
                        rs3 = stmt3.executeQuery(sql3);
                        especificacoesAtivo += "<table class=\"especificacao\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\">";

                        coluna = 0;
                        //TODO retirar isso
                        percentual = String.valueOf(1 + (int) (Math.random() * 100));
                        while (rs3.next()) {
                            ////////System.out.println("*********************" + rs3.getString("DESCRIPTION"));
                            if (rs3.getString("DESCRIPTION").equalsIgnoreCase("Percentual de Execução Física da Obra/Projeto")) {
                                percentual = rs3.getString("VALOR");
                            } else if (rs3.getString("DESCRIPTION").equalsIgnoreCase("Modo de posicionamento geográfico")) {
                                String valorMODO = (rs3.getString("VALOR") == null ? "LINHA" : rs3.getString("VALOR"));
                                //////System.out.println(rs2.getString("DESCRIPTION"));
                                //////System.out.println(valorMODO);
                                modoExibicao = (valorMODO.equalsIgnoreCase("LINHA") ? 0 : 1);
                                //////System.out.println(modoExibicao);
                            } else {
                                if (++coluna == 1) {
                                    especificacoesAtivo += "<tr>";
                                }
                                String valor = rs3.getString("VALOR");
                                valor = (valor == null ? "" : valor);
                                especificacoesAtivo += "<td bgcolor=\"#cccccc\"><b>" + rs3.getString("DESCRIPTION") + ":</b> " + valor + "</td>";
                                if (coluna == 2) {
                                    coluna = 0;
                                    especificacoesAtivo += "<tr>";
                                }
                            }
                        }

                        especificacoesAtivo += "</tr></table>";
                        //////////System.out.println("especificacao = " + especificacoesAtivo);
                        //String titulo = "";

                        titulo = "<div style=\"\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td width=\"40%\"><b><h3>" + rs2.getString("DESCRIPTION").replaceAll("'", "") + "</h3></td>"
                                + "<td width=\"60%\">";

                        titulo += "<div class=\"grafico\"><div class=\"barra4\" style=\"width: " + percentual + "%\">" + percentual + "%</div></div></div></table></div>";

                        //////////System.out.println("titulo = " + titulo);
                        sql2 = "select * from ASSET where ASSETNUM = \'" + rs.getString("ASSETNUM") + "\'";

                        //////////System.out.println("----sql2 " + sql2);

                        rs2 = stmt2.executeQuery(sql2);
                        rs2.next();


                    }
                    ////////System.out.println("pontos == " + pontos);
                    ////////System.out.println("-------------------------");
                    ////////System.out.println("------ totalLinhas " + totalLinhas);
                    ////////System.out.println("------ rs.getRow() " + rs.getRow());
                    ////////System.out.println("-------------------------");
                    //System.out.println("tl " + totalLinhas);
                    //System.out.println("la " + linhaAtual);
                    //System.out.println(assetCurrent);
                    //System.out.println(rs.getString("ASSETNUM"));
                    if (!assetCurrent.equals(rs.getString("ASSETNUM")) || linhaAtual >= totalLinhas) {
                        //System.out.println("*** ENTROU");
                        if (linhaAtual >= totalLinhas) {
                            System.out.println("---replace all "+new java.util.Date());
                            pontos += rs.getString("valor").replaceAll(",", ".").replaceAll("\\(", "new google.maps.LatLng\\(").replaceAll("\\),", "\\),\n").replaceAll(";", ",");
                            System.out.println("---replace all fim "+new java.util.Date());

                                    //"new google.maps.LatLng(" + rs.getString("UTMLAT") + ", " + rs.getDouble("UTMLON") + "),\n";
                            assetCurrent = rs.getString("ASSETNUM");
                            //System.out.println("ultimo");
                        }
                        /*if (!(linhaAtual == totalLinhas)) {
                        rs.previous();
                        }
                         * */

                        sql2 = "select * from ASSET where ASSETNUM = \'" + rs.getString("ASSETNUM") + "\'";

                        //////////System.out.println("----sql2 " + sql2);
                        rs2 = stmt2.executeQuery(sql2);
                        rs2.next();
                        ////////System.out.println("===== entrou  =====");

                        lineCounter++;


                        sql3 = "select DESCRIPTION from CLASSSTRUCTURE where CLASSSTRUCTUREID = " + rs2.getInt("CLASSSTRUCTUREID");
                        rs3 = stmt3.executeQuery(sql3);
                        rs3.next();
                        descricaoDeClasse = rs3.getString("DESCRIPTION");
                        ////////System.out.println("desc b " + descricaoDeClasse);
                        sql3 = "select contratado, planejado, efetivo from (select assetnum, sum(linecost) contratado from contractline group by assetnum) contratado, (select assetnum, sum(estmatcost) planejado, sum(actmatcost) efetivo from workorder group by assetnum) os where contratado.assetnum = os.assetnum and contratado.assetnum = \'" + assetCurrent + "\'";
                        rs3 = stmt3.executeQuery(sql3);

                        contadorCPE = 0;
                        while (rs3.next()) {
                            contadorCPE++;
                        }
                        rs3 = stmt3.executeQuery(sql3);
                        rs3.next();
                        especificacoesAtivo = "";
                        if (contadorCPE > 0) {
                            especificacoesAtivo = "<table class=\"especificacao\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\">";
                            especificacoesAtivo += "<tr><td bgcolor=\"#cccccc\"><b>Contratado:</b> " + (rs3.getDouble("contratado") > 0 ? decimalFormat.format(rs3.getDouble("contratado")) : "0,00") + "</td>"
                                    + "<td bgcolor=\"#cccccc\"><b>Planejado:</b> " + (rs3.getDouble("planejado") > 0 ? decimalFormat.format(rs3.getDouble("planejado")) : "0,00") + "</td>"
                                    + "<td bgcolor=\"#cccccc\"><b>Efetivo:</b> " + (rs3.getDouble("efetivo") > 0 ? decimalFormat.format(rs3.getDouble("efetivo")) : "0,00") + "</td>"
                                    + "<tr></table>";
                        }
                        sql3 = "select ASSETNUM, B.DESCRIPTION, (CASE WHEN ALNVALUE IS NULL THEN TO_CHAR(NUMVALUE) ELSE ALNVALUE END) VALOR, DATATYPE from assetspec A, ASSETATTRIBUTE B where assetnum = \'" + assetCurrent + "\' AND A.ASSETATTRID = B.ASSETATTRID";
                        //////////System.out.println("---- sql3 " + sql3);
                        rs3 = stmt3.executeQuery(sql3);
                        especificacoesAtivo += "<table class=\"especificacao\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\">";


                        coluna = 0;
                        //TODO retirar isso
                        percentual = String.valueOf(1 + (int) (Math.random() * 100));
                        while (rs3.next()) {
                            if (rs3.getString("DESCRIPTION").equalsIgnoreCase("Percentual de Execução Física da Obra/Projeto")) {
                                percentual = rs3.getString("VALOR");
                            } else if (rs3.getString("DESCRIPTION").equalsIgnoreCase("Modo de posicionamento geográfico")) {
                                String valorMODO = (rs3.getString("VALOR") == null ? "LINHA" : rs3.getString("VALOR"));
                                //////System.out.println(rs2.getString("DESCRIPTION"));
                                //////System.out.println(valorMODO);
                                modoExibicao = (valorMODO.equalsIgnoreCase("LINHA") ? 0 : 1);
                                //////System.out.println(modoExibicao);
                            } else {
                                if (++coluna == 1) {
                                    especificacoesAtivo += "<tr>";
                                }
                                String valor = rs3.getString("VALOR");
                                valor = (valor == null ? "" : valor);
                                especificacoesAtivo += "<td bgcolor=\"#cccccc\"><b>" + rs3.getString("DESCRIPTION") + ":</b> " + valor + "</td>";
                                if (coluna == 2) {
                                    coluna = 0;
                                    especificacoesAtivo += "<tr>";
                                }
                            }
                        }

                        especificacoesAtivo += "</tr></table>";
                        //////////System.out.println("especificacao = " + especificacoesAtivo);
                        //String titulo = "";
                        ////////System.out.println("--- antes de desc");
                        ////////System.out.println(rs2.getString("DESCRIPTION"));
                        ////////System.out.println("--- apos de desc");
                        String tituloLote = "<div style=\"\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td width=\"40%\"><b><h3>" + rs2.getString("DESCRIPTION").replaceAll("'", "") + "</h3></td>";
                        tituloLote += "<td width=\"60%\"><div class=\"grafico\"><div class=\"barra4\" style=\"width: " + percentual + "%\">" + percentual + "%</div></div></div>";
                        tituloLote += "</tr></table></div>";
                        ////////System.out.println("descricaoDeClasse = =  " + descricaoDeClasse);
                        if (descricaoDeClasse.equals("Lote de Obraww")) {
            %>

                    var markerLote<%=lineCounter%> = new google.maps.Marker({
                        position: new google.maps.LatLng(<%=rs.getDouble("UTMLAT")%>, <%=rs.getDouble("UTMLON")%>),
                        map: map,
                        title:"..."
                    });
                    google.maps.event.addListener(markerLote<%=lineCounter%>, 'click', function() {

                        var infowindowmlote<%=lineCounter%> = new google.maps.InfoWindow();

                        infowindowmlote<%=lineCounter%>.setContent('<%=tituloLote%>'+
                            '<table cellpadding="2" cellspacing="2" width="100%"><tr>'+
                            '<td bgcolor="#cccccc"><b>Classificação:</b> <%=descricaoDeClasse%></td>'+
                            '</tr></table><br>'+
                            '<%=especificacoesAtivo%>'+
                            '<hr><img src="http://www.agenciat1.com.br/wp-content/uploads/2010/11/obras-norte-sul.jpg">');


                        infowindowmlote<%=lineCounter%>.open(map,markerLote<%=lineCounter%>);
                    });
            <%
            } else {
            %>

                    var flightPlanCoordinates<%=lineCounter%> = [<%=pontos%>];
            <%if (modoExibicao == 0) {%>
                    var flightPath<%=lineCounter%> = new google.maps.Polyline({
                        path: flightPlanCoordinates<%=lineCounter%>,
                        strokeColor: "#2F4F4F",
                        strokeOpacity: 1.0,
                        strokeWeight: 5
                    });
            <%} else {%>
                    var flightPath<%=lineCounter%> = new google.maps.Polygon({
                        path: flightPlanCoordinates<%=lineCounter%>,
                        strokeColor: "#2F4F4F",
                        strokeOpacity: 1.0,
                        strokeWeight: 5
                    });
            <%}%>

                    flightPath<%=lineCounter%>.setMap(map);

                    google.maps.event.addListener(flightPath<%=lineCounter%>, 'click', function(event) {
                        var total = 0;

                        for (var i =0; i < flightPath<%=lineCounter%>.getPath().length -1; i++) {
                            total += distance(flightPath<%=lineCounter%>.getPath().getAt(i), flightPath<%=lineCounter%>.getPath().getAt(i+1));

                        }

                        var infoWindow<%=lineCounter%>;
                        infowindow<%=lineCounter%> = new google.maps.InfoWindow();

                        infowindow<%=lineCounter%>.setContent('<%=titulo%>'+
                            '<table cellpadding="2" cellspacing="2" width="100%"><tr>'+
                            '<td bgcolor="#cccccc"><b>Classificação:</b> <%=descricaoDeClasse%></td>'+
                            '<td bgcolor="#cccccc"><b>Comprimento:</b> '+total.toFixed(1)+' kms</td>'+
                            '</tr></table><br>'+
                            '<%=especificacoesAtivo%>'+
                            '<hr><img src="http://www.agenciat1.com.br/wp-content/uploads/2010/11/obras-norte-sul.jpg">');
                        infowindow<%=lineCounter%>.setPosition(event.latLng);

                        infowindow<%=lineCounter%>.open(map);



                    });

                    var marker<%=lineCounter%> = new google.maps.Marker({
                        position: new google.maps.LatLng(<%=latInicial%>, <%=lonInicial%>),
                        map: map,
                        title:"..."
                    });

                    google.maps.event.addListener(marker<%=lineCounter%>, 'click', function() {
                        total = 0;

                        for (var i =0; i < flightPath<%=lineCounter%>.getPath().length -1; i++) {
                            total += distance(flightPath<%=lineCounter%>.getPath().getAt(i), flightPath<%=lineCounter%>.getPath().getAt(i+1));

                        }


                        var infowindowm<%=lineCounter%> = new google.maps.InfoWindow();

                        infowindowm<%=lineCounter%>.setContent('<%=titulo%>'+
                            '<table cellpadding="2" cellspacing="2" width="100%"><tr>'+
                            '<td bgcolor="#cccccc"><b>Classificação:</b> <%=descricaoDeClasse%></td>'+
                            '<td bgcolor="#cccccc"><b>Comprimento:</b> '+total.toFixed(1)+' kms</td>'+
                            '</tr></table><br>'+
                            '<%=especificacoesAtivo%>'+
                            '<hr><img src="http://www.agenciat1.com.br/wp-content/uploads/2010/11/obras-norte-sul.jpg">');

                        infowindowm<%=lineCounter%>.open(map,marker<%=lineCounter%>);
                    });

            <%
                sql3 = "select DESCRIPTION from CLASSSTRUCTURE where CLASSSTRUCTUREID = " + rs2.getInt("CLASSSTRUCTUREID");
                rs3 = stmt3.executeQuery(sql3);
                rs3.next();
                descricaoDeClasse = rs3.getString("DESCRIPTION");
                ////////System.out.println("desc b " + descricaoDeClasse);
                sql3 = "select contratado, planejado, efetivo from (select assetnum, sum(linecost) contratado from contractline group by assetnum) contratado, (select assetnum, sum(estmatcost) planejado, sum(actmatcost) efetivo from workorder group by assetnum) os where contratado.assetnum = os.assetnum and contratado.assetnum = \'" + assetCurrent + "\'";
                rs3 = stmt3.executeQuery(sql3);

                contadorCPE = 0;
                while (rs3.next()) {
                    contadorCPE++;
                }
                rs3 = stmt3.executeQuery(sql3);
                rs3.next();
                especificacoesAtivo = "";
                if (contadorCPE > 0) {
                    especificacoesAtivo = "<table class=\"especificacao\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\">";
                    especificacoesAtivo += "<tr><td bgcolor=\"#cccccc\"><b>Contratado:</b> " + (rs3.getDouble("contratado") > 0 ? decimalFormat.format(rs3.getDouble("contratado")) : "0,00") + "</td>"
                            + "<td bgcolor=\"#cccccc\"><b>Planejado:</b> " + (rs3.getDouble("planejado") > 0 ? decimalFormat.format(rs3.getDouble("planejado")) : "0,00") + "</td>"
                            + "<td bgcolor=\"#cccccc\"><b>Efetivo:</b> " + (rs3.getDouble("efetivo") > 0 ? decimalFormat.format(rs3.getDouble("efetivo")) : "0,00") + "</td>"
                            + "<tr></table>";
                }
                sql3 = "select ASSETNUM, B.DESCRIPTION, (CASE WHEN ALNVALUE IS NULL THEN TO_CHAR(NUMVALUE) ELSE ALNVALUE END) VALOR, DATATYPE from assetspec A, ASSETATTRIBUTE B where assetnum = \'" + assetCurrent + "\' AND A.ASSETATTRID = B.ASSETATTRID";
                //////////System.out.println("---- sql3 " + sql3);
                rs3 = stmt3.executeQuery(sql3);
                especificacoesAtivo += "<table class=\"especificacao\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\">";

                coluna = 0;
                //TODO retirar isso
                percentual = String.valueOf(1 + (int) (Math.random() * 100));
                while (rs3.next()) {
                    if (rs3.getString("DESCRIPTION").equalsIgnoreCase("Percentual de Execução Física da Obra/Projeto")) {
                        percentual = rs3.getString("VALOR");
                    } else if (rs3.getString("DESCRIPTION").equalsIgnoreCase("Modo de posicionamento geográfico")) {
                        String valorMODO = (rs3.getString("VALOR") == null ? "LINHA" : rs3.getString("VALOR"));
                        //////System.out.println(rs2.getString("DESCRIPTION"));
                        //////System.out.println(valorMODO);
                        modoExibicao = (valorMODO.equalsIgnoreCase("LINHA") ? 0 : 1);
                        //////System.out.println(modoExibicao);

                    } else {
                        if (++coluna == 1) {
                            especificacoesAtivo += "<tr>";
                        }
                        String valor = rs3.getString("VALOR");
                        valor = (valor == null ? "" : valor);
                        especificacoesAtivo += "<td bgcolor=\"#cccccc\"><b>" + rs3.getString("DESCRIPTION") + ":</b> " + valor + "</td>";
                        if (coluna == 2) {
                            coluna = 0;
                            especificacoesAtivo += "<tr>";
                        }
                    }
                }

                especificacoesAtivo += "</tr></table>";
                //////////System.out.println("especificacao = " + especificacoesAtivo);
                //String titulo = "";

                titulo = "<div style=\"\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td width=\"40%\"><b><h3>" + rs2.getString("DESCRIPTION").replaceAll("'", "") + "</h3></td>"
                        + "<td width=\"60%\">";

                titulo += "<div class=\"grafico\"><div class=\"barra4\" style=\"width: " + percentual + "%\">" + percentual + "%</div></div></div></table></div>";

                //////////System.out.println("titulo = " + titulo);
                sql2 = "select * from ASSET where ASSETNUM = \'" + rs.getString("ASSETNUM") + "\'";

                //////////System.out.println("----sql2 " + sql2);
                rs2 = stmt2.executeQuery(sql2);
                rs2.next();

            %>

            <%

                            }
                            //pontos = "new google.maps.LatLng(" + rs.getDouble("UTMLAT") + ", " + rs.getDouble("UTMLON") + "),\n";
                            pontos = "";
                            latInicial = rs.getString("UTMLAT");
                            lonInicial = rs.getString("UTMLON");
                            assetCurrent = rs.getString("ASSETNUM");
                        }
                        //pontos += "new google.maps.LatLng(" + rs.getDouble("UTMLAT") + ", " + rs.getDouble("UTMLON") + "),\n";

                        pontos += rs.getString("valor").replaceAll(",", ".").replaceAll("\\(", "new google.maps.LatLng\\(").replaceAll("\\),", "\\),\n").replaceAll(";", ",");

                    }

                    System.out.println("Total linhas FIM " + totalLinhas);
                    System.out.println("Total linhas FIM " + new java.util.Date());


                    rs.close();


                }//if (!rs2.isAfterLast()) {
            %>

                    function distance(point1, point2) {
                        var R = 6371; // earth's mean radius in km
                        var lon1 = point1.lng()* Math.PI / 180;
                        var lat1 = point1.lat() * Math.PI / 180;
                        var lon2 = point2.lng() * Math.PI / 180;
                        var lat2 = point2.lat() * Math.PI / 180;

                        var deltaLat = lat1 - lat2
                        var deltaLon = lon1 - lon2

                        var step1 = Math.pow(Math.sin(deltaLat/2), 2) + Math.cos(lat2) * Math.cos(lat1) * Math.pow(Math.sin(deltaLon/2), 2);
                        var step2 = 2 * Math.atan2(Math.sqrt(step1), Math.sqrt(1 - step1));
                        return step2 * R;
                    }


                    google.maps.event.addListener(map, 'click', function(event) {
                        //alert("Point.X.Y: " + event.latLng);
                    });


                }


        </script>


    </head>
    <body onload="initialize()">
        <div id="map_canvas"></div>
    </body>

</html>
