/**
 * Classe para distribuiÃ§Ã£o, iniciar e parar fluxo.
 */
package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.*;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;
import psdi.workflow.DirectorInput;
import psdi.workflow.WFNotification;
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
	int qtdAnexoMsg = 0;
	String MsGrupo = ""; 
	String MSFLAGMSG = "0";
	String Statuspec ="";
	String MsDistDem ="";
	String MsDistDem2 ="";
	boolean Msckanexos;
	//Conexao para anexos	
	Properties prop;
	Connection conexao = null;
	Statement st2 = null;
	ResultSet QtdAnexos = null;
		
	

	public MsDistPEC() {	
		System.out.println(">>>>>>>>>> Dentro da classe: br.inf.ctis.ms.bean.MsDistPEC_teste00");
	}

	public int selectrecord() throws MXException, RemoteException {

		super.selectrecord();
		
		
		app.getDataBean("MAINRECORD").getMbo().setValue("MSFLAGMSG", "0", MboConstants.NOACCESSCHECK);
		save();
		//MboContants.NOACCESSCHECK força a inserção
		
		
		linhaSelecionada();
		validarCampos();
		contaAnexosMsg();
		setarValores();
		DefineDoc();
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
		Msckanexos = mboStatus.getBoolean("MSCKANEXOS");
		System.out.println(">>>>>>>>>>>>Dentro do metodo Linha selecionada");
		
		
		if(mboGrupo.getBoolean("MSHABDISTR")) {
			
			MsDistDem = mboGrupo.getString("MSMOMDIST");
			MsDistDem2 = mboGrupo.getString("MSMOMDIST2");
			System.out.println(">>>>>>>>>>>>Fluxo Habilitado para personalizado, Momento da Dest.: " + MsDistDem);
			System.out.println(">>>>>>>>>>>>Fluxo Habilitado para personalizado, Qual Momento Dest: " + MsDistDem2);
		}
		else {
			MsGrupo = mboGrupo.getString("MSGRUPO"); 
			System.out.println(">>>>>>>>>>>>Fluxo Habilitado para padrao, grupo: " + MsGrupo);
		}
				
}
	private void validarCampos() throws MXException, RemoteException  {
		System.out.println("applyCustomAction");

		MboRemote aMbo;		
		
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
	private void contaAnexosMsg()throws RemoteException, MXException  {
		
		System.out.println(">>>>>>>>>>>> Entrando no Metodo contaAnexosMsg ");

		qtdAnexoMsg = app.getDataBean("MAINRECORD").getMbo().getMboSet("MSPECANEXOS").count();
		
		System.out.println(">>>>>>>>>>>> Quantidade de Anexos na App do PEC: "+qtdAnexoMsg);
	}
	private void setarValores() throws RemoteException, MXException {

		MboRemote mboPO = app.getDataBean("MAINRECORD").getMbo();
		MboRemote mboGrupo;
		mboGrupo = getMbo().getMboSet("MSTBPEC_GRUPO").getMbo(0);

		mboPO.setValue("MSPECACAO", MsAcao);
		mboPO.setValue("MSPECFLUXO", MsFluxo);
		mboPO.setValue("STATUSPEC", Statuspec);
		mboPO.setValue("MSCKANEXOS", Msckanexos);
		
		System.out.println(">>>>>>>>>>>>Antes do IF valor:" + mboGrupo.getBoolean("MSFLAGRESP"));
		if (mboGrupo.getBoolean("MSFLAGRESP")){
			System.out.println(">>>>>>>>>>>>Valor do 2 Responsavel:" + mboPO.getString("MSSECREPB"));
			if(!mboPO.getString("MSSECREPB").equals("NENHUMA")){
				mboPO.setValue("MSFLAG2RESPORCA","1");
				System.out.println(">>>>>>>>>>>>Setando para verdeiro:" +mboPO.getBoolean("MSFLAG2RESPORCA"));
			}
		}
		else{
			mboPO.setValue("MSFLAG2RESPORCA","0");
			System.out.println(">>>>>>>>>>>>Setando para falso:" +mboPO.getBoolean("MSFLAG2RESPORCA"));
		}
				
		if(mboGrupo.getBoolean("MSHABDISTR")) {
			
			mboPO.setValue("MSPECDIST", MsDistDem);
			mboPO.setValue("MSPECDIST2", MsDistDem2);
			mboPO.setValue("MSPECGRUPO", MsGrupo);
			System.out.println(">>>>>>>>>>>>Setando Registro de Designação do Fluxo:" + MsDistDem);
		}
		else {
			mboPO.setValue("MSPECGRUPO", MsGrupo); 
			System.out.println(">>>>>>>>>>>>Setando Registro de Designação do Fluxo: " + MsGrupo);
		}
		
		
		//Adicionando a passagem da tarefa na tabela de anexos/mensagens
		System.out.println(">>>>>>>>>>>>Entrando na passagem para add a tarefa em anexos/mensagens ");
		prop = MXServer.getMXServer().getConfig();		
		String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
        String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@srvoradf0.saude.gov:1521/DFPO1.SAUDE.GOV");
        String username = prop.getProperty("mxe.db.user", "dbmaximo");
        String password = prop.getProperty("mxe.db.password", "max894512");
		try {
			Class.forName(driver).newInstance();
			conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
			st2 = conexao.createStatement();
			QtdAnexos = st2.executeQuery("select count(*) from MSPECANEXOS where ponum="+mboPO.getString("PONUM")+"");
			System.out.println(">>>>>>>>>>>>Quantidade de anexos no momento: " + QtdAnexos);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		super.save(); 

	}
	private void DefineDoc() throws RemoteException, MXException {
		
		MboRemote mboDoc;
		MboRemote mboDestino = null;
		MboRemote mboAcaoDoc;
		mboAcaoDoc = getMbo().getMboSet("MSTBPEC_ACOES2").getMbo(0);
		
		System.out.println(">>>>>>>>> Dentro do Metodo DefineDoc() ");
		
				
		if(!mboAcaoDoc.getMboSet("MSTBPEC_DOC").isEmpty()){
			System.out.println(">>>>>>>>> Existe Documento para Inserir neste momento, O DOCUMENTO E: "+mboAcaoDoc.getMboSet("MSTBPEC_DOC").getMbo(0).getString("DESCRIPTION"));
			String TipDocPo="";
			String TipDocFlux="";	
			for(int k=0;k < mboAcaoDoc.getMboSet("MSTBPEC_DOC").count();k++){
				
				TipDocPo = app.getDataBean("MAINRECORD").getMbo().getString("MSALCODTIPOCONTRATACAO");
				TipDocFlux = mboAcaoDoc.getMboSet("MSTBPEC_DOC").getMbo(k).getString("MSTIPDOC");
				System.out.println(">>>>>>>>> Dentro do for de verificação de documentos");
				System.out.println(">>>>>>>>> Valor do Tipo na DOCFLUXO: "+TipDocFlux);
				System.out.println(">>>>>>>>> Valor da Tipo na PO: "+TipDocPo);
				if(TipDocPo.equals(TipDocFlux)){
					
					System.out.println(">>>>>>>>> Dentro do if de INEXIGIBILIDADE, valor: "+mboAcaoDoc.getMboSet("MSTBPEC_DOC").getMbo(k).getInt("MSTBDOCID"));
					app.getDataBean("MAINRECORD").getMbo().setValue("MSTBDOCID",mboAcaoDoc.getMboSet("MSTBPEC_DOC").getMbo(k).getInt("MSTBDOCID"));
					super.save();
					
				}
				else{
					System.out.println(">>>>>>>>> Não Existe Documento Criado para o Modelo!");
				}
				
			}		
						
				
			
			if (app.getDataBean("MAINRECORD").getMbo(0).getMboSet("MSTBCONTE").isEmpty()){
				
				System.out.println(">>>>>>>>> Dentro do if MSTBCONTE ");
				
				for (int i = 0; ((mboDoc= app.getDataBean("MAINRECORD").getMbo(0).getMboSet("MSTBDOC").getMbo(0).getMboSet("MSTBMOD").getMbo(0).getMboSet("MSTBCLACAP").getMbo(i)) !=null); i++) {
					
					System.out.println(">>>>>>>>> Posicao da Clausula:  "+mboDoc.getString("MSPOSICAO"));
					
					mboDestino = app.getDataBean("MAINRECORD").getMbo(0).getMboSet("MSTBCONTE").add();
					
					mboDestino.setValue("DESCRIPTION", mboDoc.getString("DESCRIPTION"));
					
					mboDestino.setValue("MSTITULO", mboDoc.getString("MSTITULO"));
					
					mboDestino.setValue("MSPOSICAO", mboDoc.getString("MSPOSICAO"));
					;
					mboDestino.setValue("MSTBDOCID", app.getDataBean("MAINRECORD").getMbo(0).getInt("MSTBDOCID"));
					
					mboDestino.setValue("MSTBCLACAPID", mboDoc.getInt("MSTBCLACAPID"));
					
					mboDestino.setValue("PONUM",  app.getDataBean("MAINRECORD").getMbo(0).getString("PONUM"));
					
					System.out.println(">>>>>>>>> Antes de verificar se o PAI e NULL");
					if(mboDoc.isNull("MSPAI")){
						mboDestino.setValue("MSPAI", "0");
						System.out.println(">>>>>>>>> PAI NULL");
					}
					if(!mboDoc.isNull("MSPAI")){
						mboDestino.setValue("MSPAI",mboDoc.getString("MSPAI"));
						System.out.println(">>>>>>>>> PAI"+mboDoc.getString("MSPAI"));
					}
					
					System.out.println(">>>>>>>>> Antes da checagem do item bloqueado");
					if(mboDoc.getBoolean("MSBLOQUEADO")){
						mboDestino.setValue("MSTATUS", "BLOQUEADO");
						System.out.println(">>>>>>>>> Setando BLOQUEADO em mstatus");
					}
					else{
						mboDestino.setValue("MSTATUS", "LIBERADO");
					}
					
					
				}
			}
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
	
	
	// metodo para carregar o edital/contrato em determinado status
	private void setarDocPec() throws MXException {
		try{

			System.out.println(">>>>>>>>> Dentro do metodo setarDocPec()");
			
			if(!app.getDataBean("MAINRECORD").getMbo().isNull("MSTBDOCID")){
				
				System.out.println(">>>>>>>>> Dentro do if que verifica se o MSTBDOCID da PO esta vazio");
				MboRemote mbo;
				MboRemote mboDestino = null;
				if (app.getDataBean("MAINRECORD").getMbo().getMboSet("MSTBCONTE").isEmpty()){
					
					System.out.println(">>>>>>>>> Dentro do if que verifica se o MSTBCONTE esta vazio");
					
					for (int i = 0; ((mbo= app.getDataBean("MAINRECORD").getMbo().getMboSet("MSTBDOC").getMbo(0).getMboSet("MSTBMOD").getMbo(0).getMboSet("MSTBCLACAP").getMbo(i)) !=null); i++) {
						
						
						mboDestino = app.getDataBean("MAINRECORD").getMbo().getMboSet("MSTBCONTE").add();
						mboDestino.setValue("DESCRIPTION", mbo.getString("DESCRIPTION"));
						
						mboDestino.setValue("MSPOSICAO", mbo.getString("MSPOSICAO"));
						
						mboDestino.setValue("MSTBDOCID", app.getDataBean("MAINRECORD").getMbo().getInt("MSTBDOCID"));
						System.out.println(">>>>>>>>> Fim do FOR que carrega a tabela MSTBCONTE");
						
						if(mbo.getBoolean("MSBLOQUEADO")){
							mboDestino.setValue("MSTATUS", "BLOQUEADO");
							System.out.println(">>>>>>>>> Setando BLOQUEADO em mstatus");
						}
						else{
							mboDestino.setValue("MSTATUS", "LIBERADO");
						}
					}
					
				}
			}
		}
		 catch (RemoteException ex) {
	            Logger.getLogger(MsTbPregao.class.getName()).log(Level.SEVERE, null, ex);
	        }
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