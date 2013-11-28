package br.inf.id2.mintur.bean;

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
public class BaixaPatrimonialConjuntoPatrimonial extends DataBean {

    public BaixaPatrimonialConjuntoPatrimonial() {
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


        CustomMboSet destino = (CustomMboSet) app.getDataBean().getMbo().getMboSet("MXRLPATBAIXA01");
        destino.setFlag(MboConstants.READONLY, false);
        boolean existe;

        do {
            if (origem.getMboSet().getMbo(i) != null) {
                System.out.println(i);

                existe = false;
                System.out.println("j = " + destino.count());
                for (int j = 0; j < destino.count(); j++) {
                    System.out.println("j " + j);
                    System.out.println("MXASSETNUM " + destino.getMbo(j).getString("MXASSETNUM"));
                    System.out.println("ASSETNUM " + origem.getMboSet().getMbo(i).getString("ASSETNUM"));
                    if (destino.getMbo(j).getString("MXASSETNUM").equalsIgnoreCase(origem.getMboSet().getMbo(i).getString("ASSETNUM"))) {
                        System.out.println("EXISTS");
                        existe = true;
                        break;
                    }
                }
                if (!existe) {
                	System.out.println("---------- if existe");
                    MboRemote mboDestino = (MboRemote) destino.add();
                    mboDestino.setValue("MXASSETNUM", origem.getMboSet().getMbo(i).getString("ASSETNUM"));
                    mboDestino.setValue("MXTBBAIXAID", app.getDataBean().getMbo().getInt("MXTBBAIXAID"));
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
