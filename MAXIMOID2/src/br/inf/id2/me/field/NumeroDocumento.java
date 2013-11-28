package br.inf.id2.me.field;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;

/**
 * @author Patrick
 */
public class NumeroDocumento extends MboValueAdapter {


    public NumeroDocumento(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("*** NumeroDocumento ***");
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	System.out.println("*** validate");
    	System.out.println("*** ITDOCS - "+getMboValue("ITDOCS").getString());
    	
    	if(!getMboValue("ITDOCS").getString().equals("CPF")){
    		System.out.println("dentro do if");
    		String valor = Uteis.retiraCaracteresEspeciais(getMboValue().getString());
    		getMboValue().setValue(valor);
    	}else{
    		String nrDocumento = Uteis.getValorMascarado("999.999.999-99", Uteis.getApenasNumeros(getMboValue().getString()), true);
            getMboValue().setValue(nrDocumento, MboConstants.NOVALIDATION_AND_NOACTION);
    	}
    } 
}
