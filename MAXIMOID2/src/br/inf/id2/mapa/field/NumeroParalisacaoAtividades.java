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

public class NumeroParalisacaoAtividades extends MboValueAdapter
{

	public NumeroParalisacaoAtividades(MboValue mbv) {
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
        		getMboValue().getMbo().getString("MATBPARATIID") != null) {
	        	if (!getMboValue().getMbo().getString("ID2NUMPROTOCOLO").startsWith("SPA")) {
		        	String valor = "SPA." + Uteis.retiraCaracteresEspeciais(Uteis.adicionaValorEsquerda(
		        								getMboValue().getMbo().getString("MATBPARATIID"),
		        								"0", 9))
		        						  + "."
		        						  + new GregorianCalendar().get(GregorianCalendar.YEAR);
		        	
		        	getMboValue().getMbo().setValue("ID2NUMPROTOCOLO",valor);
		        	getMboValue().getMbo().getThisMboSet().save();
	        	}
        	}
        } catch (RemoteException re) {
        	System.out.println("######## Excecao ao definir o valor de ID2NUMPROTOCOLO: " + re.getMessage());
        }
	}
	
}