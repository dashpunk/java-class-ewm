package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.*;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;
import psdi.workflow.DirectorInput;
import psdi.workflow.WorkflowDirector;

/**
 * 
 * @author andrel.almeida
 *
 */


public class MsDistSAA extends DataBean {
	
	int PoNum = 0; 
	int MsFluxo = 0;
	int MsAcao = 0; 
	String MsGrupo = ""; 
	String MSFLAGMSG = "0";
	int Statusaa =0;

	public MsDistSAA (){
		System.out.println(">>>>>>>>>> Dentro da classe: br.inf.ctis.ms.bean.MsDistSAA_teste01");
	}
	
	public int selectrecord() throws MXException, RemoteException {
		
		super.selectrecord();
		
		linhaSelecionada();		
		setarValores();
		fecharERotear();
		
		return 1;
	}
	
	private void linhaSelecionada() throws RemoteException, MXException  {
		WebClientEvent event = this.app.getWebClientSession().getCurrentEvent();
		int row = getRowIndexFromEvent(event);		
		
				
		PoNum = app.getDataBean("MAINRECORD").getMbo().getInt("PONUM"); 
		// Antes: MsAcao = getMbo(row).getInt("NEXTAREFA");
		Statusaa = app.getDataBean("MAINRECORD").getMbo().getMboSet("MSTBSAA_DISTRIBUICAO").getMbo(0).getInt("TAREFA");
		System.out.println(">>>>>>>>>> Valor da Tarefa da tabela de distribuicao pessoal: "+Statusaa);
		
}

		private void fecharERotear() throws RemoteException, MXException {
		
		// Fechar Dialog ACTION
		
		WebClientEvent closeEvt = new WebClientEvent("dialogok",
				this.app.getCurrentPageId(), null, this.clientSession);
		
		WebClientRuntime.sendEvent(closeEvt);
		
		// Auto Iniciar WORKFLOW
		
		DataBean ownerBean = this.app.getDataBean();
		
			if (ownerBean != null) {
				MboRemote mbo = ownerBean.getMbo();
			
				WebClientSession wcs = this.clientSession;
				WorkflowDirector director = wcs.getWorkflowDirector();
				director.allowAutoInit();
				director.startInput(wcs.getCurrentAppId(), mbo,	DirectorInput.workflow);
			}
			
		save();
		
		}
		
		private void setarValores() throws RemoteException, MXException {

			MboRemote mboPO = app.getDataBean("MAINRECORD").getMbo();

			mboPO.setValue("STATUSAA", Statusaa);
			
			super.save();

		}

}
