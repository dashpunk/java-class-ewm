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
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
* ID2 Tecnologia S.A.
*
* <h4>DescriÃ§Ã£o da Classe</h4>
*    - Classe criada com o intuito de setar alguns valores de acordo com a seleÃ§Ã£o dos registros na dialog
* 
* <h4>Notas</h4>
* 
* <h4>ReferÃªncias</h4>
*    Aplicada a tela 
* 
* <h4>RevisÃµes</h4>
*    - RevisÃ£o 1.0 - @author Patrick Silva: Classe inicialmente criada.
* 
* 
* @since 13/03/2012 10:00
* @version 1.0
*/

public class DefinetecfinAppBean extends MultiselectDataBean {

    public DefinetecfinAppBean() {
        super();
    	System.out.println("############# Construtor - DefinetecfinAppBean");
    }

    @Override
    protected void initialize() throws MXException, RemoteException {

    	System.out.println("############# initialize - DefinetecfinAppBean");
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
				System.out.println("############### Destino (" + i + ") Tecnico = " + item.getString("FKSTCODTEC"));
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
	    		if (item.getString("FKSTCODTEC") != null && !item.getString("FKSTCODTEC").equals("")) {
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
			
	        DataBean tableBeanOrigem = app.getDataBean("dfntecfin");
	        tableBeanOrigem.getMbo().setValue("FKMXDSCSTAORI", dsStatus, MboConstants.NOVALIDATION_AND_NOACTION);
	        tableBeanOrigem.getMbo().setValue("FKMXNUCODSTAORI", status, MboConstants.NOVALIDATION_AND_NOACTION);
	        tableBeanOrigem.getMbo().setValueNull("FKSTCODTECFINORI");
    		
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

        DataBean tableBeanOrigem = app.getDataBean("dfntecfin");
        System.out.println("############# TableBeanOrigem = " + tableBeanOrigem);
        System.out.println("############# FKSTCODTECORI = " + tableBeanOrigem.getMbo().getString("FKSTCODTECFINORI"));
        System.out.println("############# UPOBSTEMP = " + getMbo().getString("MXSTAOBSTEMP"));
        //System.out.println("############# FKNOM = " + tableBeanOrigem.getMbo().getMboSet("RL04PER").getMbo(0).getString("DISPLAYNAME"));
        

        MboSetRemote tableBeanDestino = app.getResultsBean().getMboSet();

        MboRemote mbo;
        for (int i = 0; ((mbo = tableBeanDestino.getMbo(i)) != null); i++) {
            if (mbo.isSelected()) {
            	mbo.setValue("FKSTCODTEC", tableBeanOrigem.getMbo().getString("FKSTCODTECFINORI"), MboConstants.NOVALIDATION_AND_NOACTION);
                mbo.setValue("FKSTNOMTEC", tableBeanOrigem.getMbo().getMboSet("RL04PER").getMbo(0).getString("DISPLAYNAME"), MboConstants.NOVALIDATION_AND_NOACTION);
                mbo.setValue("MXSTAOBS", tableBeanOrigem.getMbo().getString("MXSTAOBSTEMP"), MboConstants.NOVALIDATION_AND_NOACTION);
                
                //Adicionado para definir os status também.
                System.out.println("########### FKMXNUCODSTAORI=" + tableBeanOrigem.getMbo().getString("FKMXNUCODSTAORI"));
                System.out.println("########### FKMXDSCSTAORI=" + tableBeanOrigem.getMbo().getString("FKMXDSCSTAORI"));
                mbo.setValue("DSSTAINT", tableBeanOrigem.getMbo().getString("FKMXDSCSTAORI"), MboConstants.NOVALIDATION_AND_NOACTION);
                mbo.setValue("FKMXNUCODSTATUS", tableBeanOrigem.getMbo().getString("FKMXNUCODSTAORI"), MboConstants.NOVALIDATION_AND_NOACTION);
                
                System.out.println("*** FKSTCODTECORI "+tableBeanOrigem.getMbo().getString("FKSTCODTECORI"));
                System.out.println("*** RL04PER.count() "+tableBeanOrigem.getMbo().getMboSet("RL04PER").count());
                System.out.println("*** DISPLAYNAME "+tableBeanOrigem.getMbo().getMboSet("RL04PER").getMbo(0).getString("DISPLAYNAME"));
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
