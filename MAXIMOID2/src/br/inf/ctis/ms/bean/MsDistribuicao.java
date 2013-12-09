/**
 * Classe para distribuição, iniciar e parar fluxo.
 */
package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.Method;

import psdi.app.rfq.RFQRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.SqlFormat;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.ResultsBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.workflow.WFInstance;
import psdi.workflow.WFInstanceRemote;
import psdi.workflow.WFInstanceSetRemote;
import psdi.workflow.WFProcess;
import psdi.workflow.WFProcessSetRemote;
import psdi.workflow.WorkFlowServiceRemote;
import br.inf.ctis.common.wf.StopWorkFlow;

/**
 * @author Eduardo Assis
 */

public class MsDistribuicao extends DataBean {
	public MsDistribuicao() {
	}

	// A Classe da Dialog é iniciada quando selecionada a linha

	public int selectrecord() throws MXException, RemoteException {
		MboRemote mbo = getAtorDemandaMbo();

		if (mbo != null) {
			applySelectAtorDemanda(mbo);
			pararWorkFlow(mbo);
			iniciarWorkFlow(mbo);
			// Fecha a Dialog
			WebClientEvent closeEvt = new WebClientEvent("dialogok", this.app.getCurrentPageId(), null, this.clientSession);
			WebClientRuntime.sendEvent(closeEvt);
			System.out.print("CTIS ########## MsDistribuicao - Fechou a Dialog");
		}

		return 1;
	}

	/**
	 *  Iniciando e Parando Instancias
	 */
	private void pararWorkFlow(MboRemote mbo) throws MXException, RemoteException {

		System.out.println("CTIS ############### Entrou no Rotear WORKORDER");
		WFInstanceSetRemote wfInstanceSet = (WFInstanceSetRemote) mbo.getMboSet("ACTIVEWORKFLOW");

		
		if (!wfInstanceSet.isEmpty()) {
			for (int w = 0; w < wfInstanceSet.count(); w++) {
				// Parar Instancia
				WFInstanceRemote wfInst = (WFInstanceRemote) wfInstanceSet.getMbo(w);
				wfInst.stopWorkflow("Parou, o que escrever aqui?");	
				// Salvando Instancia
				//wfInstanceSet.save();
				System.out.println("CTIS ############### Roteando: " + w);
				}
		} else {
			System.out.println("CTIS ############### wfInstanceSet está vazio.");
		}
	}
	
	/// Teste de ReStart
	
	
	public void iniciarWorkFlow(MboRemote mbo)
		    throws MXException, RemoteException
		    {
		    WFInstanceSetRemote wfInstanceSet=(WFInstanceSetRemote) mbo.getMboSet("ACTIVEWORKFLOW");

		    
		        if(!wfInstanceSet.isEmpty())
		        {
		            for(int wfInstance=0; wfInstance <wfInstanceSet.count();wfInstance++)
		            {
		            WFInstance wfInst=(WFInstance) wfInstanceSet.getMbo(wfInstance);
		            String processName = wfInst.getString("processname");         
		            WFProcessSetRemote wfProcessSet = (WFProcessSetRemote) mbo.getMboSet("WFPROCESS");
		            SqlFormat sqf1 = new SqlFormat(mbo.getUserInfo(), "processname = :1 and active = 1");
		             sqf1.setObject(1,"WFPROCESS","PROCESSNAME", processName);
		             wfProcessSet.setWhere(sqf1.format());
		            int matchingProcess = wfProcessSet.count();
		            WFProcess wfProcess = null;
		             
		            if (matchingProcess==1)
		            {
		                wfProcess = (WFProcess)wfProcessSet.getMbo(0);
		            }

		            wfInst.initiateWorkflow("Iniciou, o que escrever aqui?",wfProcess);

		            wfInstanceSet.save();
		            }
		        } 
		    } 
	
	// Guarda Valor do campo na MBO e retorna
	private MboRemote getAtorDemandaMbo() throws MXException, RemoteException {
		DataBean atorDemandaBean = this.app.getDataBean("msalnatordemanda");

		MboRemote mbo = null;
		if (atorDemandaBean != null) {
			mbo = atorDemandaBean.getMbo();
		}
		if (mbo == null) {
			DataBean appBean = this.app.getAppBean();
			mbo = appBean.getMboOrZombie();
		}
		System.out.print("CTIS ########## MsDistribuicao - Guarda Valor do MBO");
		return mbo;
	}

	// Setando Valores e Selecionando.

	private void applySelectAtorDemanda(MboRemote mbo) throws MXException,
			RemoteException {
		// Guarda as linhas selecionadas
		WebClientEvent event = this.app.getWebClientSession().getCurrentEvent();
		int row = getRowIndexFromEvent(event);
		ResultsBean resultsBean = this.app.getResultsBean();

		// Seta valores do PersonId e Flag
		if ((!mbo.isBasedOn("WORKORDER"))) {
			mbo.setValue("msalnatordemanda", getMbo(row).getString("personid"), 2L);
			mbo.setValue("msalflgescalacao", "1");
			mbo.setValue("msalnobs", "sim", 2L);

			System.out.println("CTIS ########## MsDistribuicao - Seta valores selecionados");
			return;
		}

		if (this.app.onListTab()) {
			System.out.println("CTIS ########## this.app.onListTab()");
			resultsBean.hasRecordsForAction();

			if (resultsBean.getTableStateFlags().isFlagSet(32768L)) {
				int i = 0;
				MboRemote workorder = resultsBean.getMbo(i);

				while (workorder != null) {
					if (workorder.isSelected()) {

						System.out
								.println("CTIS ########## workorder.isSelected()");
						try {
							// Verifica se a Opçãode Assinatura DIST retorna verdadeiro
							workorder.sigOptionAccessAuthorized("DIST");

							workorder.setValue("msalnatordemanda", getMbo(row).getString("personid"));
							workorder.setValue("msalflgescalacao", "1");
							workorder.setValue("msalnobs", "sim", 2L);

							System.out.print("CTIS ########## MsDistribuicao - Seta valores para WorkOrder (todas as linhas selecionadas)");
						} catch (MXException e) {
							System.out
									.print("CTIS ########## MsDistribuicao - Erro na Linha 92");
							((MboSet) workorder.getThisMboSet()).addWarning(e);
						}
					}

					i++;
					workorder = resultsBean.getMbo(i);
				}
				MboSet tkset = (MboSet) resultsBean.getMboSet();
				this.clientSession.addMXWarnings(tkset.getWarnings());
				this.clientSession.handleMXWarnings(false);
				resultsBean.save();

			}
		}
	}
}
