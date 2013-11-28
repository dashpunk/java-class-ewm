package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class QuantidadeEPorcentagemAditivada extends MboValueAdapter {

	public QuantidadeEPorcentagemAditivada(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();

		Double orderQty = getMboValue().getMbo().getMboSet("MSVWITENSNETERMOADITIVO").getMbo(0).getDouble("ORDERQTY");
		System.out.println("########## ORDERQTY = " + getMboValue().getMbo().getMboSet("MSVWITENSNETERMOADITIVO").getMbo(0).getDouble("ORDERQTY"));
		
		if (getMboValue().getName().equals("MSNUNUMPORCENTAGEMADITIVADA")) {
			if(getMboValue().getDouble() > 25){
				throw new MXApplicationException("termoaditivo", "PorcentagemExcedente");
			} else {
				getMboValue("MSNUNUMQUANTIDADEADITIVADA").setValue((getMboValue().getDouble() / 100) * orderQty);
			}
		} else if (getMboValue().getName().equals("MSNUNUMQUANTIDADEADITIVADA")) {
			if(((100 * getMboValue().getDouble()) / orderQty) > 25){
				throw new MXApplicationException("termoaditivo", "PorcentagemExcedente");
			} else {
				getMboValue("MSNUNUMPORCENTAGEMADITIVADA").setValue((100 * getMboValue().getDouble()) / orderQty);
			}
		}
	}
}