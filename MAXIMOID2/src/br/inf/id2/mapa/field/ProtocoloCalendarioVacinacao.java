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

public class ProtocoloCalendarioVacinacao extends MboValueAdapter
{

	public ProtocoloCalendarioVacinacao(MboValue mbv) {
		super(mbv);
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		define();
	}
	
	private void define() throws MXException {
        try {
        	if (!getMboValue().getMbo().getString("MACALENID").startsWith("EVC")) {
	        	String valor = "EVC " + Uteis.adicionaValorEsquerda(
	        								getMboValue().getMbo().getString("MACALENID"),
	        								"0", 9)
	        						  + "/"
	        						  + new GregorianCalendar().get(GregorianCalendar.YEAR);
	        	
	        	getMboValue().getMbo().setValue("MACALENID",valor);
        	}
        } catch (RemoteException re) {
        	System.out.println("######## Exceção ao definir o valor de MACALENID: " + re.getMessage());
        }
	}
	
}