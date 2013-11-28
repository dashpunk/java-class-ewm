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
 * @author Ricardo S Gomes
 * 
 */
public class PersonBGNuNumIdade extends MboValueAdapter {

    public PersonBGNuNumIdade(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("--- PersonBGNuNumIdade");
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        System.out.println("--- PersonBGNuNumIdade.initValue()");
        super.initValue();
        if (getMboValue("BIRTHDATE").isNull()) {
            return;
        }
        int meses = Data.getMeses(getMboValue("BIRTHDATE").getDate());
        System.out.println("--- PersonBGNuNumIdade.initValue() meses "+meses);
        Integer anos = meses / 12;
        System.out.println("--- PersonBGNuNumIdade.initValue() anos "+anos);
        getMboValue("BGNUNUMIDADE").setValue(anos, MboConstants.NOACCESSCHECK);
        System.out.println("--- PersonBGNuNumIdade.initValue() FIM");
    }
}
