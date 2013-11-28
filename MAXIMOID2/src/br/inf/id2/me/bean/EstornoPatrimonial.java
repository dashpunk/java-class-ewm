package br.inf.id2.me.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * @author Patrick
 */
public class EstornoPatrimonial extends AppBean {

    public EstornoPatrimonial() {
    }

    @Override
    public int SAVE() throws RemoteException, MXException {

        System.out.println("*** SAVE ***");

        String status = getMbo().getString("MXSTATER");
        System.out.println("*** Alterando STATUS do Ativo. Status atual geral = " + status);
        if (status != null && status.equalsIgnoreCase("EFETIVO")) {
            System.out.println("*** Status est� como Efetivo...");
            MboSetRemote mboSet = getMbo().getMboSet("MXTBESTLIN");
            System.out.println("*** Mbo do Patrimonio = " + mboSet);
            if (mboSet != null && mboSet.count() > 0) {
                System.out.println("*** Count do Mbo do Patrimonio = " + mboSet.count());
                for (int i = 0; i < mboSet.count(); i++) {
                    System.out.println("*** Patrimonio n�mero " + i + " = " + mboSet.getMbo(i).getString("ASSETNUM"));
                    System.out.println("*** Patrimonio n�mero " + i + " Status = " + mboSet.getMbo(i).getMboSet("ASSETNUM").getMbo(0).getString("STATUS"));
                    mboSet.getMbo(i).getMboSet("ASSETNUM").getMbo(0).setFieldFlag("STATUS", MboConstants.READONLY, false);
                    mboSet.getMbo(i).getMboSet("ASSETNUM").getMbo(0).setValue("STATUS", "EM USO", MboConstants.NOVALIDATION_AND_NOACTION);
                    mboSet.getMbo(i).getMboSet("ASSETNUM").getMbo(0).setFieldFlag("STATUS", MboConstants.READONLY, true);
                }
            }
        }
        return super.SAVE();
    }
}
