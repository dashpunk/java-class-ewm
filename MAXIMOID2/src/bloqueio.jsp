<%--
    Document   : Bloqueio.jsp
    Created on : 01/07/2012, 15:23:55
    Author     : id2-ricardo
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" import="java.io.*,java.sql.*,javax.sql.*,javax.naming.*,java.util.*" %>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java"
	import="java.io.*,java.text.*,java.sql.*,javax.sql.*,javax.naming.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:v="urn:schemas-microsoft-com:vml">
<style type="text/css">
@import url("http://www.google.com/uds/css/gsearch.css");

@import
	url("http://www.google.com/uds/solutions/localsearch/gmlocalsearch.css")
	;

html {
	background-color:;
	padding: 10px;
	background-image: url(../images/bg_navbarxxx.jpg);
	background-repeat: repeat;
}

html,body {
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
	font-family: arial, sans serif, verdana;
	font-size: 11px;
	padding: 1px;
}

div#Title {
	padding-top: 4px;
	padding-bottom: 10px;
	font-family: arial, sans serif, verdana;
	font-size: 70px;
}

especificacao {
	padding-top: 4px;
	padding-bottom: 10px;
	font-family: arial, sans serif, verdana;
	font-size: 12px;
}

#caixaGrafico {
	border: 1px solid #e7e7e7;
	padding: 20px 20px;
	width: 400px;
	background-color: #f8f8f8;
}

#flutuante {
	display: scroll;
	position: fixed;
	top: 0px;
	margin-left: -220px;
	left: 50%;
}

#caixaGrafico h2 {
	color: #666666;
	font-family: Arial, sans-serif;
	font-size: 1.1em;
	padding-bottom: 0.3em;
	font-weight: bold;
	text-align: center;
}

#caixaGrafico p {
	color: #888888;
	font-family: Arial, sans-serif;
	font-size: 0.8em;
	padding-top: 0.3em;
	text-align: left;
}

.grafico {
	background-color: #F0EFEF;
	border: 1px solid #cccccc;
	padding: 2px 2px;
}

.grafico .barra,.grafico .barra2,.grafico .barra3 {
	text-align: left;
	color: #ffffff;
	height: 1.8em;
	line-height: 1.8em;
	font-family: Arial, sans-serif;
	display: block;
}

.grafico .barra {
	background-color: #ff6600;
}

.grafico .barra2 {
	background-color: #66CC33;
}

.grafico .barra3 {
	background-color: #3399CC;
}

.grafico .barra4 {
	background-color: #add8e6;
}

