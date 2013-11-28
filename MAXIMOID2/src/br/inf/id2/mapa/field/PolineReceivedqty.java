package br.inf.id2.mapa.field;

import psdi.mbo.*;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;



/**
 * 
 * @author Ricardo Gomes
 */
public class PolineReceivedqty extends MboValueAdapter {

    public PolineReceivedqty(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        if (getMboValue("RECEIVEDQTY").getDouble() > getMboValue("ORDERQTY").getDouble()) {
            throw new MXApplicationException("poline", "recebimentoMaioEnvio");
        }
    }
}
