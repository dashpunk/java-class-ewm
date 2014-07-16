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
		System.out.println(">>>>>>>>> Dentro da classe: br.inf.ctis.ms.bean.MsDocPec_teste_log");
	}
	
	@Override
	public void save() throws MXException {
		System.out.println(">>>>>>>>> Dentro do metodo Save, classe: br.inf.ctis.ms.bean.MsDocPec");
		try {
			System.out.println(">>>>>>>>> Antes DO IF c/ isNull");
			if(!getMbo().isNull("MSTBDOCID")){
				System.out.println(">>>>>>>>> Dentro DO IF c/ isNull");
				MboRemote mbo;
				MboRemote mboDestino = null;
				
				
				if (getMbo().getMboSet("MSTBCONTE").isEmpty()){
					System.out.println(">>>>>>>>> Dentro DO IF c/ isEmpty");
					for (int i = 0; ((mbo= getMbo().getMboSet("MSTBMOD").getMbo(0).getMboSet("MSTBCLACAP").getMbo(i)) !=null); i++) {
						mboDestino = getMbo().getMboSet("MSTBCONTE").add();
						System.out.println(">>>>>>>>>>>> add() clausula do documento");
						mboDestino.setValue("DESCRIPTION", mbo.getString("DESCRIPTION"));
						System.out.println(">>>>>>>>>>>> DESCRIPTION = " + mbo.getString("DESCRIPTION"));
						mboDestino.setValue("MSPOSICAO", mbo.getString("MSPOSICAO"));
						System.out.println(">>>>>>>>>>>> MSPOSICAO" + mbo.getString("MSPOSICAO"));
						mboDestino.setValue("MSTBDOCID", getMbo().getInt("MSTBDOCID"));
						System.out.println(">>>>>>>>>>>> MSTBDOCID" + getMbo().getInt("MSTBDOCID"));
					}
				}
				
				super.save();
				System.out.println(">>>>>>>>> Salvando..");								
			}
		}
				
			 catch (RemoteException ex) {
            Logger.getLogger(MsTbPregao.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
}
	
	
	
	

