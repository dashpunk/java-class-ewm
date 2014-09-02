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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import br.inf.ctis.common.util.Extenso;
import br.inf.id2.common.util.Data;

public class MsDocPadraoPec01 extends MboValueAdapter {
	
		
	public MsDocPadraoPec01(MboValue mbv) throws MXException
	  {
	    super(mbv);
	    System.out.println(">>>>>>>>>>> Dentro da classe MsDocPadraoPec01, versao 03");
	  }
	
	public void initValue() throws MXException, RemoteException {
		super.initValue();
		if(getMboValue().getMbo().isNew()){
			define();
		}
	}

	public void define() throws MXException, RemoteException
	  {
	    
		MboRemote po = getMboValue().getMbo().getMboSet("PO").getMbo(0);
		
		if(po.getString("STATUSPEC").equals("Processo de compra iniciado")){
			System.out.println(">>>>>>>>>>> Dentro do if para status: Processo de compra iniciado");
			//oficio();
			portaria();
		}
		if (po.getString("STATUSPEC").equals("004 - Inserir Pesquisa de Pre�o")){
			oficio();
		}else{
		
			portaria();
		}	    
	   
	  }
	
	public void oficio() throws MXException, RemoteException{
		
		MboRemote inex = getMboValue().getMbo().getMboSet("PO").getMbo(0);
	    MboRemote purch = getMboValue().getMbo().getMboSet("PURCHVIEW").getMbo(0);
	    
	    System.out.println(">>>>>>>>>>> Inex e purch carregado respectivamente: "+inex+purch);
	    
	    // Buscando apenas o ano na PO para o numero do Oficio******************
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(inex.getDate("STATUSDATE"));
	    int AnoOf = calendar.get(Calendar.YEAR);
	    
	    System.out.println(">>>>>>>>>>> Ano do sistema carregado:" + AnoOf);
	    
	    // Gerando a Data para o Documento******************
	    Calendar data = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");
		
		System.out.println(">>>>>>>>>>> Data para o Documento"+ sdf);
		
		// Por extenso
		Double total = 0d;
		Extenso extenso = new Extenso();
		total= purch.getFloat("MSNUNUMVALORGLOBAL") * 0.5;		
		extenso.setNumber(total);
		
		System.out.println(">>>>>>>>>>> Por extenso de MSNUNUMVALORGLOBAL preenchido: "+ extenso.toString());
		
	    
	    StringBuilder val = new StringBuilder(); 
	    System.out.println(">>>>>>>>>>> Carregando o Html na variavel: val");
	    
	    val.append("<body>");
	    val.append("<table>");
	    val.append("<tr>");
	    val.append("<td width=\"900\" valign=\"top\">");
	    val.append("<p align=\"center\"><strong><img src=\"../webclient/imgpec/brasao.png\" alt=\"cabMS\" width=\"66\" height=\"76\" /></strong> <br/>");
	    val.append("MINIST�RIO DA SA�DE<br/>");
	    val.append("SECRETARIA EXECUTIVA<br />");
	    val.append("DEPARTAMENTO DE LOG�STICA EM SA�DE<br />");
	    val.append("Esplanada dos Minist�rios. Bloco G, Anexo A, 4� Andar � Sala 471� - CEP: 70.058-900 �    Bras�lia/DF<br />");
	    val.append("Telefone: (61) 3315-2110�� Fax: (61) 3225.0206<strong> </strong></p></td>");
	    val.append("</tr>");
	    val.append("</table>");
	    val.append("<table>");
	    val.append("<tr>");
	    val.append("<td width=\"900\">");
	    System.out.println(">>>>>>>>>>> Antes do numero do Oficio ");
	    val.append("<p>Of�cio n.� "+inex.getInt("PONUM")+"/"+AnoOf+"/DCIES/CGLIS/DLOG/SE/MS</p>");
	    System.out.println(">>>>>>>>>>> Antes da data do sistema para o documento ");
	    val.append("<p align=\"right\">Bras�lia/DF, "+ sdf.format(data.getTime())+".</p>");
	    val.append("<p>&nbsp;</p>");
	    val.append("<p>Ao Senhor<br/>");
	    val.append(inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("MS_VW02PER").getMbo(0).getString("DISPLAYNAME") + "<br/>");
	    val.append(inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getString("MSALDSCFORNECEDORINEX")+"<br/>");
	    val.append(inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("ADDRESS1")+"<br/>");
	    val.append("CEP: "+ inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("ADDRESS4") +"<br/>");
	    val.append("Telefone/Fax: "+inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("PHONE")+"/"+inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("FAX")+"<br/></p>");
	    val.append("<p>Assunto: Assinatura do Contrato n.�"+ purch.getString("CONTRACTNUM") +" e presta��o da garantia</p>");
	    val.append("<p>Ref.: Processo Eletr�nico de Compras: n.� "+inex.getString("MS_SIPARNUM")+"</p>");
	    val.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Senhor Representante </p>");
	    val.append("<p style=\"text-align: justify;\">1.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Comunicamos a Vossa Senhoria que essa empresa fica convocada, na forma prevista no Art. XX da Lei n.� 8.666/93, no prazo de XX (XXXXX) dias �teis, para assinatura do Contrato Administrativo n.�"+ purch.getString("CONTRACTNUM") +", cujo o objetivo � a aquisi��o de,"+inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getFloat("MSNUNUMQTDCONTRATADAINEX")+" "+inex.getMboSet("POLINE").getMbo(0).getString("DESCRIPTION")+" "+inex.getMboSet("POLINE").getMbo(0).getMboSet("ID2RELMEASUREUNIT").getMbo(0).getString("DESCRIPTION")+" sob pena de aplica��o das penalidades previstas em lei. </p>");
	    val.append("<p style=\"text-align: justify;\">2.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Informamos que essa empresa dever� apresentar, no prazo de 10 (dias) dias �teis. Contados da data de publica��o do extrato do Contrato no Di�rio Oficial da Uni�o, comprovante de presta��o de garantia, no valor de R$ "+purch.getFloat("MSNUNUMVALORGLOBAL") * 0.5+" (" + extenso.toString() + "), correspondente a XX (XXXXXX) do valor do Contrato, conforme disposto no � 1�, artigo 56, da lei n.� 8666/93. A referida garantia deve ser apresentada com <u>vig�ncia vinculada ao prazo de execu��o do Contrato</u>.</p>");
	    //A LINHA ABAIXO SERVE PARA FAZER MARCA��O DE TEXTO
	    //val.append("<p style=\"text-align: justify;\"><span style=\"background-color: #ffff00;\">"+inex.getMboSet("MSTBDOC").getMbo(0).getMboSet("MSTBCLAUPAR").getMbo(0).getMboSet("MSTBCONTE").getMbo(0).getString("DESCRIPTION")+"</span></p>");
	    val.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Atenciosamente,</p>");
	    val.append("<p align=\"center\"><strong>&nbsp;</strong></p>");
	    val.append("<p align=\"center\">XXXXXXXXXXXXXXXXXX<br />");
	    val.append("<p align=\"center\">Chefe da Divis�o de Contratos do Insumos Estrat�gicos para Sa�de</p>");
	    val.append("<p align=\"center\"><strong>&nbsp;</strong></p>");
	    val.append("<p align=\"center\">XXXXXXXXXXXXXXXXXX<br />");
	    val.append("<p align=\"center\">Coordenadora-Geral de Licita��es e Contratos de Insumos Estrat�gicos para Sa�de</p>");
	    val.append("</td>");
	    val.append("</tr>");
	    val.append("</table>");
	    val.append("</body>");
	    
	    getMboValue().setValue(val.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
	}
	
	public void despacho() throws MXException, RemoteException{
		
		 StringBuilder val = new StringBuilder(); 
		    System.out.println(">>>>>>>>>>> Dentro do metodo despacho da classe MsDocPadraoPec01");
		    // **
		    val.append("<body>");
		    val.append("<table>");
		    val.append("<tr>");
		    val.append("<td width=\"900\" valign=\"top\">");
		    val.append("</br></br>");
		    val.append("<p align=\"center\"><strong>Despacho referente ao PEC n�mero do fluxo.... e SIPAR n.� .....<strong><br/>");
		    val.append("</tr>");
		    val.append("</table>");
		    val.append("<table>");
		    val.append("<tr>");
		    val.append("<td width=\"900\">");	    
		    val.append("<p><strong>Assunto:</strong> Recomenda��es � �rea demandante � Parecer CONJUR/MS</p>");
		    val.append("<p><strong>� Coordena��o-Geral de Assist�ncia Farmac�utica e Medicamentos Estrat�gicos � CGAFME</strong></p>");
		    val.append("<p style=\"text-align: justify;\">Trata-se de processo eletr�nico de compras para aquisi��o do medicamento Tipranavir (TPV) 250mg, em atendimento � solicita��o do Departamento de Vigil�ncia, Preven��o e Controle das DST, AIDS e Hepatites Virais � DDAHV/SVS.</p>");
		    val.append("<p style=\"text-align: justify;\">	2.		Em cumprimento ao disposto no art. 38, par�grafo �nico, da Lei n�. 8.666/93, os autos foram encaminhados � d. Consultoria Jur�dica para an�lise da minuta de contrato e dos procedimentos administrativos de aquisi��o.</p>");
		    val.append("<p style=\"text-align: justify;\">	3.		Em resposta, a CONJUR/MS proferiu o Parecer n� 1774/2014/CODELICI/ AGU/CONJUR-MS/CGU/RAB, no qual se manifestou favor�vel � celebra��o do contrato, desde que observadas �s recomenda��es exaradas no bojo daquele opinativo (T29 � anexada 15/05/2014), ao que foi aprovado pela Coordenadora Geral da CODELICI/CONJUR (T29.3 � anexada 09/06/2014)</p>");
		    val.append("<p style=\"text-align: justify;\">	4.		Ante o exposto, encaminho o processo para conhecimento do inteiro teor do parecer encimado e manifesta��o quanto �s recomenda��es de compet�ncia dessa �rea, em especial quanto aos par�grafos 25 a 27, 55, 78 e 88 subitem 1, 3 e 4 do referido parecer.</p>");
		    val.append("<p>Atenciosamente,</p></br>");
		    val.append("<p align=\"right\">Bras�lia, 27 de junho de 2014.</p>");
		    val.append("<p align=\"center\"><strong>&nbsp;</strong></p>");
		    val.append("<p align=\"center\"><strong>XXXXXXXXXXXXXXXXXX</strong></br>");
		    val.append("<p align=\"center\">Coordenador Geral de An�lise das Contrata��es de");
		    val.append("<p align=\"center\">Insumos Estrat�gicos para Sa�de -Substituto");
		    val.append("<p align=\"center\">CGIES/DLOG/SE/MS");
		    val.append("</td>");
		    val.append("</tr>");
		    val.append("</table>");
		    val.append("</body>");
		    
		    getMboValue().setValue(val.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
	}
	public void portaria() throws MXException, RemoteException{
		
			MboRemote po = getMboValue().getMbo().getMboSet("PO").getMbo(0);
			MboRemote inex = getMboValue().getMbo().getMboSet("PO").getMbo(0).getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0);
			MboRemote purch = getMboValue().getMbo().getMboSet("PURCHVIEW").getMbo(0);
			
			// Data para Portaria
			 Calendar dataport = Calendar.getInstance();
			 SimpleDateFormat sdfport = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");
		 
		 
		
		    StringBuilder valport = new StringBuilder(); 
		    System.out.println(">>>>>>>>>>> Dentro do metodo portaria da classe MsDocPadraoPec02");
		    // **
		    valport.append("<body>");
		    System.out.println(">>>>>>>>>>> <body> aberto");
		    valport.append("<table>");
		    valport.append("<tr>");
		    valport.append("<td width=\"900\" valign=\"top\">");
		    valport.append("</br></br>");
		    valport.append("<p align=\"center\">PORTARIA DLOG DE "+sdfport.format(dataport.getTime())+" <br/>");
		    valport.append("</tr>");
		    valport.append("</table>");
		    valport.append("<table>");
		    valport.append("<tr>");
		    valport.append("<td width=\"900\">");
		    System.out.println(">>>>>>>>>>> Cabecalho: O Diretor do Departamento de Log�stica em Sa�de, no uso de suas atribui��es, e tendo em vista o disposto no art. 67 da Lei n.� 8.666/1993, resolve:");
		    valport.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O Diretor do Departamento de Log�stica em Sa�de, no uso de suas atribui��es, e tendo em vista o disposto no art. 67 da Lei n.� 8.666/1993, resolve:</p>");
		    valport.append("<p style=\"text-align: justify;\">Art 1.� -&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Designar, com base na confirma��o da Tarefa 94 do PEC - Termo de Refer�ncia n.�"+po.getInt("PONUM")+", o servidor "+po.getMboSet("MS_RLVW01PER").getMbo(0).getString("NOME")+", matricula SIAPE n.�"+po.getMboSet("MS_RLVW01PER").getMbo(0).getString("MATRICULA")+", e como substituta eventual a servidora "+po.getMboSet("MS_RLVW02PER").getMbo(0).getString("NOME")+", matr�cula SIAPE n.� "+po.getMboSet("MS_RLVW02PER").getMbo(0).getString("MATRICULA")+", como representantes do Minist�rio da Sa�de para acompanhar e fiscalizar a execu��o do Contrato n.� "+ purch.getString("CONTRACTNUM") +", firmado com a empresa "+inex.getString("MSALDSCFORNECEDORINEX")+", que tem por objeto a aquisi��o do medicamento "+inex.getFloat("MSNUNUMQTDCONTRATADAINEX")+" "+po.getMboSet("POLINE").getMbo(0).getString("DESCRIPTION")+" "+po.getMboSet("POLINE").getMbo(0).getMboSet("ID2RELMEASUREUNIT").getMbo(0).getString("DESCRIPTION")+", resultante da Inexigibilidade de Licita��o n.� "+ purch.getString("CONTRACTNUM") +", conforme Processo Eletr�nico de Compras n.� "+po.getInt("MS_SIPARNUM")+" - Termo de refer�ncia "+po.getInt("PONUM")+".</p>");
		    valport.append("<p style=\"text-align: justify;\">Art 2.� -&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;As atribui��es conferidas e exercidas pelos servidores est�o regulamentadas pela Portaria GM n.� 78/2006, de 16 de janeiro de 2006, publicada no BSE n.� 04 de 23 de janeiro de 2006, a qual disp�e sobre os procedimentos a serem adotados no acompanhamento e fiscaliza��o de contratos e na Circular MS/SE/GAB n.� 40, de julho de 2010, registro SIPAR n.� 25000.127193/2010-56, a qual disp�e sobre a aplica��o de penalidades a contratados.</p>");
		    valport.append("<p align=\"center\">&nbsp;</p>");
		    valport.append("<p align=\"center\">XXXXXXXXXXXXXXXXXX</br>");
		    valport.append("<p align=\"center\">Diretor do Departamento de Log�stica em Sa�de/SE/MS");
		    valport.append("</td>");
		    valport.append("</tr>");
		    valport.append("</table>");
		    System.out.println(">>>>>>>>>>> <body> fechando");
		    valport.append("</body>");
		    
		    getMboValue().setValue(valport.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
	}
	public void minutaEmpNac() throws MXException, RemoteException{
		
		
	}
}
