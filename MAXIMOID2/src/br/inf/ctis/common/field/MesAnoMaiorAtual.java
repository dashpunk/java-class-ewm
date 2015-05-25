package br.inf.ctis.common.field;

import java.rmi.RemoteException;
import java.util.Calendar;

import psdi.id2.Uteis;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MesAnoMaiorAtual  extends MboValueAdapter {

	public MesAnoMaiorAtual(MboValue mbv) throws MXException {
        super(mbv);
    }

	@Override
	public void validate() throws MXException, RemoteException{
		 	
		String valor = new String();
	    valor = Uteis.getApenasNumeros(getMboValue().getString());
	    System.out.println("###############getMboValue().getString() = " + getMboValue().getString());
	    System.out.println("###############valor = " + valor);
	
		if ((valor.length() < 5) || (valor.length() > 6)) {
            throw new MXApplicationException("generica", "MascaraInvalida");
		}
		
		if (valor.length() == 5) {
			valor = "0" + valor;
	    }
	    
	    Calendar cAtual = Calendar.getInstance();        
	
		if (Integer.valueOf(cAtual.get(Calendar.YEAR)) < Integer.valueOf(valor.substring(2, 6)).intValue()) {
			getMboValue().setValue(Uteis.getValorMascarado("##/####", valor, false));
		} else if (Integer.valueOf(cAtual.get(Calendar.YEAR)) == Integer.valueOf(valor.substring(2, 6)).intValue()) {
			if(Integer.valueOf(cAtual.get(Calendar.MONTH)+1) <= Integer.valueOf(valor.substring(0, 2)).intValue()) {
				getMboValue().setValue(Uteis.getValorMascarado("##/####", valor, false));
			} else {
				throw new MXApplicationException("generica", "MesInvalido");
			}
		} else {
	    	throw new MXApplicationException("generica", "AnoInvalido");
	    }
	
	    super.validate();
	}
}
