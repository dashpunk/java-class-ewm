package br.inf.id2.common.field;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import java.util.Date;
import br.inf.id2.common.util.Data;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class EndDateVsStartDate extends MboValueAdapter {

    public EndDateVsStartDate(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        Date startDate = getMboValue().getMbo().getDate("STARTDATE");
        Date endDate = getMboValue().getMbo().getDate("ENDDATE");

        if (Data.dataInicialMenorFinal(endDate, startDate)) {
            throw new MXApplicationException("company", "StartDateMaiorQueEndDate");
        }
    }
}
