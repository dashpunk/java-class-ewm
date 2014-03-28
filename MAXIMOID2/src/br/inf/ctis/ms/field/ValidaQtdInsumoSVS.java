package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author Andre Luiz. 
 * Classe de validacao de campos SVS
 */
public class ValidaQtdInsumoSVS extends MboValueAdapter{
	
	public ValidaQtdInsumoSVS(MboValue mbv) throws MXException {
        super(mbv);
    }
	
	@Override
	public void validate() throws MXException, RemoteException{
		
		System.out.print("********** Entrou na classe ValidaQtdInsumoSVS.");
		super.validate();
		
		int qtdTotal =0;
	      
		for (int i = 0; i < getMboValue().getMbo().getThisMboSet().count();i++){
			
			int qtdCotacao = getMboValue().getMbo().getInt("MSNUMQNT");
			int qtdInsumo = getMboValue().getMbo().getMboSet("MSTBINSUMOS").getMbo().getInt("MSNUMQNT");
						
			
			qtdTotal += qtdCotacao;		
			
			 if (qtdTotal > qtdInsumo) {
			    	throw new MXApplicationException("generica", "Quantidade superior a do Insumo");
		     }	
		}
	   	    
	  }

}
