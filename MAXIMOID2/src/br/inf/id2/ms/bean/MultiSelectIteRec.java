package br.inf.id2.ms.bean;

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
public class MultiSelectIteRec extends MultiselectDataBean {

    public MultiSelectIteRec() {
        super();
    }

    public int selMemos()
            throws MXException,
            java.rmi.RemoteException {
        super.execute();


        DataBean tableBean = app.getDataBean("msiterec");

        MboSetRemote tableBeanb = app.getDataBean().getMboSet();

        CustomMboSet destinoSet = (CustomMboSet) tableBean.getMbo().getMboSet("MS_RL01LOCRECC'");

        MboRemote origemLoc = app.getDataBean().getMboSet().getMbo();

        int i = 0;
        do {
            if (tableBean.getMboSet().getMbo(i) != null) {
                //System.out.println("linha = " + i);

                if (tableBean.getMboSet().getMbo(i).isSelected()) {
                    //System.out.println("linha = " + i + " Selecionado");
                } else {
                    //System.out.println("linha = " + i + " NAO Selecionado");
                }

                //System.out.println("-----> PRLINEID " + tableBean.getMboSet().getMbo(i).getString("PRLINEID"));
                //System.out.println("-----> ID2ITEMNUM " + tableBean.getMboSet().getMbo(i).getString("ID2ITEMNUM"));

                if (tableBean.getMboSet().getMbo(i).isSelected()) {
                    //System.out.println("entrou no MBO selecionado");
                    MboRemote memo = (MboRemote) destinoSet.add();
                    //System.out.println("adicionou");
                    memo.setValue("PRLINEID", tableBean.getMboSet().getMbo(i).getString("PRLINEID"));
                    memo.setValue("ID2ITEMNUM", tableBean.getMboSet().getMbo(i).getString("ID2ITEMNUM"));
                    //System.out.println("apos adicionar MS_RL01ITEREC ******************");
                    destinoSet.save();
                    //System.out.println("apos save******************");
                }
            } else {
                break;
            }
            i++;
        } while (true);
        //parent.getMbo().getThisMboSet().save();

        //tableMemos.refreshTable();
        sessionContext.queueRefreshEvent();
        //System.out.println("*********************** acabou");
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("SAVE", app.getApp(), null, sessionContext));
        tableBeanb.save();
        return 1;
    }
}
