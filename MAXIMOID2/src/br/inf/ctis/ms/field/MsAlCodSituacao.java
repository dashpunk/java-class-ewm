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
public class MsAlCodSituacao extends MboValueAdapter {

    public MsAlCodSituacao(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();

        if (getMboValue().getString().equalsIgnoreCase("CANCELADO")) {
            getMboValue().getMbo().setValueNull("MSNUNUMVALORTOTAL");
            getMboValue().getMbo().setValueNull("MSNUNUMQUANTIDADEREGISTRADA");
            getMboValue().getMbo().setValueNull("MSALCODMOEDA");
            getMboValue().getMbo().setValueNull("MSNUNUMVALORUNITARIO");
        }
    }
}
