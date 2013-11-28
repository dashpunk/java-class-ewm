package br.inf.id2.me.bean;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
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
		//Cria um registro no Trâmite, próprio objeto da Dialog
		insert();
		
		//pega o protocolo em tela
		Mbo protocolo = (Mbo)app.getDataBean("MAINRECORD").getMbo();
		
		//preenchimento dos dados
		//System.out.println("#################### Location = " + protocolo.getString("LOCATION"));
		//System.out.println("############## getMbo().getMboSet('RL01TRANSLIN').count() = " + getMbo().getMboSet("RL01TRANSLIN").count());
		try {
			//getMbo().setValue("DSLOCORIG", protocolo.getString("LOCATION"), MboConstants.NOACCESSCHECK);
			System.out.println("############## NUCODUAID = " + protocolo.getInt("NUCODUAID"));
			getMbo().setValue("DSLOCORIG", protocolo.getInt("NUCODUAID"), MboConstants.NOVALIDATION_AND_NOACTION);
		} catch (Exception e) {
			System.out.println("######## Deu erro..." + e.getMessage());
		}
		System.out.println("############ Depois do DSLOCORIG");
		System.out.println("#################### MboSet antes = " + getMbo().getMboSet("RL01TRANSLIN"));
		Mbo transLin = (Mbo)getMbo().getMboSet("RL01TRANSLIN").add();
		System.out.println("#################### MboSet depois = " + getMbo().getMboSet("RL01TRANSLIN").count());
		transLin.setValue("TBTRANSID", getMbo().getInt("TBTRANSID"));
		System.out.println("#################### TRAN = " + getMbo().getInt("TBTRANSID"));
		transLin.setValue("DSLOCORIG", protocolo.getInt("NUCODUAID"), MboConstants.NOVALIDATION_AND_NOACTION);
		System.out.println("#################### DSLOCORIG = " + protocolo.getInt("NUCODUAID"));
		transLin.setValue("DSNUMDOC", protocolo.getString("ASSETNUM"));		
		System.out.println("#################### DSNUMDOC = " + protocolo.getString("ASSETNUM"));
		
		refreshTable();
		reloadTable();
	}
    
    
    public void tramitar() throws RemoteException, MXException {
    	System.out.println("################### Tramitando o protocolo!");
    	long lId = getMbo().getUniqueIDValue();
    	SAVE();
    	getMboSet().getMboForUniqueId(lId);
    	int ret = ROUTEWF();
    	System.out.println("################### ROUTEWF = " + ret);
    	
    	
    	System.out.println("########### Salvando o Bean principal!");
    	Mbo mainMbo = (Mbo)app.getDataBean("MAINRECORD").getMbo();
    	System.out.println("########### Recuperando o MainRECORD");
    	//mainMbo.setValue("MXONDEESTA", getMbo().getString("DSLOCDEST"));
    	mainMbo.setValue("MXONDEESTA", getMbo().getString("NUCODUADEST"));
    	
    	System.out.println("########### Associado o MXONDEESTA...");
    	
    	
    	System.out.println("############# Setando MXONDEESTA para os Filhos");
        MboSetRemote mboSetAssetFilho = mainMbo.getMboSet("ASSETCHILDREN");
        if(!mboSetAssetFilho.isEmpty() || mboSetAssetFilho.count() != 0) {
             for(int j = 0; j < mboSetAssetFilho.count(); j++) {
             	//mboSetAssetFilho.getMbo(j).setValue("MXONDEESTA", getMbo().getString("DSLOCDEST"));
                 mboSetAssetFilho.getMbo(j).setValue("MXONDEESTA", getMbo().getString("NUCODUADEST"));
             }
        }

    	System.out.println("############## Salvando...");
    	app.getDataBean("MAINRECORD").save();
    	System.out.println("############# Bean Salvo com sucesso!");
    	
    	
    	WebClientEvent event = sessionContext.getCurrentEvent();
    	Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
    	
    	
    	//Retirado a pedido do Jessé em 16/08/2011
    	/*
		sessionContext.queueRefreshEvent();
		Utility.showMessageBox(event, new MXApplicationException("protocolo", "RedirecionamentoCentroInicio"));
    	
    	System.out.println("################ Redirecionando a tela para o Centro de Início...");
    	//Cria dois objetos de evento, sendo que o eventType receberá a página no qual queremos redirecionar.
    	WebClientEvent eventType = new WebClientEvent("loadapp" , null, "startcntr" , sessionContext); 
    	WebClientEvent eventExec = new WebClientEvent("execevent", sessionContext.getCurrentAppId(), eventType, sessionContext);

    	//Define o evento criado na sessionContext
    	sessionContext.setCurrentEvent(eventExec);

    	//O método sendEvent irá redirecionar a página registrada
    	Utility.sendEvent(eventExec);
    	System.out.println("################# Fim do Redirecionamento...");
    	*/
    	
    }
    	
}
