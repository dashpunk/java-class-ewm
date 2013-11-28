package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 
 * @author Ricardo
 * 
 */
public class Matransani extends AppBean {

	public Matransani() {

	}
	
	public void transferirAnimais() throws MXException, RemoteException {
		
		if (getMbo().getBoolean("MATRANSFERIDO")) {
			throw new MXApplicationException("matransele", "transferenciaNegada");
		}
		
		confTransferencia(getMbo().getString("MATIPTRANS"));
		
	}
	
	public void chamaProcedure(String opcao) throws MXException, RemoteException {

		
		if (opcao.equalsIgnoreCase("01")) {
			SAVE();
			return;
		}
		
		Properties prop;
		try {
			prop = MXServer.getMXServer().getConfig();

			String driver = prop.getProperty("mxe.db.driver",
					"com.inet.tds.TdsDriver");
			String url = prop
					.getProperty(
							"mxe.db.url",
							"jdbc:inetdae7:192.168.1.6:1433?database=MAXDB72&language=us_english&nowarnings=true&mars=false&charset=8859_1");
			String username = prop.getProperty("mxe.db.user", "maximo");
			String password = prop.getProperty("mxe.db.password", "id2maximo");
			Class.forName(driver).newInstance();
			java.sql.Connection conexao = DriverManager.getConnection(url,
					username, password);
			Statement stmt = conexao.createStatement();
			
			if (opcao.equalsIgnoreCase("02")) {
				PreparedStatement ps = conexao.prepareStatement("call MOVIMENTACAO_02(?,?,?,?)");
			
				ps.setInt(1, getMbo().getInt("MATRANSELEID"));
				ps.setString(2, getMbo().getString("MALOCALORI"));
				ps.setString(3, getMbo().getString("MALOCALDEST"));
				ps.setString(4, getMbo().getString("MAPONUM"));
				ps.execute();
				ps.close();			
			} else if (opcao.equalsIgnoreCase("03")) {
				PreparedStatement ps = conexao.prepareStatement("call MOVIMENTACAO_03(?,?)");
			
				ps.setInt(1, getMbo().getInt("MATRANSELEID"));
				ps.setString(2, getMbo().getString("MALOCALDEST"));
				ps.execute();
				ps.close();			
			} else if (opcao.equalsIgnoreCase("04")) {
				PreparedStatement ps = conexao.prepareStatement("call MOVIMENTACAO_04(?,?,?)");
			
				ps.setInt(1, getMbo().getInt("MATRANSELEID"));
				ps.setString(2, getMbo().getString("MALOCALORI"));
				ps.setString(3, getMbo().getString("MALOCALDEST"));
				ps.execute();
				ps.close();			
			}
			
			getMbo().setValue("MATRANSFERIDO", true);
			SAVE();
			
			
		} catch (RemoteException e) {
			System.out.println("...e1 "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("...e2 "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("...e3 "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("...e4 "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("...e5 "+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("-------------- do action END");
	}

    public int confTransferencia(String secao) throws MXException, RemoteException {
    	
    	if ( secao.equalsIgnoreCase("01") && secao.equalsIgnoreCase("02") && getMbo().getMboSet("MAPONUM").count() == 0) {
    		throw new MXApplicationException("maasset", "maponumIsEmpty");
    	}
    	
    	MboSet receber = null;     	
    	MboSet receber2 = null;
    	
    	if (secao.equalsIgnoreCase("01")) {
    		receber = (MboSet) getMbo().getMboSet("MARLTIPO01");
    	} else if (secao.equalsIgnoreCase("02")) {
    		receber  = (MboSet) getMbo().getMboSet("MARLTRANSELELINE");
    		receber2 = (MboSet) getMbo().getMboSet("MARLTIPO02");
    	} else if (secao.equalsIgnoreCase("03")) {
    		receber = (MboSet) getMbo().getMboSet("MARLTRANSELELINE");
    	} else if (secao.equalsIgnoreCase("04")) {
    		receber = (MboSet) getMbo().getMboSet("MARLTIPO04");
    	}
    	
    	
    	
    	int totalElementos = 0;
       	
	    	if (getMbo().getBoolean("MAACEITATERMOS")){	
				for (int i = 0; i < receber.count(); i++) {
					
		            if (!receber.getMbo(i).toBeDeleted() && (secao.equalsIgnoreCase("02") || secao.equalsIgnoreCase("03") || receber.getMbo(i).getBoolean("SELECIONADO") )) {
		            	totalElementos = 1;
		            	if (secao.equalsIgnoreCase("01")) {
		            		receber.getMbo(i).setValue("STATUS", "EM TRANSITO", MboConstants.NOACCESSCHECK);
		            		receber.getMbo(i).setValue("STATUS2", "PEND", MboConstants.NOACCESSCHECK);
		            		receber.getMbo(i).setValue("MACODLOC", getMbo().getMboSet("MAPONUM").getMbo(0).getString("ID2STORELOCDEST"), MboConstants.NOACCESSCHECK);
		            		receber.getMbo(i).setValue("SELECIONADO", false, MboConstants.NOACCESSCHECK);
		            	} else if (secao.equalsIgnoreCase("02")) {
		            		receber.getMbo(i).getMboSet("MARLASSET").getMbo(0).setValue("LOCATION", receber.getMbo(i).getMboSet("MARLASSET").getMbo(0).getString("MACODLOC"), MboConstants.NOACCESSCHECK);
		            		receber.getMbo(i).getMboSet("MARLASSET").getMbo(0).setValueNull("MACODLOC", MboConstants.NOACCESSCHECK);
		            		receber.getMbo(i).getMboSet("MARLASSET").getMbo(0).setValue("STATUS", "EM USO", MboConstants.NOACCESSCHECK);
		            		receber.getMbo(i).getMboSet("MARLASSET").getMbo(0).setValueNull("STATUS2", MboConstants.NOACCESSCHECK);
		            	} else if (secao.equalsIgnoreCase("03")) {
//		            		receber.getMbo(i).getMboSet("MARLASSET").getMbo(0).setValue("LOCATION", getMbo().getString("MALOCALDEST"), MboConstants.NOACCESSCHECK);
//		            		receber.getMbo(i).getMboSet("MARLASSET").getMbo(0).setValue("MACODLOC", getMbo().getString("MALOCALDEST"), MboConstants.NOACCESSCHECK);
//		            		receber.getMbo(i).getMboSet("MARLASSET").getMbo(0).setValueNull("MACODLOC", MboConstants.NOACCESSCHECK);
		            		receber.getMbo(i).getMboSet("MARLASSET").getMbo(0).setValue("STATUS", "DESLIGADO", MboConstants.NOACCESSCHECK);
		            		receber.getMbo(i).getMboSet("MARLASSET").getMbo(0).setValueNull("STATUS2", MboConstants.NOACCESSCHECK);
		            	} else if (secao.equalsIgnoreCase("04")) {
		            		receber.getMbo(i).setValue("STATUS", "TRANSF/TIT", MboConstants.NOACCESSCHECK);
		            		receber.getMbo(i).setValue("STATUS2", "PEND", MboConstants.NOACCESSCHECK);
		            		receber.getMbo(i).setValue("MACODLOC", getMbo().getString("MALOCALDEST"), MboConstants.NOACCESSCHECK);
		            		receber.getMbo(i).setValue("SELECIONADO", false, MboConstants.NOACCESSCHECK);
		            	}  		            			    		
		    		}
		    	}
				
				if (receber2 != null) {
					for (int i = 0; i < receber2.count(); i++) {
						
			            if (!receber2.getMbo(i).toBeDeleted() && receber2.getMbo(i).getBoolean("SELECIONADO")) {
			            	totalElementos = 1;
			            		receber2.getMbo(i).setValue("STATUS", "EM TRANSITO", MboConstants.NOACCESSCHECK);
			            		receber2.getMbo(i).setValue("STATUS2", "PEND", MboConstants.NOACCESSCHECK);
			            		receber2.getMbo(i).setValue("MACODLOC", getMbo().getMboSet("MAPONUM").getMbo(0).getString("ID2STORELOCDEST"), MboConstants.NOACCESSCHECK);
			            		receber2.getMbo(i).setValue("SELECIONADO", false, MboConstants.NOACCESSCHECK);
			    		}
			    	}		    	
	
				}
				
		    	if (totalElementos > 0){
		    		totalElementos = 0;
			    	reloadTable();
			    	refreshTable();
		    		save();
		    		if (secao.equalsIgnoreCase("01")) {
		    			throw new MXApplicationException("maasset", "todosElementosEnviado");
	            	} else if (secao.equalsIgnoreCase("02")) {
	            		throw new MXApplicationException("maasset", "todosElementosRecebido02");
	            	} else if (secao.equalsIgnoreCase("03")) {
	            		throw new MXApplicationException("maasset", "todosElementosRecebido03");
	            	} else if (secao.equalsIgnoreCase("04")) {
	            		throw new MXApplicationException("maasset", "todosElementosTransferido");
	            	}  	
		    	}
		    	if (totalElementos <= 0){
		    		throw new MXApplicationException("maasset", "semElementosEnviado");
		    	}
	    	} else {
	    		throw new MXApplicationException("maasset", "AceitarTermos");
	    	}
    	return EVENT_HANDLED;
    }
			
}
