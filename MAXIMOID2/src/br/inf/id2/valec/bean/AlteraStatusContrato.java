package br.inf.id2.valec.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.app.contract.purch.PurchView;

import psdi.mbo.MboConstants;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Patrick
 */
public class AlteraStatusContrato extends psdi.webclient.beans.contmaster.ReviseContractBean {

    public AlteraStatusContrato() {
    }

    @Override
    public int execute() throws MXException, RemoteException {
        int retorno = 0;
        try {
            //MboSetRemote mboSet;
            //mboSet = app.getDataBean("MAINRECORD").getMbo().getMboSet("rl02con".toUpperCase());

            String contractnum = parent.getMbo().getString("contractnum");
            Integer revisionnum = parent.getMbo().getInt("revisionnum");
            System.out.println("------------------------ valor1 " + contractnum);
            System.out.println("------------------------ valor2 " + revisionnum);

            MboSetRemote mboSet;
            mboSet = psdi.server.MXServer.getMXServer().getMboSet("PURCHVIEW", sessionContext.getUserInfo());

            mboSet.setWhere("masternum = \'" + contractnum + "\' and revisionnum = " + revisionnum);
            mboSet.reset();

            //System.out.println("*** STATUS antes if - "+app.getDataBean("MAINRECORD").getMbo().getString("STATUS"));
            //se o status for VIGENTE ele altera para EM ALTERAÇÃO
            if (app.getDataBean("MAINRECORD").getMbo().getString("STATUS").equals("VIGENTE")) {

                app.getDataBean("MAINRECORD").getMboSet().getMbo().setFieldFlag("STATUS", MboConstants.READONLY, false);
                app.getDataBean("MAINRECORD").getMboSet().getMbo().setValue("STATUS", "EM ALTERAÇÃO", MboConstants.NOVALIDATION_AND_NOACTION);
                app.getDataBean("MAINRECORD").getMboSet().getMbo().setFieldFlag("STATUS", MboConstants.READONLY, true);

                //System.out.println("*** STATUS alterado if - "+app.getDataBean("MAINRECORD").getMbo().getString("STATUS"));
            }


            retorno = super.execute();

            System.out.println("----------- retorno = "+retorno);

            //Utility.sendEvent(new WebClientEvent("dialogok", app.getCurrentPageId(), null, sessionContext));

            Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
            Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

            System.out.println(">>> dlg revisão");
            System.out.println(">>> mboSet");

            //mboSet = app.getDataBean("MAINRECORD").getMbo().getMboSet("Childcontracts".toUpperCase());

            System.out.println(">>> mboSet " + mboSet.count());
            //Contract c;
            PurchView p;
            PurchView pN;
            int contador = mboSet.count();
            System.out.println("----------- contador " + contador);
            //mboSet.setFlag(MboConstants.NOACCESSCHECK, true);
            //for (int i = 0; ((c = (Contract) mboSet.getMbo(i)) != null); i++) {
            if (mboSet.count() < 5) {
                for (int i = 0; ((p = (PurchView) mboSet.getMbo(contador - 1)) != null); i++) {
                    if (i == contador) {
                        break;
                    }
                    System.out.println(">>> i " + i);
                    System.out.println("---- flag");
                    //p.setFlag(MboConstants.NOACCESSCHECK, true);
                    System.out.println("---- revisionInProgress");
                    //p.revisionInProgress(true);
                    System.out.println("---- reviseContract");
                    //p.canReviseContract();
                    pN = (PurchView) p.copy();

                    pN.setValue("revisionnum", revisionnum + 1, MboConstants.NOACCESSCHECK);
                    pN.setValue("CONTRACTNUM", p.getString("CONTRACTNUM"));
                    //p.reviseContract("V1");

                    //p.revisionInProgress(false);

                    System.out.println(">>> reviseContract " + this.getString("REVDESCRIPTION"));
                }
            }
            mboSet.save();
            mboSet.setFlag(MboConstants.READONLY, true);
            System.out.println(">>> fim");
        } catch (RemoteException ex) {
            Logger.getLogger(AlteraStatusContrato.class.getName()).log(Level.SEVERE, null, ex);
        }



        return retorno;
    }

