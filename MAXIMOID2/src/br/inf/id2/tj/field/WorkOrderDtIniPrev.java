package br.inf.id2.tj.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class WorkOrderDtIniPrev extends MboValueAdapter {

    public WorkOrderDtIniPrev(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        boolean readOnly = getMboValue().getMbo().getMboValueData("TARGSTARTDATE").isReadOnly();
        if (readOnly) {
            //System.out.println("============= TARGSTARTDATE readonly to false");
            getMboValue().getMbo().setFieldFlag("TARGSTARTDATE", MboConstants.READONLY, false);
        }
        //System.out.println("============= TARGSTARTDATE value from DTINIPREV");
        getMboValue().getMbo().setValue("TARGSTARTDATE", getMboValue().getMbo().getDate("DTINIPREV"), MboConstants.NOVALIDATION_AND_NOACTION);
        if (readOnly) {
            //System.out.println("============= TARGSTARTDATE readonly to true");
            getMboValue().getMbo().setFieldFlag("TARGSTARTDATE", MboConstants.READONLY, true);
        }

    }
}
