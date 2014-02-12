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
			String despacho1 = "AO DIRETOR DO DEPARTAMENTO DE LOG�STICA EM SA�DE / DLOG\n\n\tTendo em vista as senten�as judiciais que determinaram as aquisi��es de medicamentos, correlatos e suplementos alimentares que foram agrupados neste processo e considerando a orienta��o da Consultoria Jur�dica � CONJUR/MS para prosseguimento da referida contrata��o, esta Coordena��o de Compra por Determina��o Judicial � CDJU/CGIES/DLOG/SE/MS, solicitou �s empresas fornecedoras de insumos para a sa�de apresenta��o de cota��es cujas melhores propostas foram consolidadas na tabela anexa.\n\n\tO disp�ndio da referida contrata��o dar-se-� no valor global " + 
			valor + 
			".\n\n\tCumpre-nos informar que os medicamentos " + 
			medicamentos1 + 
			" n�o ser�o adquiridos por falta de interesse na cota��o pelos fornecedores.\n\n\t Cumpre-nos informar que o suplemento alimentar " + 
			medicamentos2 + 
			" , objetos da demanda judicial em tela, n�o constam na tabela CMED uma vez que esta s� � aplicada a medicamentos, o que impossibilita a an�lise quanto ao atendimento do CAP, obedecendo ao disposto na RESOLU��O CMED N� 3, de 02 de mar�o de 2011.\n\n\t Cumpre-nos informar que a proposta da empresa " + 
			empresa1 + 
			" , foi desconsiderada, tendo em vista o impedimento de licitar com �rg�os do Governo Federal, conforme informa��o do SICAF � Sistema de Cadastramento Unificado de Fornecedores. Informamos ainda que a proposta desconsiderada foi substitu�da pela da empresa " + 
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

