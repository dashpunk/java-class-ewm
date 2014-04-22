package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Andre Luiz. 
 * Classe de validacao de campos SVS
 */
public class MsPrecoTotalCotacao extends MboValueAdapter{
	
	public MsPrecoTotalCotacao(MboValue mbv) throws MXException {
        super(mbv);
    }
	
	@Override
	public void validate() throws MXException, RemoteException{
		
		System.out.print("-------------  Entrou na classe MsPrecoTotalCotacao");
		super.validate();
		
			System.out.print("-------------  Valor Total antes da operacao:" + getMboValue().getMbo().getDouble("MSNUMPREC"));
			System.out.print("-------------  Quantidade:" + getMboValue().getMbo().getDouble("MSNUMQNT"));
			getMboValue().getMbo().setValue("MSPRECTOTAL", getMboValue().getMbo().getDouble("MSNUMPREC") * getMboValue().getMbo().getDouble("MSNUMQNT"));
			System.out.print("-------------  Preço Total:" + getMboValue().getMbo().getDouble("MSNUMPREC") * getMboValue().getMbo().getDouble("MSNUMQNT"));	      	
			    
	  }

}

