package br.inf.id2.valec.bean;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Dyogo
 *
 */
public class GuiaTramiteDialogBean extends psdi.webclient.system.beans.AppBean {


    public GuiaTramiteDialogBean() {
    	System.out.println("############ Construtor");
    }

    /**
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
	protected void initialize() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		super.initialize();
		//Cria um registro no Tr�mite, pr�prio objeto da Dialog
		insert();
		
		//pega o protocolo em tela
		Mbo protocolo = (Mbo)app.getDataBean("MAINRECORD").getMbo();
		
		//preenchimento dos dados
		getMbo().setValue("DSLOCORIG", protocolo.getString("LOCATION"));
		Mbo transLin = (Mbo)getMbo().getMboSet("MXRL01TRANSLIN").add();
		transLin.setValue("MXTRANSID", getMbo().getInt("MXTBTRANSID"));
		transLin.setValue("DSLOCORIG", protocolo.getString("LOCATION"));		
		transLin.setValue("DSNUMDOC", protocolo.getString("ASSETNUM"));
		
		System.out.println("################ Inicializando GuiaTramiteDialogBean");
		
    }
    
    
    public void tramitar() throws RemoteException, MXException {
    	System.out.println("################### Tramitando o protocolo!");
		//if (app.getDataBean("MAINRECORD").getMbo().getMboSet("RL02TRANS") != null && app.getDataBean("MAINRECORD").getMbo().getMboSet("RL02TRANS").count() > 0) {
		//	app.getDataBean("MAINRECORD").getMbo().setValue("DTRES", getMbo().getMboSet("RL02TRANS").getMbo(0).getDate("DTRES"));
		//}
    	long lId = getMbo().getUniqueIDValue();
    	SAVE();
    	getMboSet().getMboForUniqueId(lId);
    	int ret = ROUTEWF();
    	System.out.println("################### ROUTEWF = " + ret);

    	System.out.println("########### Salvando o Bean principal!");
    	Mbo mainMbo = (Mbo)app.getDataBean("MAINRECORD").getMbo();
    	mainMbo.setValue("MXONDEESTA", getMbo().getString("DSLOCDEST"));
    	// Adicionado à classe original INICIO
    	        MboSetRemote mboSetAssetFilho = mainMbo.getMboSet("ASSETCHILDREN");
    	             if(!mboSetAssetFilho.isEmpty() || mboSetAssetFilho.count() != 0)
    	                  {
    	                  for(int j = 0; j < mboSetAssetFilho.count(); j++)
    	                      mboSetAssetFilho.getMbo(j).setValue("MXONDEESTA", getMbo().getString("DSLOCDEST"));

    	                  }
    	
    	app.getDataBean("MAINRECORD").save();
    	System.out.println("############# Bean Salvo com sucesso!");

    	WebClientEvent event = sessionContext.getCurrentEvent();
    	Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
    	
    	
    }
    	
}
