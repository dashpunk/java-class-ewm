package br.inf.id2.tj.field;

import psdi.mbo.*;


import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class FldInventoryAvblBalance extends psdi.app.inventory.FldInventoryAvblBalance {

    public FldInventoryAvblBalance(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        System.out.println("--- initValue() FldInventoryAvblBalance ");
        super.initValue();
        double valorAtual = getMboValue().getDouble();
        System.out.println("--- initValue() FldInventoryAvblBalance  " + valorAtual);
        MboSet wpMaterial;
        wpMaterial = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("WPMATERIAL", getMboValue().getMbo().getUserInfo());

        wpMaterial.setWhere(""
                + "itemnum = '" + getMboValue().getMbo().getString("ITEMNUM") + "' AND "
                + "location = '" + getMboValue().getMbo().getString("location") + "' AND "
                + "itemsetid = '" + getMboValue().getMbo().getString("itemsetid") + "' AND "
                //+ "storelocsiteid = '" + getMboValue().getMbo().getString("storelocsiteid") + "' AND "
                + "YNMATETJDF2 = 1");
        System.out.println("--- initValue() FldInventoryAvblBalance  reset b "+wpMaterial.getWhere());
        wpMaterial.reset();
        System.out.println("--- initValue() FldInventoryAvblBalance  reset a");

        double valorTotal = wpMaterial.sum("ITEMQTY");
        System.out.println("--- initValue() FldInventoryAvblBalance  valorTotal = " + valorTotal);
        getMboValue().setValue(valorAtual + valorTotal, 11L);
        System.out.println("--- initValue() FldInventoryAvblBalance  FIM valorTotal = " + getMboValue().getDouble());


    }
}
