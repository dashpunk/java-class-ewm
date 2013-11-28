package br.inf.id2.valec.bean;

import java.rmi.RemoteException;
import java.util.Iterator;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Patrick
 */
public class ID2MultiSelectGuiaTramiteDocumento extends DataBean {

    public ID2MultiSelectGuiaTramiteDocumento() {
        super();
    }

    public synchronized int execute() throws MXException, RemoteException {
        super.execute();
        DataBean origem = app.getDataBean("todosProtocolos");

        MboSet linhas = (MboSet) parent.getMbo().getMboSet("MXRL01TRANSLIN");

        CustomMboSet destino = (CustomMboSet) app.getDataBean("MAINRECORD").getMbo().getMboSet("MXRL01TRANSLIN");
        destino.setFlag(MboConstants.READONLY, false);
        boolean existe;

        if (origem.getMboSet().getSelection() != null) {
	        Iterator itSelec = origem.getMboSet().getSelection().iterator();
	        
			while (itSelec.hasNext()) {
				
				MboRemote item = (MboRemote) itSelec.next();
		        System.out.println("*** Select = " + item);

		        existe = false;
		        for (int j = 0; j < destino.count(); j++) {
		        	System.out.println("*** j " + j);
		        	System.out.println("DSNUMDOC destino " + destino.getMbo(j).getString("DSNUMDOC"));
		        	System.out.println("ASSETNUM origem " + item.getString("ASSETNUM"));
	                if (destino.getMbo(j).getString("DSNUMDOC").equalsIgnoreCase(item.getString("ASSETNUM"))) {
	                    existe = true;
	                    break;
	                }
		        }
	            if (!existe) {
	            	MboRemote mboDestino = (MboRemote) destino.add();
	                
	                destino.setValue("DSLOCORIG", app.getDataBean("MAINRECORD").getMbo().getString("DSLOCORIG"));
	                destino.setValue("DSNUMDOC", item.getString("ASSETNUM"));
	                destino.setValue("MXTRANSID", app.getDataBean("MAINRECORD").getMbo().getString("MXTBTRANSID"));
	                
	                destino.save();
	            }
			}
        }

        linhas.save();
        
        sessionContext.queueRefreshEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

        return 1;
    }
}
