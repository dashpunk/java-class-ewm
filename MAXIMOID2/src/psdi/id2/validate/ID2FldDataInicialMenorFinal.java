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
public class ID2FldDataInicialMenorFinal extends psdi.app.location.FldLocLocation {

    public ID2FldDataInicialMenorFinal(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        Date valor = getMboValue().getDate();
        super.validate();
        Date dataAtual = new Date();
        if (!dataAtual.after(valor)) {
            throw new MXApplicationException("company", "DataNascimentoMaiorQueAtual");
        }
    }
}
