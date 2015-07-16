package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class CalculaValorTotalItensPregaoSAA extends MboValueAdapter {

	public CalculaValorTotalItensPregaoSAA(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		
		String campo = getMboValue().getName();
		System.out.println("########## Campo validando = " + campo);
		
		if(campo.equalsIgnoreCase("MSNUNUMQUANTIDADEREGISTRADA")) {
			//QUANTIDADE
			System.out.println("########## QUANTIDADE");
			
			getMboValue("MSNUNUMVALORTOTALREFERENCIA").setValue(getMboValue("MSNUNUMQUANTIDADEREGISTRADA").getDouble() * getMboValue("MSNUNUMVALORREFERENCIA").getDouble());
			getMboValue("MSNUNUMVALORTOTAL").setValue(getMboValue("MSNUNUMQUANTIDADEREGISTRADA").getDouble() * getMboValue("MSNUNUMVALORUNITARIO").getDouble());
			
		} else if(campo.equalsIgnoreCase("MSNUNUMVALORREFERENCIA")) {
			//VALOR UNITARIO DE REFERENCIA
			System.out.println("########## VALOR UNITARIO DE REFERENCIA");
			
			getMboValue("MSNUNUMVALORTOTALREFERENCIA").setValue(getMboValue("MSNUNUMQUANTIDADEREGISTRADA").getDouble() * getMboValue("MSNUNUMVALORREFERENCIA").getDouble());
			
		} else if(campo.equalsIgnoreCase("MSNUNUMVALORUNITARIO")) {
			//VALOR UNITARIO HOMOLOGADO
			System.out.println("########## VALOR UNITARIO HOMOLOGADO");
			
			getMboValue("MSNUNUMVALORTOTAL").setValue(getMboValue("MSNUNUMQUANTIDADEREGISTRADA").getDouble() * getMboValue("MSNUNUMVALORUNITARIO").getDouble());
			
		} 		
		
	}

}
