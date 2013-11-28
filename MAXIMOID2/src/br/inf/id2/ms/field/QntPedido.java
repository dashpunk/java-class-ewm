package br.inf.id2.ms.field;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author ID2
 *
 * Antes extendia de: psdi.app.common.purchasing.FldPurOrderQty
 * 
 */
public class QntPedido extends MboValueAdapter {

    /**
     * Método construtor de ID2FldOrderQty
     * @param mbv 
     * @throws MXException
     */
    public QntPedido(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();

        getMboValue().getMbo().setValue("ORDERQTY",getMboValue().getDouble());

    }

}
