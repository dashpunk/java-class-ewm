package br.inf.id2.tj.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class MatUserTransPositiveQuantity extends psdi.app.inventory.FldMatUseTransPositiveQuantity {

    public MatUserTransPositiveQuantity(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	System.out.println("------------------ MatUserTransPositiveQuantity.validate");
        boolean material = getMboValue().getMbo().getString("DSTIPO").equals("MATERIAL");
         System.out.println("########### Material =" + material + "| YN=" + getMboValue().getMbo().getBoolean("YNMATETJDF"));
        if (getMboValue().getMbo().getBoolean("YNMATETJDF") && material) {
        	boolean isReadOnly = getMboValue("POSITIVEQUANTITY").isReadOnly();
            if (isReadOnly) {
            	getMboValue("POSITIVEQUANTITY").setReadOnly(false);
            }
            getMboValue().getMbo().setValue("POSITIVEQUANTITY", 1, MboConstants.NOVALIDATION);
            getMboValue("POSITIVEQUANTITY").setReadOnly(isReadOnly);
            
        }
        super.validate();
        calculaCusto();
        System.out.println("--- MatUserTransPositiveQuantity.setValue.novalidate 1 " + getMboValue().getMbo().getBoolean("YNMATETJDF"));
        if (getMboValue().getMbo().getBoolean("YNMATETJDF") && material) {
            System.out.println("--- MatUserTransPositiveQuantity.setValue.novalidate b");
            
            boolean isReadOnly = getMboValue("POSITIVEQUANTITY").isReadOnly();
            if (isReadOnly) {
            	getMboValue("POSITIVEQUANTITY").setReadOnly(false);
            }
            getMboValue().getMbo().setValue("POSITIVEQUANTITY", 0, MboConstants.NOVALIDATION);
            getMboValue("POSITIVEQUANTITY").setReadOnly(isReadOnly);
            
            
            System.out.println("--- MatUserTransPositiveQuantity.POSITIVEquantity setValue = " + getMboValue().getDouble());
        }
    }

    void calculaCusto() throws MXException, RemoteException {

        //System.out.println("---> Entrou no Unitcost");
        double amcustmaoobra = getMboValue().getMbo().getDouble("AMCUSTMAOOBRA");
        double amcustmate = getMboValue().getMbo().getDouble("AMCUSTMATE");
        //double amcustmatetjdf = getMboValue().getMbo().getDouble("AMCUSTMATETJDF");

        double valor = 0;
        if (!getMboValue().getMbo().getBoolean("YNMATETJDF")) {
            valor = amcustmaoobra + amcustmate;

        } else {
            valor = amcustmaoobra;
        }

        System.out.println("######## Valor Unitcost = " + valor);
        getMboValue().getMbo().setValue("UNITCOST", valor, MboConstants.NOACCESSCHECK);

        double unitcost = getMboValue().getMbo().getDouble("UNITCOST");

        double positiveQuantity = getMboValue().getMbo().getDouble("POSITIVEQUANTITY");

        valor = (unitcost * positiveQuantity);

        getMboValue().getMbo().getMboValue("LINECOST").setReadOnly(false);
        getMboValue().getMbo().setValue("LINECOST", valor, MboConstants.NOVALIDATION_AND_NOACTION_ALLOWCROSSOVER);
        getMboValue().getMbo().getMboValue("LINECOST").setReadOnly(true);

    }
}
