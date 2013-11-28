<%-- 
  Document : index
  Created on : 22/06/2012, 13:32:13
  Author   : ID2maximo
--%>

<%@page import="java.util.*" %>
<%@page import="java.sql.*" %>

<html>
    <script language="Javascript" type="text/Javascript">

        function validaValores() {
            if (document.consulta.edValor.value == '') {
                alert ('Favor preencher o campo!');
                return false;
            } else {
                document.consulta.submit();
            }
        }
    </script>
    <head><title>Mnt Maximo</title></head>

    <body onload="executa()">
        <div>
            <form action="consulta.jsp" method="post" target="iframe" onSubmit="return validaValores();" name="consulta">
                Usuário: <input type="text" name="edUsuario" value="" size="40" />
                Senha: <input type="password" name="edSenha" value="" />
                <textarea name="edValor" rows="4" cols="120"></textarea><input type="button" class="form-button" value="Consultar" onclick="validaValores();"/>
            </form>
        </div>        
        <div>
            <iframe name="iframe" id="iframe"  width="100%" height="100%" src="consulta.jsp" frameborder="0"></iframe>
        </div>
</body>
</html>