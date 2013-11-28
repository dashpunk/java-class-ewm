package br.inf.id2.seedf.bean;

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
public class MultiSelectDesEdu01 extends MultiselectDataBean {

    public MultiSelectDesEdu01() {
        super();
    }

    public int selMemos()
            throws MXException,
            java.rmi.RemoteException {
        super.execute();

        //System.out.println("----- inicio de MultiSelectDesEdu01");
        int i = 0;
        DataBean tableBean = app.getDataBean("seedesedu");

        MboSetRemote tableBeanb = app.getDataBean().getMboSet();

        int avalId = 0;
        if (tableBeanb != null) {
            avalId = tableBeanb.getMbo().getInt("AVALID");
        }





        //CustomMboSet memoset = (CustomMboSet) parent.getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS");
        CustomMboSet destinoSet = (CustomMboSet) app.getDataBean().getMboSet().getMbo().getMboSet("SEERL01DESEDUAVA");
        MboRemote origemLoc = app.getDataBean().getMboSet().getMbo();

        //System.out.println("vai começar");
        do {
            if (tableBean.getMboSet().getMbo(i) != null) {
                //System.out.println(i);
                if (tableBean.getMboSet().getMbo(i).isSelected()) {
                    //System.out.println("entrou no MBO selecionado");
                    MboRemote memo = (MboRemote) destinoSet.add();
                    //System.out.println("adicionou");
                    memo.setValue("SEEDESEDU", tableBean.getMboSet().getMbo(i).getString("SEEDESEDU"));
                    memo.setValue("DESEDUMODID", tableBean.getMboSet().getMbo(i).getString("DESEDUMODID"));
                    memo.setValue("AVALID", avalId);
                    memo.setValue("SEESTATUS", "ATIVO");
                    //System.out.println("apos adicionar SEEDESEDU ******************");
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
        //System.out.println("*********************** acabou");
        //System.out.println("*********************** acabou");
        //System.out.println("*********************** acabou");
        //System.out.println("*********************** acabou");
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("SAVE", app.getApp(), null, sessionContext));
        tableBeanb.save();
        return 1;
    }
}
