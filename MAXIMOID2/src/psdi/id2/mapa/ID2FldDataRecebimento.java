package psdi.id2.mapa;

import psdi.mbo.*;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.id2.Uteis;

/**
 * @author Ricardo S Gomes
 */
public class ID2FldDataRecebimento extends MboValueAdapter {

    public ID2FldDataRecebimento(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException{
	    Date data = new Date();
	    SimpleDateFormat formatador = new SimpleDateFormat("MMyyyy");
	    String mesAnoAtual = formatador.format(data);
	    int mes = Integer.valueOf(mesAnoAtual.substring(0, 2)).intValue();
	    int ano = Integer.valueOf(mesAnoAtual.substring(2, 6)).intValue();

	    String valor = new String();
	    valor = Uteis.getApenasNumeros(getMboValue().getString());

	    if ((valor.length() < 5) || (valor.length() > 6)) {
	      throw new MXApplicationException("company", "DataRecebimentoInvalida");
	    }

	    if (valor.length() == 5) {
	      valor = "0" + valor;
	    }

	    if ((Integer.valueOf(valor.substring(0, 2)).intValue() > 12) || (Integer.valueOf(valor.substring(0, 2)).intValue() < 1)) {
	      throw new MXApplicationException("company", "DataRecebimentoInvalida");
	    }

	    if (((Integer.valueOf(valor.substring(0, 2)).intValue() >= mes) && (Integer.valueOf(valor.substring(2, 6)).intValue() >= ano)) || 
	      (Integer.valueOf(valor.substring(2, 6)).intValue() > ano))
	    {
	      getMboValue().setValue(Uteis.getValorMascarado("##/####", valor, false));
	    }
	    else {
	      throw new MXApplicationException("company", "DataRecebimentoInvalida");
	    }

	    Mbo mboOwner = (Mbo)getMboValue().getMbo().getOwner();
	    int anoDemanda = mboOwner.getInt("ID2PERIODO");
	    System.out.println("############### Data Demanda:" + anoDemanda);
	    System.out.println("############### Data Recebimento:" + ano);
	    if (ano < anoDemanda) {
	      throw new MXApplicationException("demandas", "AnoRecMenorQueAnoDemanda");
	    }

	    super.validate();
	  }
}