    public synchronized void btnOks() throws MXException {
        //System.out.println("*** btnOk ***");

        try {
            //MboSetRemote mboSet;
            //mboSet = app.getDataBean("MAINRECORD").getMbo().getMboSet("rl02con".toUpperCase());

            String contractnum = parent.getMbo().getString("contractnum");
            Integer revisionnum = parent.getMbo().getInt("revisionnum");
            System.out.println("------------------------ valor1 " + contractnum);
            System.out.println("------------------------ valor2 " + revisionnum);

            MboSetRemote mboSet;
            mboSet = psdi.server.MXServer.getMXServer().getMboSet("PURCHVIEW", sessionContext.getUserInfo());

            mboSet.setWhere("masternum = \'" + contractnum + "\' and revisionnum = " + revisionnum);
            mboSet.reset();

            //System.out.println("*** STATUS antes if - "+app.getDataBean("MAINRECORD").getMbo().getString("STATUS"));
            //se o status for VIGENTE ele altera para EM ALTERAÇÃO
            if (app.getDataBean("MAINRECORD").getMbo().getString("STATUS").equals("VIGENTE")) {

                app.getDataBean("MAINRECORD").getMboSet().getMbo().setFieldFlag("STATUS", MboConstants.READONLY, false);
                app.getDataBean("MAINRECORD").getMboSet().getMbo().setValue("STATUS", "EM ALTERAÇÃO", MboConstants.NOVALIDATION_AND_NOACTION);
                app.getDataBean("MAINRECORD").getMboSet().getMbo().setFieldFlag("STATUS", MboConstants.READONLY, true);

                //System.out.println("*** STATUS alterado if - "+app.getDataBean("MAINRECORD").getMbo().getString("STATUS"));
            }


            Utility.sendEvent(new WebClientEvent("dialogok", app.getCurrentPageId(), null, sessionContext));

            Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
            Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

            System.out.println(">>> dlg revisão");
            System.out.println(">>> mboSet");

            //mboSet = app.getDataBean("MAINRECORD").getMbo().getMboSet("Childcontracts".toUpperCase());

            System.out.println(">>> mboSet " + mboSet.count());
            //Contract c;
            PurchView p;
            PurchView pN;
            int contador = mboSet.count();
            System.out.println("----------- contador " + contador);
            //mboSet.setFlag(MboConstants.NOACCESSCHECK, true);
            //for (int i = 0; ((c = (Contract) mboSet.getMbo(i)) != null); i++) {
            if (mboSet.count() < 5) {
                for (int i = 0; ((p = (PurchView) mboSet.getMbo(contador - 1)) != null); i++) {
                    if (i == contador) {
                        break;
                    }
                    if (i > 5) {
                        break;
                    }
                    System.out.println(">>> i " + i);
                    System.out.println("---- flag");
                    //p.setFlag(MboConstants.NOACCESSCHECK, true);
                    System.out.println("---- revisionInProgress");
                    //p.revisionInProgress(true);
                    System.out.println("---- reviseContract");
                    //p.canReviseContract();
                    pN = (PurchView) p.copy();

                    pN.setValue("revisionnum", revisionnum + 1, MboConstants.NOACCESSCHECK);
                    pN.setValue("CONTRACTNUM", p.getString("CONTRACTNUM"));
                    //p.reviseContract("V1");

                    //p.revisionInProgress(false);

                    System.out.println(">>> reviseContract " + this.getString("REVDESCRIPTION"));
                }
            }
            mboSet.save();
            mboSet.setFlag(MboConstants.READONLY, true);
            System.out.println(">>> fim");
        } catch (RemoteException ex) {
            Logger.getLogger(AlteraStatusContrato.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
//masternum = :contractnum and revisionnum = :revisionnum

