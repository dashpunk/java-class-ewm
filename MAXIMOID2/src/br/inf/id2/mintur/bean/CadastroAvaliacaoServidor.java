package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;

/**
 *
 * @author Dyogo Dantas
 *
 */
public class CadastroAvaliacaoServidor extends AppBean {

    /**
     *
     */
    public CadastroAvaliacaoServidor() {
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        super.dataChangedEvent(speaker);
    }

    @Override
    public synchronized void fireDataChangedEvent(DataBean arg0) {
        try {
            if (!getMbo().isNull("RHTBCASE01ID")
                    && !getMbo().isNull("RHTBAVAL01ID")
                    && !getMbo().isNull("RHSTCODNUMAVAL")
                    && !getMbo().isNull("RHDTDTAAVALIACAO")) {
                System.out.println("--- readonly");
                refreshTable();
                reloadTable();
//                Utility.sendEvent(new WebClientEvent("SAVE", app.getApp(), null, sessionContext));
                getMbo().setFieldFlag("RHTBAVAL01ID", MboConstants.READONLY, true);
                getMbo().setFieldFlag("RHTBCASE01ID", MboConstants.READONLY, true);
                getMbo().setFieldFlag("RHDTDTAAVALIACAO", MboConstants.READONLY, true);
                getMbo().setFieldFlag("RHSTCODNUMAVAL", MboConstants.READONLY, true);
            } else {
                MboSetRemote mboSetQues = getMbo().getMboSet("RHRLQUESAV01");
                if (getMbo().getMboSet("RHRLQUESAV01").count() > 0) {
                    mboSetQues.deleteAndRemoveAll();
                    mboSetQues.save();
                    refreshTable();
                    reloadTable();
                }
            }
        } catch (Exception e) {
        }
        super.fireDataChangedEvent(arg0);
    }

    @Override
    public synchronized boolean fetchRecordData() throws MXException, RemoteException {
        if (!getMbo().isNull("RHTBCASE01ID")
                && !getMbo().isNull("RHTBAVAL01ID")
                && !getMbo().isNull("RHSTCODNUMAVAL")
                && !getMbo().isNull("RHDTDTAAVALIACAO")) {
            System.out.println("--- readonly");
            refreshTable();
            reloadTable();
//                Utility.sendEvent(new WebClientEvent("SAVE", app.getApp(), null, sessionContext));
            getMbo().setFieldFlag("RHTBAVAL01ID", MboConstants.READONLY, true);
            getMbo().setFieldFlag("RHTBCASE01ID", MboConstants.READONLY, true);
            getMbo().setFieldFlag("RHDTDTAAVALIACAO", MboConstants.READONLY, true);
            getMbo().setFieldFlag("RHSTCODNUMAVAL", MboConstants.READONLY, true);
        }
        return super.fetchRecordData();
    }
}
