package br.inf.id2.tj.field;

import psdi.mbo.*;


import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class WpMatrialUnitcostLineCost extends MboValueAdapter {

    public WpMatrialUnitcostLineCost(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        System.out.println("---- WpMatrialUnitcostLineCost");
        double amcustmaoobra = getMboValue().getMbo().getDouble("AMCUSTMAOOBRA");
        double amcustmate = getMboValue().getMbo().getDouble("AMCUSTMATE");
        //double amcustmatetjdf = getMboValue().getMbo().getDouble("AMCUSTMATETJDF");
        System.out.println("---vals");
        System.out.println("1 = " + amcustmaoobra);
        System.out.println("2 = " + amcustmate);

        double valor = 0;
        boolean yn = false;
        try {
            System.out.println("3 = " + getMboValue().getMbo().getBoolean("YNMATETJDF2"));
            yn = getMboValue().getMbo().getBoolean("YNMATETJDF2");
        } catch (Exception e) {
            System.out.println("3 exception = false");
        }
        if (!yn) {
            valor = amcustmaoobra + amcustmate;

        } else {
            valor = amcustmaoobra;
        }
        System.out.println("4 = " + valor);

        getMboValue().getMbo().setValue("UNITCOST", valor, MboConstants.NOACCESSCHECK);

        double unitcost = getMboValue().getMbo().getDouble("UNITCOST");

        double positiveQuantity = getMboValue().getMbo().getDouble("ITEMQTY");

        System.out.println("5 = " + unitcost);
        System.out.println("6 = " + positiveQuantity);

        valor = (unitcost * positiveQuantity);
        System.out.println("7 = " + valor);

        getMboValue().getMbo().getMboValue("LINECOST").setReadOnly(false);
        getMboValue().getMbo().setValue("LINECOST", valor, MboConstants.NOACCESSCHECK);
        getMboValue().getMbo().getMboValue("LINECOST").setReadOnly(true);

        System.out.println("---- WpMatrialUnitcostLineCost FIM");
    }
}
