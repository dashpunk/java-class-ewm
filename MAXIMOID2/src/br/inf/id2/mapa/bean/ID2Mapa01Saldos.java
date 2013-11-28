package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Willians Andrade
 */
public class ID2Mapa01Saldos extends DataBean {

    public ID2Mapa01Saldos() {
    }

    public int inserirLoteIndividual() throws MXException, RemoteException {
        MboRemote mboDestino = app.getDataBean().getMboSet().getMbo().getMboSet("MARL01LOTABA").add();

        String personId = getMboSet().getMbo(getMboSet().getCurrentPosition()).getString("PERSONID");
        String prorprietario = getMboSet().getMbo(getMboSet().getCurrentPosition()).getString("ID2CODPROP");

        mboDestino.setValue("MACODPROP", prorprietario, MboConstants.NOACCESSCHECK);
        mboDestino.setValue("PERSONID", personId, MboConstants.NOACCESSCHECK);
        mboDestino.setValue("MATBMAPASID", app.getDataBean().getMboSet().getMbo().getString("MATBMAPASID"), MboConstants.NOACCESSCHECK);
        mboDestino.setValue("COMMODITY", app.getDataBean().getMboSet().getMbo().getString("ID2CODESP"), MboConstants.NOACCESSCHECK);
        System.out.println("---> "+app.getDataBean().getMboSet().getMbo().getString("ID2CODESP"));
        mboDestino.setValue("MADATABA", app.getDataBean().getMboSet().getMbo().getString("ID2DATABA"), MboConstants.NOACCESSCHECK);
        mboDestino.setValue("MANUMREG", app.getDataBean().getMboSet().getMbo().getString("ID2NUMREG"), MboConstants.NOACCESSCHECK);
        System.out.println("aquiiiiiiiiiiiiiiiiiiiiiiiiiiii");
        System.out.println(app.getDataBean().getMboSet().getMbo().getString("ID2CODESP"));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));
        return EVENT_HANDLED;
    }
}
