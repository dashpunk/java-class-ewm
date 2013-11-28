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

public class ProtocoloComunicadoVacinacao extends MboValueAdapter
{

	public ProtocoloComunicadoVacinacao(MboValue mbv) {
		super(mbv);
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		define();
	}
	
	private void define() throws MXException {
        try {
        	if (!getMboValue().getMbo().getString("ID2VACEXPPECID").startsWith("CVC")) {
	        	String valor = "CVC " + Uteis.adicionaValorEsquerda(
	        								getMboValue().getMbo().getString("ID2VACEXPPECID"),
	        								"0", 9)
	        						  + "/"
	        						  + new GregorianCalendar().get(GregorianCalendar.YEAR);
	        	
	        	getMboValue().getMbo().setValue("ID2VACEXPPECID",valor);
        	}
        } catch (RemoteException re) {
        	System.out.println("######## Exceção ao definir o valor de ID2VACEXPPECID: " + re.getMessage());
        }
	}
	
}