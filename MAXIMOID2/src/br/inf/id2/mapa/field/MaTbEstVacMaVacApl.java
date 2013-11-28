package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 * 
 */
public class MaTbEstVacMaVacApl extends MboValueAdapter {

    public MaTbEstVacMaVacApl(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        int valor1 = getMboValue().getMbo().getInt("CURBAL");
        int valor2 = getMboValue().getMbo().getInt("MAVACAPL");

        if (valor2 > valor1) {
            throw new MXApplicationException("marbestvac", "mavacaplMaiorQueCurbal");
        }

    }
}
