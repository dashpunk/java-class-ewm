package br.inf.id2.mapa.field;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class LimpaCampoSolicitacaoTransferencia extends MboValueAdapter {

    public LimpaCampoSolicitacaoTransferencia(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("*** LimpaCampoSolicitacaoTransferencia ***");
    }
    
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
    	super.validate();
    	System.out.println("*** validate");

    	if(!getMboValue().getBoolean()){
    		getMboValue("ID2NOVONOMRES").setValueNull();
    	}
    }
}
