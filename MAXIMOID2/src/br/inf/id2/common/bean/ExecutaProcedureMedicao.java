package br.inf.id2.common.bean;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.util.Properties;

import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.bean.report.ReportBasicoBean;
/**
* ID2 Tecnologia S.A.
*
* <h4>Descrição da Classe</h4>
*    - Executa a procedure GOMEDICAOPRC03
*    
*    
*    
* <h4>Notas</h4>
* 
* <h4>Referências</h4>
*    - Classe aplicada na tela 
* 
* <h4>Revisões</h4>
*    - Revisão 1.0 - @author Nelson Benevides: Classe inicialmente criada.
* 
* 
* @since 10/02/2012 10:00
* @version 1.0
* 
* 
*/

public class ExecutaProcedureMedicao extends ReportBasicoBean {
	
	@Override
	protected void initialize() throws MXException, RemoteException {
		
		Properties prop;
		prop = MXServer.getMXServer().getConfig();
		
		String numeroContrato = app.getDataBean().getMbo().getString("FKGOCONTRACTNUM");
		String numeroMedicao = app.getDataBean().getMbo().getString("GONUNUMMEDICAO");
		
		try {
			
			java.sql.Connection conexao = MXServer.getMXServer().getDBManager().getConnection(MXServer.getMXServer().getSystemUserInfo().getConnectionKey());
			CallableStatement proc = null;
			String nmProcedure = "GOMEDICAOPRC03";
			
			proc = conexao.prepareCall("{ call GOMEDICAOPRC03(?, ?) }");
			proc.setString(1, numeroContrato);
			proc.setString(2, numeroMedicao);
			proc.execute();

			System.out.println("******** PROCEDURE EXECUTADA ********");
			
			
		} catch (Exception e) {
		    throw new MXApplicationException("Ocorreu um erro ao executar a procedure : ( GOMEDICAOPRC03 )", e.getMessage());
		}
		
		super.initialize();
	}
}
