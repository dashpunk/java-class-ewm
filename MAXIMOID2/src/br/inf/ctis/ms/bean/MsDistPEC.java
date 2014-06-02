/**
 * Classe para distribuição, iniciar e parar fluxo.
 */
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
 * @author Willians L Andrade
 * @author andrel.almeida
 */

public class MsDistPEC extends DataBean {

	String camposObrigatorios = "";
	
	int PoNum = 0; 
	int MsFluxo = 0;
	int MsAcao = 0; 
	String MsGrupo = ""; 
	String MSFLAGMSG = "0";
	String Statuspec ="";
	String MsDistDem ="";
	
	

	public MsDistPEC() {	
	}

	public int selectrecord() throws MXException, RemoteException {

		super.selectrecord();
		
		
		app.getDataBean("MAINRECORD").getMbo().setValue("MSFLAGMSG", "0", MboConstants.NOACCESSCHECK);
		save();
		//MboContants.NOACCESSCHECK for�a a inser��o
		
		
		linhaSelecionada();
		validarCampos();
		setarValores();
		fecharERotear();
		
		

		return 1;
	}

	private void linhaSelecionada() throws RemoteException, MXException  {
				WebClientEvent event = this.app.getWebClientSession().getCurrentEvent();
				int row = getRowIndexFromEvent(event);

				MboRemote mboAcao;
				mboAcao = getMbo().getMboSet("MSTBPEC_ACOES").getMbo(0);
				
				MboRemote mboStatus;
				mboStatus = getMbo().getMboSet("MSTBPEC_ACOES2").getMbo(0);

				MboRemote mboGrupo;
				mboGrupo = getMbo().getMboSet("MSTBPEC_GRUPO").getMbo(0);

				PoNum = app.getDataBean("MAINRECORD").getMbo().getInt("PONUM"); 
				MsFluxo = mboAcao.getInt("MSTBPEC_FLUXOID");
				MsAcao = getMbo(row).getInt("MSACAO");
				Statuspec = mboStatus.getString("DESCRIPTION");
				System.out.println(">>>>>>>>>>>>Dentro do metodo Linha selecionada");
				
				
				if(mboGrupo.getBoolean("MSHABDISTR")) {
					
					MsDistDem = mboGrupo.getString("MSMOMDIST");
					System.out.println(">>>>>>>>>>>>Fluxo Habilitado para personalizado, grupo: " + MsDistDem);
				}
				else {
					MsGrupo = mboGrupo.getString("MSGRUPO"); 
					System.out.println(">>>>>>>>>>>>Fluxo Habilitado para padrao, grupo: " + MsGrupo);
				}
						
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
	
	private void validarCampos() throws MXException, RemoteException  {
		System.out.println("applyCustomAction");

		MboRemote aMbo;
		MboRemote bMbo;
		
		MboSet mboCampos;
		mboCampos = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MSTBPEC_CAMPOS", getMbo().getUserInfo());
		mboCampos.setWhere("MSTBPEC_OPCOESID in (SELECT MSTBPEC_OPCOESID FROM MSTBPEC_OPCOES WHERE MSACAO = '"+MsAcao+"')");
		mboCampos.reset();
		
		int retorno = 0;
		
		// Se Existir Campos Obrigatorios
		if (mboCampos.count() > 0) {
		
			for (int i = 0; (aMbo = mboCampos.getMbo(i)) != null; i++) {
				
				System.out.println("Campo Obrigatorio " + i + ":" + aMbo.getString("MSALNCAMPOS"));
				
				String Campo = aMbo.getString("MSALNCAMPOS");
				String DescCampo = aMbo.getString("DESCRIPTION");
				String Objeto = aMbo.getString("MSRELACIONAMENTO");
				
				
				MboSet mboPO;
				mboPO = (MboSet) app.getDataBean("MAINRECORD").getMbo().getMboSet(Objeto);
				
				int jTamanho = mboPO.count();
				
					for (int j = 0; j < jTamanho; j++) {
						if (mboPO.getMbo(j).isNull(Campo)){
							setResultadoCampoObrigatorio(DescCampo);
							retorno++;
						}
					
				}
				
				
			}
		}
		
		if (app.getDataBean("MAINRECORD").getMbo().isNull("MSAREADAF")){
			app.getDataBean("MAINRECORD").getMbo().setValue("MSFLAGMSG", "1", MboConstants.NOACCESSCHECK);
    		save();
    		throw new MXApplicationException("ms_clpo01", "informeAreaDaf");
		}
		
		if (retorno > 0){
			
	        if (!camposObrigatorios.equals("")) {
	        		app.getDataBean("MAINRECORD").getMbo().setValue("MSFLAGMSG", "1", MboConstants.NOACCESSCHECK);
	        		save();
 		            throw new MXApplicationException("ms_clpo01", "camposObrigatorios", new String[]{camposObrigatorios});
	        }
			
		}
		
		app.getDataBean("MAINRECORD").getMbo().setValue("MSFLAGMSG", "0", MboConstants.NOACCESSCHECK);
		save();
	}

	private void setarValores() throws RemoteException, MXException {

		MboRemote mboPO = app.getDataBean("MAINRECORD").getMbo();

		mboPO.setValue("MSPECACAO", MsAcao);
		mboPO.setValue("MSPECFLUXO", MsFluxo);
		mboPO.setValue("MSPECGRUPO", MsGrupo);
		mboPO.setValue("STATUSPEC", Statuspec);
		mboPO.setValue("MSPECFLUXSTA", MsDistDem);
		System.out.println(">>>>>>>>>>>>Setando Registro de Designa��o do Fluxo:" + MsDistDem);

	}

	private void setResultadoCampoObrigatorio(String string) {
		if (string.equals("")) {
			camposObrigatorios = "";
		} else {
			if (camposObrigatorios.indexOf(string) == -1) {
				camposObrigatorios += "\n" + string;
			}
		}
	}

	public String toWhereClause(Object arg0, MboSetRemote arg1)
			throws MXException, RemoteException {
		return "";
	}

}