package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsAlDscDespacho1 extends MboValueAdapter {
	
	public MsAlDscDespacho1(MboValue mbv) {
		super(mbv);
	}

	public void initValue() throws MXException, RemoteException {
		super.initValue();
		if(getMboValue().getMbo().isNew()){
			define();
		}
	}

	private void define() throws MXException {
		try {
			Double valor = 0d;
			String medicamentos1 = null;
			String medicamentos2 = null;
			String empresa1 = "____________________________";
			String empresa2 = "____________________________";
			String despacho1 = "AO DIRETOR DO DEPARTAMENTO DE LOGÍSTICA EM SAÚDE / DLOG\n\n\tTendo em vista as sentenças judiciais que determinaram as aquisições de medicamentos, correlatos e suplementos alimentares que foram agrupados neste processo e considerando a orientação da Consultoria Jurídica – CONJUR/MS para prosseguimento da referida contratação, esta Coordenação de Compra por Determinação Judicial – CDJU/CGIES/DLOG/SE/MS, solicitou às empresas fornecedoras de insumos para a saúde apresentação de cotações cujas melhores propostas foram consolidadas na tabela anexa.\n\n\tO dispêndio da referida contratação dar-se-á no valor global " + 
			valor + 
			".\n\n\tCumpre-nos informar que os medicamentos " + 
			medicamentos1 + 
			" não serão adquiridos por falta de interesse na cotação pelos fornecedores.\n\n\t Cumpre-nos informar que o suplemento alimentar " + 
			medicamentos2 + 
			" , objetos da demanda judicial em tela, não constam na tabela CMED uma vez que esta só é aplicada a medicamentos, o que impossibilita a análise quanto ao atendimento do CAP, obedecendo ao disposto na RESOLUÇÃO CMED Nº 3, de 02 de março de 2011.\n\n\t Cumpre-nos informar que a proposta da empresa " + 
			empresa1 + 
			" , foi desconsiderada, tendo em vista o impedimento de licitar com órgãos do Governo Federal, conforme informação do SICAF – Sistema de Cadastramento Unificado de Fornecedores. Informamos ainda que a proposta desconsiderada foi substituída pela da empresa " + 
			empresa2 + 
			" , conforme apresentada.";
			System.out.println("########## Despacho1" + despacho1);
			System.out.println("########## MSALDSCDESPACHO1_LONGDESCRIPTION = " + getMboValue().getMbo().getString("MSALDSCDESPACHO1_LONGDESCRIPTION"));
			getMboValue().getMbo().setValue("MSALDSCDESPACHO1_LONGDESCRIPTION", despacho1);
			System.out.println("########## MSALDSCDESPACHO1_LONGDESCRIPTION PREENCHIDO = " + getMboValue().getMbo().getString("MSALDSCDESPACHO1_LONGDESCRIPTION"));

		} catch (RemoteException re) {
			System.out.println("######## Despacho invalido: "+ re.getMessage());
		}
	}
}

