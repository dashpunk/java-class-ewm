package psdi.id2.validate;

import psdi.mbo.*;
import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import java.util.Date;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldBirthDate extends psdi.app.location.FldLocLocation {

    public ID2FldBirthDate(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        Date valor = getMboValue().getDate();
        super.validate();
        Date dataAtual = new Date();
        if (dataAtual.after(valor)) {
            ;
        } else {
            //busca mensagem do maximo, grupo mensagem e chave mensagem
            throw new MXApplicationException("company", "DataNascimentoMaiorQueAtual");
        }
    }
}
