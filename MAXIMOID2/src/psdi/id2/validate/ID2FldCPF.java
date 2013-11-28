package psdi.id2.validate;

import psdi.mbo.*;
import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;
import psdi.id2.Validar;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldCPF extends psdi.app.location.FldLocLocation {

    public ID2FldCPF(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        String valor = new String();
        valor = getMboValue().getString();
        super.validate();
        String param[] = {valor};
        if (!Validar.CPF(valor)) {
            throw new MXApplicationException("company", "CPFInvalido", param);
        }
    }
}
