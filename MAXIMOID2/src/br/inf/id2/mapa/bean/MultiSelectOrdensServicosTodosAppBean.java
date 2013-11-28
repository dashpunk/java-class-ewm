package br.inf.id2.mapa.bean;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.MultiselectDataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Leysson Barbosa Moreira
 */
public class MultiSelectOrdensServicosTodosAppBean extends MultiselectDataBean {

    public MultiSelectOrdensServicosTodosAppBean() {
        super();
    }

    public int selMemos()
            throws MXException,
            java.rmi.RemoteException {
        super.execute();


        int i = 0;
        DataBean tableBean = app.getDataBean("memos2");

        //DataBean tableMemos = app.getDataBean("new_table");

        MboSetRemote tableBeanb = app.getDataBean().getMboSet();

        //CustomMboSet memoset = (CustomMboSet) parent.getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS");
        CustomMboSet memoset = (CustomMboSet) app.getDataBean().getMboSet().getMbo().getMboSet("MARL05MEMOS");
        //MboRemote memosetWO = app.getDataBean().getMboSet().getMbo().getMboSet("MARL05WOR").getMbo(0);
        MboRemote memosetLoc = app.getDataBean().getMboSet().getMbo();
        memoset.setFlag(MboConstants.READONLY, false);
        boolean existe;
        do {
            if (tableBean.getMboSet().getMbo(i) != null) {
                if (tableBean.getMboSet().getMbo(i).isSelected()) {
                    existe = false;
                    System.out.println("j = " + memoset.count());
                    for (int j = 0; j < memoset.count(); j++) {
                        System.out.println("j " + j);
                        System.out.println("ID2CPF   " + memoset.getMbo(j).getString("ID2CPF"));
                        System.out.println("PERSONID " + tableBean.getMboSet().getMbo(i).getString("PERSONID"));
                        if (memoset.getMbo(j).getString("ID2CPF").equalsIgnoreCase(tableBean.getMboSet().getMbo(i).getString("PERSONID"))) {
                            System.out.println("EXISTS");
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        MboRemote memo = (MboRemote) memoset.add();

                        memo.setValue("ID2CPF", tableBean.getMboSet().getMbo(i).getString("PESSOA"));
                        memo.setValue("WONUM", memosetLoc.getString("WONUM"));
                        memo.setValue("ID2WONUM", memosetLoc.getString("ID2WONUM"));
                        //memo.setValue("LOCATION", memosetLoc.getString("LOCATION"));
                        memo.setValue("ID2CPFORG", tableBean.getMboSet().getMbo(i).getString("PESSOA"));

                    }
                }
            } else {
                break;
            }
            i++;
        } while (true);

        sessionContext.queueRefreshEvent();
        //System.out.println("*********************** acabou");
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("SAVE", app.getApp(), null, sessionContext));

        return 1;
    }
}
