package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;

/**
* ID2 Tecnologia S.A.
*
* <h4>Descrição da Classe</h4>
*    - Classe criada para atender a demanda de aplicar uma máscara no campo número do protocolo.
*    
* 
* <h4>Notas</h4>
*    - 
*    
* 
* <h4>Referências</h4>
*    - Classe aplicada na tela macalen
* 
* <h4>Revisões</h4>
*    - Revisão 1.0 - @author Leysson Moreira: Classe inicialmente criada.
* 
* 
* @since 23/02/2012 16:40
* @version 1.0
* 
* 
*/

public class NumeroProtocolo extends MboValueAdapter
{

	public NumeroProtocolo(MboValue mbv) {
		super(mbv);
	}
	
	@Override
	public void action() throws MXException, RemoteException {
		super.action();
		define();
	}
	
	private void define() throws MXException {
        try {
        	if (!getMboValue().getMbo().getString("MACALEN").startsWith("CVA")) {
	        	String valor = "CVA." + Uteis.adicionaValorEsquerda(
	        								getMboValue().getMbo().getString("MACALEN"),
	        								"0", 9)
	        						  + "."
	        						  + new GregorianCalendar().get(GregorianCalendar.YEAR);
	        	
	        	getMboValue().getMbo().setValue("MACALEN",valor);
        	}
        } catch (RemoteException re) {
        	System.out.println("######## Exceção ao definir o valor de MACALENID: " + re.getMessage());
        }
	}
	
}