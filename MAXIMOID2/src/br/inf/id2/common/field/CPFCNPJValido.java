package br.inf.id2.common.field;

import br.inf.id2.common.util.Validar;
import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class CPFCNPJValido extends MboValueAdapter {

    public CPFCNPJValido(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        String valor = new String();
        valor = getMboValue().getString();
        super.validate();
        String param[] = {valor};
        if ((!Validar.CPF(valor)) && (!Validar.CNPJ(valor))) {
            throw new MXApplicationException("company", "CPFCNPJInvalido");
        }
    }

}