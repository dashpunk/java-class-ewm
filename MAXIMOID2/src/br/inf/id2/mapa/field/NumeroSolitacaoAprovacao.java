package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;

public class NumeroSolitacaoAprovacao extends MboValueAdapter {
	public NumeroSolitacaoAprovacao(MboValue mbv) {
		super(mbv);
	}

	public void initValue() throws MXException, RemoteException {
		super.initValue();
		define();
	}

	private void define() throws MXException {
		try {
			if ((getMboValue().getMbo().getString("ID2NUMPROTOCOLOAPR") != null) && (getMboValue().getMbo().getString("LOCATION") != null) && (!getMboValue().getMbo().getString("ID2NUMPROTOCOLOAPR").startsWith("SAP"))) {
				
				String valor = "SAP."+ Uteis.retiraCaracteresEspeciais(Uteis.adicionaValorEsquerda(getMboValue().getMbo().getString("LOCATION"), "0", 9)) + "."+ new GregorianCalendar().get(1);

				getMboValue().getMbo().setValue("ID2NUMPROTOCOLOAPR", valor);
				getMboValue().getMbo().getThisMboSet().save();
			}
		} catch (RemoteException re) {
			System.out.println("######## Excecao ao definir o valor de ID2NUMPROTOCOLO: "+ re.getMessage());
		}
	}
}