.content {
	font-family: arial, sans serif, verdana;
	margin-left: 13.5em;
	margin-right: 13.5em;
	margin-bottom: 20px;
	height: 85%;
	color: #333;
	background: #ffc;
	border: 1px solid #333;
	padding: 0 10px;
}
</style>
<head>
<script type="text/javascript">
            <%
                //System.out.println("____init()");
                String user = "GTA_PGA";
                String pass = "id2maximo";
                int contadorCPE = 0;
                String titulo = "";
                String especificacoesPonto = "";
                String aurl = request.getServerName();
                String percentual = "";
                java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("0,000.00");
                int coluna = 0;
                int lineCounter = 0;
                String pontos = "";
                int modoExibicao = 0; //0 = linha e 1 = poligono
                double fatorLat = 2.5;
                double fatorLon = 2.5;

                //modoExibicao(aurl);
                String chave = "";
                Connection connection = null;
                Class.forName("oracle.jdbc.OracleDriver");
                //TODO Informa??es de conex?o com o banco
                //System.out.println("___local == " + aurl);
                if (aurl.equalsIgnoreCase("localhost") || aurl.equalsIgnoreCase("10.102.1.193") || aurl.equalsIgnoreCase("10.102.5.104")) {
                    //System.out.println("___local");
                    user = "GTA_PGA";
                    pass = "id2maximo";
                    connection = DriverManager.getConnection("jdbc:oracle:thin:@10.102.1.193:1521:oramapa", user, pass);
                    chave = "https://www.google.com/jsapi?key=ABQIAAAAw-yqRVh0fXt4BVMn2fazxRQ7YNF5N61Ft6oZUsVVqOd0EfP-chTEGn0serPdYfTLgS4nzjNKn4ElpA";
                } else if (aurl.equalsIgnoreCase("pgahom.agricultura.gov.br")) {
                    connection = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = mavrt515.agricultura.gov.br)(PORT = 1521)) (CONNECT_DATA = (SERVER = DEDICATED) (SERVICE_NAME = cnahom)))", "gta_pga", "id2maximo");
                    chave = "http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=true&amp;key=ABQIAAAAw-yqRVh0fXt4BVMn2fazxRRDimpgHhS8Zj3QHzMDDKxC_70DFxRZxSQ1CIM4ynlS5EDd6EnWWUtjsQ";
                } else if (aurl.equalsIgnoreCase("pga.agricultura.gov.br")) {
                    connection = DriverManager.getConnection("mxe.db.url=jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = masrv371-vip.agricultura.gov.br)(PORT = 1521)) (ADDRESS = (PROTOCOL = TCP)(HOST = masrv372-vip.agricultura.gov.br)(PORT = 1521)) (LOAD_BALANCE = YES) (FAILOVER = ON) (CONNECT_DATA = (SERVER = DEDICATED) (SERVICE_NAME = prodpga.agricultura.gov.br) (FAILOVER_MODE = (TYPE = SELECT) (METHOD = BASIC) (RETRIES = 180) (DELAY = 5))))", "gta_pga", "id2maximo");
                    chave = "http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=true&amp;key=ABQIAAAAw-yqRVh0fXt4BVMn2fazxRTOsgos3WJNV28rfN5BTiJyB5ginBRySRRVHi6emkhUuSU-gdJ3kcRRMQ";
                } else {
                    //System.out.println("___local");
                    user = "GTA_PGA";
                    pass = "id2maximo";
                    connection = DriverManager.getConnection("jdbc:oracle:thin:@10.102.1.193:1521:oramapa", user, pass);
                    chave = "https://www.google.com/jsapi?key=ABQIAAAAw-yqRVh0fXt4BVMn2fazxRQ7YNF5N61Ft6oZUsVVqOd0EfP-chTEGn0serPdYfTLgS4nzjNKn4ElpA";
                }

                ////////////System.out.println(aurl);
                //////////////System.out.println(chave);
                ResultSet rs;
                ResultSet rs2;
                String sql;
                String sql2;
                PreparedStatement instrucaoSql;
                PreparedStatement instrucaoSql2;

                String latitude = (request.getParameter("lat") == null ? "0" : request.getParameter("lat"));
                String longitude = (request.getParameter("lon") == null ? "0" : request.getParameter("lon"));

                try {
                    Double alat = Double.valueOf(latitude);
                    Double alon = Double.valueOf(longitude);
                } catch (NumberFormatException e) {
                    return;
                } catch (NullPointerException e) {
                    return;
                }

                String tipo = (request.getParameter("tipo") == null ? "01" : request.getParameter("tipo"));
                double centroLat = Double.valueOf(request.getParameter("lat").replace(',', '.'));
                double centroLon = Double.valueOf(request.getParameter("lon").replace(',', '.'));



                Statement stmt = null;
                Statement stmt2 = null;

                if (connection != null) {
                    //stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    stmt = connection.createStatement();
                    stmt2 = connection.createStatement();
                } else {
                    ////////////System.out.println("---- connection null");
                    //TODO avisar e fechar
                }

                //TODO VEIRIFICAR SE TODOS ESTÃO OBTENDO APENAS AS COORDENADAS PRINCIPAIS
                if (tipo.equalsIgnoreCase("OESA")) {

                    sql = "select  D.ID2UTMLON, D.ID2UTMLAT, B.DESCRIPTION ID2TIPOLOCAL, A.DESCRIPTION, A.ID2LOCSIGLA, A.ID2LOCUF,  A.ID2CEPCODE, A.ID2ADDUF, A.ID2ADDNUM, A.ID2ADDCOM, A.ID2EMAIL, (SELECT ADDRESS4 FROM ADDRESS WHERE ADDRESSID = A.ID2CEPCODE) MUNICIPIO, (SELECT ADDRESS1 FROM ADDRESS WHERE ADDRESSID = A.ID2CEPCODE) LOGRADOURO, (SELECT ADDRESS5 FROM ADDRESS WHERE ADDRESSID = A.ID2CEPCODE) BAIRRO FROM LOCATIONS A, ID2COOEXT D, ALNDOMAIN B "
                            + "WHERE D.LOCATION = A.LOCATION AND A.ID2TIPOLOCAL in ( \'13\',\'10\',\'11\',\'12\') AND B.domainid=\'ID2TIPOLOCAL\' AND B.VALUE = A.ID2TIPOLOCAL";
                } //Estabelecimento Rural
                else if (tipo.equalsIgnoreCase("01")) {

                    sql = "select B.DESCRIPTION , A.LOCATION, A.ID2UTMLON, A.ID2UTMLAT, C.DESCRIPTION UF, (SELECT D.DESCRIPTION FROM LOCATIONS D WHERE D.LOCATION = B.ID2CODMUN) MUNICIPIO, B.ID2ADDRESS1, B.ID2INCRA, B.ID2ARETOT, B.ID2QTDPROD, B.ID2QTDEXPL from ID2COOEXT a, LOCATIONS B, LOCATIONS C WHERE B.ID2TIPOLOCAL = \'" + tipo + "\' AND A.LOCATION = B.LOCATION AND ID2UTMLON BETWEEN " + String.valueOf(centroLon - fatorLon) + " and " + String.valueOf(centroLon + fatorLon) + " AND ID2UTMLAT BETWEEN " + String.valueOf(centroLat - fatorLat) + " and " + String.valueOf(centroLat + fatorLat) + " AND B.ID2LOCUF = C.LOCATION AND A.MAIN = 1";
                    //sql = "select B.DESCRIPTION , A.LOCATION, A.ID2UTMLON, A.ID2UTMLAT, C.DESCRIPTION UF, (SELECT D.DESCRIPTION FROM LOCATIONS D WHERE D.LOCATION = B.ID2CODMUN) MUNICIPIO, B.ID2ADDRESS1, B.ID2INCRA, B.ID2ARETOT, B.ID2QTDPROD, B.ID2QTDEXPL from ID2COOEXT a, LOCATIONS B, LOCATIONS C WHERE B.ID2TIPOLOCAL = \'" + tipo + "\' AND A.LOCATION = B.LOCATION AND B.ID2LOCUF = C.LOCATION AND A.MAIN = 1";
                } //Aglomeração
                else if (tipo.equalsIgnoreCase("03")) {
                    sql = "select   A.ID2UTMLON, A.ID2UTMLAT, (SELECT D.DESCRIPTION FROM LOCATIONS D WHERE D.LOCATION = B.ID2LOCUF) UF, (SELECT D.DESCRIPTION FROM LOCATIONS D WHERE D.LOCATION = B.ID2CODMUN) MUNICIPIO, B.ID2ADDRESS1,E.DESCRIPTION EVENTO, B.ID2NOMEVE DESCRIPTION, F.DESCRIPTION LOCALIZACAO, B.ID2PROEVE, B.ID2PERINI, B.ID2PERFIN, B.ID2PAGWEB, B.ID2EMAIL from ID2COOEXT a, LOCATIONS B, ALNDOMAIN E, ALNDOMAIN F WHERE B.ID2TIPOLOCAL = \'" + tipo + "\' AND A.LOCATION = B.LOCATION AND ID2UTMLON BETWEEN " + String.valueOf(centroLon - fatorLon) + " and " + String.valueOf(centroLon + fatorLon) + " AND ID2UTMLAT BETWEEN " + String.valueOf(centroLat - fatorLat) + " and " + String.valueOf(centroLat + fatorLat) + "  AND A.MAIN = 1 AND E.DOMAINID = 'ID2TIPEVE' AND E.VALUE = B.ID2TIPEVE AND F.DOMAINID = 'ID2TIPLOC' AND F.VALUE = B.ID2TIPLOC";
                } //Estabelecimento POA
                else if (tipo.equalsIgnoreCase("02")) {
                    sql = "select  D.ID2UTMLON, D.ID2UTMLAT,A.ID2TIPEST, A.ID2NUMCONT, B.DISPLAYNAME DESCRIPTION, A.ID2CEPCODE, A.ID2ADDUF, A.ID2ADDNUM, A.ID2ADDCOM, A.ID2EMAIL, (SELECT ADDRESS4 FROM ADDRESS WHERE ADDRESSID = A.ID2CEPCODE) MUNICIPIO, (SELECT ADDRESS1 FROM ADDRESS WHERE ADDRESSID = A.ID2CEPCODE) LOGRADOURO, (SELECT ADDRESS5 FROM ADDRESS WHERE ADDRESSID = A.ID2CEPCODE) BAIRRO FROM LOCATIONS A, PERSON B, ID2COOEXT D "
                            + "WHERE D.LOCATION = A.LOCATION  AND  B.PERSONID = A.ID2PJ AND A.ID2TIPOLOCAL=\'02\' AND ID2UTMLON BETWEEN " + String.valueOf(centroLon - fatorLon) + " and " + String.valueOf(centroLon + fatorLon) + " AND ID2UTMLAT BETWEEN " + String.valueOf(centroLat - fatorLat) + " and " + String.valueOf(centroLat + fatorLat);
                } else {
                    sql = "select  coalesce(a.DESCRIPTION, b.DESCRIPTION, a.DESCRIPTION) DESCRIPTION, A.LOCATION, A.ID2UTMLON, A.ID2UTMLAT, C.DESCRIPTION UF, D.DESCRIPTION MUNICIPIO, B.ID2ADDRESS1, B.ID2INCRA, B.ID2ARETOT, B.ID2QTDPROD, B.ID2QTDEXPL from ID2COOEXT a, LOCATIONS B, LOCATIONS C, LOCATIONS D WHERE B.ID2TIPOLOCAL = \'" + tipo + "\' AND A.LOCATION = B.LOCATION AND ID2UTMLON BETWEEN " + String.valueOf(centroLon - fatorLon) + " and " + String.valueOf(centroLon + fatorLon) + " AND ID2UTMLAT BETWEEN " + String.valueOf(centroLat - fatorLat) + " and " + String.valueOf(centroLat + fatorLat) + " AND B.ID2LOCUF = C.LOCATION AND B.ID2CODMUN = D.LOCATION AND A.MAIN = 1";
                }
                //System.out.println(sql);
                rs = stmt.executeQuery(sql);
                //System.out.println("execute 1");
                //////////System.out.println(sql);

                rs = stmt.executeQuery(sql);
                String assets = "";
                boolean primeiro = true;

            %>

        </script>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>Bloqueio</title>
