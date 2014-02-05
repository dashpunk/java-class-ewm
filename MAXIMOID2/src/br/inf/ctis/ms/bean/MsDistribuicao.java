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
 * @author Willians L Andrade
 */

public class MsDistribuicao extends DataBean {
	public MsDistribuicao() {
	}

	// A Classe da Dialog é iniciada quando selecionada a linha

	public int selectrecord() throws MXException, RemoteException {
		MboRemote mbo = getAtorDemandaMbo();

			applySelectAtorDemanda(mbo);
			mbo = getAtorDemandaMbo();
			pararWorkFlow(mbo);
			iniciarWorkFlow(mbo);
			WebClientEvent closeEvt = new WebClientEvent("dialogok", this.app.getCurrentPageId(), null, this.clientSession);
			WebClientRuntime.sendEvent(closeEvt);


		return 1;
	}
	/**
	 * Iniciando Instancias   
	 */
    
	public void iniciarWorkFlow(MboRemote mbo)
            throws MXException, RemoteException
            {
		
            WFInstanceSetRemote wfInstanceSet=(WFInstanceSetRemote) mbo.getMboSet("ACTIVEWORKFLOW");

            
                if(!wfInstanceSet.isEmpty())
                {
                    for(int wfInstance=0; wfInstance <wfInstanceSet.count();wfInstance++)
                    {
                    	System.out.println("Chamar iniciar: wfInstance");
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

                    wfInst.initiateWorkflow("Demanda Judicial Designada",wfProcess);

                    wfInstanceSet.save();
                    }
                } 
            } 
    
	/**
	 * Parando Instancias
	 */

	private void pararWorkFlow(MboRemote mbo) throws MXException, RemoteException {
		// Guarda as linhas selecionadas
		WebClientEvent event = this.app.getWebClientSession().getCurrentEvent();
		int row = getRowIndexFromEvent(event);
		ResultsBean resultsBean = this.app.getResultsBean();

		if (this.app.onListTab()) {
			System.out.println("CTIS ########## this.app.onListTab()");
			resultsBean.hasRecordsForAction();

			if (resultsBean.getTableStateFlags().isFlagSet(32768L)) {
				int i = 0;
				MboRemote workorder = resultsBean.getMbo(i);

				while (workorder != null) {
					if (workorder.isSelected()) {

						System.out.println("CTIS ########## workorder.isSelected()");
						try {

							WFInstanceSetRemote wfInstanceSet = (WFInstanceSetRemote) workorder.getMboSet("ACTIVEWORKFLOW");
							
							if (!wfInstanceSet.isEmpty()) {
								for (int w = 0; w < wfInstanceSet.count(); w++) {
									WFInstanceRemote wfInst = (WFInstanceRemote) wfInstanceSet.getMbo(w);
									wfInst.stopWorkflow("Demanda Judicial foi parada.");
									wfInstanceSet.save();
								}

							} else {
								System.out.println("CTIS ############### wfInstanceSet está vazio.");
							}
							System.out.println("Chamar iniciar.");
							iniciarWorkFlow(workorder);
							
							System.out.print("CTIS ########## MsDistribuicao - Seta valores para WorkOrder (todas as linhas selecionadas)");
						} catch (MXException e) {
							System.out.print("CTIS ########## MsDistribuicao - Erro na Linha 92");
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
	// Guarda Valor do campo na MBO e retorna
	private MboRemote getAtorDemandaMbo() throws MXException, RemoteException {
		DataBean atorDemandaBean = this.app.getDataBean("msalnatordemanda");
		
		System.out.println("Chamar getAtorDemandaMbo:" + atorDemandaBean);
		
		MboRemote mbo = null;
		if (atorDemandaBean != null) {
			mbo = atorDemandaBean.getMbo();
		}
		if (mbo == null) {
			DataBean appBean = this.app.getAppBean();
			mbo = appBean.getMboOrZombie();
		}
		return mbo;
	}

	// Setando Valores e Selecionando.

	private void applySelectAtorDemanda(MboRemote mbo) throws MXException, RemoteException {
		// Guarda as linhas selecionadas
		WebClientEvent event = this.app.getWebClientSession().getCurrentEvent();
		int row = getRowIndexFromEvent(event);
		ResultsBean resultsBean = this.app.getResultsBean();

		if (this.app.onListTab()) {
			resultsBean.hasRecordsForAction();

			if (resultsBean.getTableStateFlags().isFlagSet(32768L)) {
				int i = 0;
				MboRemote workorder = resultsBean.getMbo(i);

				while (workorder != null) {
					if (workorder.isSelected()) {

						try {
							workorder.sigOptionAccessAuthorized("DIST");

							workorder.setValue("msalnatordemanda", getMbo(row).getString("personid"));
							workorder.setValue("msalflgescalacao", "1");
							
						} catch (MXException e) {
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