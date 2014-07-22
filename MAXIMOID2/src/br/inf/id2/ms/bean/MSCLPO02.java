package br.inf.id2.ms.bean;

import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

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
    	System.out.println(">>>>>>>>> Dentro da classe: br.inf.id2.ms.bean.MSCLPO02_teste8");
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
        	
        //}
        
        System.out.println(">>>>>>>>> Metodo save executado");
        return super.SAVE();
    }

    @SuppressWarnings("null")
	@Override
    protected void initialize() throws MXException, RemoteException {
    	super.initialize();
    	try {
            Properties prop;
            prop = MXServer.getMXServer().getConfig();
            String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
            String url = prop.getProperty("mxe.db.url");
            String username = prop.getProperty("mxe.db.user", "dbmaximo");
            String password = prop.getProperty("mxe.db.password", "max894512");

            Class.forName(driver).newInstance();
            java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
            Statement stmt = conexao.createStatement();
            PreparedStatement ps = conexao.prepareStatement("UPDATE PO SET MSQTDANEXOPEC = ? WHERE PONUM = ?");
            System.out.println("########## Quantidade de registros na tabela de Anexos/PEC: "+ getMbo().getMboSet("MSPECANEXOS").count());
            ps.setInt(1, getMbo().getMboSet("MSPECANEXOS").count());
            ps.setString(2, getMbo().getString("PONUM"));


            System.out.println("########## updatePO execute b");
            int r = ps.executeUpdate();
            System.out.println("########## updatePO execute a " + r);
            conexao.commit();
            System.out.println("########## commit");
            
            super.SAVE();
            

            conexao.close();
        } catch (Exception e) {
            System.out.println("########## e = " + e.getMessage());
        }
    	
    }

}


