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
public class RHTbFerias01RHNuNumTotalDias extends MboValueAdapter {

    public RHTbFerias01RHNuNumTotalDias(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("---- RHTbFerias01RHNuNumTotalDias");
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        System.out.println("---- RHTbFerias01RHNuNumTotalDias.validade()");
        if (getMboValue().isNull()) {
            return;
        }

        if (getMboValue().getInt() < 1 || getMboValue().getInt() > 30) {
            throw new MXApplicationException("ferias", "totalGozoFeriasInvalido");
        }
        System.out.println("---- RHTbFerias01RHNuNumTotalDias.validade() FIM");
    }
}
