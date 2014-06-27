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
import br.inf.id2.common.util.Data;

public class MsDocPadraoPec01 extends MboValueAdapter {
	
		
	public MsDocPadraoPec01(MboValue mbv) throws MXException
	  {
	    super(mbv);
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
			oficio();
		}else{
		
			despacho();
		}	    
	   
	  }
	
	public void oficio() throws MXException, RemoteException{
		
		MboRemote inex = getMboValue().getMbo().getMboSet("PO").getMbo(0);
	    MboRemote purch = getMboValue().getMbo().getMboSet("PURCHVIEW").getMbo(0);
	    
	    // Buscando apenas o ano na PO para o numero do Oficio******************
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(inex.getDate("STATUSDATE"));
	    int AnoOf = calendar.get(Calendar.YEAR);
	    
	    // Gerando a Data para o Documento******************
	    Calendar DtDoc = Calendar.getInstance();
	    DtDoc.setTime(getMboValue().getMbo().getDate("MSDATA"));
	    int dia = DtDoc.get(Calendar.DAY_OF_YEAR);
	    int mes = DtDoc.get(Calendar.MONTH);        
	    int ano = DtDoc.get(Calendar.YEAR);
	    
	    StringBuilder val = new StringBuilder(); 
	    System.out.println(">>>>>>>>>>> Dentro do metodo initValue da classe MsDocPadraoPec01");
	    System.out.println(">>>>>>>>>>> Apresentando o Ano do Oficio: "+AnoOf);
	    // **
	    val.append("<body>");
	    val.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\">");
	    val.append("<tr>");
	    val.append("<td width=\"900\" valign=\"top\">");
	    val.append("<p align=\"center\"><strong><img src=\"../webclient/imgpec/brasao.png\" alt=\"cabMS\" width=\"66\" height=\"76\" /></strong> <br/>");
	    val.append("<strong>MINISTÉRIO DA SAÚDE</strong><br/>");
	    val.append("<strong>SECRETARIA EXECUTIVA</strong><br />");
	    val.append("<strong>DEPARTAMENTO DE LOGÍSTICA EM SAÚDE</strong><br />");
	    val.append("Esplanada dos Ministérios. Bloco G, Anexo A, 4º Andar – Sala 471ª - CEP: 70.058-900 –    Brasília/DF<br />");
	    val.append("Telefone: (61) 3315-2110   Fax: (61) 3225.0206<strong> </strong></p></td>");
	    val.append("</tr>");
	    val.append("</table>");
	    val.append("<table>");
	    val.append("<tr>");
	    val.append("<td width=\"900\">");	    
	    val.append("<p>Ofício n.° "+inex.getInt("PONUM")+"/"+AnoOf+"/DCIES/CGLIS/DLOG/SE/MS</p>");
	    val.append("<p align=\"right\">Brasília, 27 de junho "+ano+".</p>");
	    val.append("<p>&nbsp;</p>");
	    val.append("<p>Ao Senhor<br/>");
	    //val.append(inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("MS_VW02PER").getMbo(0).getString("DISPLAYNAME") + "<br/>");
	    //val.append(inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getString("MSALDSCFORNECEDORINEX")+"<br/>");
	    //val.append(inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("ADDRESS1")+"<br/>");
	    val.append("CEP: "+ inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("ADDRESS4") +"<br/>");
	    val.append("Telefone/Fax: "+inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("PHONE")+"/"+inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getMboSet("COMPANIES").getMbo(0).getString("FAX")+"<br/></p>");
	    val.append("<p>Assunto: Assinatura do Contrato n.°"+ purch.getString("CONTRACTNUM") +"/"+ano+" e prestação da garantia</p>");
	    val.append("<p>Ref.: Processo Eletrônico de Compras: n.° "+inex.getString("MS_SIPARNUM")+"</p>");
	    val.append("<p>Senhor Representante </p>");
	    val.append("<p style=\"text-align: justify;\">	Comunicamos a Vossa Senhoria que essa empresa fica convocada, na forma prevista no Art. XX da Lei n.° 8.666/93, no prazo de XX (XXXXX) dias úteis, para assinatura do Contrato Administrativo n.°"+ purch.getString("CONTRACTNUM") +"/"+ano+", cujo o objetivo é a aquisição de,"+inex.getMboSet("POLINE").getMbo(0).getMboSet("MSTBITENSINEXIGIBILIDADE").getMbo(0).getFloat("MSNUNUMQTDCONTRATADAINEX")+" "+inex.getMboSet("POLINE").getMbo(0).getString("DESCRIPTION")+" "+inex.getMboSet("POLINE").getMbo(0).getMboSet("ID2RELMEASUREUNIT").getMbo(0).getString("DESCRIPTION")+" sob pena de aplicação das penalidades previstas em lei. </p>");
	    val.append("<p style=\"text-align: justify;\">	Informamos que essa empresa deverá apresentar, no prazo de 10 (dias) dias úteis. Contados da data de publicação do extrato do Contrato no Diário Oficial da União, comprovante de prestação de garantia, no valor de R$ "+purch.getFloat("MSNUNUMVALORGLOBAL") * 0.5+", correspondente a XX (XXXXXX) do valor do Contrato, conforme disposto no § 1°, artigo 56, da lei n.° 8666/93. A referida garantia deve ser apresentada com <u>vigência vinculada ao prazo de execução do Contrato</u>.</p>");
	    val.append("<p>Atenciosamente,</p>");
	    val.append("<p align=\"center\"><strong>&nbsp;</strong></p>");
	    val.append("<p align=\"center\"><strong>XXXXXXXXXXXXXXXXXX</strong><br />");
	    val.append("<p align=\"center\">Chefe da Divisão de Contratos do Insumos Estratégicos para Saúde</p>");
	    val.append("<p align=\"center\"><strong>&nbsp;</strong></p>");
	    val.append("<p align=\"center\"><strong>XXXXXXXXXXXXXXXXXX</strong><br />");
	    val.append("<p align=\"center\">Coordenadora-Geral de Licitações e Contratos de Insumos Estratégicos para Saúde</p>");
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
		    val.append("<p align=\"center\"><strong>Despacho referente ao PEC número do fluxo.... e SIPAR n.° .....<strong><br/>");
		    val.append("</tr>");
		    val.append("</table>");
		    val.append("<table>");
		    val.append("<tr>");
		    val.append("<td width=\"900\">");	    
		    val.append("<p><strong>Assunto:</strong> Recomendações à área demandante – Parecer CONJUR/MS</p>");
		    val.append("<p><strong>À Coordenação-Geral de Assistência Farmacêutica e Medicamentos Estratégicos – CGAFME</strong></p>");
		    val.append("<p style=\"text-align: justify;\">Trata-se de processo eletrônico de compras para aquisição do medicamento Tipranavir (TPV) 250mg, em atendimento à solicitação do Departamento de Vigilância, Prevenção e Controle das DST, AIDS e Hepatites Virais – DDAHV/SVS.</p>");
		    val.append("<p style=\"text-align: justify;\">	2.		Em cumprimento ao disposto no art. 38, parágrafo único, da Lei nº. 8.666/93, os autos foram encaminhados à d. Consultoria Jurídica para análise da minuta de contrato e dos procedimentos administrativos de aquisição.</p>");
		    val.append("<p style=\"text-align: justify;\">	3.		Em resposta, a CONJUR/MS proferiu o Parecer nº 1774/2014/CODELICI/ AGU/CONJUR-MS/CGU/RAB, no qual se manifestou favorável à celebração do contrato, desde que observadas às recomendações exaradas no bojo daquele opinativo (T29 – anexada 15/05/2014), ao que foi aprovado pela Coordenadora Geral da CODELICI/CONJUR (T29.3 – anexada 09/06/2014)</p>");
		    val.append("<p style=\"text-align: justify;\">	4.		Ante o exposto, encaminho o processo para conhecimento do inteiro teor do parecer encimado e manifestação quanto às recomendações de competência dessa área, em especial quanto aos parágrafos 25 a 27, 55, 78 e 88 subitem 1, 3 e 4 do referido parecer.</p>");
		    val.append("<p>Atenciosamente,</p></br>");
		    val.append("<p align=\"right\">Brasília, 27 de junho de 2014.</p>");
		    val.append("<p align=\"center\"><strong>&nbsp;</strong></p>");
		    val.append("<p align=\"center\"><strong>XXXXXXXXXXXXXXXXXX</strong></br>");
		    val.append("<p align=\"center\">Coordenador Geral de Análise das Contratações de");
		    val.append("<p align=\"center\">Insumos Estratégicos para Saúde -Substituto");
		    val.append("<p align=\"center\">CGIES/DLOG/SE/MS");
		    val.append("</td>");
		    val.append("</tr>");
		    val.append("</table>");
		    val.append("</body>");
		    
		    getMboValue().setValue(val.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
	}
}
