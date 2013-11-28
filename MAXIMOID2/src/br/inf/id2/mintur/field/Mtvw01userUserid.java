package br.inf.id2.mintur.field;

import br.inf.id2.common.util.Uteis;
import br.inf.id2.common.util.Validar;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class Mtvw01userUserid extends psdi.app.signature.FldMaxUserUserID {

    public Mtvw01userUserid(MboValue mbv) throws MXException {
        super(mbv);
    }
    

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        if (!Validar.CPF(Uteis.getApenasNumeros(getMboValue().getString()))) {
            throw new MXApplicationException("id2message", "CpfInvalido");
        }
        super.validate();
    }

}
