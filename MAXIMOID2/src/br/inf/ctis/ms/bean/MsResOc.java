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
		val.append("<p align=\"left\">Registro SIPAR n.�: " + sipar + " � A��o Judicial � <strong>XXXXXXXXXX<strong><br/>");
		val.append("</td>");
		val.append("</tr>");
		val.append("</table>");
		val.append("<table>");
		val.append("<tr>");
		val.append("<td width=\"900\">");
		val.append("</br>");
		val.append("<p>AO DIRETOR DO DEPARTAMENTO DE LOG�STICA EM SA�DE / DLOG</p><br/>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tendo em vista as senten�as judiciais que determinaram as aquisi��es de medicamentos, correlatos e suplementos alimentares que foram agrupados neste processo e considerando a orienta��o da Consultoria Jur�dica � CONJUR/MS para prosseguimento da referida contrata��o, esta Coordena��o de Compra por Determina��o Judicial � CDJU/CGIES/DLOG/SE/MS, solicitou �s empresas fornecedoras de insumos para a sa�de apresenta��o de cota��es cujas melhores propostas foram consolidadas na tabela anexa.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cumpre-nos informar que XXXXXXXXXX, objetos da demanda judicial em tela, n�o constam na tabela CMED uma vez que esta s� � aplicada a medicamentos, o que impossibilita a an�lise quanto ao atendimento do CAP, obedecendo ao disposto na RESOLU��O CMED N� XX, de XX de XXXXX de 20XX.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cumpre-nos ressaltar que n�o h� par�metro de an�lise quanto ao atendimento do CAP pela RESOLU��O CMED N� XX, de XX de XXXXX de 20XX para XXXXXXXXXXX, cotado pela empresa XXXXXXXXXXX, pois n�o consta na tabela CMED e ABCFARMA.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cumpre-nos informar que XXXXXXXXXX n�o ser�o adquiridos por falta de cota��o junto aos fornecedores.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cumpre-nos informar que a proposta da empresa XXXXXXXXXX, foi desconsiderada, tendo em vista o impedimento de licitar com �rg�os do Governo Federal, conforme informa��o do SICAF � Sistema de Cadastramento Unificado de Fornecedores � fl. XXX. Informamos ainda que a proposta desconsiderada foi substitu�da pela da empresa, conforme apresentada �s fls. XXX.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O disp�ndio da referida contrata��o dar-se-� no valor global <strong>R$ " + total + " (" + extenso.toString() + ").</strong></p></br>");
		val.append("</br>");
		val.append("<p align=\"center\">Bras�lia/DF, " + sdf.format(data.getTime()) + ".</p>");
		val.append("</br></br>");
		val.append("<p align=\"center\"><strong>ALEXANDRE POZZA URNAU SILVA</strong><br>Coordenador de Compra por Determina��o Judicial<br>CDJU/CGIES/DLOG/SE/MS</p>");
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
		val.append("<p align=\"left\">Registro SIPAR n.�: " + sipar + " � A��o Judicial � <strong>XXXXXXXXXX<strong><br/>");
		val.append("</td>");
		val.append("</tr>");
		val.append("</table>");
		val.append("<table>");
		val.append("<tr>");
		val.append("<td width=\"900\">");
		val.append("</br>");
		val.append("<p>AO DIRETOR DO DEPARTAMENTO DE LOG�STICA EM SA�DE / DLOG</p><br/>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Estando os autos devidamente instru�dos e observando a contrata��o, nos moldes do art. 26, caput, fundamentada no <strong>art. 24, inc. IV da Lei n.� 8.666/93</strong>, da Lei n.� 8.666/93, informo sobre a necessidade de ratificar a dispensa de licita��o nos termos contidos neste despacho, bem como autorizar o lan�amento no SIASG/SIDEC, para posterior publica��o e emiss�o das respectivas <strong>Notas de Empenho</strong>.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Encaminhe-se of�cio ao Secret�rio Executivo da CMED/ANVISA, acerca da inobserv�ncia pela empresa XXXXXXXXXX, XXXXXXXXXXXX.</p></br>");
		val.append("</br>");
		val.append("<p align=\"center\">Bras�lia/DF, " + sdf.format(data.getTime()) + ".</p>");
		val.append("</br></br>");
		val.append("<p align=\"center\"><strong>EDUARDO SEARA POJO</strong><br>Coordenador-Geral de An�lise das Contrata��es de Insumos Estrat�gicos para Sa�de - Subistituto<br>CGIES/DLOG/SE/MS</p>");
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
		val.append("<p align=\"left\">Registro SIPAR n.�: " + sipar + " � A��o Judicial � <strong>XXXXXXXXXX<strong><br/>");
		val.append("</td>");
		val.append("</tr>");
		val.append("</table>");
		val.append("<table>");
		val.append("<tr>");
		val.append("<td width=\"900\">");
		val.append("</br>");
		val.append("<p>AO DIRETOR DO DEPARTAMENTO DE LOG�STICA EM SA�DE / DLOG</p><br/>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ratifico a Dispensa de licita��o, nos moldes do art. 26, caput, da Lei n.� 8.666/93, fundamentada no <strong>artigo 24, inciso IV</strong>, do mesmo diploma legal, para a presente aquisi��o, conforme tabela apresentada por �ltimo pela Coordena��o de Compras por Determina��o Judicial � CDJU/CGIES/DLOG/SE/MS, vistada por essa diretoria e amparada pelo despacho da Coordena��o Geral de An�lise das Contrata��es de Insumos Estrat�gicos para a Sa�de.</p>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O disp�ndio da referida contrata��o dar-se-� no valor global <strong>R$ " + total + " (" + extenso.toString() + ").</strong></p></br>");
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Devolva-se � Coordena��o de Compras por Determina��o Judicial � CDJU/CGIES/DLOG/SE/MS, para publica��o na imprensa oficial, e posterior encaminhamento � Coordena��o de Execu��o Or�ament�ria e Financeira - CEOF/DLOG/SE/MS para emiss�o da Nota de Empenho.</p>");
		val.append("</br>");
		val.append("<p align=\"center\">Bras�lia/DF, " + sdf.format(data.getTime()) + ".</p>");
		val.append("</br></br>");
		val.append("<p align=\"center\"><strong>GIRLEY VIEIRA DAMASCENO</strong><br>Diretor do Departamento de Log�stica em Sa�de<br>DLOG/SE/MS</p>");
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