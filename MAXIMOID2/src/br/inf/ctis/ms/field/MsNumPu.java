/**
 * Classe do SVS
 * Calcula o Preço Unitario Baseado no FOB e Quantidade
 */
package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author willians.andrade
 * @author rafael.morais
 */

public class MsNumPu extends MboValueAdapter {

	public MsNumPu(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		
		System.out.print("********** Entrou na classe MsNumPu.");
		super.validate();

		MboRemote MSTBCOTSVS = getMboValue().getMbo();
		
		if ((!MSTBCOTSVS.isNull("MSNUMFOBUSD")) && (MSTBCOTSVS.getFloat("MSNUMQNT") != 0.0F))
	    {
	      float ValorQnt = MSTBCOTSVS.getFloat("MSNUMQNT");
	      float FobUSD = MSTBCOTSVS.getFloat("MSNUMFOBUSD");

	      float ValorUnit = FobUSD / ValorQnt;

	      MSTBCOTSVS.setValue("MSNUMPU", ValorUnit);
	    }
		
		System.out.print("-------------- Antes do IF de validacao Qtd por Insumo");
		if((MSTBCOTSVS.getFloat("MSNUMQNT") != 0.0F) && (!MSTBCOTSVS.getMboSet("MSTBINSUMOS").isNull("MSNUMQNT"))){
		System.out.print("-------------- Dentro do IF . Valor da quantidade Cotacao:"+MSTBCOTSVS.getFloat("MSNUMQNT"));
		System.out.print("-------------- Dentro do IF . Valor da Quantidade Insumos:"+MSTBCOTSVS.getMboSet("MSTBINSUMOS").getMbo(0).getInt("MSNUMQNT"));
			if (MSTBCOTSVS.getFloat("MSNUMQNT") > MSTBCOTSVS.getMboSet("MSTBINSUMOS").getMbo(0).getInt("MSNUMQNT")) {
				throw new MXApplicationException("generica", "Quantidade superior a do Insumo");
			}
		
			
			
		}
	    if (MSTBCOTSVS.getFloat("MSNUMQNT") == 0.0F)
	      MSTBCOTSVS.setValue("MSNUMPU", "");
	  }
}
