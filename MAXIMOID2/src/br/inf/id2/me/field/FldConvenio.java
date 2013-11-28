package br.inf.id2.me.field;

import java.rmi.RemoteException;
import br.inf.id2.common.field.*;

import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class FldConvenio extends NumeroMascaraAno {

    public FldConvenio(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	if(getMboValue().getMbo().getMboSet("VALIDA_CONVENIO").count() > 0)
    		throw new MXApplicationException("id2","ConvenioExiste");
    	super.validate();
    }

	@Override
	public void action() throws MXException, RemoteException {
    	if(getMboValue().getMbo().getMboSet("VALIDA_CONVENIO").count() > 0)
    		throw new MXApplicationException("id2","ConvenioExiste");
		super.action();
	}

}
