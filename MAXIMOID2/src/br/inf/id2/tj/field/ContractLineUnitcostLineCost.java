package br.inf.id2.tj.field;

import psdi.mbo.*;


import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class ContractLineUnitcostLineCost extends MboValueAdapter {

    public ContractLineUnitcostLineCost(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        //System.out.println("---> Entrou no Unitcost");
        double amcustmaoobra = getMboValue().getMbo().getDouble("AMCUSTMAOOBRA");
        double amcustmate = getMboValue().getMbo().getDouble("AMCUSTMATE");
        double amcustmatetjdf = getMboValue().getMbo().getDouble("AMCUSTMATETJDF");
        try {
            amcustmatetjdf = getMboValue().getMbo().getDouble("AMCUSTMATETJDF");
        } catch (Exception e) {
            //System.out.println("Nao tem o atributo AMCUSTMATETJDF");
        }
        //double valor = (amcustmaoobra + amcustmate) - amcustmatetjdf;
        //Alterado a pedido do Ivan
        double valor = amcustmaoobra + amcustmate;
        getMboValue().getMbo().setValue("UNITCOST", valor, MboConstants.NOVALIDATION_AND_NOACTION_ALLOWCROSSOVER);

        //System.out.println("===== " + getMboValue().getMbo().getString("UNITCOST"));
        //System.out.println("===== AMCUSTMATETJDF " + getMboValue().getMbo().getString("AMCUSTMATETJDF"));
        //System.out.println("---> Entrou no Linecost");
        double unitcost = getMboValue().getMbo().getDouble("UNITCOST");
        try {
            if (!getMboValue().getMbo().getBoolean("YNMATETJDF")) {
                amcustmatetjdf = 0;
            }
        } catch (Exception e) {
            //System.out.println("))))))))))) e = " + e.getMessage());
        }
        double orderQty = getMboValue().getMbo().getDouble("ORDERQTY");
        //valor = unitcost  * orderQty;
        //Alterado a pedido do Ivan
        valor = (unitcost * orderQty) - amcustmatetjdf;

        try {
            getMboValue().getMbo().getMboValue("LINECOST").setReadOnly(false);
            getMboValue().getMbo().setValue("LINECOST", valor, MboConstants.NOVALIDATION_AND_NOACTION_ALLOWCROSSOVER);
            getMboValue().getMbo().getMboValue("LINECOST").setReadOnly(true);
        } catch (Exception e) {
            //System.out.println("-----ex " + e.getMessage());
        }

    }
}
