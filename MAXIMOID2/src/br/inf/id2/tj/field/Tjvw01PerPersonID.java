package br.inf.id2.tj.field;

import br.inf.id2.common.util.Validar;
import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class Tjvw01PerPersonID extends MboValueAdapter {

    public Tjvw01PerPersonID(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        if (getMboValue().getMbo().getString("EMPLOYEETYPE").equalsIgnoreCase("EXTERNO")) {
            String valor = new String();
            valor = getMboValue().getString();
            String param[] = {valor};
            if (!Validar.CPF(valor)) {
                throw new MXApplicationException("company", "CPFInvalido", param);
            }
        }

    }
}
