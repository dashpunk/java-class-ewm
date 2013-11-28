package br.inf.id2.me.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.field.*;

public class FldProposta extends NumeroMascaraAno {

    public FldProposta(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	if(getMboValue().getMbo().getMboSet("VALIDA_PROPOSTA").count() > 0)
    		throw new MXApplicationException("id2","PropostaExiste");
    	super.validate();
    }

	@Override
	public void action() throws MXException, RemoteException {
    	if(getMboValue().getMbo().getMboSet("VALIDA_PROPOSTA").count() > 0)
    		throw new MXApplicationException("id2","PropostaExiste");
		super.action();
	}

}
