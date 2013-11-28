package br.inf.id2.me.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class ID2Cdtpc extends MboValueAdapter {

    public ID2Cdtpc(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();

        Date endDate = getMboValue().getMbo().getDate("ENDDATE");

        if (endDate != null) {
            getMboValue().getMbo().setValue("ID2CDTPC", Data.getDataAcrescimo(endDate, 30));
        }

    }
}
