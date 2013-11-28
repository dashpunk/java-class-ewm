package br.inf.id2.valec.bean;

import java.rmi.RemoteException;
import java.util.Iterator;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Ricardo S Gomes
 */
public class ContratosObrasSelecionarMultiplosItens extends DataBean {

    public ContratosObrasSelecionarMultiplosItens() {
        super();
        System.out.println("------ ContratosObrasSelecionarMultiplosItens ------");
    }

    public synchronized int execute() throws MXException, RemoteException {
        System.out.println("------ ContratosObrasSelecionarMultiplosItens execute ------");
        super.execute();

        System.out.println("------ antes origem");
        DataBean origem = app.getDataBean("selectgoitem");
        System.out.println("------ origem.count " + origem.count());


        MboSetRemote destino =  app.getDataBean("MAINRECORD").getMbo().getMboSet("CHILDCONTRACTS").getMbo(app.getDataBean("MAINRECORD").getMbo().getMboSet("CHILDCONTRACTS").getCurrentPosition()).getMboSet("CONTRACTLINE");
        destino.setFlag(MboConstants.READONLY, false);
        boolean existe;

        String contractNum = app.getDataBean("MAINRECORD").getMbo().getMboSet("CHILDCONTRACTS").getMbo(app.getDataBean("MAINRECORD").getMbo().getMboSet("CHILDCONTRACTS").getCurrentPosition()).getString("CONTRACTNUM");

        System.out.println("------ contractNum " + contractNum);

        System.out.println("----------- Selection = " + origem.getMboSet().getSelection());
        if (origem.getMboSet().getSelection() != null) {
            Iterator itSelec = origem.getMboSet().getSelection().iterator();

            while (itSelec.hasNext()) {

                MboRemote item = (MboRemote) itSelec.next();
                System.out.println("---------------------- Select = " + item);

                existe = false;
                for (int j = 0; j < destino.count(); j++) {
                    System.out.println("------ j " + j);
                    System.out.println("ITEMNUM destino " + destino.getMbo(j).getString("ITEMNUM"));
                    System.out.println("ITEMNUM origem " + item.getString("ITEMNUM"));
                    if (destino.getMbo(j).getString("ITEMNUM").equalsIgnoreCase(item.getString("ITEMNUM"))) {
                        System.out.println("------ Ja existe no destino");
                        existe = true;
                        break;
                    }
                }
                if (!existe) {
                    System.out.println("------ no if antes de setar valor");
                    MboRemote mboDestino = (MboRemote) destino.add();
                    mboDestino.setValue("ITEMNUM", item.getString("ITEMNUM"));
                    mboDestino.setValue("CONTRACTNUM", contractNum);
                }
            }
        }

        sessionContext.queueRefreshEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

        return 1;
    }
}
