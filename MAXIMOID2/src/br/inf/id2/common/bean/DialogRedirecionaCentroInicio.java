package br.inf.id2.common.bean;

import java.rmi.RemoteException;

import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Dyogo
 *
 */
public class DialogRedirecionaCentroInicio extends psdi.webclient.system.beans.AppBean {

    public DialogRedirecionaCentroInicio() {
    	System.out.println("################ Construtor");
    }

    public void fecharSalvando() throws RemoteException, MXException {
    	System.out.println("################### Salvando a tela!");
    	SAVE();
    	app.getDataBean("MAINRECORD").save();
     	System.out.println("############# Bean Salvo com sucesso!");
    	
    	System.out.println("############# Fechando a Dialog");
    	WebClientEvent event = sessionContext.getCurrentEvent();
    	Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
    	
		sessionContext.queueRefreshEvent();
		Utility.showMessageBox(event, new MXApplicationException("dialog", "RedirecionamentoCentroInicio"));
    	
    	System.out.println("################ Redirecionando a tela para o Centro de In�cio...");
    	//Cria dois objetos de evento, sendo que o eventType receber� a p�gina no qual queremos redirecionar.
    	WebClientEvent eventType = new WebClientEvent("loadapp" , null, "startcntr" , sessionContext); 
    	WebClientEvent eventExec = new WebClientEvent("execevent", sessionContext.getCurrentAppId(), eventType, sessionContext);

    	//Define o evento criado na sessionContext
    	sessionContext.setCurrentEvent(eventExec);

    	//O m�todo sendEvent ir� redirecionar a p�gina registrada
    	Utility.sendEvent(eventExec);
    	System.out.println("################# Fim do Redirecionamento...");
    	
    }
    	
}
