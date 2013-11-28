package psdi.id2.seedf;

import psdi.id2.Validar;
import psdi.mbo.*;


import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class ID2FldCNPJ extends MboValueAdapter {

    private final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    /**
     *
     * @param mbv
     * @throws MXException
     */
    public ID2FldCNPJ(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    /**
     *
     * @throws MXException
     * @throws RemoteException
     */
    public void validate()
            throws MXException, RemoteException {
        super.validate();
        String param[] = {new String()};
        if (Validar.CNPJ(getMboValue().getString())) {
            return;
        } else {
            throw new MXApplicationException("company", "CNPJInvalidoFLDCNPJ", param);
        }
    }
}
