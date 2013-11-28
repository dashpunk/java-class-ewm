package br.inf.id2.mintur.field;

import br.inf.id2.common.util.Data;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author ricardo
 */
public class RHNuNumQntParcelas extends MboValueAdapter {

    public RHNuNumQntParcelas(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("---- RHNuNumQntParcelas");
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        System.out.println("---- RHNuNumQntParcelas.validade()");
        if (getMboValue().isNull()) {
            return;
        }

        if (getMboValue().getInt() < 1 || getMboValue().getInt() > 3) {
            throw new MXApplicationException("ferias", "totalParcelaFeriasInvalido");
        }
        System.out.println("---- RHNuNumQntParcelas.validade() FIM");
    }
}
