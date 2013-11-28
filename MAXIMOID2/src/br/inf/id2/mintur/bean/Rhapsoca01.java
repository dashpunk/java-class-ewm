package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 * 
 * @author ricardo
 */
public class Rhapsoca01 extends psdi.webclient.system.beans.AppBean {

    public Rhapsoca01() {
        System.out.println("--- Rhapsoca01");
    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        MboRemote mbo;
        int total = getMbo().getMboSet("rhrlseas01").count();
        System.out.println("--- Rhapsoca01.SAVE() " + total);
        try {
            getMbo().setValue("RHNUQTDVAG", total, MboConstants.NOVALIDATION_AND_NOACTION);
        } catch (Exception e) {
            System.out.println("--- ex " + e.getMessage());
            e.getStackTrace();
        }

        System.out.println("--- Rhapsoca01.SAVE() FIM");
        return super.SAVE();
    }
}
