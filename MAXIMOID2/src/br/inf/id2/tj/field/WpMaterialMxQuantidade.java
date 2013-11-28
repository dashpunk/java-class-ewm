package br.inf.id2.tj.field;

import psdi.mbo.*;


import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class WpMaterialMxQuantidade extends MboValueAdapter {

    public WpMaterialMxQuantidade(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        double valor = getMboValue().getDouble();
        System.out.println("---validade() WpMaterialMxQuantidade " + valor);
        boolean yn = false;
        try {
            yn = getMboValue().getMbo().getBoolean("YNMATETJDF2");
        } catch (Exception e) {
        }
        System.out.println("---validade() yn " + yn);
        boolean material = getMboValue().getMbo().getString("DSTIPO").equals("MATERIAL");

        if (yn && material) {
            getMboValue().getMbo().setValue("ITEMQTY", 0, MboConstants.NOACCESSCHECK);
        } else {
            getMboValue().getMbo().setValue("ITEMQTY", valor, MboConstants.NOACCESSCHECK);
        }

    }
}
