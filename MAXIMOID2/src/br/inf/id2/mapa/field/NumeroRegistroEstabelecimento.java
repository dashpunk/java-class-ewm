package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;

/**
 * 
 * @author Dyogo
 *
 */

public class NumeroRegistroEstabelecimento extends MboValueAdapter
{

	public NumeroRegistroEstabelecimento(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void initValue() throws MXException, RemoteException {
		super.initValue();
		define();
	}
	
	private void define() throws MXException {
        try {
        	if (getMboValue().getMbo().getString("ID2NUMPROTOCOLO") != null &&
        		getMboValue().getMbo().getString("LOCATION") != null) {
	        	if (!getMboValue().getMbo().getString("ID2NUMPROTOCOLO").startsWith("SVT")) {
		        	String valor = "SVT." + Uteis.adicionaValorEsquerda(
		        								getMboValue().getMbo().getString("LOCATION"),
		        								"0", 9)
		        						  + "."
		        						  + new GregorianCalendar().get(GregorianCalendar.YEAR);
		        	
		        	getMboValue().getMbo().setValue("ID2NUMPROTOCOLO",valor);
	        	}
        	}
        } catch (RemoteException re) {
        	System.out.println("######## Exceção ao definir o valor de ID2NUMPROTOCOLO: " + re.getMessage());
        }
	}
	
}