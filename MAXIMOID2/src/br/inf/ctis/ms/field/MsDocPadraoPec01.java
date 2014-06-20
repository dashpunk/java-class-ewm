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

	    MboRemote inex = getMboValue().getMbo().getMboSet("PO").getMbo(0);
	    MboRemote purch = getMboValue().getMbo().getMboSet("PURCHVIEW").getMbo();
	    StringBuilder val = new StringBuilder(); 
	    System.out.println(">>>>>>>>>>> Dentro do metodo initValue da classe MsDocPadraoPec01");

	    // **
	    val.append("<body>");
	    val.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\">");
	    val.append("<tr>");
	    val.append("<td width=\"655\" valign=\"top\">");
	    val.append("<p align=\"center\"><strong><img src=\"../webclient/imgpec/brasao.png\" alt=\"cabMS\" width=\"66\" height=\"76\" /></strong> <br/>");
	    val.append("<strong>MINIST�RIO DA SA�DE</strong><br/>");
	    val.append("<strong>SECRETARIA EXECUTIVA</strong><br />");
	    val.append("<strong>DEPARTAMENTO DE LOG�STICA EM SA�DE</strong><br />");
	    val.append("Esplanada dos Minist�rios. Bloco G, Anexo A, 4� Andar � Sala 471� - CEP: 70.058-900 �    Bras�lia/DF<br />");
	    val.append("Telefone: (61) 3315-2110�� Fax: (61) 3225.0206<strong> </strong></p></td>");
	    val.append("</tr>");
	    val.append("</table>");
	    val.append("<table>");
	    val.append("<tr>");
	    val.append("<td width=\"655\">");	    
	    val.append("<p>Of�cio n.�"+inex.getInt("PONUM")+"/2014/DCIES/CGLIS/DLOG/SE/MS</p>");
	    val.append("<p align=\"right\">Bras�lia,�de junho de 2014.</p>");
	    val.append("<p>&nbsp;</p>");
	    val.append("<p>Ao Senhor<br/>");
	    val.append(inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("MS_VW02PER").getMbo(0).getString("DISPLAYNAME") + "<br/>");
	    val.append(inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getString("MSALDSCFORNECEDORINEX")+"<br/>");
	    val.append(inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("ADDRESS1")+"<br/>");
	    System.out.println(">>>>>>>>>>> Apresentando o endereco da empresa: "+inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("ADDRESS1"));
	    val.append("CEP: "+ inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("ADDRESS4") +"<br/>");
	    //val.append("Telefone/Fax: "+inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("PHONE")+"/"+inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("FAX")+"<br/></p>");
	    //val.append("<p>Assunto: Assinatura do Contrato n.�"+ purch.getInt("CONTRACTNUM") +"/"+purch.getDate("STARTDATE")+"e presta��o da garantia</p>");
	    val.append("<p>Ref.: Processo Eletr�nico de Compras: n.� "+inex.getString("MS_SIPARNUM")+"</p>");
	    val.append("<p>Senhor Representante </p>");
	    //val.append("<p>Comunicamos a Vossa Senhoria que essa empresa fica convocada, na forma prevista no Art. XX da Lei n.� 8.666/93, no prazo de XX (XXXXX) dias �teis, para assinatura do Contrato Administrativo n.�"+ purch.getInt("CONTRACTNUM") +"/"+purch.getDate("STARTDATE")+", cujo o objetivo � a aquisi��o de"+inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getString("MSNUNUMQTDCONTRATADAINEX")+" "+inex.getMboSet("POLINE").getMbo(0).getString("DESCRIPTION")+" "+inex.getMboSet("POLINE").getMbo(0).getMboSet("ID2RELMEASUREUNIT").getString("DESCRIPTION")+", sob pena de aplica��o das penalidades previstas em lei. </p>");
	    //val.append("<p>Informamos que essa empresa dever� apresentar, no prazo de 10 (dias) dias �teis. Contados da data de publica��o do extrato do Contrato no Di�rio Oficial da Uni�o, comprovante de presta��o de garantia, no valor de R$ "+purch.getFloat("MSNUNUMVALORGLOBAL") * 0.5+", correspondente a XX (XXXXXX) do valor do Contrato, conforme disposto no � 1�, artigo 56, da lei n.� 8666/93. A referida garantia deve ser apresentada com <u>vig�ncia vinculada ao prazo de execu��o do Contrato</u>.</p>");
	    val.append("<p>Atenciosamente,</p>");
	    val.append("<p align=\"center\"><strong>&nbsp;</strong></p>");
	    val.append("<p align=\"center\"><strong>XXXXXXXXXXXXXXXXXX</strong><br />");
	    val.append("<p>Chefe da Divis�o de Contratos do Insumos Estrat�gicos para Sa�de</p>");
	    val.append("<p align=\"center\"><strong>&nbsp;</strong></p>");
	    val.append("<p align=\"center\"><strong>XXXXXXXXXXXXXXXXXX</strong><br />");
	    val.append("<p>Coordenadora-Geral de Licita��es e Contratos de Insumos Estrat�gicos para Sa�de</p>");
	    val.append("</td>");
	    val.append("</tr>");
	    val.append("</table>");
	    val.append("</body>");
	    
	    getMboValue().setValue(val.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
	    
	   
	  }
}
