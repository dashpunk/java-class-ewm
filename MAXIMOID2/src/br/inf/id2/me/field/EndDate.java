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
public class EndDate extends MboValueAdapter {

    public EndDate(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        Date startDate = getMboValue().getMbo().getDate("MESTARTDATE");
        Date endDate = getMboValue().getMbo().getDate("ENDDATE");

        if (startDate != null && endDate != null) {
            if (Data.dataInicialMenorFinal(endDate, startDate)) {
                throw new MXApplicationException("company", "StartDateMaiorQueEndDate");
            }
            getMboValue().getMbo().setValue("ID2CDTPC", Data.getDataAcrescimo(endDate, 30));
        }

    }
}
