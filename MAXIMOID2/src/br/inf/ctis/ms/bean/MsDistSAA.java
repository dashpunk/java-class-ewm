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
	String Statuspec ="";

	public MsDistSAA (){
		System.out.println(">>>>>>>>>> Dentro da classe: br.inf.ctis.ms.bean.MsDistSAA_teste00");
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

		MboRemote mboAcao;
		mboAcao = getMbo().getMboSet("MSTBPEC_ACOESPREG").getMbo(0);
		
		MboRemote mboStatus;
		mboStatus = getMbo().getMboSet("MSTBPEC_ACOESPREG2").getMbo(0);

		MboRemote mboGrupo;
		mboGrupo = getMbo().getMboSet("MSTBPEC_GRUPO").getMbo(0);

		PoNum = app.getDataBean("MAINRECORD").getMbo().getInt("PONUM"); 
		MsFluxo = mboAcao.getInt("MSTBPEC_FLUXOID");
		MsAcao = getMbo(row).getInt("MSACAO");
		Statuspec = mboStatus.getString("DESCRIPTION");
		MsGrupo = mboGrupo.getString("MSGRUPO"); 		
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

			mboPO.setValue("MSPECACAO", MsAcao);
			mboPO.setValue("MSPECFLUXO", MsFluxo);
			mboPO.setValue("MSPECGRUPO", MsGrupo);
			mboPO.setValue("STATUSPEC", Statuspec);
			System.out.println("Setando Status PEC:" + Statuspec);

		}

}
