package br.inf.id2.valec.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;

import psdi.mbo.MboSet;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class MTTerAdiMTNumAdi extends MboValueAdapter {

    public MTTerAdiMTNumAdi(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        System.out.println("---------------MTTerAdiMTNumAdi iv()");
        super.initValue();

        if (getMboValue().getMbo().isNew()) {
            MboSet termosAditivosSubsequentesMboSet;
            termosAditivosSubsequentesMboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MTTERADI", getMboValue().getMbo().getUserInfo());
            System.out.println("-------------- mtconnum = \'" + getMboValue("mtconnum").getString() + "\'");
            termosAditivosSubsequentesMboSet.setWhere("mtconnum = '" + getMboValue("mtconnum").getString() + "'");
            termosAditivosSubsequentesMboSet.setOrderBy("MTNUMADI desc");
            termosAditivosSubsequentesMboSet.reset();
            int valor = 0;
            if (termosAditivosSubsequentesMboSet.count() > 0) {
                valor = termosAditivosSubsequentesMboSet.getMbo(0).getInt("MTNUMADI");
            }
            MboRemote mbo;
            for (int i =0; ((mbo = getMboValue().getMbo().getThisMboSet().getMbo(i)) != null); i++) {
                if (mbo.getInt("MTNUMADI") >= valor) {
                    valor = mbo.getInt("MTNUMADI");
                }
            }
            System.out.println("------------- valor = " + valor);
            String valorRef = String.valueOf(++valor);
            System.out.println("-------------- vr = " + valorRef);
            getMboValue().setValue(valorRef, MboConstants.NOACCESSCHECK);
            getMboValue().setReadOnly(true);
            System.out.println("--------------------MTTerAdiMTNumAdi iv() fim");
        }
    }
}
