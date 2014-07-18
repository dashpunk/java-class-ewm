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
				
				super.save();
											
			}
		}
				
			 catch (RemoteException ex) {
            Logger.getLogger(MsTbPregao.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
	
	
	
	

