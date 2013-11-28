package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.app.asset.Asset;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.common.StatefulAppBean;

/**
 *
 * @author Jesse Rovira
 */
public class MaidSisbov extends StatefulAppBean {

    /**
     *
     */
    public MaidSisbov() {
    }

    /**
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    public int SAVE()
            throws MXException, RemoteException {
        System.out.println("############################# Entrou no ID2PlanAPPBean SAVE() 25 de abr");

        try {

            for (int i = 0; i < getMboSet().count(); i++) {

                Asset asset = (Asset) getMboSet().getMbo(i);
                System.out.println("################# Iteracao " + i + " ASSET: " + asset);

                System.out.println("################ ASSET SELECIONADO? = " + asset.isSelected());
                if (asset.isSelected()) {
                    //String valor = asset.getMboSet("MARLPR01").getMbo(0).getMboSet("PRLINE").getMbo(0).getString("MASTORELOC");
                    //asset.setValue("LOCATION", valor, MboConstants.NOACCESSCHECK);
                    try {
                        /*
                        System.out.println("############### DATA = " + asset.getDate("MADATENASC"));
                        GregorianCalendar gregoriancalendar = new GregorianCalendar();
                        gregoriancalendar.setTime(asset.getDate("MADATENASC"));
                        gregoriancalendar.setLenient(false);
                        java.util.Date date = gregoriancalendar.getTime();
                         */
                    } catch (Exception exception) {
                        throw new MXApplicationException("asset", "DataNascimentoInvalida");
                    }

                    System.out.println("--------------- before if. tamanho " + getMboSet().getMbo(i).getMboSet("ID2RLINVBAL").count());
                    Thread.sleep(0);
                    if (getMboSet().getMbo(i).getMboSet("ID2RLINVBAL").count() > 0) {
                        double maqTDid = asset.getMboSet("ID2RLINVBAL").getMbo(0).getDouble("MAQTDID");
                        double curBal = asset.getMboSet("ID2RLINVBAL").getMbo(0).getDouble("CURBAL");
                        System.out.println("----- matTdid " + maqTDid);
                        System.out.println("----- curbal  " + curBal);
                        Thread.sleep(0);
                        //retirado a pedido do Alexandre Tesk
                        //if (maqTDid < curBal) {
                        System.out.println("--- before setvalue maqtdid");
                        Thread.sleep(0);
                        //asset.getMboSet("ID2RLINVBAL").getMbo(0).setValue("MAQTDID", maqTDid + 1, MboConstants.NOVALIDATION_AND_NOACTION);
                        Thread.sleep(0);
                        System.out.println("--- after setvalue maqtdid");
                        Thread.sleep(0);
                        asset.changeStatus("EM USO", false, false, false, false);
                        asset.setValue("MADTINSPLA", new Date());
                        Thread.sleep(0);
                        //asset.save();
                        //} else {
                        //    asset.save();
                        //    throw new MXApplicationException("asset", "maqtdidMaiorQueCurBal");
                        //}
                    } else {
                        throw new MXApplicationException("asset", "relacionamentoID2RLINVBALVazio");
                    }



                }

            }
            System.out.println("-------- antes do save");
            getMboSet().save();
            System.out.println("-------- apos do save");
        } catch (InterruptedException ex) {
            Logger.getLogger(MaidSisbov.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("-------- antes do save 2");
        int j = super.SAVE();
        System.out.println("-------- apos do save 2");
        return j;
    }
}
