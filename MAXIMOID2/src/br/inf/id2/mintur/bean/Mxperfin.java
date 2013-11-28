package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class Mxperfin extends psdi.webclient.system.beans.AppBean {

    /**
     *
     */
    public Mxperfin() {
    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        if (!getMbo().isNull("FINANCIALPERIOD")) {
            String valor = getMbo().getString("FINANCIALPERIOD");
            int mes = Integer.valueOf(valor.substring(0, 2));
            int ano = Integer.valueOf(valor.substring(3, 7));
            System.out.println(ano);
            if (mes > 12 || mes < 1) {
                throw new MXApplicationException("id2message", "mesInvalido");
            }
            if (ano > 2080 || ano < 1950) {
                throw new MXApplicationException("id2message", "anoInvalido");
            }
        }
        return super.SAVE();
    }
}
