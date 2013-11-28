//psdi.app.common.purchasing.FldPurUnitCost era
package br.inf.id2.tj.field;

import psdi.mbo.*;


import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Dyogo
 */
public class Unitcost extends MboValueAdapter {

    public Unitcost(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        System.out.println("---> Entrou no Unitcost");
        if ((!getMboValue().getMbo().isNull("AMCUSTMAOOBRA"))
                && (!getMboValue().getMbo().isNull("AMCUSTMATE"))
                && (!getMboValue().getMbo().isNull("AMCUSTMATETJDF"))) {

            double amcustmaoobra = getMboValue().getMbo().getDouble("AMCUSTMAOOBRA");
            double amcustmate = getMboValue().getMbo().getDouble("AMCUSTMATE");
            double amcustmatetjdf = 0;
            try {
                amcustmatetjdf = getMboValue().getMbo().getDouble("AMCUSTMATETJDF");
            } catch (Exception e) {
                System.out.println("Nao tem o atributo AMCUSTMATETJDF");
            }
            double valor = (amcustmaoobra + amcustmate) - amcustmatetjdf;
            getMboValue().getMbo().setValue("UNITCOST", valor, MboConstants.NOVALIDATION_AND_NOACTION);
        }
    }
}
