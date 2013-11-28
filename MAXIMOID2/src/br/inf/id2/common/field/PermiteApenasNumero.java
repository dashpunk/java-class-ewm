package br.inf.id2.common.field;

import br.inf.id2.common.util.Uteis;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author Dyogo Dantas
 */
public class PermiteApenasNumero extends MboValueAdapter {

	public PermiteApenasNumero(MboValue mbv) throws MXException {
		super(mbv);
	}

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        if (!Uteis.isApenasNumero(getMboValue().getString())) {
        	getMboValue().setValueNull();
        	throw new MXApplicationException("validacao", "CampoDigitadoNaoNumerico");
        }
    }

}
