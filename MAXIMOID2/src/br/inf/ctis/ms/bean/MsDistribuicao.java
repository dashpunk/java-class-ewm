/**
 * Classe para distribuição, iniciar e parar fluxo.
 */
package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.Method;

import psdi.app.rfq.RFQRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.SqlFormat;
import psdi.server.MXServer;
import psdi.util.DBConnect;
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
import br.inf.id2.common.util.Uteis;

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
	private void pararWorkFlow(String NumProcesso) throws MXException, RemoteException {

		String Num = NumProcesso;
		System.out.println("CTIS ############### Entrou no Parar WORKORDER" + Num);
		
		if (Num != "") {
			String WFID 		= "0";
			String OWNERID 		= "0";
			String NODEID 		= "0";
			String PROCESSREV 	= "0";
			String PROCESSNAME 	= "0";
			
			// Selecionado o maior WFID e OWNERID
			
			MboSet WFINSTANCE;
			WFINSTANCE = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("WFINSTANCE", sessionContext.getUserInfo());
			WFINSTANCE.setWhere("OWNERTABLE = 'WORKORDER' and OWNERID in (select workorderid from workorder where MSNUMPROC = '" + NumProcesso +"') order by WFID desc");
			WFINSTANCE.reset();
			
			WFID = WFINSTANCE.getMbo(0).getString("WFID");
			WFID = Uteis.getApenasNumeros(WFID);
			
			OWNERID = WFINSTANCE.getMbo(0).getString("OWNERID");
			OWNERID = Uteis.getApenasNumeros(OWNERID);
			
			System.out.println("CTIS ############### Parar WORKORDER WFID: (" + WFINSTANCE.count() + ") -- " + WFID);
			
			// Localiza Nodeid Process e ProcessName
			
			MboSet WFCALLSTACK;
			WFCALLSTACK = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("WFCALLSTACK", sessionContext.getUserInfo());
			WFCALLSTACK.setWhere("WFID = '" + WFID + "'");
			WFCALLSTACK.reset();
			
			NODEID = WFCALLSTACK.getMbo(0).getString("NODEID");
			NODEID = Uteis.getApenasNumeros(NODEID);
			PROCESSREV = WFCALLSTACK.getMbo(0).getString("PROCESSREV");
			PROCESSNAME = WFCALLSTACK.getMbo(0).getString("PROCESSNAME");
			
			String driver = null;
			String url = null;
			String username = null;
			String password = null;
			Properties prop;
			
			// CONEXAO BANCO DESENVOLVIMENTO 
		        prop = MXServer.getMXServer().getConfig();
		        byte[] bytes = null;
		        driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
		        url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=srvoradf2.saude.gov)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=DFHO1.SAUDE.GOV)(FAILOVER_MODE=(TYPE=SELECT)(METHOD=BASIC)(RETRIES=20)(DELAY=5))))");
		        username = prop.getProperty("mxe.db.user", "dbmaximo");
		        password = prop.getProperty("mxe.db.password", "max894512");

	        try {
				Class.forName(driver).newInstance();
	            java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
	            Statement stmt = conexao.createStatement();
	            // 1 update
	            PreparedStatement up1 = conexao.prepareStatement("UPDATE WFINSTANCE SET ACTIVE = 0 WHERE WFID = '" + WFID + "'");
	            up1.execute();
	            conexao.commit();
	            // 2 update
	            PreparedStatement up2 = conexao.prepareStatement("UPDATE WFASSIGNMENT SET ASSIGNSTATUS = 'INACTIVE' WHERE WFID = '" + WFID + "'");
	            up2.execute();
	            conexao.commit();
	            // 3 update
	            PreparedStatement up3 = conexao.prepareStatement("UPDATE WFCALLSTACK SET ACTIVE = 0 WHERE WFID = '" + WFID + "'");
	            up3.execute();
	            conexao.commit();
	            // 4 insert
	            PreparedStatement in4 = conexao.prepareStatement("Insert into WFTRANSACTION   (TRANSID, NODEID, WFID, TRANSTYPE, TRANSDATE, NODETYPE, PROCESSREV, PROCESSNAME, PERSONID, OWNERTABLE, OWNERID) (select wftransactionseq.nextval, " + NODEID + ", " + WFID + ", 'WFINTUS',  sysdate, 'TAREFA', " + PROCESSREV + ", '" + PROCESSNAME + "', 'MAXADMIN', 'WORKORDER', " + OWNERID + "  from wfinstance where wfid = " + WFID +")");
	            in4.execute();
	            conexao.commit();
	            
	        } catch (Exception e) {
	            System.out.println("---e "+e.getMessage());
	        }

		} else {
			System.out.println("CTIS ############### Parar WORKORDER: Retornou vazio!");
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
							
							pararWorkFlow(workorder.getString("MSNUMPROC"));

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
