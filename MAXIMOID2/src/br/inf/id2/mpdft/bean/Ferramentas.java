package br.inf.id2.mpdft.bean;

import java.rmi.RemoteException;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo Gomes
 */
public class Ferramentas extends psdi.webclient.beans.item.ItemAppBean {

    public Ferramentas() {
        System.out.println("--- Ferramentas 1715");
    }

    @Override
    public int STATUS() throws RemoteException, MXApplicationException, MXException {
        System.out.println("--- status "+getMbo().getString("STATUS"));
        if (getMbo().getString("STATUS").equalsIgnoreCase("OBSOLETA")) {
            throw new MXApplicationException("itemstatus", "obsoleta");
        }
        return super.STATUS();
    }
}
