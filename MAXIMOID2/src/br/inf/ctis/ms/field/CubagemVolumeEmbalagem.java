package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author marcelosydney.lima
 *  
 */

public class CubagemVolumeEmbalagem extends MboValueAdapter{
	
	public CubagemVolumeEmbalagem(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
		
		System.out.println("########## altura: " + getMboValue("MSNUNUMCUBAGEMALTURA").getDouble());
		System.out.println("########## largura: " + getMboValue("MSNUNUMCUBAGEMLARGURA").getDouble());
		System.out.println("########## profundidade: " + getMboValue("MSNUNUMCUBAGEMPROFUNDIDADE").getDouble());
		
		System.out.println("########## Multiplicacao: " + (getMboValue("MSNUNUMCUBAGEMALTURA").getDouble() * getMboValue("MSNUNUMCUBAGEMLARGURA").getDouble() * getMboValue("MSNUNUMCUBAGEMPROFUNDIDADE").getDouble()));
		Double volume = getMboValue("MSNUNUMCUBAGEMALTURA").getDouble() * getMboValue("MSNUNUMCUBAGEMLARGURA").getDouble() * getMboValue("MSNUNUMCUBAGEMPROFUNDIDADE").getDouble();
		System.out.println("########## volume: " + volume);
		
		
		getMboValue("MSNUNUMCUBAGEMVOLUME").setValue(volume);
	
	}
}
