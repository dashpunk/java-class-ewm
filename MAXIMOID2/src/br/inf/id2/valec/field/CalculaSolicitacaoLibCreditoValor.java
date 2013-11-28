package br.inf.id2.valec.field;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class CalculaSolicitacaoLibCreditoValor extends MboValueAdapter {
	
	public CalculaSolicitacaoLibCreditoValor(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        String attributeName = "";

        System.out.println("############# Validate - CalculaSolicitacaoLibCreditoValor ");
        Double baseCalculo = getMboValue().getMbo().getDouble("MXBASE");
        Double valor = getMboValue().getMbo().getDouble("MXVALOR");
        
        Double resultado = 0.0;
        
    	resultado = baseCalculo / valor;
    	attributeName = "MXPORCE";
    	
    	System.out.println("############## Antes do setValue");
    	getMboValue().getMbo().setValue(attributeName, resultado, MboConstants.NOVALIDATION_AND_NOACTION);        
        
    	super.validate();
    }
}
