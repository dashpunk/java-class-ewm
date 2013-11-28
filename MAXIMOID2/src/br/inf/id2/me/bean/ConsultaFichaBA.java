package br.inf.id2.me.bean;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @author Dyogo
 */
public class ConsultaFichaBA extends DataBean {

	public ConsultaFichaBA() {
		super();
	}

	@Override
	protected void initialize() throws MXException, RemoteException {
		
		consultaFicha(null, null, null, null, null);
		super.initialize();
	}

	public void salvaDadosFicha() throws RemoteException, MXException {
		//Verifica se o documento existe
		try
		{
			//System.out.println("#### entrou 1");
			DataBean tbfichas = app.getDataBean("tbgrdfichas01");
			//System.out.println("#### entrou 2");
			MboRemote mainrec = app.getDataBean("MAINRECORD").getMbo();
			//System.out.println("#### entrou 3");
			
			String personid = ""; 
			//System.out.println("#### entrou 4");
			
			if(!tbfichas.getMboSet().isEmpty())
			{	
				//System.out.println("#### entrou 5");
				MboRemote bambo = tbfichas.getMbo();
				//System.out.println("#### entrou 6");
				
				MboSetRemote docSet = MXServer.getMXServer().getMboSet("TBDOCPES", getMbo().getUserInfo());
				//System.out.println("#### entrou 7");
				docSet.setWhere("DSNUMDOC = '"+bambo.getString("NR_CPF_ATLETA")+"'");
				//System.out.println("#### entrou 8");
				docSet.reset();
				//System.out.println("#### entrou 9");
				
				if(!docSet.isEmpty())
				{
					//System.out.println("#### entrou 10");
					personid = docSet.getMbo(0).getString("PERSONID");
					//System.out.println("#### entrou 11");
				} 
				else
				{
					//System.out.println("#### entrou 12");
					MboSetRemote personSet = MXServer.getMXServer().getMboSet("VW01PESFIS", getMbo().getUserInfo());
					//System.out.println("#### entrou 13");
					MboRemote person = personSet.add();
					//System.out.println("#### entrou 14");
					person.setValue("ID2PERTYPE", "01");
					//System.out.println("#### entrou 15");
					person.setValue("DISPLAYNAME", bambo.getString("NM_ATLETA"));
					//System.out.println("#### entrou 16");
					personid = person.getString("PERSONID");
					//System.out.println("#### entrou 17");
					long personuid = person.getUniqueIDValue();
					//System.out.println("#### entrou 18");
					personSet.save();
					//System.out.println("#### entrou 19");
					person = personSet.getMboForUniqueId(personuid);
					//System.out.println("#### entrou 20");
					MboRemote doc = person.getMboSet("RL01DOCS").add();
					//System.out.println("#### entrou 21");
					doc.setValue("PERSONID", personid);
					//System.out.println("#### entrou 22");
					doc.setValue("ITDOCS", "CPF");
					//System.out.println("#### entrou 23");
					doc.setValue("UPSTAT", "ATIVO");
					//System.out.println("#### entrou 24");
					doc.setValue("DSNUMDOC", bambo.getString("NR_CPF_ATLETA"));
					//System.out.println("#### entrou 25");
					person.getMboSet("RL01DOCS").save();
					//System.out.println("#### entrou 26");
				}
				//System.out.println("#### entrou 27");
				
				MboRemote procpro = mainrec.getMboSet("RL01PROCPRO").add();
				//System.out.println("#### entrou 28");
				procpro.setValue("ASSETNUM", mainrec.getString("ASSETNUM"));
				//System.out.println("#### entrou 29");
				procpro.setValue("PERSONID", personid);
				//System.out.println("#### entrou 30");
				procpro.setValue("DSTIPREQ", "EXTERNO");
				//System.out.println("#### entrou 31");
				procpro.setValue("SITEID", "ST.01");
				//System.out.println("#### entrou 32");
				//mainrec.getMboSet("RL01PROCPRO").save();
				//System.out.println("#### entrou 33");
				
				MboRemote intpro = mainrec.getMboSet("RL01INTPRO").add();
				//System.out.println("#### entrou 34");
				intpro.setValue("ASSETNUM", mainrec.getString("ASSETNUM"));
				//System.out.println("#### entrou 35");
				intpro.setValue("PERSONID", personid);
				//System.out.println("#### entrou 36");
				//mainrec.getMboSet("RL01INTPRO").save();
				//System.out.println("#### entrou 37");
				
				MboRemote solpro = mainrec.getMboSet("RL01SOLPRO").add();
				//System.out.println("#### entrou 38");
				solpro.setValue("ASSETNUM", mainrec.getString("ASSETNUM"));
				//System.out.println("#### entrou 39");
				solpro.setValue("PERSONID", personid);
				//System.out.println("#### entrou 40");
				//mainrec.getMboSet("RL01SOLPRO").save();
				//System.out.println("#### entrou 41");
				
				mainrec.setValue("ASSETTAG", tbfichas.getMboSet().getMbo().getString("NR_FICHA"));
				//System.out.println("#### entrou 42");
				mainrec.setValue("FKMXNUCODFICHABA", tbfichas.getMboSet().getMbo().getString("ID_FICHA_ATLETA"));
				//System.out.println("#### entrou 43");
				mainrec.setValue("DESCRIPTION", "SOLICITA A INSCRIÇÃO "+tbfichas.getMboSet().getMbo().getString("NM_ATLETA")+" NO PROGRAMA BOLSA-ATLETA.");
				
				app.getDataBean().reloadTable();
				app.getDataBean().refreshTable();
				
				sessionContext.queueRefreshEvent();
				Utility.sendEvent(new WebClientEvent("dialogclose", app.getCurrentPageId(), null, sessionContext));
				
			}			
		}
		catch (Exception e) {
			//System.out.println(e.getMessage());
			throw new MXApplicationException("asset", "errobolsaatleta");
		}
	}
	
