package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsAlDscDespacho3 extends MboValueAdapter{

	public MsAlDscDespacho3(MboValue mbv) {
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
			String artigo = null;
			String despacho3 = "Ratifico a Dispensa de licita��o, nos moldes do art. 26, caput, da Lei n.� 8.666/93, fundamentada no " +
			artigo +
			", do mesmo diploma legal, para a presente aquisi��o, conforme tabela apresentada por �ltimo pela Coordena��o de Compras por Determina��o Judicial � CDJU/CGIES/DLOG/SE/MS, vistada por essa diretoria e amparada pelo despacho da Coordena��o Geral de An�lise das Contrata��es de Insumos Estrat�gicos para a Sa�de.\n\n\tO disp�ndio da referida contrata��o dar-se-� no valor global " +
			valor + 
			".\n\n\tDevolva-se � Coordena��o de Compras por Determina��o Judicial � CDJU/CGIES/DLOG/SE/MS, para publica��o na imprensa oficial, e posterior encaminhamento � Coordena��o de Execu��o Or�ament�ria e Financeira - CEOF/DLOG/SE/MS para emiss�o da Nota de Empenho.";
			System.out.println("########## Despacho3" + despacho3);
			System.out.println("########## MSALDSCDESPACHO3_LONGDESCRIPTION = " + getMboValue().getMbo().getString("MSALDSCDESPACHO3_LONGDESCRIPTION"));
			getMboValue().getMbo().setValue("MSALDSCDESPACHO3_LONGDESCRIPTION", despacho3);
			System.out.println("########## MSALDSCDESPACHO3_LONGDESCRIPTION PREENCHIDO = " + getMboValue().getMbo().getString("MSALDSCDESPACHO3_LONGDESCRIPTION"));

		} catch (RemoteException re) {
			System.out.println("######## Despacho invalido: "+ re.getMessage());
		}
	}
}
	




