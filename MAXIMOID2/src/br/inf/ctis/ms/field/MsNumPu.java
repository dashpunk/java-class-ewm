/**
 * Classe do SVS
 * Calcula o Preço Unitario Baseado no FOB e Quantidade
 */
package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
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
		
		System.out.println("********** Entrou na classe MsNumPu.");
		super.validate();

		MboRemote MSTBCOTSVS = getMboValue().getMbo();
		
		if ((!MSTBCOTSVS.isNull("MSNUMFOBUSD")) && (MSTBCOTSVS.getFloat("MSNUMQNT") != 0.0F))
	    {
	      float ValorQnt = MSTBCOTSVS.getFloat("MSNUMQNT");
	      float FobUSD = MSTBCOTSVS.getFloat("MSNUMFOBUSD");

	      float ValorUnit = FobUSD / ValorQnt;

	      MSTBCOTSVS.setValue("MSNUMPU", ValorUnit);
	      
	      
	      float qtdTotal =0;
	      
	      for (int i = 0; i < MSTBCOTSVS.getThisMboSet().count();i++){
	    	  Mbo mboValor = (Mbo) ((MboSetRemote) MSTBCOTSVS).getMbo(i);
	    	  System.out.println("-------------- Valor do campo qtd por registro"+mboValor);
	    	  qtdTotal += mboValor.getFloat("MSNUMQNT");
	    	  System.out.println("-------------- Somatorio da Qtd p/ comparar com insumo:"+qtdTotal);
	      }	      
	      if (qtdTotal > MSTBCOTSVS.getMboSet("MSTBINSUMOS").getMbo(0).getInt("MSNUMQNT")) {
	    	    System.out.println("-------------- IF de validacao Qtd por Insumo");
				throw new MXApplicationException("generica", "Quantidade superior a do Insumo");
			}
	      
	    }		
		
	    if (MSTBCOTSVS.getFloat("MSNUMQNT") == 0.0F)
	      MSTBCOTSVS.setValue("MSNUMPU", "");
	  }
}

