package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.id2.Uteis;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class NumeroNotaFiscal extends MboValueAdapter {

	/**
	 * @author marcelosydney.lima
	 */
	
	public NumeroNotaFiscal(MboValue mbv) throws MXException {
        super(mbv);
    }

	@Override
	public void validate() throws MXException, RemoteException{
	    
	    boolean valor;
	    valor = Uteis.isApenasNumeros(getMboValue().getString());
	    System.out.println("###############getMboValue().getString() = " + getMboValue().getString());
	    System.out.println("###############valor = " + valor);
	    
	    if (!valor) {
	    	throw new MXApplicationException("notafiscal", "DigiteApenasNumeros");
	    } else if (valor){
	    	getMboValue().setValue(getMboValue().getString().replaceAll("^0*", ""));
	    	System.out.println("###############valor = " + getMboValue().getString().replaceAll("^0*", ""));
	    }
	   
	    super.validate();
	  }
}