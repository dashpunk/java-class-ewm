package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.util.Iterator;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
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
public class DefinetecAppBean extends MultiselectDataBean {

    public DefinetecAppBean() {
        super();
    	System.out.println("############# Construtor - DefinetecAppBean");
    }

    @Override
    protected void initialize() throws MXException, RemoteException {

    	System.out.println("############# initialize - DefinetecAppBean");
    	super.initialize();
    	
    	//Verifica se todos os status dos registros selecionados são iguais    	
    	int i = 0;
    	int status = 0;
    	String dsStatus = "";
    	MboSetRemote tableBeanDestino = app.getResultsBean().getMboSet();
    	
    	if (app == null || app.getResultsBean() == null || tableBeanDestino == null) {
    		return;
    	}
    	
        System.out.println("####### Selection = " + tableBeanDestino.getSelection());
        if (tableBeanDestino.getSelection() != null && tableBeanDestino.getSelection().size() >= 1) {
        	
	        Iterator itSelec = tableBeanDestino.getSelection().iterator();
	        
			while (itSelec.hasNext()) {
				MboRemote item = (MboRemote) itSelec.next();
				System.out.println("############### Destino (" + i + ") Status = " + item.getString("DSSTAINT"));
				System.out.println("############### Destino (" + i + ") Tecnico = " + item.getString("FKSTCODTECFIN"));
	    		if (i != 0 && status != item.getInt("FKMXNUCODSTATUS")) {
	    			System.out.println("############### O status anterior é diferente do atual");
	    			Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
	    			sessionContext.queueRefreshEvent();
	    			WebClientEvent event = sessionContext.getCurrentEvent();
	    			Utility.showMessageBox(event, new MXApplicationException("dialog", "statusSelecionadosDiferentes"));
	    			return;
	    			//throw new MXApplicationException("dialog", "statusSelecionadosDiferentes");
	    		}
	    		
	    		//O Técnico não pode estar definido
	    		if (item.getString("FKSTCODTECFIN") != null && !item.getString("FKSTCODTECFIN").equals("")) {
	    			System.out.println("############### O tecnico não está nulo e já foi definido");
	    			Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
	    			sessionContext.queueRefreshEvent();
	    			WebClientEvent event = sessionContext.getCurrentEvent();
	    			Utility.showMessageBox(event, new MXApplicationException("dialog", "tecnicoJaHaviaSidoDefinido"));
	    			return;
	    	    	//throw new MXApplicationException("dialog", "tecnicoJaHaviaSidoDefinido");
	    		}
	    		
	    		status = item.getInt("FKMXNUCODSTATUS");
	    		dsStatus = item.getString("DSSTAINT");
	    		i++;
			}
			
	        DataBean tableBeanOrigem = app.getDataBean("definetec");
	        tableBeanOrigem.getMbo().setValue("FKMXDSCSTAORI", dsStatus, MboConstants.NOVALIDATION_AND_NOACTION);
	        tableBeanOrigem.getMbo().setValueNull("FKSTCODRESORI");
    		
    	} else {
           	System.out.println("############## Caiu na exceção...");
           	Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
           	sessionContext.queueRefreshEvent();
			WebClientEvent event = sessionContext.getCurrentEvent();
			Utility.showMessageBox(event, new MXApplicationException("dialog", "deveEstarSelecionadoAoMenosUm"));
			return;
   	    	//throw new MXApplicationException("dialog", "deveEstarSelecionadoAoMenosUm");
    	}
        
        System.out.println("############# Fim do initialize...");
        
    }
    
    public int selMemos() throws MXException, java.rmi.RemoteException {

        super.execute();

        DataBean tableBeanOrigem = app.getDataBean("definetec");

        MboSetRemote tableBeanDestino = app.getResultsBean().getMboSet();

        MboRemote mbo;
        for (int i = 0; ((mbo = tableBeanDestino.getMbo(i)) != null); i++) {
            if (mbo.isSelected()) {
                mbo.setValue("FKSTCODRES", tableBeanOrigem.getMbo().getString("FKSTCODRESORI"), MboConstants.NOVALIDATION_AND_NOACTION);
                mbo.setValue("FKSTNOMRES", tableBeanOrigem.getMbo().getMboSet("RL03PER").getMbo(0).getString("DISPLAYNAME"), MboConstants.NOVALIDATION_AND_NOACTION);
                
                //Adicionado para definir os status também.
                System.out.println("########### FKMXNUCODSTAORI=" + tableBeanOrigem.getMbo().getString("FKMXNUCODSTAORI"));
                System.out.println("########### FKMXDSCSTAORI=" + tableBeanOrigem.getMbo().getString("FKMXDSCSTAORI"));
                mbo.setValue("DSSTAINT", tableBeanOrigem.getMbo().getString("FKMXDSCSTAORI"), MboConstants.NOVALIDATION_AND_NOACTION);
                mbo.setValue("FKMXNUCODSTATUS", tableBeanOrigem.getMbo().getString("FKMXNUCODSTAORI"), MboConstants.NOVALIDATION_AND_NOACTION);
                
                System.out.println("*** RL03PER.count() "+tableBeanOrigem.getMbo().getMboSet("RL03PER").count());
                System.out.println("*** DISPLAYNAME "+tableBeanOrigem.getMbo().getMboSet("RL03PER").getMbo(0).getString("DISPLAYNAME"));
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
