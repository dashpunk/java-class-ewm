<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pt-br" lang="pt-br">
<body>
	<%	String pais = request.getParameter("pais"); %>
	<%	String area = request.getParameter("area"); %>
	<%	String categoria = request.getParameter("categoria"); %>
	<%	String especie = request.getParameter("especie"); %>

	<%	
	if (request.getParameter("pais") != null) {
		pais =(request.getParameter("pais").equals("") ? "a.id2pais <> \'XXXX\'" : "a.id2pais = \'"+request.getParameter("pais")+"\'"); 
	}
	%>
	<%	
	if (request.getParameter("area") != null) {
		area = (request.getParameter("area").equals("") ? "b.id2nome <> \'XXXX\'" : "b.id2nome = \'"+request.getParameter("area")+"\'");
}	 %>
	
	
	<%if (request.getParameter("categoria") != null) {
		categoria = (request.getParameter("categoria").equals("") ? "b.id2cat <> \'XXXX\'" : "b.id2cat = \'"+request.getParameter("categoria")+"\'"); 
	 }%>
	
	
	<%if (request.getParameter("especie") != null) {
		especie = (request.getParameter("especie").equals("") ? "b.id2esp <> \'XXXX\'" : "b.id2esp = \'"+request.getParameter("especie")+"\'"); 
	}%>
	
	
		<form name="jasperjasperreportForm" id="jasperjasperreportForm" action="jasperReportAction">
			<input type="hidden" name="relatorio" value="prod_estab_estrang.jasper" >
			<input type="hidden" name="PAIS" value="<%=pais%>" >
			<input type="hidden" name="AREA" value="<%=area%>" >
			<input type="hidden" name="CATEGORIA" value="<%=categoria%>" >
			<input type="hidden" name="ESPECIE" value="<%=especie%>" >
<!--- 			<input type="hidden" name="contentType" value="text/html; charset=UTF-8"> --->
		</form>

<script language="JavaScript" type="text/JavaScript">
	<%	
		if (pais != null || area != null || categoria != null || especie != null) {
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