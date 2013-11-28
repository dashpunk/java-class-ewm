package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.util.Iterator;

import psdi.app.collection.CollectDetailsSetRemote;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Patrick
 */
public class ID2MultiSelectSelecaoBens extends DataBean {

    public ID2MultiSelectSelecaoBens() {
        super();
        System.out.println("*** ID2MultiSelectSelecaoBens ***");
    }

    public synchronized int execute() throws MXException, RemoteException {
        System.out.println("*** execute ***");
        super.execute();

        System.out.println("*** antes origem");
        DataBean origem = app.getDataBean("todosAtivos");
        System.out.println("*** origem.count " + origem.count());

        CollectDetailsSetRemote destino = (CollectDetailsSetRemote) app.getDataBean("MAINRECORD").getMbo().getMboSet("COLLECTDETAILS");
//        CustomMboSet destino = (CustomMboSet) app.getDataBean("MAINRECORD").getMbo().getMboSet("MXRL01COL");
        destino.setFlag(MboConstants.READONLY, false);
        boolean existe;

        System.out.println("####### Selection = " + origem.getMboSet().getSelection());
        if (origem.getMboSet().getSelection() != null) {
	        Iterator itSelec = origem.getMboSet().getSelection().iterator();
	        
			while (itSelec.hasNext()) {
				
				MboRemote item = (MboRemote) itSelec.next();
		        System.out.println("############## Select = " + item);

		        existe = false;
		        for (int j = 0; j < destino.count(); j++) {
		        	System.out.println("*** j " + j);
		        	System.out.println("ASSETNUM destino " + destino.getMbo(j).getString("ASSETNUM"));
		        	System.out.println("ASSETNUM origem " + item.getString("ASSETNUM"));
	                if (destino.getMbo(j).getString("ASSETNUM").equalsIgnoreCase(item.getString("ASSETNUM"))) {
	                    System.out.println("*** Ja existe no destino");
	                    existe = true;
	                    break;
	                }
		        }
	            if (!existe) {
	            	System.out.println("*** no if antes de setar valor");
	                MboRemote mboDestino = (MboRemote) destino.add();

	                mboDestino.setValue("ASSETNUM", item.getString("ASSETNUM"));
	                mboDestino.setValue("CIASSETLOCATIONDESCRIPTION", item.getString("DESCRIPTION"));
	                
//	                mboDestino.setValue("COLLECTIONNUM", app.getDataBean("MAINRECORD").getMbo().getString("COLLECTIONNUM"));
	            }
			}
        }        

        sessionContext.queueRefreshEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

        return 1;
    }
}
