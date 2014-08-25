package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.inf.ctis.common.util.Extenso;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class MsResOc extends AppBean {
	
	public MsResOc() {
	}

	public void despacho1() throws MXException, RemoteException {
		
		System.out.println("########## Dentro do metodo despacho1() da classe DespachosCdju");
		
		Calendar data = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");
		
		String sipar = "XXXXX.XXXXXX/XXXX-XX";
				
		System.out.println("########## SIPAR IS: " + getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).isNull("MSNUMSIPAR"));
		if(!getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).isNull("MSNUMSIPAR")) {
			System.out.println("########## SIPAR: " + getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).getString("MSNUMSIPAR"));
			sipar = getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).getString("MSNUMSIPAR");
		}
		
		Double total = 0d;
		
		System.out.println("########## TOTAL IS: " + getMbo().isNull("MSNUMTOTAL"));
		if(!getMbo().isNull("MSNUMTOTAL")) {
			System.out.println("########## TOTAL: " + getMbo().getDouble("MSNUMTOTAL"));
			total = getMbo().getDouble("MSNUMTOTAL");
		}
		
		Extenso extenso = new Extenso();
		extenso.setNumber(total);
		
		System.out.println("########## sipar: " + sipar);
		System.out.println("########## data: " + sdf.format(data.getTime()));
		
		StringBuilder val = new StringBuilder(); 
        // **
        val.append("<body>");
        val.append("<table>");
        val.append("<tr>");
		val.append("<td width=\"900\" valign=\"top\">");
		val.append("<p align=\"left\"><strong>Despacho Referente ao Processo<strong><br/>");
		val.append("<p align=\"left\">Registro SIPAR n.º: " + sipar + " - Ação Judicial - <strong>XXXXXXXXXX<strong><br/>");
		val.append("</td>");
		val.append("</tr>");
		val.append("</table>");
		val.append("<table>");
		val.append("<tr>");
		val.append("<td width=\"900\">");
		val.append("</br>");
		val.append("<p>AO DIRETOR DO DEPARTAMENTO DE LOGÍSTICA EM SAÚDE / DLOG</p><br/>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tendo em vista as sentenças judiciais que determinaram as aquisições de medicamentos, correlatos e suplementos alimentares que foram agrupados neste processo e considerando a orientação da Consultoria Jurídica – CONJUR/MS para prosseguimento da referida contratação, esta Coordenação de Compra por Determinação Judicial – CDJU/CGIES/DLOG/SE/MS, solicitou às empresas fornecedoras de insumos para a saúde apresentação de cotações cujas melhores propostas foram consolidadas na tabela anexa.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cumpre-nos informar que XXXXXXXXXX, objetos da demanda judicial em tela, não constam na tabela CMED uma vez que esta só é aplicada a medicamentos, o que impossibilita a análise quanto ao atendimento do CAP, obedecendo ao disposto na RESOLUÇÃO CMED Nº XX, de XX de XXXXX de 20XX.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cumpre-nos ressaltar que não há parâmetro de análise quanto ao atendimento do CAP pela RESOLUÇÃO CMED Nº XX, de XX de XXXXX de 20XX para XXXXXXXXXXX, cotado pela empresa XXXXXXXXXXX, pois não consta na tabela CMED e ABCFARMA.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cumpre-nos informar que XXXXXXXXXX não serão adquiridos por falta de cotação junto aos fornecedores.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cumpre-nos informar que a proposta da empresa XXXXXXXXXX, foi desconsiderada, tendo em vista o impedimento de licitar com órgãos do Governo Federal, conforme informação do SICAF – Sistema de Cadastramento Unificado de Fornecedores à fl. XXX. Informamos ainda que a proposta desconsiderada foi substituída pela da empresa, conforme apresentada às fls. XXX.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O dispêndio da referida contratação dar-se-á no valor global <strong>R$ " + total + " (" + extenso.toString() + ").</strong></p></br>");
		val.append("</br>");
		val.append("<p align=\"center\">Brasília/DF, " + sdf.format(data.getTime()) + ".</p>");
		val.append("</br></br>");
		val.append("<p align=\"center\"><strong>ALEXANDRE POZZA URNAU SILVA</strong><br>Coordenador de Compra por Determinação Judicial<br>CDJU/CGIES/DLOG/SE/MS</p>");
		val.append("</td>");
		val.append("</tr>");
		val.append("</table>");
		val.append("</body>");
              
        getMbo().setValue("MSCLOBDESPACHO01", val.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
        super.SAVE();
        super.refreshTable();
        super.reloadTable();
		
	}

	public void despacho2() throws MXException, RemoteException {
		
		System.out.println("########## Dentro do metodo despacho2() da classe DespachosCdju");
		
		Calendar data = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");
		
		String sipar = "XXXXX.XXXXXX/XXXX-XX";
				
		System.out.println("########## SIPAR IS: " + getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).isNull("MSNUMSIPAR"));
		if(!getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).isNull("MSNUMSIPAR")) {
			System.out.println("########## SIPAR: " + getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).getString("MSNUMSIPAR"));
			sipar = getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).getString("MSNUMSIPAR");
		}
			
		System.out.println("########## sipar: " + sipar);
		System.out.println("########## data: " + sdf.format(data.getTime()));
		
		StringBuilder val = new StringBuilder(); 
        // **
        val.append("<body>");
        val.append("<table>");
        val.append("<tr>");
		val.append("<td width=\"900\" valign=\"top\">");
		val.append("<p align=\"left\"><strong>Despacho Referente ao Processo<strong><br/>");
		val.append("<p align=\"left\">Registro SIPAR n.º: " + sipar + " - Ação Judicial - <strong>XXXXXXXXXX<strong><br/>");
		val.append("</td>");
		val.append("</tr>");
		val.append("</table>");
		val.append("<table>");
		val.append("<tr>");
		val.append("<td width=\"900\">");
		val.append("</br>");
		val.append("<p>AO DIRETOR DO DEPARTAMENTO DE LOGÍSTICA EM SAÚDE / DLOG</p><br/>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Estando os autos devidamente instruídos e observando a contratação, nos moldes do art. 26, caput, fundamentada no <strong>art. 24, inc. IV da Lei n.º 8.666/93</strong>, da Lei n.º 8.666/93, informo sobre a necessidade de ratificar a dispensa de licitação nos termos contidos neste despacho, bem como autorizar o lançamento no SIASG/SIDEC, para posterior publicação e emissão das respectivas <strong>Notas de Empenho</strong>.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Encaminhe-se ofício ao Secretário Executivo da CMED/ANVISA, acerca da inobservância pela empresa XXXXXXXXXX, XXXXXXXXXXXX.</p></br>");
		val.append("</br>");
		val.append("<p align=\"center\">Brasília/DF, " + sdf.format(data.getTime()) + ".</p>");
		val.append("</br></br>");
		val.append("<p align=\"center\"><strong>EDUARDO SEARA POJO</strong><br>Coordenador-Geral de Análise das Contratações de Insumos Estratégicos para Saúde - Subistituto<br>CGIES/DLOG/SE/MS</p>");
		val.append("</td>");
		val.append("</tr>");
		val.append("</table>");
		val.append("</body>");
              
        getMbo().setValue("MSCLOBDESPACHO02", val.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
        super.SAVE();
        super.refreshTable();
        super.reloadTable();
		
	}

	public void despacho3() throws MXException, RemoteException {
		
		System.out.println("########## Dentro do metodo despacho3() da classe DespachosCdju");
		
		Calendar data = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");
		
		String sipar = "XXXXX.XXXXXX/XXXX-XX";
				
		System.out.println("########## SIPAR IS: " + getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).isNull("MSNUMSIPAR"));
		if(!getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).isNull("MSNUMSIPAR")) {
			System.out.println("########## SIPAR: " + getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).getString("MSNUMSIPAR"));
			sipar = getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).getString("MSNUMSIPAR");
		}
		
		Double total = 0d;
		
		System.out.println("########## TOTAL IS: " + getMbo().isNull("MSNUMTOTAL"));
		if(!getMbo().isNull("MSNUMTOTAL")) {
			System.out.println("########## TOTAL: " + getMbo().getDouble("MSNUMTOTAL"));
			total = getMbo().getDouble("MSNUMTOTAL");
		}
		
		Extenso extenso = new Extenso();
		extenso.setNumber(total);
		
		System.out.println("########## sipar: " + sipar);
		System.out.println("########## data: " + sdf.format(data.getTime()));
		
		StringBuilder val = new StringBuilder(); 
        // **
        val.append("<body>");
        val.append("<table>");
        val.append("<tr>");
		val.append("<td width=\"900\" valign=\"top\">");
		val.append("<p align=\"left\"><strong>Despacho Referente ao Processo<strong><br/>");
		val.append("<p align=\"left\">Registro SIPAR n.º: " + sipar + " - Ação Judicial - <strong>XXXXXXXXXX<strong><br/>");
		val.append("</td>");
		val.append("</tr>");
		val.append("</table>");
		val.append("<table>");
		val.append("<tr>");
		val.append("<td width=\"900\">");
		val.append("</br>");
		val.append("<p>AO DIRETOR DO DEPARTAMENTO DE LOGÍSTICA EM SAÚDE / DLOG</p><br/>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ratifico a Dispensa de licitação, nos moldes do art. 26, caput, da Lei n.º 8.666/93, fundamentada no <strong>artigo 24, inciso IV</strong>, do mesmo diploma legal, para a presente aquisição, conforme tabela apresentada por último pela Coordenação de Compras por Determinação Judicial – CDJU/CGIES/DLOG/SE/MS, vistada por essa diretoria e amparada pelo despacho da Coordenação Geral de Análise das Contratações de Insumos Estratégicos para a Saúde.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O dispêndio da referida contratação dar-se-á no valor global <strong>R$ " + total + " (" + extenso.toString() + ").</strong></p></br>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Devolva-se à Coordenação de Compras por Determinação Judicial – CDJU/CGIES/DLOG/SE/MS, para publicação na imprensa oficial, e posterior encaminhamento à Coordenação de Execução Orçamentária e Financeira - CEOF/DLOG/SE/MS para emissão da Nota de Empenho.</p>");
		val.append("</br>");
		val.append("<p align=\"center\">Brasília/DF, " + sdf.format(data.getTime()) + ".</p>");
		val.append("</br></br>");
		val.append("<p align=\"center\"><strong>GIRLEY VIEIRA DAMASCENO</strong><br>Diretor do Departamento de Logística em Saúde<br>DLOG/SE/MS</p>");
		val.append("</td>");
		val.append("</tr>");
		val.append("</table>");
		val.append("</body>");
              
        getMbo().setValue("MSCLOBDESPACHO03", val.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
        super.SAVE();
        super.refreshTable();
        super.reloadTable();
		
	}

	public void despacho4() throws MXException, RemoteException {
		
		System.out.println("########## Dentro do metodo despacho4() da classe DespachosCdju");
		
	}

	public void despacho5() throws MXException, RemoteException {
		
		System.out.println("########## Dentro do metodo despacho5() da classe DespachosCdju");

	}
}