package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsNuNumCubagemVolume extends MboValueAdapter{
	
	public MsNuNumCubagemVolume(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
		
		System.out.println("########## getMboValue().getDouble(MSNUNUMQTDEMBALAGENS): " + getMboValue("MSNUNUMQTDEMBALAGENS").getDouble());
		Double qtdEmbalagens = getMboValue("MSNUNUMQTDEMBALAGENS").getDouble();
		System.out.println("########## qtdEmbalagens: " + qtdEmbalagens);

		System.out.println("########## getMboValue().getDouble(): " + getMboValue().getDouble());
		Double cubagemVolume = getMboValue().getDouble();
		System.out.println("########## cubagemVolume: " + cubagemVolume);
		
		System.out.println("########## Multiplicacao aquisicaoVolume: " + (cubagemVolume * qtdEmbalagens));
		Double aquisicaoVolume =  cubagemVolume * qtdEmbalagens;
		System.out.println("########## aquisicaoVolume: " + aquisicaoVolume);
		
		getMboValue("MSNUNUMAQUISICAOVOLUME").setValue((aquisicaoVolume/100));
	}
}
