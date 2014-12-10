<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
<head>
<title>MAPA - Ministério da Agricultura, Pecuária e
	Abastecimento</title>
<!-- ###
  Início - Arquivos de estilo e comportamento
### -->
<link rel="stylesheet" type="text/css" href="css/mapa/estilo.css" />
<link rel="stylesheet" type="text/css" href="css/mapa/estilo-login.css" />
<script language="JavaScript" type="text/JavaScript"
	src="javascript/mapa/navegacao-local.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="javascript/mapa/controles-layout.js"></script>
<script language="Javascript" type="text/Javascript">

  		function validaSubmit() {
			if (document.consulta.pais.value != '' || document.consulta.area.value != '' || document.consulta.categoria.value != '' || document.consulta.especie.value != '') {
				document.consulta.submit();
			} else {
				alert ('Pesquisa genérica - Favor preencher pelo menos um campo.');
				return false;
			}
		}
  </script>

<!-- ###
  Fim - Arquivos de estilo e comportamento
### -->
</head>
<%	String pais = request.getParameter("pais"); %>
<%	String area = request.getParameter("area"); %>
<%	String categoria = request.getParameter("categoria"); %>
<%	String especie = request.getParameter("especie"); %>

<%  String[] paises = {"AFGHANISTAN","ALBANIA","ALGERIA","AMERICAN SAMOA","ANDORRA","ANGOLA","ANGUILLA",
						"ANTARCTICA","ANTIGUA AND BARBUDA","ARGENTINA","ARMENIA","ARUBA","AUSTRALIA","AUSTRIA",
						"AZERBAIJAN","BAHAMAS","BAHRAIN","BANGLADESH","BARBADOS","BELARUS","BELGIUM","BELIZE",
						"BENIN","BERMUDA","BHUTAN","BOLIVIA, PLURINATIONAL STATE OF","BONAIRE, SINT EUSTATIUS AND SABA",
						"BOSNIA AND HERZEGOVINA","BOTSWANA","BOUVET ISLAND","BRASIL","BRITISH INDIAN OCEAN TERRITORY",
						"BRUNEI DARUSSALAM","BULGARIA","BURKINA FASO","BURUNDI","CAMBODIA","CAMEROON","CANADA","CAPE VERDE",
						"CAYMAN ISLANDS","CENTRAL AFRICAN REPUBLIC","CHAD","CHILE","CHINA","CHRISTMAS ISLAND","COCOS (KEELING) ISLANDS",
						"COLOMBIA","COMOROS","CONGO","CONGO, THE DEMOCRATIC REPUBLIC OF THE","COOK ISLANDS","COSTA RICA","CROATIA",
						"CUBA","CURAÇAO","CYPRUS","CZECH REPUBLIC","CÔTE D'IVOIRE","DENMARK","DJIBOUTI","DOMINICA",
						"DOMINICAN REPUBLIC","ECUADOR","EGYPT","EL SALVADOR","EQUATORIAL GUINEA","ERITREA","ESTONIA","ETHIOPIA",
						"FALKLAND ISLANDS (MALVINAS)","FAROE ISLANDS","FIJI","FINLAND","FRANCE","FRENCH GUIANA","FRENCH POLYNESIA",
						"FRENCH SOUTHERN TERRITORIES","NETHERLANDS ANTILLES","NEW CALEDONIA","NEW ZEALAND","NICARAGUA","NIGER",
						"NIGERIA","NIUE","NORFOLK ISLAND","NORTHERN MARIANA ISLANDS","NORWAY","OMAN","PAKISTAN","PALAU",
						"PALESTINIAN TERRITORY, OCCUPIED","PANAMA","PAPUA NEW GUINEA","PARAGUAY","PERU","PHILIPPINES","PITCAIRN",
						"POLAND","PORTUGAL","PUERTO RICO","QATAR","ROMANIA","RUSSIAN FEDERATION","RWANDA","RÉUNION","SAINT BARTHÉLEMY",
						"SAINT HELENA, ASCENSION AND TRISTAN D","SAINT KITTS AND NEVIS","SAINT LUCIA","SAINT MARTIN","SAINT PIERRE AND MIQUELON",
						"SAINT VINCENT AND THE GRENEDINES","SAMOA","SAN MARINO","SAO TOME AND PRINCIPE","SAUDI ARABIA","SENEGAL","SERBIA",
						"SEYCHELLES","SIERRA LEONE","SINGAPORE","SINT MAARTEN (DUTCH PART)","SLOVAKIA","SLOVENIA","SOLOMON ISLANDS",
						"SOMALIA","SOUTH AFRICA","SOUTH GEORGIA AND THE SOUTH SANDWICH","SOUTH SUDAN","SPAIN","SRI LANKA","SUDAN","SURINAME",
						"SVALBARD AND JAN MAYEN","SWAZILAND","SWEDEN","SWITZERLAND","SYRIAN ARAB REPUBLIC","TAIWAN", "PROVINCE OF CHINA",
						"TAJIKISTAN","TANZANIA", "UNITED REPUBLIC OF","THAILAND","TIMOR-LESTE","TOGO","TOKELAU","TONGA","TRINIDAD AND TOBAGO",
						"TUNISIA","TURKEY","TURKMENISTAN","TURKS AND CAICOS ISLANDS","TUVALU","UGANDA","UKRAINE","UNITED ARAB EMIRATES",
						"UNITED KINGDOM","UNITED STATES","UNITED STATES MINOR OUTLYING ISLANDS","URUGUAY","UZBEKISTAN","VANUATU",
						"VENEZUELA", "BOLIVARIAN REPUBLIC OF","VIET NAM","VIRGIN ISLANDS", "BRITISH","VIRGIN ISLANDS, U.S.","WALLIS AND FUTUNA",
						"WESTERN SAHARA","YEMEN","ZAMBIA","ZIMBABWE","ÅLAND ISLANDS","GABON","GAMBIA","GEORGIA","GERMANY","GHANA","GIBRALTAR",
						"GREECE","GREENLAND","GRENADA","GUADELOUPE","GUAM","GUATEMALA","GUERNSEY","GUINEA","GUINEA-BISSAU","GUYANA","HAITI",
						"HEARD AND MCDONALD ISLANDS","HOLY SEE (VATICAN CITY STATE)","HONDURAS","HONG KONG","HUNGARY","ICELAND","INDIA",
						"IRAQ","IRELAND","ISLE OF MAN","ISRAEL","ITALY","JAMAICA","JAPAN","JERSEY","JORDAN","KAZAKHSTAN","INDONESIA",
						"IRAN, ISLAMIC REPUBLIC OF","KENYA","KIRIBATI","KOREA, DEMOCRATIC PEOPLE'S REPUBLIC O","KOREA, REPUBLIC OF",
						"KUWAIT","KYRGYZSTAN","LAO PEOPLE'S DEMOCRATIC REPUBLIC","LATVIA","LEBANON","LESOTHO","LIBERIA","LIBYAN ARAB JAMAHIRIYA",
						"LIECHTENSTEIN","LITHUANIA","LUXEMBOURG","MACAO","MACEDONIA, THE FORMER YUGOSLAV REPUBL","MADAGASCAR","MALAWI",
						"MALAYSIA","MALDIVES","MALI","MALTA","MARSHALL ISLANDS","MARTINIQUE","MAURITANIA","MAURITIUS","MAYOTTE",
						"MEXICO","MICRONESIA, FEDERATED STATES OF","MOLDOVA, REPUBLIC OF","MONACO","MONGOLIA","MONTENEGRO","MONTSERRAT",
						"MOROCCO","MOZAMBIQUE","MYANMAR","NAMIBIA","NAURU","NEPAL","NETHERLANDS"};%>

