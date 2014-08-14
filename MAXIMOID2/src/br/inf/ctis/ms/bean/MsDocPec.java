package br.inf.ctis.ms.bean;

import java.beans.Statement;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
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
	
	
	protected MboSetRemote mboSetRemote = null;
	
	
	public MsDocPec() {
		System.out.println(">>>>>>>>>> Dentro da classe: br.inf.ctis.ms.bean.MsDocPec8");
	}
	
	@SuppressWarnings("null")
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
						
						mboDestino.setValue("MSTBCLACAPID", mbo.getInt("MSTBCLACAPID"));
						
						if(mbo.isNull("MSPAI")){
							mboDestino.setValue("MSPAI", 0);
						}
						if(!mbo.isNull("MSPAI")){
							mboDestino.setValue("MSPAI",mbo.getString("MSPAI"));
						}
						
						
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
				
				/*if(!getMbo().isNull("DESCRIPTION")){
					
					 DataBean daBean = this.app.getResultsBean();
					 String userAndQbeWhere = daBean.getUserAndQbeWhere();
					 userAndQbeWhere = WebClientRuntime.decodeSafevalue(userAndQbeWhere);
					
					String Teste2 ="";
					System.out.println(">>>>>>>>> Dentro do IF description, do metodo save p/ para regra de parametro");
					String Description = getMbo().getString("DESCRIPTION");	
					
					MboSetRemote qfr = ((AppBean)this.app.getAppBean()).getQuickFindRemote();
					qfr.resetQbe(); 
					qfr.setUserWhereAfterParse(Description);
					qfr.reset();
					 
					// Teste2 = WebClientRuntime.decodeSafevalue(Description);
					    //    getMbo().setValue("MSASSUNTO", Teste2 );
					 System.out.println(">>>>>>>>> Valor da clausula where da Tela "+qfr.getMbo(0));
					 
					
					
				}*/
				
				super.save();
											
			}
		}
				
			 catch (RemoteException ex) {
            Logger.getLogger(MsTbPregao.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
	
	
	
	

