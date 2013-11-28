package br.inf.id2.me.bean;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.MultiselectDataBean;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.controller.Utility;

/**
 *
 * @author Ricardo S Gomes
 */
public class Id2Ap02TrnMultSelect extends DataBean {

    public Id2Ap02TrnMultSelect() {
        super();
    }

    @Override
    public synchronized int execute() throws MXException, RemoteException {
        System.out.println("-------------antes dos novos valores");
        super.execute();

        int i = 0;
        System.out.println("----- origem.count b");
        DataBean origem = app.getDataBean("1314652219750");
        System.out.println("----- origem.count " + origem.count());


        CustomMboSet destino = (CustomMboSet) app.getDataBean().getMbo().getMboSet("MXRL01TERTRALIN");
        destino.setFlag(MboConstants.READONLY, false);
        boolean existe;

        do {
            if (origem.getMboSet().getMbo(i) != null) {
                //System.out.println(i);

                existe = false;
                System.out.println("j = " + destino.count());
                for (int j = 0; j < destino.count(); j++) {
                    System.out.println("j " + j);
                    System.out.println("ASSETNUM   " + destino.getMbo(j).getString("ASSETNUM"));
                    System.out.println("ASSETNUM " + origem.getMboSet().getMbo(i).getString("ASSETNUM"));
                    if (destino.getMbo(j).getString("ASSETNUM").equalsIgnoreCase(origem.getMboSet().getMbo(i).getString("ASSETNUM"))) {
                        System.out.println("EXISTS");
                        existe = true;
                        break;
                    }
                }
                if (!existe) {
                    MboRemote mboDestino = (MboRemote) destino.add();
                    mboDestino.setValue("ASSETNUM", origem.getMboSet().getMbo(i).getString("ASSETNUM"));
                    System.out.println("----- LOCATION = "+origem.getMboSet().getMbo(i).getMboSet("ASSET").getMbo(0).getString("LOCATION"));
                    mboDestino.setValue("FROMLOC", origem.getMboSet().getMbo(i).getMboSet("ASSET").getMbo(0).getString("LOCATION"));
                    mboDestino.setValue("DSLOCORIG", origem.getMboSet().getMbo(i).getMboSet("ASSET").getMbo(0).getString("LOCATION"));
                    
                    mboDestino.setValue("mxtbtertraid", app.getDataBean().getMbo().getString("mxtbtertraid"));

                    mboDestino.setValue("toloc", app.getDataBean().getMbo().getString("dslocdest"));

                    System.out.println("-------------- mxtbtertraid"+app.getDataBean().getMbo().getString("mxtbtertraid"));
                    System.out.println("-------------- dslocdest"+app.getDataBean().getMbo().getString("dslocdest"));
                }

            } else {
                break;
            }
            i++;
        } while (true);
        //parent.getMbo().getThisMboSet().save();

        //tableMemos.refreshTable();
        sessionContext.queueRefreshEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));
        //Utility.sendEvent(new WebClientEvent("SAVE", app.getApp(), null, sessionContext));
        //tableBeanb.save();

        return 1;
    }
}
