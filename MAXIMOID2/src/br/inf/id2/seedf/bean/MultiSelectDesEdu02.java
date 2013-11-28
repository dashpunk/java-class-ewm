package br.inf.id2.seedf.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.MultiselectDataBean;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.controller.Utility;

/**
 *
 * @author Ricardo S Gomes
 */
public class MultiSelectDesEdu02 extends MultiselectDataBean {

    public MultiSelectDesEdu02() {
        super();
    }

    public int selMemos()
            throws MXException,
            java.rmi.RemoteException {
        super.execute();

        DataBean tableBean = app.getDataBean("seedesedude");

        MboSetRemote tableBeanb = app.getDataBean().getMboSet();


        int tcomcurid = 0;
        int deaulaid = 0;

        if (tableBeanb != null) {
            tcomcurid = tableBeanb.getMbo().getInt("TCOMCURID");
            if (tableBeanb.getMbo().getMboSet("SEERL01DEAULA").count() == 0) {
                throw new MXApplicationException("seetbdeaula", "SEERL01DEAULAVazio");
            }
            deaulaid = tableBeanb.getMbo().getMboSet("SEERL01DEAULA").getMbo(tableBeanb.getMbo().getMboSet("SEERL01DEAULA").getCurrentPosition()).getInt("DEAULAID");
        }

        CustomMboSet destinoSet = (CustomMboSet) app.getDataBean().getMboSet().getMbo().getMboSet("SEERL01DEAULA").getMbo(app.getDataBean().getMboSet().getMbo().getMboSet("SEERL01DEAULA").getCurrentPosition()).getMboSet("SEERL01DEDESEDU");
        MboRemote origemLoc = app.getDataBean().getMboSet().getMbo();

        for (int i = 0; i < tableBean.getMboSet().count(); i++) {
            System.out.println(i);

            if (tableBean.getMboSet().getMbo(i).isSelected()) {
                System.out.println("entrou no MBO selecionado");
                MboRemote mboDestino = (MboRemote) destinoSet.add();
                System.out.println("adicionou");
                mboDestino.setValue("SEEDESEDU", tableBean.getMboSet().getMbo(i).getString("SEEDESEDU"));
                mboDestino.setValue("DESEDUMODID", tableBean.getMboSet().getMbo(i).getString("DESEDUMODID"));
                mboDestino.setValue("TCOMCURID", tcomcurid);
                mboDestino.setValue("DEAULAID", deaulaid);
                destinoSet.save();
            }
        }
        sessionContext.queueRefreshEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        //Utility.sendEvent(new WebClientEvent("SAVE", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));
        tableBeanb.save();
        return 1;
    }
}
