package br.inf.id2.ms.field;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 * Antes extendia de: psdi.app.common.purchasing.FldPurOrderQty
 * 
 */
public class PRLineID2PPA extends MboValueAdapter {

    /**
     * Método construtor de ID2FldOrderQty
     * @param mbv 
     * @throws MXException
     */
    public PRLineID2PPA(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();


        getMboValue().getMbo().setValueNull("ID2FUNCIONAL");
        getMboValue().getMbo().setValueNull("ID2FUNCIONAL2");
        getMboValue().getMbo().setValueNull("ID2ACAONOME");

    }
}
