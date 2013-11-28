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
public class MatUserTransUnitcostLineCost extends MboValueAdapter {

    public MatUserTransUnitcostLineCost(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("------------------ validate");
        super.validate();
        try {
            calculeValor();
            System.out.println("########### Passou...");
        } catch (Exception e) {
        }

    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        try {
            calculeValor();
        } catch (Exception e) {
        }
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        try {
            calculeValor();
        } catch (Exception e) {
        }
    }

    private void calculeValor() throws MXException, RemoteException {
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

        System.out.println("############ UnitCost = " + valor);
        getMboValue().getMbo().setValue("UNITCOST", valor, MboConstants.NOACCESSCHECK);

        double unitcost = getMboValue().getMbo().getDouble("UNITCOST");

        double positiveQuantity = getMboValue().getMbo().getDouble("POSITIVEQUANTITY");

        valor = (unitcost * positiveQuantity);

        System.out.println("############ LineCost = " + valor);
        getMboValue().getMbo().getMboValue("LINECOST").setReadOnly(false);
        getMboValue().getMbo().setValue("LINECOST", valor, MboConstants.NOVALIDATION_AND_NOACTION_ALLOWCROSSOVER);
        getMboValue().getMbo().getMboValue("LINECOST").setReadOnly(true);
        
        System.out.println("############ Fim MatUserTransUnitcostLineCost");
    }
}