<script src=type="text/javascript"></script>
<link
	href="http://code.google.com/apis/maps/documentation/javascript/examples/default.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyB6Py81KG0H57podswdsWBUd3KISfXYCXg&sensor=false&libraries=geometry"></script>
<script type="text/javascript">
            
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
            
            function PontosControl(controlDiv, map) {

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

                var myLatLng = new google.maps.LatLng(  <%=centroLat%>, <%=centroLon%>);
                var myOptions = {
                    zoom: 8,
                    center: myLatLng,
                    mapTypeId: google.maps.MapTypeId.TERRAIN
                };


                var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
                var triangleCoords = [
                    new google.maps.LatLng(<%=centroLat%>-<%=fatorLat%>, <%=centroLon%>-<%=fatorLon%>),
                    new google.maps.LatLng(<%=centroLat%>-<%=fatorLat%>, <%=centroLon%>+<%=fatorLon%>),
                    new google.maps.LatLng(<%=centroLat%>+<%=fatorLat%>, <%=centroLon%>+<%=fatorLon%>),
                    new google.maps.LatLng(<%=centroLat%>+<%=fatorLat%>, <%=centroLon%>-<%=fatorLon%>)
                ];
                
                var flagEstabelecimentoRural = new google.maps.MarkerImage('images/flagEstabelecimentoRural.png',
                new google.maps.Size(20, 32),
                new google.maps.Point(0,0),
                new google.maps.Point(0, 32));
                var flagEstabelecimentoPOA = new google.maps.MarkerImage('images/flagEstabelecimentoPOA.png',
                new google.maps.Size(20, 32),
                new google.maps.Point(0,0),
                new google.maps.Point(0, 32));
                var flagAglomeracao = new google.maps.MarkerImage('images/flagAglomeracao.png',
                new google.maps.Size(20, 32),
                new google.maps.Point(0,0),
                new google.maps.Point(0, 32));
                var flagOESA = new google.maps.MarkerImage('images/flagOESA.png',
                new google.maps.Size(20, 32),
                new google.maps.Point(0,0),
                new google.maps.Point(0, 32));
                var shadow = new google.maps.MarkerImage('images/shadow.png',
                new google.maps.Size(37, 32),
                new google.maps.Point(0,0),
                new google.maps.Point(0, 32));
                var shape = {
                    coord: [1, 1, 1, 20, 18, 20, 18 , 1],
                    type: 'poly'
                };
                bermudaTriangle = new google.maps.Polygon({
                    paths: triangleCoords,
                    strokeColor: "#000000",
                    strokeOpacity: 0.8,
                    strokeWeight: 3,
                    fillColor: "#000000",
                    fillOpacity: 0
                });

                bermudaTriangle.setMap(map);

            <%
                rs.close();
                rs = stmt.executeQuery(sql);
                int contadorProprietario = 0;
                String proprietarios = "";
                String tituloLote = "";
                while (rs.next()) {
                    lineCounter++;

                    modoExibicao = 0;
                    //////////System.out.println("linecount" + lineCounter);

                    percentual = String.valueOf(1 + (int) (Math.random() * 100));



                    //String titulo = "";

                    //Estabelecimento Rural
                    if (tipo.equals("01")) {
                        especificacoesPonto += "</tr></table>";
                        sql2 = "select B.DISPLAYNAME from ID2LOCATIONUSERCUST A, PERSON B  WHERE B.PERSONID = A.PERSONID AND A.LOCATION = \'" + rs.getString("LOCATION") + "\'";
                        rs2 = stmt2.executeQuery(sql2);
                        contadorProprietario = 0;
                        proprietarios = "";
                        while (rs2.next()) {
                            if (++contadorProprietario > 1) {
                                proprietarios += "<br>";
                            }
                            proprietarios += rs2.getString("DISPLAYNAME");
                            //System.out.println(proprietarios);
                        }
                        rs2.close();

                        titulo = "<div style=\"\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td width=\"40%\"><b><h3> Estabelecimento Rural</h3></div></td></table></div>"
                                + "<td width=\"100%\">";

                        //titulo += "<div class=\"grafico\"><div class=\"barra4\" style=\"width: " + percentual + "%\">" + percentual + "%</div></div></div></table></div>";

                        //TODO obter proprietários
                        tituloLote = "<div style=\"\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td width=\"40%\"><b><h3>Estabelecimento Rural</h3></td></div></div></div></tr></table></div>";
                        especificacoesPonto = "<table class=\"especificacao\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\">";
                        especificacoesPonto += "<tr><td bgcolor=\"#cccccc\"><b>Código:</b></td><td> " + rs.getString("LOCATION") + " </td></tr>"
                                + "<tr><td bgcolor=\"#cccccc\"><b>Nome:</b></td><td> " + (rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION").replaceAll("'", "")) + " </td></tr>"
                                + "<tr><td bgcolor=\"#cccccc\"><b>Proprietário(s):</b></td><td> " + proprietarios.replace("#13", "\n") + "</td></tr>"
                                + "<tr><td bgcolor=\"#cccccc\"><b>UF:</b></td><td> " + (rs.getString("UF") == null ? "" : rs.getString("UF")) + "</td></tr>"
                                + "<tr><td bgcolor=\"#cccccc\"><b>Município:</b></td><td> " + (rs.getString("MUNICIPIO") == null ? "" : rs.getString("MUNICIPIO").replaceAll("'", "")) + "</td></tr>"
                                + "<tr><td bgcolor=\"#cccccc\"><b>Endereço:</b></td><td> " + (rs.getString("ID2ADDRESS1") == null ? "" : rs.getString("ID2ADDRESS1")) + "</td></tr>"
                                + "<tr><td bgcolor=\"#cccccc\"><b>Incra:</b></td><td> " + (rs.getString("ID2INCRA") == null ? "" : rs.getString("ID2INCRA")) + "</td></tr>"
                                + "<tr><td bgcolor=\"#cccccc\"><b>Área Total:</b></td><td> " + (rs.getString("ID2ARETOT") == null ? "" : rs.getString("ID2ARETOT")) + "</td></tr>"
                                + "<tr><td bgcolor=\"#cccccc\"><b>Qtd Produtores:</b></td><td> " + (rs.getString("ID2QTDPROD") == null ? "" : rs.getString("ID2QTDPROD")) + "</td></tr>"
                                + "<tr><td bgcolor=\"#cccccc\"><b>Qtd Explorações:</b></td><td> " + (rs.getString("ID2QTDEXPL") == null ? "" : rs.getString("ID2QTDEXPL")) + "</td></tr>"
                                + "</table>";

            %>
                    var markerLote<%=lineCounter%> = new google.maps.Marker({
                        position: new google.maps.LatLng(<%=rs.getDouble("ID2UTMLAT")%>, <%=rs.getDouble("ID2UTMLON")%>),
                        map: map,
                        shadow: shadow,
                        icon: flagEstabelecimentoRural,
                        shape: shape,
                        title:"Nome: <%=(rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION").replaceAll("'", ""))%>\nLatitude: <%=rs.getDouble("ID2UTMLAT")%>\nLongitude: <%=rs.getDouble("ID2UTMLON")%>"});
            <%
            } //Aglomeração
            else if (tipo.equals("03")) {
                especificacoesPonto += "</tr></table>";
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                df.setLenient(false);
                //System.out.println("--------------------------------");
                String d1 = (rs.getString("ID2PERINI") != null ? df.format(rs.getDate("ID2PERINI")) : "");
                //System.out.println("-------------------------------- "+d1);
                String d2 = (rs.getString("ID2PERFIN") != null ? df.format(rs.getDate("ID2PERFIN")) : "");
                //System.out.println("-------------------------------- "+d2);


                titulo = "<div style=\"\"><p><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td width=\"40%\"><b><h3>Aglomeração</h3></div></td></table></div>"
                        + "<td width=\"100%\">";

                tituloLote = "<div style=\"\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td width=\"40%\"><b><h3>Aglomeração</h3></td></div></div></div></tr></table></div>";
                especificacoesPonto = "<table class=\"especificacao\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\">";
                especificacoesPonto +=
                        "<tr><td bgcolor=\"#cccccc\"><b>Tipo de Evento:</b></td><td> " + rs.getString("EVENTO") + " </td></tr>"
                        +"<tr><td bgcolor=\"#cccccc\"><b>Nome do Evento:</b></td><td> " + (rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Localização:</b></td><td> " + (rs.getString("LOCALIZACAO") == null ? "" : rs.getString("LOCALIZACAO")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>UF:</b></td><td> " + (rs.getString("UF") == null ? "" : rs.getString("UF")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Município:</b></td><td> " + (rs.getString("MUNICIPIO") == null ? "" : rs.getString("MUNICIPIO")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Promotor de Evento:</b></td><td> " + (rs.getString("ID2PROEVE") == null ? "" : rs.getString("ID2PROEVE")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Dt Inicial Evento:</b></td><td> " + d1 + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Dt Final Evento:</b></td><td> " + d2 + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Página WEB:</b></td><td> " + (rs.getString("ID2PAGWEB") == null ? "" : rs.getString("ID2PAGWEB")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>E-mail:</b></td><td> " + (rs.getString("ID2EMAIL") == null ? "" : rs.getString("ID2EMAIL")) + " </td></tr>"
                        + "</table>";
            %>
                    var markerLote<%=lineCounter%> = new google.maps.Marker({
                        position: new google.maps.LatLng(<%=rs.getDouble("ID2UTMLAT")%>, <%=rs.getDouble("ID2UTMLON")%>),
                        map: map,
                        shadow: shadow,
                        icon: flagAglomeracao,
                        shape: shape,
                        title:"Nome: <%=(rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION").replaceAll("'", ""))%>\nLatitude: <%=rs.getDouble("ID2UTMLAT")%>\nLongitude: <%=rs.getDouble("ID2UTMLON")%>"});
            <%


            } //Estabelecimento POA
            else if (tipo.equals("02")) {
                especificacoesPonto += "</tr></table>";

                titulo = "<div style=\"\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td width=\"40%\"><b><h3> Aglomeração</h3></div></td></table></div>"
                        + "<td width=\"100%\">";

                tituloLote = "<div style=\"\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td width=\"40%\"><b><h3>Estabelecimento POA</h3></td></div></div></div></tr></table></div>";
                especificacoesPonto = "<table class=\"especificacao\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\">";
                especificacoesPonto += "<tr><td bgcolor=\"#cccccc\"><b>Tipo de Inspeção:</b></td><td> " + rs.getString("ID2TIPEST") + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Nr Controle:</b></td><td> " + (rs.getString("ID2NUMCONT") == null ? "" : rs.getString("ID2NUMCONT")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Nome:</b></td><td> " + (rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>CEP:</b></td><td> " + (rs.getString("ID2CEPCODE") == null ? "" : rs.getString("ID2CEPCODE")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>UF:</b></td><td> " + (rs.getString("ID2ADDUF") == null ? "" : rs.getString("ID2ADDUF")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Município:</b></td><td> " + (rs.getString("MUNICIPIO") == null ? "" : rs.getString("MUNICIPIO")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Logradouro:</b></td><td> " + (rs.getString("LOGRADOURO") == null ? "" : rs.getString("LOGRADOURO")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Número:</b></td><td> " + (rs.getString("ID2ADDNUM") == null ? "" : rs.getString("ID2ADDNUM")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Complemento:</b></td><td> " + (rs.getString("ID2ADDCOM") == null ? "" : rs.getString("ID2ADDCOM")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Bairro:</b></td><td> " + (rs.getString("BAIRRO") == null ? "" : rs.getString("BAIRRO")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Email:</b></td><td> " + (rs.getString("ID2EMAIL") == null ? "" : rs.getString("ID2EMAIL")) + " </td></tr>"
                        + "</table>";


            %>
                    var markerLote<%=lineCounter%> = new google.maps.Marker({
                        position: new google.maps.LatLng(<%=rs.getDouble("ID2UTMLAT")%>, <%=rs.getDouble("ID2UTMLON")%>),
                        map: map,
                        shadow: shadow,
                        icon: flagEstabelecimentoPOA,
                        shape: shape,
                        title:"Nome: <%=(rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION").replaceAll("'", ""))%>\nLatitude: <%=rs.getDouble("ID2UTMLAT")%>\nLongitude: <%=rs.getDouble("ID2UTMLON")%>"});
            <%

            } //Estabelecimento POA
            else if (tipo.equalsIgnoreCase("OESA")) {
                especificacoesPonto += "</tr></table>";

                titulo = "<div style=\"\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td width=\"40%\"><b><h3> Aglomeração</h3></div></td></table></div>"
                        + "<td width=\"100%\">";

                tituloLote = "<div style=\"\"><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"><tr><td width=\"40%\"><b><h3>OESA – Órgão Estadual de Sanidade Animal</h3></td></div></div></div></tr></table></div>";
                especificacoesPonto = "<table class=\"especificacao\" cellpadding=\"2\" cellspacing=\"2\" width=\"100%\">";
                especificacoesPonto += "<tr><td bgcolor=\"#cccccc\"><b>Tipo de Unidade:</b></td><td> " + rs.getString("ID2TIPOLOCAL") + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Unidade OESA:</b></td><td> " + (rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Sigla:</b></td><td> " + (rs.getString("ID2LOCSIGLA") == null ? "" : rs.getString("ID2LOCSIGLA")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>CEP:</b></td><td> " + (rs.getString("ID2CEPCODE") == null ? "" : rs.getString("ID2CEPCODE")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>UF:</b></td><td> " + (rs.getString("ID2ADDUF") == null ? "" : rs.getString("ID2ADDUF")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Município:</b></td><td> " + (rs.getString("MUNICIPIO") == null ? "" : rs.getString("MUNICIPIO")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Logradouro:</b></td><td> " + (rs.getString("LOGRADOURO") == null ? "" : rs.getString("LOGRADOURO")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Número:</b></td><td> " + (rs.getString("ID2ADDNUM") == null ? "" : rs.getString("ID2ADDNUM")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Complemento:</b></td><td> " + (rs.getString("ID2ADDCOM") == null ? "" : rs.getString("ID2ADDCOM")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Bairro:</b></td><td> " + (rs.getString("BAIRRO") == null ? "" : rs.getString("BAIRRO")) + " </td></tr>"
                        + "<tr><td bgcolor=\"#cccccc\"><b>Email:</b></td><td> " + (rs.getString("ID2EMAIL") == null ? "" : rs.getString("ID2EMAIL")) + " </td></tr>"
                        + "</table>";

            %>
                    var markerLote<%=lineCounter%> = new google.maps.Marker({
                        position: new google.maps.LatLng(<%=rs.getDouble("ID2UTMLAT")%>, <%=rs.getDouble("ID2UTMLON")%>),
                        map: map,
                        shadow: shadow,
                        icon: flagOESA,
                        shape: shape,
                        title:"Nome: <%=(rs.getString("DESCRIPTION") == null ? "" : rs.getString("DESCRIPTION").replaceAll("'", ""))%>\nLatitude: <%=rs.getDouble("ID2UTMLAT")%>\nLongitude: <%=rs.getDouble("ID2UTMLON")%>"});
            <%


                }

            %>

                   
                    google.maps.event.addListener(markerLote<%=lineCounter%>, 'click', function() {

                        var infowindowmlote<%=lineCounter%> = new google.maps.InfoWindow();

                        infowindowmlote<%=lineCounter%>.setContent('<%=tituloLote%><hr>'+
                            ''+
                            ''+
                            '<%=especificacoesPonto%> <hr>');
                        //                            '<hr><img src="http://www.agenciat1.com.br/wp-content/uploads/2010/11/obras-norte-sul.jpg">');


                        infowindowmlote<%=lineCounter%>.open(map,markerLote<%=lineCounter%>);
                    });
                    
            <%

                }
                //System.out.println("---> " + lineCounter);
%>
    }




             
        </script>
</head>
<body onload="initialize()">
	<div id="map_canvas">
		<img id="flutuante" src="images/bannerInfo.png " />
	</div>
</body>
<div style="width: 100%">
	<img id="flutuante" src="images/bannerInfo.png " />
</div>

</html>
