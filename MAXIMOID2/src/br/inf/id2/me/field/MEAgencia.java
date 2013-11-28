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
public class MEAgencia extends MboValueAdapter {

    public MEAgencia(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        //System.out.println("---> inicio MEAgencia");

        String agencia = getMboValue().getMbo().getString("ID2PBANCOAG");
        String agenciaDV = getMboValue().getMbo().getString("MEAGEDV");


        if ((!agencia.equals("")) && (!agenciaDV.equals(""))) {
            String digito = "" + Uteis.mod11(agencia);
            if (!digito.equals(agenciaDV)) {
                throw new MXApplicationException("company", "AgenciaDVInvalido");
            }
        }


        //System.out.println("---> FIM MEAgencia");
    }
}