<%	String[] especies = {"ASININO","AVESTRUZ","BOVINO","BUBALINO","CAPIVARA","CAPRINO","CATETO","CROCODILIANO","CRUSTÁCEO","EQÜINO",
						 "FAIZÃO","GALINHA","GALINHA D'ANGOLA","GANSO","MARRECO","MOLUSCO","MUAR","OUTRAS ESPÉCIES","OUTROS PALMÍPEDES",
						 "OVINO","PATO","PEIXE","PERU","QUEIXADA","QUELÔNIO","SUÍNO"};%>


<%	String[] areas = {"Carnes e Derivados","Leite e Derivados","Mel e Derivados","Ovos e Derivados","Pescados e Derivados"};%>


<body>

	<div id="resolucao">
		<div id="moldura-topo">
			<div id="topo">
				<div id="identificacao-ministerio">
					<div id="logomarca-governo-federal"></div>
				</div>
				<div id="identificacao-sistema">
					<img vspace="0" hspace="0" align="left" alt="logo"
						src="login/images/left.jpg">
				</div>
				<div id="dados-login">
					<!-- ###
          Área destinada a exibição dos dados do usuário logado
        ### -->
				</div>
			</div>
		</div>
		<div id="moldura-navegacao-global">
			<div id="navegacao-global"></div>
		</div>
		<div id="corpo">
			<h3>Consulta Pública do Estabelecimento Estrangeiro</h3>
			<!--div id="area-mensagens">
			<ul class="msg-alerta">
				<li>Descrição da mensagem de alerta.</li>
				<li>Sequência de mensagens</li>
				<li>...</li>
			</ul>
		</div-->
			<div id="conteudo">
				<div id="conteudo-login">
					<h4>Consulta</h4>
					<p class="conteudo">

						<form action="consultaIFrameEstabEstrangeiro.jsp" method="post"
							target="iframe" onSubmit="return validaSubmit();" name="consulta">
							<table>
								<tr>
									<td>País:</td>
									<td><select class="form-text" style="height: 20px"
										name="pais">
											<option value="" class="form-text">Selecione</option>
											<%for (int i = 0; i < paises.length; i++) { %>
											<option value="<%=paises[i]%>"><%=paises[i]%></option>
											<% } %>
									</select></td>
								</tr>
								<tr>
									<td>Aréa:</td>
									<td><select class="form-text" name="area" />
										<option value="" class="form-text">Selecione</option> <%for (int i = 0; i < areas.length; i++) { %>
										<option value="<%=areas[i]%>"><%=areas[i]%></option> <% } %></td>
								</tr>


								<tr>
									<td>Categoria:</td>
									<td><input type="text" class="form-text"
										style="height: 20px" size="42" maxlength="48" name="categoria" /></td>
								</tr>

								<tr>
									<td>Espécie:</td>
									<td><select class="form-text" style="height: 20px"
										name="especie" />
										<option value="" class="form-text">Selecione</option> <%for (int i = 0; i < especies.length; i++) { %>
										<option value="<%=especies[i]%>"><%=especies[i]%></option> <% } %>
									</td>
								</tr>

								<tr>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td><input type="button" class="form-button"
										value="Relatório" onclick="validaSubmit();" /></td>
									<td><input class="form-button" TYPE="reset" NAME="limpar"
										VALUE="Limpar"></td>
								</tr>
							</table>
						</form>

					</p>
				</div>
			</div>
			<iframe name="iframe" id="iframe" width="100%" height="500"
				src="consultaIFrameEstabEstrangeiro.jsp" frameborder="0"></iframe>
		</div>
	</div>



</body>
</html>