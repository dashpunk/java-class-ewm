package br.inf.id2.me.bean;

import java.rmi.RemoteException;

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
public class ID2MultiSelectPessoaCatalogoBean extends DataBean {

    public ID2MultiSelectPessoaCatalogoBean() {
        super();
    }

    public synchronized int executeAssunto() throws MXException, RemoteException {
        System.out.println("*** executeAssunto ***");
        super.execute();

        int i = 0;
//        System.out.println("*** antes origem ######");
        DataBean origem = app.getDataBean("pescatass");
//        System.out.println("*** origem.count " + origem.count());


        CustomMboSet destino = (CustomMboSet) app.getDataBean("MAINRECORD").getMbo().getMboSet("RL01MXTBCATPES");
        destino.setFlag(MboConstants.READONLY, false);
        boolean existe;

        while (origem.getMboSet().getMbo(i) != null) {
            if (origem.getMboSet().getMbo(i).isSelected()) {
                existe = false;
            	for (int j = 0; j < destino.count(); j++) {
                    if (destino.getMbo(j).getString("MXTBCATID").equalsIgnoreCase(origem.getMboSet().getMbo(i).getString("MXTBCATID"))) {
                        existe = true;
                        break;
                    }
                }
                if (!existe) {
                    MboRemote mboDestino = (MboRemote) destino.add();
                    
                    mboDestino.setValue("MXTBCATID", origem.getMboSet().getMbo(i).getString("MXTBCATID"));
                    mboDestino.setValue("DESCRIPTION", origem.getMboSet().getMbo(i).getString("DESCRIPTION"));
                    mboDestino.setValue("PERSONID", app.getDataBean("MAINRECORD").getMbo().getString("PERSONID"));
                    mboDestino.setValue("MXTIPCAT", "ASSUNTO");
                }
            }
            i++;
        }

        sessionContext.queueRefreshEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

        return 1;
    }
    
    public synchronized int executeModalidade() throws MXException, RemoteException {
        System.out.println("*** executeModalidade ***");
        super.execute();

        int i = 0;
//        System.out.println("*** antes origem");
        DataBean origem = app.getDataBean("pescatmod");
//        System.out.println("*** origem.count " + origem.count());


        CustomMboSet destino = (CustomMboSet) app.getDataBean().getMbo().getMboSet("RL02MXTBCATPES");
        destino.setFlag(MboConstants.READONLY, false);
        boolean existe;

        while (origem.getMboSet().getMbo(i) != null) {
//        	System.out.println("*** i "+i);
//            System.out.println("j = " + destino.count());
            if (origem.getMboSet().getMbo(i).isSelected()) {
                existe = false;
	            for (int j = 0; j < destino.count(); j++) {
	                System.out.println("*** j " + j);
	                if (destino.getMbo(j).getString("MXTBCATID").equalsIgnoreCase(origem.getMboSet().getMbo(i).getString("MXTBCATID"))) {
	                    existe = true;
	                    break;
	                }
	            }
	            if (!existe) {
	                MboRemote mboDestino = (MboRemote) destino.add();
	                
                    mboDestino.setValue("MXTBCATID", origem.getMboSet().getMbo(i).getString("MXTBCATID"));
                    mboDestino.setValue("DESCRIPTION", origem.getMboSet().getMbo(i).getString("DESCRIPTION"));
                    mboDestino.setValue("PERSONID", app.getDataBean("MAINRECORD").getMbo().getString("PERSONID"));
                    mboDestino.setValue("MXTIPCAT", "MODALIDADE");
	            }
            }
            i++;
        }

        sessionContext.queueRefreshEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

        return 1;
    }
    
}
