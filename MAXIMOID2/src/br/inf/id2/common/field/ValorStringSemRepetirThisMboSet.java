package br.inf.id2.common.field;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class ValorStringSemRepetirThisMboSet extends MboValueAdapter {

    public ValorStringSemRepetirThisMboSet(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        MboRemote mbo;
        int registroCorrente = getMboValue().getMbo().getThisMboSet().getCurrentPosition();
        String campo = getMboValue().getName();
        for (int i = 0; ((mbo = getMboValue().getMbo().getThisMboSet().getMbo(i)) != null); i++) {
            if (i != registroCorrente && mbo.getString(campo).equalsIgnoreCase(getMboValue().getString())) {
                throw new MXApplicationException("id2message", "valorStringDuplicadoThisMboSet");
            }

        }

        super.validate();

    }
}
