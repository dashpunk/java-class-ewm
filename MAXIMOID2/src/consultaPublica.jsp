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
			if (document.consulta.codigoBarras.value == '') {
				alert ('Informe um código de barras de e-GTA');
				return false;
			} else {
				document.consulta.submit();
			}
		}
  </script>

<!-- ###
  Fim - Arquivos de estilo e comportamento
### -->
</head>
<%	String codigoBarra = request.getParameter("codigoBarras"); %>
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
			<h3>Consulta Pública de GTA</h3>
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
						<form action="consultaIFrame.jsp" method="post" target="iframe"
							onSubmit="return validaSubmit();" name="consulta">
							Código de Barras:
							<input type="text" class="form-text" style="height: 20px"
								size="55" maxlength="48" name="codigoBarras" />
							<input type="button" class="form-button" value="Enviar"
								onclick="validaSubmit();" />
						</form>
					</p>
				</div>
			</div>
			<iframe name="iframe" id="iframe" width="100%" height="500"
				src="consultaIFrame.jsp" frameborder="0"></iframe>
		</div>
	</div>



</body>
</html>