	private void consultaFicha(String numeroFicha, String numeroCPF,
							   String nomeAtleta, Date dataCadastro, String sgUf) {
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection(MXServer.getMXServer().getProperty("mxe.bolsaatleta.connectionurl"),MXServer.getMXServer().getProperty("mxe.bolsaatleta.dbuser"),MXServer.getMXServer().getProperty("mxe.bolsaatleta.dbpassword"));
			Statement st = conn.createStatement();
			String query = "SELECT atl.id_atleta, atl.nm_atleta, fic.id_ficha_atleta, " +
						   "fic.nr_ficha, fic.nr_ano_exercicio, fic.st_tipo_inscricao, " +
						   "fic.st_finalizado, fic.nr_protocolo, clu.nm_clube, " +
						   "atl.dt_cadastro, atl.nr_cpf_atleta, atl.sg_uf_endereco, " +
						   "atl.cd_municipio_ibge_endereco " +
						   "FROM bolsaatleta.tb_atleta atl " +
						   "INNER JOIN bolsaatleta.tb_ficha_atleta fic ON atl.id_atleta = fic.id_atleta " +
						   "LEFT OUTER JOIN bolsaatleta.tb_clube clu ON fic.id_ficha_atleta = clu.id_ficha_atleta " +
						   "WHERE 1=1 " +
						   "AND fic.nr_ficha is not null " +
						   "AND fic.nr_ano_exercicio = 2011 " +
						   "AND fic.nr_protocolo is null ";
			
			   if (numeroFicha != null && !numeroFicha.equals("")) {
				   query +="AND UPPER(fic.nr_ficha) like '%" + numeroFicha.toUpperCase() + "%' ";   
			   }
			   if (numeroCPF != null && !numeroCPF.equals("")) {
				   query +="AND UPPER(atl.nr_cpf_atleta) like '%" + numeroCPF.toUpperCase() + "%' ";   
			   }
			   if (nomeAtleta != null && !nomeAtleta.equals("")) {
				   query +="AND UPPER(atl.nm_atleta) like '%" + nomeAtleta.toUpperCase() + "%' ";   
			   }
			   if (sgUf != null && !sgUf.equals("")) {
				   query +="AND UPPER(atl.sg_uf_endereco) like '%" + sgUf.toUpperCase() + "%' ";   
			   }
			   /*if (dataCadastro != null) {
				   query +="AND atl.dt_cadastro = " + dataCadastro;   
			   }*/
						   
		   	query += "ORDER BY fic.nr_ficha, atl.nm_atleta";
		   	
		   	//System.out.println("######Query = " + query);
		   
			ResultSet rs = st.executeQuery(query);
			MboSetRemote mboSet = app.getDataBean("tbgrdfichas01").getMboSet();
			//mboSet.deleteAll();
			mboSet.deleteAndRemoveAll();
			app.getDataBean("tbgrdfichas01").refreshTable();
			app.getDataBean("tbgrdfichas01").reloadTable();
			int i = 0;
			while (rs.next()) {
				//System.out.println("##### i = " + i++ );
				MboRemote mboLinha = mboSet.add();
				mboLinha.setValue("ID_ATLETA", rs.getString("ID_ATLETA"));
				mboLinha.setValue("NM_ATLETA", rs.getString("NM_ATLETA"));
				mboLinha.setValue("ID_FICHA_ATLETA", rs.getString("ID_FICHA_ATLETA"));
				mboLinha.setValue("NR_FICHA", rs.getString("NR_FICHA"));
				mboLinha.setValue("NR_ANO_EXERCICIO", rs.getString("NR_ANO_EXERCICIO"));
				mboLinha.setValue("ST_TIPO_INSCRICAO", rs.getString("ST_TIPO_INSCRICAO"));
				mboLinha.setValue("ST_FINALIZADO", rs.getString("ST_FINALIZADO"));
				mboLinha.setValue("NR_PROTOCOLO", rs.getString("NR_PROTOCOLO"));
				mboLinha.setValue("NM_CLUBE", rs.getString("NM_CLUBE"));
				mboLinha.setValue("DT_CADASTRO", rs.getDate("DT_CADASTRO"));
				mboLinha.setValue("NR_CPF_ATLETA", rs.getString("NR_CPF_ATLETA"));
				mboLinha.setValue("SG_UF_ENDERECO", rs.getString("SG_UF_ENDERECO"));
				mboLinha.setValue("CD_MUNICIPIO_IBGE_ENDERECO", rs.getString("CD_MUNICIPIO_IBGE_ENDERECO"));
			}
			app.getDataBean("tbgrdfichas01").refreshTable();
			app.getDataBean("tbgrdfichas01").reloadTable();
			
			//MXTOFICHABA
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void recarregarConsulta() throws MXException, RemoteException {
		//Verifica os campos de consulta

		//System.out.println("### 1 = " + app.getDataBean("PPDLG01").getMboSet().getMbo().getString("NR_FICHA"));
		//System.out.println("### 2 = " + app.getDataBean("PPDLG01").getMboSet().getMbo(0).getString("NR_FICHA"));
		//System.out.println("### 3 = " + app.getDataBean("PPDLG01").getMboSet().getMbo().getString("NR_FICHA"));
		//System.out.println("### 4 = " + app.getDataBean("PPDLG01").getMbo().getString("NR_FICHA"));
		//System.out.println("### 5 = " + app.getDataBean("PPDLG01").getString("NR_FICHA"));
		//System.out.println("### 6 = " + getString("NR_FICHA"));
		//System.out.println("### 7 = " + getMbo().getString("NR_FICHA"));
		//System.out.println("### 8 = " + getMboSet().getMbo().getString("NR_FICHA"));
		//System.out.println("### 9 = " + getMboSet().getMbo(0).getString("NR_FICHA"));
		//System.out.println("### 10 = " + getDefaultValue("NR_FICHA"));
		//System.out.println("### 11 = " + getAttributes());
		//System.out.println("### 12 = " + getKeyAttributes());
		//System.out.println("### 13 = " + getMboValueData("NR_FICHA"));
		//System.out.println("### 14 = " + getMbo().getMboValueData("NR_FICHA"));
		
		MboRemote actMbo = app.getDataBean("PPDLG01").getMboSet().getMbo();
		
		
		String numeroFicha = actMbo.getString("NR_FICHA");
		String numeroCPF = actMbo.getString("NR_CPF_ATLETA");
		String nomeAtleta = actMbo.getString("NM_ATLETA");
		Date dataCadastro = actMbo.getDate("DT_CADASTRO");
		String sgUf = actMbo.getString("SG_UF_ENDERECO");
		//System.out.println(numeroFicha + "#" + numeroCPF + "#" + nomeAtleta + "#" + dataCadastro + "#" + sgUf);

		consultaFicha(numeroFicha, numeroCPF, nomeAtleta, dataCadastro, sgUf);
		
	}

}