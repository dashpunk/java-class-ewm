package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsAlDscDespacho2 extends MboValueAdapter {
	
	public MsAlDscDespacho2(MboValue mbv) {
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
			String artigo = null;
			String despacho2 = "Estando os autos devidamente instru�dos e observando a contrata��o, nos moldes do art. 26, caput, fundamentada no " + 
			artigo + 
			", da Lei n.� 8.666/93, informo sobre a necessidade de ratificar a dispensa de licita��o nos termos contidos neste despacho, bem como autorizar o lan�amento no SIASG/SIDEC, para posterior publica��o e emiss�o das respectivas Notas de Empenho.";
			System.out.println("########## Despacho2" + despacho2);
			System.out.println("########## MSALDSCDESPACHO2_LONGDESCRIPTION = " + getMboValue().getMbo().getString("MSALDSCDESPACHO2_LONGDESCRIPTION"));
			getMboValue().getMbo().setValue("MSALDSCDESPACHO2_LONGDESCRIPTION", despacho2);
			System.out.println("########## MSALDSCDESPACHO2_LONGDESCRIPTION PREENCHIDO = " + getMboValue().getMbo().getString("MSALDSCDESPACHO2_LONGDESCRIPTION"));

		} catch (RemoteException re) {
			System.out.println("######## Despacho invalido: "+ re.getMessage());
		}
	}
}

