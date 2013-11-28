package br.inf.id2.tj.field;

import psdi.mbo.*;


import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Dyogo
 */
public class Linecost extends MboValueAdapter {

    public Linecost(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    //WPMATERIAL.LINECOST = (WPMATERIAL.UNITCOST - WPMATERIAL.AMCUSTMATETJDF) * WPMATERIAL.ITEMQTY
    public void validate() throws MXException, RemoteException {
        super.validate();

        if ((!getMboValue().getMbo().isNull("UNITCOST"))
                && (!getMboValue().getMbo().isNull("AMCUSTMATETJDF"))
                    && (!getMboValue().getMbo().isNull("ITEMQTY"))) {
            System.out.println("---> Entrou no Linecost");
            double unitcost = getMboValue().getMbo().getDouble("UNITCOST");
            double amcustmatetjdf = getMboValue().getMbo().getDouble("AMCUSTMATETJDF");
            double itemqty = getMboValue().getMbo().getDouble("ITEMQTY");
            double valor = (unitcost - amcustmatetjdf) * itemqty;
            getMboValue().getMbo().setValue("LINECOST", valor, MboConstants.NOVALIDATION_AND_NOACTION);
        }

    }
}
