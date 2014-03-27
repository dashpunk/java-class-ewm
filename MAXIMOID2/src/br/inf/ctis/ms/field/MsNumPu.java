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
		
		float ValorQnt = MSTBCOTSVS.getFloat("MSNUMQNT");
		float FobUSD = MSTBCOTSVS.getFloat("MSNUMFOBUSD");
		float qtdInsumo = MSTBCOTSVS.getMboSet("MSTBINSUMOS").getInt("MSNUMQNT");

		if (!MSTBCOTSVS.isNull("MSNUMFOBUSD") && MSTBCOTSVS.getFloat("MSNUMQNT") != 0){

			//float ValorQnt = MSTBCOTSVS.getFloat("MSNUMQNT");
			//float FobUSD = MSTBCOTSVS.getFloat("MSNUMFOBUSD");
			
			float ValorUnit = FobUSD / ValorQnt;
			
			MSTBCOTSVS.setValue("MSNUMPU", ValorUnit);
		}
		
		if (MSTBCOTSVS.getFloat("MSNUMQNT") == 0){
			MSTBCOTSVS.setValue("MSNUMPU", "");
		}
		//Validacao da qtd de cotas em relacao a qtd insumo
		if (ValorQnt > qtdInsumo) {
		    	throw new MXApplicationException("generica", "Quantidade superior a do Insumo");
		    }
	}

}
