package br.inf.id2.tj.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class MatUserTransQuantity extends psdi.app.inventory.FldMatUseTransQuantity {

    public MatUserTransQuantity(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        boolean material = getMboValue().getMbo().getString("DSTIPO").equals("MATERIAL");
        if (getMboValue().getMbo().getBoolean("YNMATETJDF") && material) {
            System.out.println("----------- MatUserTransQuantity SIM");

            getMboValue().getMbo().setValue("QUANTITY", 1, MboConstants.NOVALIDATION);
            
        }

        super.validate();
        System.out.println("--- MatUserTransQuantity.setValue.novalidate 1 " + getMboValue().getMbo().getBoolean("YNMATETJDF"));
        System.out.println("--- MatUserTransQuantity.setValue.novalidate 2 " + material);
        if (getMboValue().getMbo().getBoolean("YNMATETJDF") && material) {
            System.out.println("----------- MatUserTransQuantity SIM 2");
            System.out.println("--- MatUserTransQuantity.setValue.novalidate b");
            
            getMboValue().setValue(0, MboConstants.NOVALIDATION);
        }

    }
}
