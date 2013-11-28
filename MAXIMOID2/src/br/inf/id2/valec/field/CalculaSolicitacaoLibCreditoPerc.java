package br.inf.id2.valec.field;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class CalculaSolicitacaoLibCreditoPerc extends MboValueAdapter {
	
	public CalculaSolicitacaoLibCreditoPerc(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        String attributeName = "";

        System.out.println("############# Validate - CalculaSolicitacaoLibCreditoPerc ");
        Double baseCalculo = getMboValue().getMbo().getDouble("MXBASE");
		Double percentual = getMboValue().getMbo().getDouble("MXPORCE");
        
        Double resultado = 0.0;
        
    	resultado = baseCalculo / 100 * percentual;
    	attributeName = "MXVALOR";
    	
    	System.out.println("############## Antes do setValue");
    	getMboValue().getMbo().setValue(attributeName, resultado, MboConstants.NOVALIDATION_AND_NOACTION);        
        
    	super.validate();
    }
}
