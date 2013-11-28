package br.inf.id2.common.field;

import br.inf.id2.common.util.Uteis;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Wilians Andrade
 */
public class ApenasNumero extends MboValueAdapter {

	public ApenasNumero(MboValue mbv) throws MXException {
		super(mbv);
	}

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        String valor = Uteis.getApenasNumeros(getMboValue().getString());
        getMboValue().setValue(valor, MboConstants.NOVALIDATION_AND_NOACTION);
    }

}
