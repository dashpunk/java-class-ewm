package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsResOc extends MboValueAdapter {
	
	public MsResOc(MboValue mbv) {
		super(mbv);
	}

	public void initValue() throws MXException, RemoteException {
		super.initValue();
		if(getMboValue().getMbo().isNew()) {
			define();
		}
	}

	private void define() throws MXException {
		try {
			if(getMboValue().getName().equalsIgnoreCase("MSCLOBDESPACHO01")) {
				despacho1();
			} else if(getMboValue().getName().equalsIgnoreCase("MSCLOBDESPACHO02")) {
				despacho2();
			} else if(getMboValue().getName().equalsIgnoreCase("MSCLOBDESPACHO03")) {
				despacho3();
			} else if(getMboValue().getName().equalsIgnoreCase("MSCLOBDESPACHO04")) {
				despacho4();
			} else if(getMboValue().getName().equalsIgnoreCase("MSCLOBDESPACHO05")) {
				despacho5();
			}
			
		} catch (RemoteException re) {
			System.out.println("######## Despacho invalido: " + re.getMessage());
		}
	}

	private void despacho1() throws MXException, RemoteException {
		
		System.out.println("########## Dentro do metodo despacho1() da classe DespachosCdju");
		
		Calendar data = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");
		
		String sipar = "XXXXX.XXXXXX/XXXX-XX";
		
		/*System.out.println("########## SIPAR IS: " + getMboValue().getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).getString("MSNUMSIPAR").isEmpty());
		if(!getMboValue().getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).getString("MSNUMSIPAR").isEmpty()) {
			System.out.println("########## SIPAR: " + getMboValue().getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).getString("MSNUMSIPAR"));
			sipar = getMboValue().getMbo().getMboSet("MSTBMEDICAMENTO").getMbo(0).getMboSet("WORKORDER").getMbo(0).getString("MSNUMSIPAR");
		}*/
		
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
		val.append("<p style=\"text-align: justify;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;O disp�ndio da referida contrata��o dar-se-� no valor global R$ XXXXXXXXXX.</p></br>");
		val.append("</br>");
		val.append("<p align=\"center\">Bras�lia/DF, " + sdf.format(data.getTime()) + ".</p>");
		val.append("</br></br>");
		val.append("<p align=\"center\"><strong>ALEXANDRE POZZA URNAU SILVA</strong></br>Coordenador de Compra por Determina��o Judicial</br>CDJU/CGIES/DLOG/SE/MS</p>");
		val.append("</td>");
		val.append("</tr>");
		val.append("</table>");
		val.append("</body>");
              
        getMboValue().setValue(val.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
		
	}

	private void despacho2() throws MXException, RemoteException {
		
		System.out.println("########## Dentro do metodo despacho2() da classe DespachosCdju");
		
	}

	private void despacho3() throws MXException, RemoteException {
		
		System.out.println("########## Dentro do metodo despacho3() da classe DespachosCdju");
		
	}

	private void despacho4() throws MXException, RemoteException {
		
		System.out.println("########## Dentro do metodo despacho4() da classe DespachosCdju");
		
	}

	private void despacho5() throws MXException, RemoteException {
		
		System.out.println("########## Dentro do metodo despacho5() da classe DespachosCdju");

	}
}