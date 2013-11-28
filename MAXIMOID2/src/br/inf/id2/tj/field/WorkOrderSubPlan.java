package br.inf.id2.tj.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class WorkOrderSubPlan extends MboValueAdapter {

    public WorkOrderSubPlan(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();

        double amSubMat = 0;
        double amSubServ = 0;

        if (!getMboValue().getMbo().isNull("AMSUBMAT")) {
            amSubMat = getMboValue().getMbo().getDouble("AMSUBMAT");
        }
        if (!getMboValue().getMbo().isNull("AMSUBSERV")) {
            amSubServ = getMboValue().getMbo().getDouble("AMSUBSERV");
        }

        //System.out.println("--------- amSubMat = " + amSubMat);
        //System.out.println("--------- amSubServ = " + amSubServ);

        getMboValue().getMbo().setValue("AMSUBPLAN", amSubMat + amSubServ);


    }
}
