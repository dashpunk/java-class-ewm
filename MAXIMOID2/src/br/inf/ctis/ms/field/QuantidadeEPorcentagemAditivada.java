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
		String tipo = getMboValue().getMbo().getString("MSALCODTIPO");
		
		
		if (tipo.equalsIgnoreCase("ACRESCIMO")) {
			if (getMboValue().getName().equals("MSNUNUMPORCENTAGEMADITIVADA")) {
				if(getMboValue().getDouble() > 25){
					throw new MXApplicationException("termoaditivo", "PorcentagemExcedenteAcrescimo");
				} else {
					getMboValue("MSNUNUMQUANTIDADEADITIVADA").setValue((getMboValue().getDouble() / 100) * orderQty);
				}
			} else if (getMboValue().getName().equals("MSNUNUMQUANTIDADEADITIVADA")) {
				if(((100 * getMboValue().getDouble()) / orderQty) > 25){
					throw new MXApplicationException("termoaditivo", "PorcentagemExcedenteAcrescimo");
				} else {
					getMboValue("MSNUNUMPORCENTAGEMADITIVADA").setValue((100 * getMboValue().getDouble()) / orderQty);
				}
			}
		} else if (tipo.equalsIgnoreCase("SUPRESSAO")) {
			if (getMboValue().getName().equals("MSNUNUMPORCENTAGEMADITIVADA")) {
				if(getMboValue().getDouble() > 100){
					throw new MXApplicationException("termoaditivo", "PorcentagemExcedenteSupressao");
				} else {
					getMboValue("MSNUNUMQUANTIDADEADITIVADA").setValue((getMboValue().getDouble() / 100) * orderQty);
				}
			} else if (getMboValue().getName().equals("MSNUNUMQUANTIDADEADITIVADA")) {
				if(((100 * getMboValue().getDouble()) / orderQty) > 100){
					throw new MXApplicationException("termoaditivo", "PorcentagemExcedenteSupressao");
				} else {
					getMboValue("MSNUNUMPORCENTAGEMADITIVADA").setValue((100 * getMboValue().getDouble()) / orderQty);
				}
			}
		}
		
		
	}
}