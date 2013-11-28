package br.inf.id2.mapa.field;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 */
public class MatrectransReceiptquantity extends psdi.app.inventory.FldMatRecTransReceiptQuantity {

    public MatrectransReceiptquantity(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	
    	int valor = getMboValue().getInt();
    	int comparacao = getMboValue().getMbo().getMboSet("MARLPOLINE").getMbo(0).getInt("ORDERQTY"); //getMboValue().getMbo().getInt("ORDERQTY");
    	
    	System.out.println("*** valor "+valor);
    	System.out.println("*** comparacao "+comparacao);
    	
    	if(valor > comparacao){
    		System.out.println("*** IF ");
    		getMboValue().setValue(0,MboConstants.NOVALIDATION_AND_NOACTION);
    		System.out.println("*** getMboValue() "+getMboValue().getInt());
    		throw new MXApplicationException("data", "valorMaiorQueInformado");
    	}
    }

}
