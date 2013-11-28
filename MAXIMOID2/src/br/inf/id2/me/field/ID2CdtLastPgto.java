package br.inf.id2.me.field;

import br.inf.id2.common.util.Data;
import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class ID2CdtLastPgto extends MboValueAdapter {

    public ID2CdtLastPgto(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        //System.out.println("*** ID2CdtLastPgto ***");
        super.validate();
        Date startDate = getMboValue().getMbo().getDate("MESTARTDATE");

        Date id2CdtLastPgto = getMboValue().getMbo().getDate("ID2CDTLASTPGTO");
        //System.out.println("*** dataAssinatura " + id2CdtLastPgto);

        if (id2CdtLastPgto != null && startDate != null) {
            //System.out.println("*** if ***");
            if (Data.dataInicialMenorFinal(id2CdtLastPgto, startDate)) {
                throw new MXApplicationException("company", "DataMenorQueStartDate");
            }
        }

    }
}
