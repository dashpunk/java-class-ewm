package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author marcelosydney.lima
 *  
 */

public class MsAlCodTipoProduto extends MboValueAdapter{
	
	public MsAlCodTipoProduto(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException{
		super.validate();

		String tipoproduto = getMboValue().getString();
		Double prazo = 0d;
		
		MboRemote mbo1;
		
		for (int i = 0; ((mbo1 = getMboValue().getMbo().getMboSet("MSPARAMETROSPRODUTO").getMbo(i)) !=null); i++) {
			if (mbo1.getString("VALUE").equalsIgnoreCase(tipoproduto)) {
				System.out.println("########## Tipo do Produto: " + tipoproduto + " ////// Campo em tela: " + mbo1.getString("VALUE"));
				
				if (mbo1.getString("MSALCODPARAMETROPRODUTO").equalsIgnoreCase("PRAZO")) {
					
					System.out.println("########## Prazo: " + mbo1.getDouble("MSNUNUMVALOR"));
					prazo = mbo1.getDouble("MSNUNUMVALOR");
				}
				System.out.println("########## prazo: " + prazo);
			}
			
		}
		
		getMboValue("MSNUNUMPRAZOPRODUTO").setValue(prazo);	
		
	}
}