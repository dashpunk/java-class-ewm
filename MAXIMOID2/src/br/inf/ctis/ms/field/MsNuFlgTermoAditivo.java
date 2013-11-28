package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 * 
 * @author marcelosydney.lima
 *  
 */
public class MsNuFlgTermoAditivo extends MboValueAdapter {

    public MsNuFlgTermoAditivo(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();

        if (getMboValue().getBoolean()) {
            getMboValue().getMbo().setValueNull("MSALCODMODALIDADE");
            getMboValue().getMbo().setValueNull("MSALNUMMODALIDADE");
        } else if (!getMboValue().getBoolean()) {
        	getMboValue().getMbo().setValueNull("MSALNUMINSTRUMENTOCONTRATACAO");
        }

    }
}
