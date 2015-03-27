package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsNuNumQtdEmbalagens extends MboValueAdapter{
	
	public MsNuNumQtdEmbalagens(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
	
		System.out.println("########## getMboValue().getDouble(): " + getMboValue().getDouble());
		Double qtdEmbalagens = getMboValue().getDouble();
		System.out.println("########## qtdEmbalagens: " + qtdEmbalagens);
		
		System.out.println("########## getMboValue(MSNUNUMEMBALAGEMPESO).getDouble(): " + getMboValue("MSNUNUMEMBALAGEMPESO").getDouble());
		Double embalagemPeso = getMboValue("MSNUNUMEMBALAGEMPESO").getDouble();
		System.out.println("########## embalagemPeso: " + embalagemPeso);
		
		System.out.println("########## Multiplicacao aquisicaoPeso: " + (embalagemPeso * qtdEmbalagens));
		Double aquisicaoPeso =  embalagemPeso * qtdEmbalagens;
		System.out.println("########## aquisicaoPeso: " + aquisicaoPeso);
		
		getMboValue("MSNUNUMAQUISICAOPESO").setValue((aquisicaoPeso/1000));
		
		System.out.println("########## getMboValue(MSNUNUMCUBAGEMVOLUME).getDouble(): " + getMboValue("MSNUNUMCUBAGEMVOLUME").getDouble());
		Double cubagemVolume = getMboValue("MSNUNUMCUBAGEMVOLUME").getDouble();
		System.out.println("########## cubagemVolume: " + cubagemVolume);
		
		System.out.println("########## Multiplicacao aquisicaoVolume: " + (cubagemVolume * qtdEmbalagens));
		Double aquisicaoVolume =  cubagemVolume * qtdEmbalagens;
		System.out.println("########## aquisicaoVolume: " + aquisicaoVolume);
		
		getMboValue("MSNUNUMAQUISICAOVOLUME").setValue((aquisicaoVolume/100));
	}

}
