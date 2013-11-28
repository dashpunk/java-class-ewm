package br.inf.id2.tj.field;

import psdi.mbo.*;


import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class MatUseTransMxQuantidade extends MboValueAdapter {

    public MatUseTransMxQuantidade(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        double valor = getMboValue().getDouble();
        //System.out.println("---validade() WpMaterialMxQuantidade " + valor);
        boolean yn = getMboValue().getMbo().getBoolean("YNMATETJDF");
        boolean material = getMboValue().getMbo().getString("DSTIPO").equals("MATERIAL");
        
        System.out.println("---validade() yn " + yn);

        if (yn && material) {
            System.out.println("--- MatUseTransMxQuantidade y");
            getMboValue().getMbo().setValue("POSITIVEQUANTITY", 0, MboConstants.NOACCESSCHECK);
            System.out.println("--- MatUseTransMxQuantidade positivequantity");
            getMboValue().getMbo().setValue("QUANTITY", 0, MboConstants.NOACCESSCHECK);
            System.out.println("--- MatUseTransMxQuantidade quantity");

        } else {
            getMboValue().getMbo().setValue("POSITIVEQUANTITY", valor, MboConstants.NOACCESSCHECK);
            getMboValue().getMbo().setValue("QUANTITY", valor, MboConstants.NOACCESSCHECK);
            //getMboValue().getMbo().setValue("QUANTITY", valor, MboConstants.NOVALIDATION_AND_NOACTION_ALLOWCROSSOVER);
        }

        calculaCusto();
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

        getMboValue().getMbo().setValue("UNITCOST", valor, MboConstants.NOACCESSCHECK);

        double unitcost = getMboValue().getMbo().getDouble("UNITCOST");

        double positiveQuantity = getMboValue().getMbo().getDouble("POSITIVEQUANTITY");

        valor = (unitcost * positiveQuantity);

        getMboValue().getMbo().getMboValue("LINECOST").setReadOnly(false);
        getMboValue().getMbo().setValue("LINECOST", valor, MboConstants.NOVALIDATION_AND_NOACTION_ALLOWCROSSOVER);
        getMboValue().getMbo().getMboValue("LINECOST").setReadOnly(true);


    }
}
