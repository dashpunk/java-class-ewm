package br.inf.id2.valec.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.app.contract.Contract;
import psdi.app.contract.master.MasterView;
import psdi.mbo.MboConstants;

import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Ricardo S Gomes
 */
public class DlgAlterarStatus extends DataBean {

    public DlgAlterarStatus() {
    }

    public synchronized void btnOk() throws MXException {
        /*
         * 
        try {
        MasterView m = (MasterView) app.getDataBean().getMboSet().getMbo();
        System.out.println("---------------------- vai");
        m.reviseContract("V1");
        System.out.println("---------------------- foi");
        } catch (Exception ex) {
        System.out.println("-------------- e"+ex.getMessage());
        }*/
        Utility.sendEvent(new WebClientEvent("dialogok", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

        MboSetRemote mboSet;
        try {
            mboSet = app.getDataBean("MAINRECORD").getMbo().getMboSet("rl01con".toUpperCase());
            Contract c;
            for (int i = 0; ((c = (Contract) mboSet.getMbo(i)) != null); i++) {
                System.out.println(">>> i " + i);
                System.out.println(">>> status atual dlg " + getMbo().getString("STATUS"));
                System.out.println(">>> status atual dlg " + app.getDataBean().getMbo().getString("STATUS"));
                //c.setFieldFlag("STATUS", MboConstants.READONLY, false);
                //a constante MboConstants.NOACCESSCHECK garante a atribuição
                //de valor, não importando se o atributo, o mbo ou o mboSet esta read only
                System.out.println("----- antes do status " + c.getString("contractnum"));
                System.out.println("----- antes do status " + c.getString("masternum"));
                System.out.println("----- antes do status " + c.getString("revisionnum"));
                String contractnum = parent.getMbo().getString("contractnum");
                Integer revisionnum = parent.getMbo().getInt("revisionnum");
                System.out.println("------------------------ valor1 " + contractnum);
                System.out.println("------------------------ valor2 " + revisionnum);

                c.setValue("STATUS", app.getDataBean().getMbo().getString("STATUS"), MboConstants.NOACCESSCHECK);
                int aonde = 0;
                System.out.println("----- aonde " + ++aonde);
                //c.setValue("paymentsched", false, MboConstants.NOACCESSCHECK);
                System.out.println("----- aonde " + ++aonde);
                c.setFieldFlag("paymentsched", 7L, false);
                System.out.println("----- aonde " + ++aonde);
                c.setFieldFlag("paymentsched", 7L, true);
                System.out.println("----- aonde " + ++aonde);
                MboSetRemote propertyDefaultSet = c.getMboSet("PROPERTYDEFAULT");
                System.out.println("----- aonde " + ++aonde);
                propertyDefaultSet.setQbe("propertyid", "PAYMENTSCHED");
                System.out.println("----- aonde " + ++aonde);
                propertyDefaultSet.reset();
                System.out.println("----- aonde " + ++aonde);
                System.out.println("@@@@@@@@@@@@@@@@@@@@  count " + propertyDefaultSet.count());
                try {
                    c.setFieldFlag("paymentsched", 7L, !propertyDefaultSet.getMbo(0).getBoolean("editable"));
                } catch (Exception e) {
                     c.setFieldFlag("paymentsched", 7L, false);
                }
                System.out.println("----- aonde " + ++aonde);
                System.out.println("----- antes do após");
                //c.setFieldFlag("STATUS", MboConstants.READONLY, false);
            }
            System.out.println("----- antes do save " + mboSet.count());
            mboSet.save();
            System.out.println("----- após do save");
        } catch (RemoteException ex) {
            System.out.println("------ ex " + ex.getMessage());
            Logger.getLogger(DlgAlterarStatus.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
