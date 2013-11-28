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

public class ProtocoloRequisicaoElementos extends MboValueAdapter
{

	public ProtocoloRequisicaoElementos(MboValue mbv) {
		super(mbv);
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		define();
	}
	
	private void define() throws MXException {
        try {
        	if (!getMboValue().getMbo().getString("PRNUM").startsWith("REI")) {
	        	String valor = "REI." + Uteis.adicionaValorEsquerda(
	        								getMboValue().getMbo().getString("PRNUM"),
	        								"0", 9)
	        						  + "."
	        						  + new GregorianCalendar().get(GregorianCalendar.YEAR);
	        	
	        	getMboValue().getMbo().setValue("PRNUM",valor);
        	}
        } catch (RemoteException re) {
        	System.out.println("######## Exce��o ao definir o valor de PRNUM: " + re.getMessage());
        }
	}
	
}