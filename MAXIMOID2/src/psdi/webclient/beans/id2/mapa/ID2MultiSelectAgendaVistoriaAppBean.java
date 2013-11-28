package psdi.webclient.beans.id2.mapa;

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
public class ID2MultiSelectAgendaVistoriaAppBean extends MultiselectDataBean {

    public ID2MultiSelectAgendaVistoriaAppBean() {
        super();
    }

    public int selMemos()
            throws MXException,
            java.rmi.RemoteException {
        super.execute();


        int i = 0;
        DataBean tableBean = app.getDataBean("memos");

        //DataBean tableMemos = app.getDataBean("new_table");

        MboSetRemote tableBeanb = app.getDataBean().getMboSet();
        //DataBean tableMemos = app.getDataBean("new_table");
        //System.out.println("------------------------- ");
        //System.out.println("------------------------- ");
        //System.out.println("------------------------- ");
        //System.out.println("------------------------- ");
        //System.out.println("------------------------- ");
        //System.out.println("------------------------- ");
        //System.out.println("------------------------- ");
        if (this.getMbo() == null) {
            //System.out.println("------------------------- A nulo");
        } else {
            //System.out.println("------------------------- A nulo NAO");
        }

        if (tableBean == null) {
            //System.out.println("********************** B null");
        } else {
            //System.out.println("********************** B null NAO");
            //System.out.println("********************** B null NAO a: "+tableBean.getMboSet().getName());
            //System.out.println("********************** B null NAO b: "+tableBean.getMboSet().getMboSetInfo().getClassName());
        }

        if (tableBeanb == null) {
            //System.out.println("********************** B2 null");
        } else {
            //System.out.println("********************** B2 null NAO a: "+tableBeanb.getName());
            //System.out.println("********************** B2 null NAO b: "+tableBeanb.getMboSetInfo().getClassName());
        }

        //CustomMboSet memoset = (CustomMboSet) parent.getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS");
        CustomMboSet memoset = (CustomMboSet) app.getDataBean().getMboSet().getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS");
        MboRemote memosetWO = app.getDataBean().getMboSet().getMbo().getMboSet("MARL05WOR").getMbo(0);
        MboRemote memosetLoc = app.getDataBean().getMboSet().getMbo();

        //System.out.println("vai começar");
        do {
            if (tableBean.getMboSet().getMbo(i) != null) {
                //System.out.println(i);
                if (tableBean.getMboSet().getMbo(i).isSelected()) {
                    //System.out.println("entrou no MBO selecionado");
                    MboRemote memo = (MboRemote) memoset.add();
                    //System.out.println("adicionou");
                    //memo.setValue("cxglaccount", tableBean.getMboSet().getMbo(i).getString("COMPVALUE"));
                    memo.setValue("ID2CPF", tableBean.getMboSet().getMbo(i).getString("PERSONID"));
                    memo.setValue("WONUM", memosetWO.getString("WONUM"));
                    memo.setValue("LOCATION", memosetLoc.getString("LOCATION"));
                    //System.out.println("apos adicionar personid ******************");
                    //memo.setValue("ID2CARGO", "FISCAL FEDERAL AGROPECUÁRIO");
                    //memo.getThisMboSet().save();
                    memoset.save();
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
