package br.inf.id2.mintur.field;

import br.inf.id2.common.util.Data;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author ricardo
 */
public class RHTbAfas01RHNuNumDias extends MboValueAdapter {

    public RHTbAfas01RHNuNumDias(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("---- RHTbAfas01RHNuNumDias");
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        calculaDias();
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        calculaDias();
    }

    private void calculaDias() throws MXException, RemoteException  {
        System.out.println("---- RHTbAfas01RHNuNumDias.validade()");
        if (getMboValue("RHDTDTAINICIO").isNull() || getMboValue("RHDTDTATERMINO").isNull()) {
            System.out.println("---- RHTbAfas01RHNuNumDias.validade() null");
            getMboValue("RHNUNUMDIAS").setValueNull(MboConstants.NOACCESSCHECK);
        } else {
            System.out.println("---- RHTbAfas01RHNuNumDias.validade() not null");
            int dias = Data.recuperaDiasEntreDatas(getMboValue("RHDTDTAINICIO").getDate(), getMboValue("RHDTDTATERMINO").getDate()) + 1;
            System.out.println("---- RHTbAfas01RHNuNumDias.validade() dias = " + dias);
            getMboValue("RHNUNUMDIAS").setValue(dias, MboConstants.NOACCESSCHECK);

        }
        System.out.println("---- RHTbAfas01RHNuNumDias.validade() FIM");
    }
}
