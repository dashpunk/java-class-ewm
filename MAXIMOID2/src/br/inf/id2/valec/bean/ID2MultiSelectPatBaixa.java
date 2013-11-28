package br.inf.id2.valec.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Patrick
 */
public class ID2MultiSelectPatBaixa extends DataBean {

    public ID2MultiSelectPatBaixa() {
        super();
    }

    public synchronized int execute() throws MXException, RemoteException {
        System.out.println("*** execute ***");
        super.execute();

        int i = 0;
//        System.out.println("*** antes origem ######");
        DataBean origem = app.getDataBean("todosAtivos");
//        System.out.println("*** origem.count " + origem.count());


        CustomMboSet destino = (CustomMboSet) app.getDataBean("MAINRECORD").getMbo().getMboSet("MXRLPATBAIXA01");
        destino.setFlag(MboConstants.READONLY, false);
        boolean existe;

        while (origem.getMboSet().getMbo(i) != null) {
//        	System.out.println("*** i "+i);
//            System.out.println("j = " + destino.count());
//            System.out.println("############## Seelect = " +origem.getMboSet().getMbo(i).isSelected());
//            System.out.println("############## CATID = " + origem.getMboSet().getMbo(i).getString("MXTBCATID"));
            if (origem.getMboSet().getMbo(i).isSelected()) {
                existe = false;
            	for (int j = 0; j < destino.count(); j++) {
//                    System.out.println("*** j " + j);
//                    System.out.println("ASSETNUM destino " + destino.getMbo(j).getString("ASSETNUM"));
//                    System.out.println("ASSETNUM origem " + origem.getMboSet().getMbo(i).getString("ASSETNUM"));
                    if (destino.getMbo(j).getString("MXASSETNUM").equalsIgnoreCase(origem.getMboSet().getMbo(i).getString("ASSETNUM"))) {
//                        System.out.println("*** Ja existe no destino");
                        existe = true;
                        break;
                    }
                }
                if (!existe) {
//                	System.out.println("*** no if antes de setar valor");
                    MboRemote mboDestino = (MboRemote) destino.add();
                    System.out.println("*** MXTBBAIXAID da main "+app.getDataBean("MAINRECORD").getMbo().getString("MXTBBAIXAID"));
                    mboDestino.setValue("MXASSETNUM", origem.getMboSet().getMbo(i).getString("ASSETNUM"));
                    mboDestino.setValue("MXTBBAIXAID", app.getDataBean("MAINRECORD").getMbo().getString("MXTBBAIXAID"));
                }
            }
            i++;
        }

        sessionContext.queueRefreshEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

        return 1;
    }
}
