package br.inf.ctis.ms.bean;

import java.beans.Statement;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.runtime.WebClientRuntime;
/**
 * @author andrel.almeida
 */

public class MsDocPec extends AppBean {
	
	public MsDocPec() {
		System.out.println(">>>>>>>>>> Dentro da classe: br.inf.ctis.ms.bean.MsDocPec");
	}
	
	@Override
	public void save() throws MXException {
		
		try {
			
			if(!getMbo().isNull("MSTBDOCID")){
				
				MboRemote mbo;
				MboRemote mboTeste = null;
				MboRemote mboDestino = null;
				
				
				if (getMbo().getMboSet("MSTBCONTE").isEmpty()){
					
					for (int i = 0; ((mbo= getMbo().getMboSet("MSTBMOD").getMbo(0).getMboSet("MSTBCLACAP").getMbo(i)) !=null); i++) {
						mboDestino = getMbo().getMboSet("MSTBCONTE").add();
						
						mboDestino.setValue("DESCRIPTION", mbo.getString("DESCRIPTION"));
						
						mboDestino.setValue("MSPOSICAO", mbo.getString("MSPOSICAO"));
						;
						mboDestino.setValue("MSTBDOCID", getMbo().getInt("MSTBDOCID"));
						System.out.println(">>>>>>>>> Antes da checagem do item bloqueado");
						if(mbo.getBoolean("MSBLOQUEADO")){
							mboDestino.setValue("MSTATUS", "BLOQUEADO");
							System.out.println(">>>>>>>>> Setando BLOQUEADO em mstatus");
						}
						else{
							mboDestino.setValue("MSTATUS", "LIBERADO");
						}
						
						
					}
				}
				
				if(!getMbo().isNull("DESCRIPTION")){
					
					System.out.println(">>>>>>>>> Dentro do IF description, do metodo save");
					 DataBean daBean = this.app.getResultsBean();
					 if (daBean == null) {
					      daBean = this.app.getAppBean();
					      System.out.println(">>>>>>>>> Apresentando o daBean da Tela"+daBean);
					 }
					 String userAndQbeWhere = daBean.getUserAndQbeWhere();
					 if (userAndQbeWhere != null) {
					        userAndQbeWhere = WebClientRuntime.decodeSafevalue(userAndQbeWhere);
					        mboTeste.setValue("MSASSUNTO", userAndQbeWhere );
					        System.out.println(">>>>>>>>> Valor da clausula where da Tela"+userAndQbeWhere);
					 }
					
				}
				
				super.save();
											
			}
		}
				
			 catch (RemoteException ex) {
            Logger.getLogger(MsTbPregao.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
	
	
	
	

