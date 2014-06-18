/**
 * 
 */
package br.inf.ctis.ms.field;

/**
 * @author andrel.almeida
 *
 */
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Properties;
import java.util.Date;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

public class MsDocPadraoPec01 extends MboValueAdapter {
	
	public MsDocPadraoPec01(MboValue mbv) throws MXException
	  {
	    super(mbv);
	  }

	public void initValue() throws MXException, RemoteException
	  {
	    super.initValue();

	    StringBuilder val = new StringBuilder(); 

	    // **
	    val.append("<body>");
	    val.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\">");
	    val.append("<tr>");
	    val.append("<td width=\"655\" valign=\"top\">");
	    val.append("<p align=\"center\"><strong><img src=\"../webclient/imgpec/brasao.png\" alt=\"cabMS\" width=\"66\" height=\"76\" /></strong> <br/>");
	    val.append("<strong>MINIST�RIO DA SA�DE</strong><br/>");
	    val.append("<strong>SECRETARIA EXECUTIVA</strong><br />");
	    val.append("<strong>DEPARTAMENTO DE LOG�STICA EM SA�DE</strong><br />");
	    val.append("<strong>COORDENA��O-GERAL DE AN�LISE DAS CONTRATA��ES DE INSUMOS ESTRAT�GICOS PARA SA�DE � CGIES</strong><br />");
	    val.append("Esplanada dos Minist�rios. Bloco G, Anexo A, 4� Andar � Sala 471� - CEP: 70.058-900 �    Bras�lia/DF<br />");
	    val.append("Telefone: (61) 3315.3384�� Fax: (61) 3315.3958<strong> </strong></p></td>");
	    val.append("</tr>");
	    val.append("</table>");
	    val.append("<table>");
	    val.append("<tr>");
	    val.append("<td width=\"655\">");	    
	    val.append("<p><strong>Of�cio n�. 19/2014/CGIES/DLOG/SE/MS</strong></p>");
	    val.append("<p align=\"right\">Bras�lia, �����de fevereiro  de 2014.</p>");
	    val.append("<p>&nbsp;</p>");
	    val.append("<p>� Senhora<br/>");
	    val.append("Maria  Fernanda<br/>");
	    val.append("Representante da empresa Janssen-Cilag  Farmac�utica Ltda<br/>");
	    val.append("Rodovia Presidente Dutra, s/n - Km 154  - Jd. das Ind�strias<br/>");
	    val.append("CEP: 12.20-909 - S�o Jos� dos  Campos/SP<br/>");
	    val.append("Tel.: (62) 8152 2943� Fax: (62) 3945 9007� / Cel: (62) 8117 7777 <br/>");
	    val.append("e-mail: <a href=\"mailto:ms@saude.gov.br\">ms@saude.gov.br</a>. </p>");
	    val.append("<p><strong>Assunto</strong>: Solicita��o de proposta comercial dos medicamentos <strong>TELAPREVIR 375MG, INCIVO</strong><strong>� </strong></p>");
	    val.append("<p>Prezados Senhor(a), </p>");
	    val.append("<p>Solicitamos que nos apresente proposta comercial, <u>via meio  eletr�nico........</u>,");
	    val.append("<p>Atenciosamente,</p>");
	    val.append("<p align=\"center\"><strong>&nbsp;</strong></p>");
	    val.append("<p align=\"center\"><strong>XXXXXXXXXXXXXXXXXX</strong><br />");
	    val.append("Coordenadora-Geral de An�lise das Contrata��es<br/>");
	    val.append("de Insumos Estrat�gicos para Sa�de </p>");
	    val.append("CGIES/DLOG/SE/MS");
	    val.append("</td>");
	    val.append("</tr>");
	    val.append("</table>");
	    val.append("</body>");
	    
	    getMboValue().setValue(val.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
	    
	   
	  }
}
