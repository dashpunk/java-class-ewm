package br.inf.ctis.ms.field;

import psdi.mbo.*;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.id2.Uteis;

/**
*
* @author Marcelo Lima
* 
*/

public class MesMaiorIgualJunho extends MboValueAdapter {

	public MesMaiorIgualJunho(MboValue mbv) throws MXException {
        super(mbv);
    }

	@Override
	public void validate() throws MXException, RemoteException{
	    
	    Mbo mboOwner = (Mbo)getMboValue().getMbo().getOwner();
	    int anoDemanda = mboOwner.getInt("ID2PERIODO");
	    System.out.println("############### Data Demanda:" + anoDemanda);
	    
	    String valor = new String();
	    valor = Uteis.getApenasNumeros(getMboValue().getString());
	    System.out.println("###############getMboValue().getString() = " + getMboValue().getString());
	    System.out.println("###############valor = " + valor);

	    if ((valor.length() < 5) || (valor.length() > 6)) {
	      throw new MXApplicationException("demandas", "DataPrimeiraEntregaInvalida");
	    }

	    if (valor.length() == 5) {
	      valor = "0" + valor;
	    }

	    if ((Integer.valueOf(valor.substring(0, 2)).intValue() > 12) || (Integer.valueOf(valor.substring(0, 2)).intValue() < 1)) {
	      throw new MXApplicationException("demandas", "MesPrimeiraEntregaInvalido");
	    }
	    
	    if ((Integer.valueOf(valor.substring(0, 2)).intValue() < 6)) {
		      throw new MXApplicationException("demandas", "MesPrimeiraEntregaJunho");
		}
	    
	    if (Integer.valueOf(valor.substring(2, 6)).intValue() < anoDemanda) {
		      throw new MXApplicationException("demandas", "AnoPrimeiraEntregaMenorAnoDemanda");
		}

	    if (((Integer.valueOf(valor.substring(0, 2)).intValue() >= 6) && (Integer.valueOf(valor.substring(2, 6)).intValue() >= anoDemanda)) || 
	      (Integer.valueOf(valor.substring(2, 6)).intValue() > anoDemanda))
	    {
	      getMboValue().setValue(Uteis.getValorMascarado("##/####", valor, false));
	    }
	    else {
	      throw new MXApplicationException("demandas", "DataPrimeiraEntregaMascaraInvalida");
	    }

	    super.validate();
	  }
}
