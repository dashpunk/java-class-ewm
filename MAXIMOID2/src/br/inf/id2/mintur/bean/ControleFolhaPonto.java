/**
 * @author Dyogo
 */
package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

public class ControleFolhaPonto extends AppBean {

	public ControleFolhaPonto() {
		System.out.println("################# ControleFolhaPonto ");
	}

	@Override
	public int SAVE() throws MXException, RemoteException {
		System.out.println("################# SAVE() - ControleFolhaPonto");
		int ret = super.SAVE();
		//MboSetRemote mboSetView = getMbo().getMboSet("RHRLMVCTRLPT01");
		String mes = getMbo().getString("RHSTCODMES");
		String ano = getMbo().getString("RHSTNUMANO");
		System.out.println("############# MES = " + mes + " | ANO = " + ano);
		
		try {
			java.sql.Connection conexao = MXServer.getMXServer().getDBManager().getConnection(MXServer.getMXServer().getSystemUserInfo().getConnectionKey());
			Statement st = conexao.createStatement();
			String query = "SELECT * from UFN_RHMVCONTROLEPONTO01('" + mes + "','" + ano + "')";
			ResultSet rs = st.executeQuery(query);
			int i = 0;
			while (rs.next()) {
				System.out.println("############## Iteração = " + i++ + " | RHMVCONTROLEPONTO01ID = " + rs.getString("RHMVCONTROLEPONTO01ID"));
				MboRemote mboControle = (MboRemote) getMbo().getMboSet("RHRLCRPO01").add();
	
				//mboControle.getMboSet("RHRLCASE01").add().setValue("RHSTCODSIAPE", rs.getString("RHSTCODSIAPE"), MboConstants.NOACCESSCHECK);
				mboControle.setValue("RHTBCASE01ID", rs.getString("RHTBCASE01ID"), MboConstants.NOACCESSCHECK);
				//mboControle.setValue("RHTBCRPO01ID", rs.getString("RHMVCONTROLEPONTO01ID"), MboConstants.NOACCESSCHECK);
				mboControle.setValue("RHTBCSER01ID", rs.getString("RHTBCSER01ID"), MboConstants.NOACCESSCHECK);
				mboControle.setValue("RHSTCODSIAPE", rs.getString("RHSTCODSIAPE"), MboConstants.NOACCESSCHECK);
				mboControle.setValue("RHNUFLGFOLHADEPONTO", rs.getString("RHNUFLGFOLHADEPONTO"), MboConstants.NOACCESSCHECK);
				mboControle.setValue("RHSTCODOCORRENCIA", rs.getString("RHSTCODOCORRENCIA"), MboConstants.NOACCESSCHECK);
				mboControle.setValue("RHNUFLGSEMPENDENCIA", 0, MboConstants.NOACCESSCHECK);
				mboControle.setValue("RHNUCODUOEXEID", rs.getString("RHNUCODUOEXEID"), MboConstants.NOACCESSCHECK);
				mboControle.setValue("RHNUCODUOLOTID", rs.getString("RHNUCODUOLOTID"), MboConstants.NOACCESSCHECK);
				//mboControle.setValue("RHDTDTARECEBIMENTO", mbo.getString("RHSTCODOCORRENCIA"));
				//mboControle.setValue("RHSTDSCOBS", mbo.getString("RHSTCODOCORRENCIA"));
				mboControle.setValue("RHSTCODFUNCAO", rs.getString("RHSTCODFUNCAO"), MboConstants.NOACCESSCHECK);
				mboControle.setValue("RHSTCODTIPCAR", rs.getString("RHSTCODTIPCAR"), MboConstants.NOACCESSCHECK);
				mboControle.setValue("RHTBCARGO01ID", rs.getString("RHTBCARGO01ID"), MboConstants.NOACCESSCHECK);	
			}
			//getMbo().getMboSet("RHRLCRPO01").save();
			
		} catch (Exception e) {
			System.out.println("############## Ocorreu uma exceção");
			e.printStackTrace();
			throw new MXApplicationException("folhaponto", "excecaosql");
		}
		
		System.out.println("###########3 Tamanho MBOSet final = " +  getMbo().getMboSet("RHRLCRPO01").count());
		refreshTable();
		reloadTable();
		return super.SAVE();
		//return ret;
		
	}

}
