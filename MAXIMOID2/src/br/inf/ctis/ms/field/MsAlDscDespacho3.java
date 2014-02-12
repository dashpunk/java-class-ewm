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
			String despacho3 = "Ratifico a Dispensa de licitação, nos moldes do art. 26, caput, da Lei n.º 8.666/93, fundamentada no " +
			artigo +
			", do mesmo diploma legal, para a presente aquisição, conforme tabela apresentada por último pela Coordenação de Compras por Determinação Judicial – CDJU/CGIES/DLOG/SE/MS, vistada por essa diretoria e amparada pelo despacho da Coordenação Geral de Análise das Contratações de Insumos Estratégicos para a Saúde.\n\n\tO dispêndio da referida contratação dar-se-á no valor global " +
			valor + 
			".\n\n\tDevolva-se à Coordenação de Compras por Determinação Judicial – CDJU/CGIES/DLOG/SE/MS, para publicação na imprensa oficial, e posterior encaminhamento à Coordenação de Execução Orçamentária e Financeira - CEOF/DLOG/SE/MS para emissão da Nota de Empenho.";
			System.out.println("########## Despacho3" + despacho3);
			System.out.println("########## MSALDSCDESPACHO3_LONGDESCRIPTION = " + getMboValue().getMbo().getString("MSALDSCDESPACHO3_LONGDESCRIPTION"));
			getMboValue().getMbo().setValue("MSALDSCDESPACHO3_LONGDESCRIPTION", despacho3);
			System.out.println("########## MSALDSCDESPACHO3_LONGDESCRIPTION PREENCHIDO = " + getMboValue().getMbo().getString("MSALDSCDESPACHO3_LONGDESCRIPTION"));

		} catch (RemoteException re) {
			System.out.println("######## Despacho invalido: "+ re.getMessage());
		}
	}
}
	




