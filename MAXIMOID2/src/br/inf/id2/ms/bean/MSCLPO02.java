package br.inf.id2.ms.bean;

import br.inf.ctis.ms.bean.MsFinance;
import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author andrel.almeida
 */
public class MSCLPO02 extends psdi.webclient.beans.po.POAppBean {

    // variaveis
	int qtdAnexoMsg = 0;
	
    public MSCLPO02() {
    	System.out.println(">>>>>>>>> Dentro da classe: br.inf.id2.ms.bean.MSCLPO02_versao00");
    }

    /**
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {    	
    	

        Executa.atualizaAtributo((MboSet) getMbo().getMboSet("POLINE"), "GLDEBITACCT", "0.0.0.0");
        
        Properties propTR;
		Connection conexaoTR = null;

        //verifica se algum poline foi deletado
        MboRemote mbo;
        MboSet prlineSet;
        for (int i = 0; ((mbo = getMbo().getMboSet("POLINE").getMbo(i)) != null); i++) {
            
            if (mbo.toBeDeleted()) {
                
                MboSet polineSet;
                polineSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("POLINE", sessionContext.getUserInfo());

                polineSet.setWhere("polineid <> " + mbo.getInt("POLINEID") + " AND prlineid = " + mbo.getInt("PRLINEID"));
                polineSet.reset();

                

                if (polineSet.count() == 0) {

                    prlineSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("PRLINE", sessionContext.getUserInfo());

                    prlineSet.setWhere("prlineid = " + mbo.getInt("PRLINEID"));
                    prlineSet.reset();
                    
                    MboRemote mbob;
                    if ((mbob = prlineSet.getMbo(0)) != null) {
                        
                        mbob.setValue("ID2STATUS", "ENVIADO", MboConstants.NOACCESSCHECK);
                        
                        prlineSet.save();
                        
                    }

                }

            } else {
                
                prlineSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("PRLINE", sessionContext.getUserInfo());

                prlineSet.setWhere("prlineid = " + mbo.getInt("PRLINEID"));
                prlineSet.reset();
               
                MboRemote mbob;
                if ((mbob = prlineSet.getMbo(0)) != null) {
                  
                    mbob.setValue("ID2STATUS", "TR", MboConstants.NOACCESSCHECK);
                    
                    prlineSet.save();
                   
                }
            }

        }
        
       
        if ((getMbo().getMboSet("MS_RL04PER") != null) && (getMbo().isNull("ID2CODCOO"))) {
        	
            if (getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO") != null) {
                getMbo().setValue("ID2CODCOO", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO"));
                getMbo().setValue("ID2SEC", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2SEC"));
                
                getMbo().setValue("ID2DIR", getMbo().getMboSet("MS_RL04PER").getMbo(0).getString("ID2DIR"));
            } else {
               
                throw new MXApplicationException("pr", "SemLotacao");
            }
        }
        
        // IF p/ setar o STATUS=EDITADO/LIBERADO nas cláusulas que tiveram alterações(Trabalhando c/ auditoria, por isso o super.save)
        super.save(); 
       // if(!getMbo().getMboSet("MSTBCONTE").getMbo(0).isNew()){
        //	System.out.println(">>>>>>>>> Dentro do If para o objeto MSTBCONTE");
        	for(int i=0;i < getMbo().getMboSet("MSTBCONTE").count();i++){
        		
        		System.out.println(">>>>>>>>> Dentro do I: "+getMbo().getMboSet("MSTBCONTE").getMbo(i).getInt("MSTBCONTEID"));
        		
        		for(int j=0;j < getMbo().getMboSet("MSTBCONTE").getMbo(i).getMboSet("A_MSTBCONTE3").count();j++){
        			
        			System.out.println(">>>>>>>>> Valor da Auditoria para o MSTBCONTEID: "+getMbo().getMboSet("MSTBCONTE").getMbo(i).getMboSet("A_MSTBCONTE3").getMbo(j).getString("EAUDITTYPE"));
        			
        			if(getMbo().getMboSet("MSTBCONTE").getMbo(i).getMboSet("A_MSTBCONTE3").getMbo(j).getString("EAUDITTYPE").equalsIgnoreCase("U")){
        				
        				System.out.println(">>>>>>>>> Dentro do IF para setar valor ");
        				MboRemote mboDestinodoc = getMbo().getMboSet("MSTBCONTE").getMbo(i);
        				mboDestinodoc.setValue("MSTATUS", "EDITADO/LIBERADO");
        				System.out.println(">>>>>>>>> Valor setado ");
        			}
        		}        		
        		
        	}
        
        //Gerando o valor total de compra do TR para o PEC
        	super.save(); 
        	try {
        		
        		MboRemote mbo1;
        		float qtdTotal =0;
                propTR = MXServer.getMXServer().getConfig();
                
                String driver = propTR.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
                String url = propTR.getProperty("mxe.db.url");
                String username = propTR.getProperty("mxe.db.user", "dbmaximo");
                String password = propTR.getProperty("mxe.db.password", "max894512");

                Class.forName(driver).newInstance();
                
                try {
                	conexaoTR = DBConnect.getConnection(url, username, password, propTR.getProperty("mxe.db.schemaowner", "dbmaximo"));
    			} catch (Exception e) {
    				Logger.getLogger(MSCLPO02.class.getName()).log(Level.SEVERE, null, e);
    				e.printStackTrace();
    			}
                
                for(int i = 0; ((mbo1 = getMbo().getMboSet("POLINE").getMbo(i)) != null); i++){
                	
                	qtdTotal+=(mbo1.getFloat("MSVALEST") * mbo1.getFloat("ORDERQTY"));
                	
                }              
                              
                
//                Statement stmt = conexao.createStatement(); //não estava sendo utilizada. por isso foi comentado.
                PreparedStatement ps = conexaoTR.prepareStatement("UPDATE PO SET MSVLRTOTALCOMPRA = ? WHERE PONUM = ?");//---- MSQTDANEXOPEC
                ps.setFloat(1, qtdTotal);
                System.out.println("########## vALOR DO TOTAL: "+ getMbo().getFloat("MSVLRTOTALCOMPRA"));
                ps.setString(2, getMbo().getString("PONUM"));                

                System.out.println("########## updatePO execute b");
                int r = ps.executeUpdate();  
                
                System.out.println("########## updatePO execute a " + r);                
                conexaoTR.commit();
                System.out.println("########## commit");
                
                super.SAVE();
        	} catch (RemoteException ex) {
                Logger.getLogger(MSCLPO02.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            } catch (SQLException e) {
            	 Logger.getLogger(MSCLPO02.class.getName()).log(Level.SEVERE, null, e);
            	e.printStackTrace();
    		} catch (Exception e) {
    			 Logger.getLogger(MSCLPO02.class.getName()).log(Level.SEVERE, null, e);
    			e.printStackTrace();
    		} finally {
    		    try { 
    		    		conexaoTR.close(); 
    		    } catch (Exception e) { 
    		    	Logger.getLogger(MSCLPO02.class.getName()).log(Level.SEVERE, null, e);
    				e.printStackTrace();
    			}
    		}
        	super.save(); 
        	
        
        
        System.out.println(">>>>>>>>> Metodo save executado");
        return super.SAVE();
    }

    @SuppressWarnings("null")
	@Override
    protected void initialize() throws MXException, RemoteException {
    	Properties prop;
		Connection conexao = null;
		MboRemote mboAnexo;
    	
    	super.initialize();
    	
    	for (int J = 0; ((mboAnexo = getMbo().getMboSet("MSPECANEXOS").getMbo(J)) != null); J++) {
 		   mboAnexo.setValue("MSFLAGLEITURA", true);
 		   
 	   }
 	   super.SAVE();
    	
    	try {
            prop = MXServer.getMXServer().getConfig();
            
            String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
            String url = prop.getProperty("mxe.db.url");
            String username = prop.getProperty("mxe.db.user", "dbmaximo");
            String password = prop.getProperty("mxe.db.password", "max894512");

            Class.forName(driver).newInstance();
            
            try {
            	conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
			} catch (Exception e) {
				Logger.getLogger(MSCLPO02.class.getName()).log(Level.SEVERE, null, e);
				e.printStackTrace();
			}
            
            
//            Statement stmt = conexao.createStatement(); //não estava sendo utilizada. por isso foi comentado.
            PreparedStatement ps = conexao.prepareStatement("UPDATE PO SET MSQTDANEXOPEC = ? WHERE PONUM = ?");//---- MSQTDANEXOPEC
            System.out.println("########## Quantidade de registros na tabela de Anexos/PEC: "+ getMbo().getMboSet("MSPECANEXOS").count());
            ps.setInt(1, getMbo().getMboSet("MSPECANEXOS").count());
            ps.setString(2, getMbo().getString("PONUM"));

            PreparedStatement ps2 = conexao.prepareStatement("UPDATE PO SET MSQTDPESDISTR = ? WHERE PONUM = ?"); //---- MSQTDPESDISTR
            System.out.println("########## Quantidade de registros na tabela de MSDISPESPEC: "+ getMbo().getMboSet("MSDISPESPEC").count());
            ps2.setInt(1, getMbo().getMboSet("MSDISPESPEC").count());
            ps2.setString(2, getMbo().getString("PONUM"));

            System.out.println("########## updatePO execute b");
            int r = ps.executeUpdate(); //---- MSQTDANEXOPEC
            int r2 = ps2.executeUpdate(); //---- MSQTDPESDISTR
            
            System.out.println("########## updatePO execute a " + r);
            System.out.println("########## updatePO execute a2 " + r2);
            conexao.commit();
            System.out.println("########## commit");
            
            super.SAVE();
    	} catch (RemoteException ex) {
            Logger.getLogger(MSCLPO02.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (SQLException e) {
        	 Logger.getLogger(MSCLPO02.class.getName()).log(Level.SEVERE, null, e);
        	e.printStackTrace();
		} catch (Exception e) {
			 Logger.getLogger(MSCLPO02.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		} finally {
		    try { 
		    		conexao.close(); 
		    } catch (Exception e) { 
		    	Logger.getLogger(MSCLPO02.class.getName()).log(Level.SEVERE, null, e);
				e.printStackTrace();
			}
		}
    }

}


