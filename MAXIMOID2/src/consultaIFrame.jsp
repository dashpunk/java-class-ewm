<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
<body>
	<%	String codigoBarra = request.getParameter("codigoBarras"); %>

		<form name="jasperjasperreportForm" id="jasperjasperreportForm" action="jasperReportAction">
			<input type="hidden" name="relatorio" value="e-GTA-CodeBar.jasper" >
			<input type="hidden" name="CODBARRA" value="<%=codigoBarra %>" >
		</form>

<script language="JavaScript" type="text/JavaScript">
	<%	
		if (codigoBarra != null) {
	%>
			var jasperreportForm = document.forms[(document.forms.length - 1)];
			jasperreportForm.action = "/sispga/servlet/JasperRPT";
			jasperreportForm.method = "post";
			jasperreportForm.target = "iframe";
			jasperreportForm.submit();
			
	<%
		}
	%>

</script>

</body>
</html>