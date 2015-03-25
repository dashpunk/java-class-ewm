package br.inf.ctis.common.field;

import java.rmi.RemoteException;
import java.util.Calendar;

import psdi.id2.Uteis;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MesBarraAnoMaiorQueAtual extends MboValueAdapter{
	
	public MesBarraAnoMaiorQueAtual(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();
		
		System.out.println("########## Valor do campo: " + getMboValue().getString());
		String valor = Uteis.getApenasNumeros(getMboValue().getString());
		System.out.println("########## valor: " + valor);
		
		if ((valor.length() != 6)) {
	    	throw new MXApplicationException("generica", "MascaraInvalida");
	    }

	    if (valor.length() == 5) {
	    	valor = "0" + valor;
	    }
	    
	    Calendar cAtual = Calendar.getInstance();        

        if (Integer.valueOf(cAtual.get(Calendar.YEAR)) > Integer.valueOf(valor.substring(3, 7)).intValue()) {
        	getMboValue().setValue(Uteis.getValorMascarado("##/####", valor, false));
        } else if (Integer.valueOf(cAtual.get(Calendar.YEAR)) == Integer.valueOf(valor.substring(2, 6)).intValue() && (Integer.valueOf(cAtual.get(Calendar.MONTH))+1) > Integer.valueOf(valor.substring(0, 2)).intValue()) {
        	getMboValue().setValue(Uteis.getValorMascarado("##/####", valor, false));
	    } else {
	      throw new MXApplicationException("generica", "DataInseridaMenorQueAtual");
	    }
	
	}

}
