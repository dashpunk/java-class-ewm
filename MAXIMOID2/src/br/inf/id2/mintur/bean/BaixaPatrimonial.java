package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 
 * @author Dyogo
 *
 */
public class BaixaPatrimonial extends AppBean {

    public BaixaPatrimonial() {
    }

    @Override
    public int SAVE() throws RemoteException, MXException {

        System.out.println("######################### Entrei no Save()");

        MboSetRemote mboSet = getMbo().getMboSet("MXRLPATBAIXA01");
        
        String status = getMbo().getString("STATUS");
        System.out.println("############# Alterando STATUS do Ativo. Status atual geral = " + status);
        if (status != null && status.equalsIgnoreCase("EFETIVO")) {
            System.out.println("############### Status est� como Efetivo...");
            System.out.println("############### Mbo do Patrimonio = " + mboSet);
            if (mboSet != null && mboSet.count() > 0) {
                System.out.println("############### Count do Mbo do Patrimonio = " + mboSet.count());
                for (int i = 0; i < mboSet.count(); i++) {
                    System.out.println("############## Patrimonio n�mero " + i + " = " + mboSet.getMbo(i).getString("MXASSETNUM"));
                    System.out.println("############## Patrimonio n�mero " + i + " Status = " + mboSet.getMbo(i).getMboSet("MXRLASS01").getMbo(0).getString("STATUS"));
                    mboSet.getMbo(i).getMboSet("MXRLASS01").getMbo(0).setFieldFlag("STATUS", MboConstants.READONLY, false);
                    mboSet.getMbo(i).getMboSet("MXRLASS01").getMbo(0).setValue("STATUS", "BAIXADO", MboConstants.NOVALIDATION_AND_NOACTION);
                    mboSet.getMbo(i).getMboSet("MXRLASS01").getMbo(0).setFieldFlag("STATUS", MboConstants.READONLY, true);
                }
            }
        }
        
        double soma = 0d;
        System.out.println("*** mboSet "+mboSet.count());

        for (int i = 0; i < mboSet.count(); i++) {
            System.out.println("*** i "+ i);
            System.out.println("*** PURCHASEPRICE "+mboSet.getMbo(i).getMboSet("MXRLASS01").getMbo(0).getDouble("PURCHASEPRICE"));
            
            soma += mboSet.getMbo(i).getMboSet("MXRLASS01").getMbo(0).getDouble("PURCHASEPRICE");
        }
        System.out.println("*** soma "+soma);
        
        getMbo().setValue("MXVALTOT", soma);

        return super.SAVE();
    }
}
