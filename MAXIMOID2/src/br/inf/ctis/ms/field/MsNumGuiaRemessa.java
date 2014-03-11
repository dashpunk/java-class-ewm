/**
 * Classe criada para atender a demanda de aplicar uma máscara no campo número da guia de remessa.
 * Classe aplicada na tela MSREMESSA
 */
package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

import br.inf.id2.common.util.Uteis;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author willians.andrade
 *
 */
public class MsNumGuiaRemessa extends MboValueAdapter{

	public MsNumGuiaRemessa(MboValue mbv) {
		super(mbv);
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		define();
	}	
	
	private void define() throws MXException {
        try {
        	System.out.println("Entrou na Classe GUIA REMESSA FIELD");
        	if (!getMboValue().getMbo().getString("MSCODREMESSA").startsWith("GUIA.")) {
	        	String valor =  "GUIA." +  Uteis.adicionaValorEsquerda(getMboValue().getMbo().getString("MSCODREMESSA"),"0", 9)
	        					+ "/"
	        					+ new GregorianCalendar().get(GregorianCalendar.YEAR);
	        	
	        	System.out.println("Valor" + valor);
	        	
	        	getMboValue().getMbo().setValue("MSCODREMESSA",valor, MboConstants.NOACCESSCHECK);
	        	System.out.println("Setou Valor");
        	}
        } catch (RemoteException re) {
        	System.out.println("######## Exceção ao definir o valor de MSCODREMESSA: " + re.getMessage());
        }
	}	
}
