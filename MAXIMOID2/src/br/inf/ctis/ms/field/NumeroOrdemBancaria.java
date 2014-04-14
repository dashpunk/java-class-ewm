package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Calendar;

import psdi.id2.Uteis;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class NumeroOrdemBancaria extends MboValueAdapter {

	/**
	 * @author marcelosydney.lima
	 */
	
	public NumeroOrdemBancaria(MboValue mbv) throws MXException {
        super(mbv);
    }

	@Override
	public void validate() throws MXException, RemoteException{
	    
	    String valor = new String();
	    valor = Uteis.getApenasNumeros(getMboValue().getString());
	    System.out.println("###############getMboValue().getString() = " + getMboValue().getString());
	    System.out.println("###############valor = " + valor);
	    
	    if ((valor.length() < 10) || (valor.length() > 10)) {
	    	throw new MXApplicationException("ordembancaria", "MascaraInvalida");
	    }
	    
	    System.out.println("###############getMboValue().getString().substring(4, 6) = " + getMboValue().getString().substring(4, 6));
	    if (!getMboValue().getString().substring(4, 6).equalsIgnoreCase("OB")) {
	    	throw new MXApplicationException("ordembancaria", "MascaraInvalida");
	    }
	    
	    Calendar cAtual = Calendar.getInstance();        
	    
	    System.out.println("###############valor.substring(0, 4)" + valor.substring(0, 4));
        if (/*Integer.valueOf(cAtual.get(Calendar.YEAR)) <= Integer.valueOf(valor.substring(0, 4)).intValue()*/ !getMboValue().isNull()) {
        	getMboValue().setValue(Uteis.getValorMascarado("####OB######", valor, false));
        }
	    else {
	      throw new MXApplicationException("ordembancaria", "AnoInvalido");
	    }

	    super.validate();
	  }
}