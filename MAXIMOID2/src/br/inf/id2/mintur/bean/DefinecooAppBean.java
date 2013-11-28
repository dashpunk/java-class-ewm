package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.MultiselectDataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Ricardo S Gomes
 */
public class DefinecooAppBean extends MultiselectDataBean {

    public DefinecooAppBean() {
        super();
    }
    
    @Override
    protected void initialize() throws MXException, RemoteException {
    	super.initialize();
    	getMbo().setValueNull("FKRHTBUOIDORI");
    }

    public int selMemos() throws MXException, java.rmi.RemoteException {

        super.execute();

        DataBean tableBeanOrigem = app.getDataBean("definecoo");

        MboSetRemote tableBeanDestino = app.getResultsBean().getMboSet();

        MboRemote mbo;
        for (int i = 0; ((mbo = tableBeanDestino.getMbo(i)) != null); i++) {
            if (mbo.isSelected()) {
                mbo.setValue("FKRHTBUOID", tableBeanOrigem.getMbo().getInt("FKRHTBUOIDORI"), MboConstants.NOVALIDATION_AND_NOACTION);
                mbo.setValue("FKRHSTNOMUO", tableBeanOrigem.getMbo(0).getMboSet("RL02UO").getMbo(0).getString("RHSTNOMUO"), MboConstants.NOVALIDATION_AND_NOACTION);
                System.out.println("*** RL02UO.count() "+tableBeanOrigem.getMbo(0).getMboSet("RL02UO").count());
                System.out.println("*** RHSTNOMUO "+tableBeanOrigem.getMbo(0).getMboSet("RL02UO").getMbo(0).getString("RHSTNOMUO"));
            }

        }

        tableBeanDestino.save();
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("SAVE", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        return 1;
    }
}
