package br.inf.id2.tj.field;

import br.inf.id2.common.util.Validar;
import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class LaborLaborCode extends psdi.app.labor.FldLaborLaborcode {

    public LaborLaborCode(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        String valor = new String();
        valor = getMboValue().getString();
        String param[] = {valor};
        if (!Validar.CPF(valor)) {
            throw new MXApplicationException("company", "CPFInvalido", param);
        }

    }
}
