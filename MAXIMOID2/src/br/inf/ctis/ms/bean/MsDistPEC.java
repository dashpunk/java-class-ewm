/**
 * Classe para distribuição, iniciar e parar fluxo.
 */
package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;

import com.sun.enterprise.util.Utility;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.*;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;
import psdi.workflow.DirectorInput;
import psdi.workflow.WorkflowDirector;

/**
 * @author Willians L Andrade
 */

public class MsDistPEC extends DataBean {
	public MsDistPEC() {
		System.out.println("-------------------- PEC | Entrou na Classe MsDistPEC.");
	}

	public int selectrecord() throws MXException, RemoteException {

		WebClientEvent event = this.app.getWebClientSession().getCurrentEvent();
		int row = getRowIndexFromEvent(event);

		System.out.println("-------------------- PEC | Linha Selecionada DESCRICAO: " + getMbo(row).getString("DESCRIPTION"));
		System.out.println("-------------------- PEC | Linha Selecionada ID FILHO: " + getMbo(row).getInt("MSTBPEC_OPCOESID"));
		System.out.println("-------------------- PEC | Linha Selecionada PAI: " + getMbo(row).getInt("MSTBPEC_ACOESID"));
		System.out.println("-------------------- PEC | Linha Selecionada DESTINO: " + getMbo(row).getInt("MSACAO"));

		
		// Fechar Dialog ACTION

		WebClientEvent closeEvt = new WebClientEvent("dialogok", this.app.getCurrentPageId(), null, this.clientSession);

		WebClientRuntime.sendEvent(closeEvt);

		// Auto Iniciar WORKFLOW
		super.selectrecord();

		DataBean ownerBean = this.app.getDataBean();
		if (ownerBean != null) {
			MboRemote mbo = ownerBean.getMbo();

			WebClientSession wcs = this.clientSession;
			WorkflowDirector director = wcs.getWorkflowDirector();
			director.allowAutoInit();
			director.startInput(wcs.getCurrentAppId(), mbo, DirectorInput.workflow);
		}
		
		// Setar Valores na PO
		
		MboRemote mboAcao;
		mboAcao = getMbo().getMboSet("MSTBPEC_ACOES").getMbo(0);
		
		MboRemote mboGrupo;
		mboGrupo = getMbo().getMboSet("MSTBPEC_GRUPO").getMbo(0);
		
		int PoNum = app.getDataBean("MAINRECORD").getMbo().getInt("PONUM"); // Número da PO
		int MsFluxo = mboAcao.getInt("MSTBPEC_FLUXOID"); 					// Fluxo de Ação
		int MsAcao = getMbo(row).getInt("MSACAO"); 							// Ação de Opções
		String MsGrupo = mboGrupo.getString("MSGRUPO"); 					// Grupo de Grupos
		
		System.out.println("-------------------- PEC | PO/Fluxo/Ação/Grupo: ----> " + PoNum + " / " + MsFluxo + " / " +  MsAcao + " / " +  MsGrupo);
		
		// Setando Valores na PO
		
		MboRemote mboPO = app.getDataBean("MAINRECORD").getMbo();
			
		mboPO.setValue("MSPECACAO", MsAcao);
		mboPO.setValue("MSPECFLUXO", MsFluxo);
		mboPO.setValue("MSPECGRUPO", MsGrupo);
		
		System.out.println("-------------------- PEC | Valores Setados na PO: ----> " + PoNum + " / " + MsFluxo + " / " +  MsAcao + " / " +  MsGrupo);
		
		
		save();
		
		return 1;
	}

}