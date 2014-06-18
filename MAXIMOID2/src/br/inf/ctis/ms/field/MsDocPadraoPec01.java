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
	    val.append("<strong>MINISTÉRIO DA SAÚDE</strong><br/>");
	    val.append("<strong>SECRETARIA EXECUTIVA</strong><br />");
	    val.append("<strong>DEPARTAMENTO DE LOGÍSTICA EM SAÚDE</strong><br />");
	    val.append("<strong>COORDENAÇÃO-GERAL DE ANÁLISE DAS CONTRATAÇÕES DE INSUMOS ESTRATÉGICOS PARA SAÚDE – CGIES</strong><br />");
	    val.append("Esplanada dos Ministérios. Bloco G, Anexo A, 4º Andar – Sala 471ª - CEP: 70.058-900 –    Brasília/DF<br />");
	    val.append("Telefone: (61) 3315.3384   Fax: (61) 3315.3958<strong> </strong></p></td>");
	    val.append("</tr>");
	    val.append("</table>");
	    val.append("<table>");
	    val.append("<tr>");
	    val.append("<td width=\"655\">");	    
	    val.append("<p><strong>Ofício nº. 19/2014/CGIES/DLOG/SE/MS</strong></p>");
	    val.append("<p align=\"right\">Brasília,      de fevereiro  de 2014.</p>");
	    val.append("<p>&nbsp;</p>");
	    val.append("<p>À Senhora<br/>");
	    val.append("Maria  Fernanda<br/>");
	    val.append("Representante da empresa Janssen-Cilag  Farmacêutica Ltda<br/>");
	    val.append("Rodovia Presidente Dutra, s/n - Km 154  - Jd. das Indústrias<br/>");
	    val.append("CEP: 12.20-909 - São José dos  Campos/SP<br/>");
	    val.append("Tel.: (62) 8152 2943  Fax: (62) 3945 9007  / Cel: (62) 8117 7777 <br/>");
	    val.append("e-mail: <a href=\"mailto:ms@saude.gov.br\">ms@saude.gov.br</a>. </p>");
	    val.append("<p><strong>Assunto</strong>: Solicitação de proposta comercial dos medicamentos <strong>TELAPREVIR 375MG, INCIVO</strong><strong>® </strong></p>");
	    val.append("<p>Prezados Senhor(a), </p>");
	    val.append("<p>Solicitamos que nos apresente proposta comercial, <u>via meio  eletrônico........</u>,");
	    val.append("<p>Atenciosamente,</p>");
	    val.append("<p align=\"center\"><strong>&nbsp;</strong></p>");
	    val.append("<p align=\"center\"><strong>XXXXXXXXXXXXXXXXXX</strong><br />");
	    val.append("Coordenadora-Geral de Análise das Contratações<br/>");
	    val.append("de Insumos Estratégicos para Saúde </p>");
	    val.append("CGIES/DLOG/SE/MS");
	    val.append("</td>");
	    val.append("</tr>");
	    val.append("</table>");
	    val.append("</body>");
	    
	    getMboValue().setValue(val.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
	    
	   
	  }
}
