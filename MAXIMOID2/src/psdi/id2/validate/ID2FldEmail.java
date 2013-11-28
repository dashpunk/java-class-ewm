package psdi.id2.validate;

import psdi.mbo.*;
import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldEmail extends psdi.app.location.FldLocLocation {

    public ID2FldEmail(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        String valor = getMboValue().getString();
        super.validate();
        String param[] = {valor};
        if (valor.indexOf("@") == -1) {
            throw new MXApplicationException("company", "EmailInvalido", param);
        }
    }
}
