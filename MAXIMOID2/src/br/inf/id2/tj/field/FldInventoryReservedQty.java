package br.inf.id2.tj.field;

import psdi.mbo.*;


import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class FldInventoryReservedQty extends psdi.app.inventory.FldInventoryReservedQty {

    public FldInventoryReservedQty(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        System.out.println("--- initValue()");
        super.initValue();
        double valorAtual = getMboValue().getDouble();
        System.out.println("--- initValue() " + valorAtual);
        MboSet wpMaterial;
        wpMaterial = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("WPMATERIAL", getMboValue().getMbo().getUserInfo());

        wpMaterial.setWhere(""
                + "itemnum = '" + getMboValue().getMbo().getString("ITEMNUM") + "' AND "
                + "location = '" + getMboValue().getMbo().getString("location") + "' AND "
                + "itemsetid = '" + getMboValue().getMbo().getString("itemsetid") + "' AND "
                //+ "storelocsiteid = '" + getMboValue().getMbo().getString("storelocsiteid") + "' AND "
                + "YNMATETJDF2 = 1");
        System.out.println("--- initValue() reset b "+wpMaterial.getWhere());
        wpMaterial.reset();
        System.out.println("--- initValue() reset a");

        double valorTotal = wpMaterial.sum("ITEMQTY");
        System.out.println("--- initValue() valorTotal = " + valorTotal);
        getMboValue().setValue(valorAtual - valorTotal, 11L);
        System.out.println("--- initValue() FIM valorTotal = " + getMboValue().getDouble());


    }
}
