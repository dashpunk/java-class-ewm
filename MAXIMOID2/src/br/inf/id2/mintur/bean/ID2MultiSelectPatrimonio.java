package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
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
public class ID2MultiSelectPatrimonio extends DataBean {

    private List<Integer> selecao;

    public ID2MultiSelectPatrimonio() {
        super();
        System.out.println("*** ID2MultiSelectPatrimonio ***");
        selecao = new ArrayList<Integer>();
    }

    @Override
    public synchronized void select(int row) throws MXException {
        System.out.println(">>>>> selet " + row);
        selecao.add(row);
        super.select(row);
    }

    @Override
    public synchronized void select(int startIndex, int count) throws MXException {
        System.out.println(">>>>> selet " + startIndex + " a " + count);
        for (int i = 0; i < count; i++) {
            System.out.println(">>>>> i " + startIndex+i);
            selecao.add(startIndex+i);
        }
        super.select(startIndex, count);
    }

    @Override
    public synchronized void unselect(int row) throws MXException {
        System.out.println(">>>>> unSelect " + row);
        selecao.remove(selecao.get(selecao.indexOf(row)));
        super.unselect(row);
    }

    @Override
    public synchronized void unselect(int startIndex, int count) throws MXException {
        System.out.println(">>>>> unSelect " + startIndex + " a " + count);
        for (int i = 0; i < count; i++) {
            System.out.println(">>>>> i " + startIndex+i);
            selecao.remove(selecao.get(selecao.indexOf(startIndex+i)));
        }
        super.unselect(startIndex, count);
    }

    public synchronized int execute() throws MXException, RemoteException {
        System.out.println("*** execute ***");
        super.execute();

        System.out.println("*** antes origem");
        DataBean origem = app.getDataBean("todosAtivos");
        System.out.println(">>>> origem.count A " + selecao.size());


        CustomMboSet destino = (CustomMboSet) app.getDataBean("MAINRECORD").getMbo().getMboSet("MXRL01TERTRALIN");
        destino.setFlag(MboConstants.READONLY, false);
//
//        System.out.println("####### Selection = " + origem.getMboSet().getSelection());
        boolean existe;        
        for (int i = 0; i < selecao.size(); i++) {
            MboRemote item = (MboRemote) origem.getMboSet().getMbo(selecao.get(i));
            System.out.println("############## Select = " + item);

            existe = false;
            for (int j = 0; j < destino.count(); j++) {
                System.out.println("*** j " + j);
                System.out.println("ASSETNUM destino " + destino.getMbo(j).getString("ASSETNUM"));
                System.out.println("ASSETNUM origem " + item.getString("ASSETNUM"));
                if (destino.getMbo(j).getString("ASSETNUM").equalsIgnoreCase(item.getString("ASSETNUM"))) {
                    System.out.println("*** Ja existe no destino");
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                System.out.println("*** no if antes de setar valor");
                MboRemote mboDestino = (MboRemote) destino.add();
                System.out.println("*** MXTBTERTRAID da main " + app.getDataBean("MAINRECORD").getMbo().getString("MXTBTERTRAID"));

                mboDestino.setValue("ASSETNUM", item.getString("ASSETNUM"));
                mboDestino.setValue("FROMLOC", item.getString("LOCATION"));
                mboDestino.setValue("MXTBTERTRAID", app.getDataBean("MAINRECORD").getMbo().getString("MXTBTERTRAID"));
            }
        }



//        }

        sessionContext.queueRefreshEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

        return 1;
    }
}
