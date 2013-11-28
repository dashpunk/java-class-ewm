package br.inf.id2.me.field;

import br.inf.id2.common.util.Uteis;
import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class MEContaCorrente extends MboValueAdapter {

    public MEContaCorrente(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        //System.out.println("---> inicio MEContaCorrente");

        String contaCorrente = getMboValue().getMbo().getString("MECCNUM");
        String contaCorrenteDV = getMboValue().getMbo().getString("MECCDV");


        if ((!contaCorrente.equals("")) && (!contaCorrenteDV.equals(""))) {
            String digito = "" + Uteis.mod11(contaCorrente);
            if (!digito.equals(contaCorrenteDV)) {
                throw new MXApplicationException("company", "ContaDVInvalido");
            }
        }


        //System.out.println("---> FIM MEContaCorrente");
    }
}
