package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MsNuNumQtdPorEmbalagem extends MboValueAdapter{
	
	public MsNuNumQtdPorEmbalagem(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
		if (getMboValue().getDouble() <= getMboValue("ID2QNTPEDIDO").getDouble()) {
			System.out.println("########## getMboValue().getDouble(): " + getMboValue().getDouble());
			Double qtdPorEmbalagem = (getMboValue().getDouble() != 0) ? getMboValue().getDouble() : 1;
			System.out.println("########## qtdPorEmbalagem: " + qtdPorEmbalagem);
			
			System.out.println("########## getMboValue(ID2QNTPEDIDO).getDouble(): " + getMboValue("ID2QNTPEDIDO").getDouble());
			Double qtdTotal = getMboValue("ID2QNTPEDIDO").getDouble();
			System.out.println("########## qtdTotal: " + qtdTotal);
			
			System.out.println("########## Divisao: " + (qtdTotal / qtdPorEmbalagem));
			Double qtdDeEmbalagens = (double) Math.round(qtdTotal / qtdPorEmbalagem);
			System.out.println("########## qtdDeEmbalagens: " + qtdDeEmbalagens);
			
			getMboValue("MSNUNUMQTDEMBALAGENS").setValue(qtdDeEmbalagens);
		} else {
			throw new MXApplicationException("prline", "QuantidadePorEmbalagemExcedida");
		}
		
	}

}
