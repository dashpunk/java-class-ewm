package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsNuNumEmbalagemPeso extends MboValueAdapter{
	
	public MsNuNumEmbalagemPeso(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
		
		System.out.println("########## getMboValue().getDouble(MSNUNUMQTDEMBALAGENS): " + getMboValue("MSNUNUMQTDEMBALAGENS").getDouble());
		Double qtdEmbalagens = getMboValue("MSNUNUMQTDEMBALAGENS").getDouble();
		System.out.println("########## qtdEmbalagens: " + qtdEmbalagens);
		
		System.out.println("########## getMboValue().getDouble(): " + getMboValue().getDouble());
		Double embalagemPeso = getMboValue().getDouble();
		System.out.println("########## embalagemPeso: " + embalagemPeso);
		
		System.out.println("########## Multiplicacao aquisicaoPeso: " + (embalagemPeso * qtdEmbalagens));
		Double aquisicaoPeso =  embalagemPeso * qtdEmbalagens;
		System.out.println("########## aquisicaoPeso: " + aquisicaoPeso);
		
		getMboValue("MSNUNUMAQUISICAOPESO").setValue(aquisicaoPeso);
	}
}