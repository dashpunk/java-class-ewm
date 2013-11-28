package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import psdi.app.asset.Asset;
import psdi.mbo.MboConstants;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.common.StatefulAppBean;

public class MaPlanSisbov extends StatefulAppBean {

    public MaPlanSisbov() {
    }

    public int SAVE()
            throws MXException, RemoteException {
        System.out.println("############################# Entrou no ID2PlanAPPBean SAVE() 12 de ago");
        try {
            for (int i = 0; i < getMboSet().count(); i++) {
                Asset asset = (Asset) getMboSet().getMbo(i);
                System.out.println((new StringBuilder()).append("################# Iteracao ").append(i).append(" ASSET: ").append(asset).toString());
                System.out.println((new StringBuilder()).append("################ ASSET SELECIONADO? = ").append(asset.isSelected()).toString());

                if (asset.isSelected()) {
                     System.out.println("----------------------------------------------------------------- linha selecionada");
                    if (!asset.isNull("MACODLOC") || asset.getString("MACODLOC").length() > 0) {
                         System.out.println("----------------------------------------------------------------- entrou");
                        asset.setValue("LOCATION", asset.getString("MACODLOC"), MboConstants.NOACCESSCHECK);
                       
                        if (getMbo().getMboSet("MARLINVBALANCES01").count() > 0) {
                            double maqTDid = getMbo().getMboSet("MARLINVBALANCES01").getMbo(0).getDouble("MAQTDID");
                            double curBal = getMbo().getMboSet("MARLINVBALANCES01").getMbo(0).getDouble("CURBAL");
                            System.out.println((new StringBuilder()).append("----- matTdid ").append(maqTDid).toString());
                            System.out.println((new StringBuilder()).append("----- curbal  ").append(curBal).toString());
                            if (maqTDid < curBal) {
                                System.out.println("--- before setvalue maqtdid");
                                getMbo().getMboSet("MARLINVBALANCES01").getMbo(0).setValue("MAQTDID", maqTDid + 1.0D, 9L);
                                System.out.println("--- after setvalue maqtdid");
                                //asset.changeStatus("EM USO", false, false, false, false);
                            } else {
                                throw new MXApplicationException("asset", "maqtdidMaiorQueCurBal");
                            }
                        } else {
                            throw new MXApplicationException("asset", "relacionamentoMARLINVBALANCES01Vazio");
                        }
                        System.out.println("----- antes " + asset.getString("MACODLOC"));
                        asset.setValue("LOCATION", asset.getString("MACODLOC"));
                        System.out.println("----- apos " + asset.getString("LOCATION"));

                        //asset.save();
                        System.out.println("------------antes do save ");
                        getMboSet().save();
                        System.out.println("------------apos do save ");
                    }
                }
            }

            //getMboSet().save();
        } catch (MXException mxexception) {
            mxexception.printStackTrace();
        } catch (RemoteException remoteexception) {
            remoteexception.printStackTrace();
        }
        System.out.println("------------antes do save 2");
        int j = super.SAVE();
        System.out.println("------------apos do save 2");
        return j;
    }
}
