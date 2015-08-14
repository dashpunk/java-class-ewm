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

public class MSCLPO04 extends psdi.webclient.beans.po.POAppBean {
	
	int revclausula = 0;
	
	
	public MSCLPO04() {
    	System.out.println(">>>>>>>>> Dentro da classe: br.inf.id2.ms.bean.MSCLPO04  Teste01");
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
       
       //Save para as clausulas da SAA
       //super.save();
       
       MboRemote mboDestinodoc;
       
       for(int i=0;((mboDestinodoc = getMbo().getMboSet("MSTBPO_CLAUSULAS").getMbo(i)) != null);i++){
			if(mboDestinodoc.toBeSaved() && !mboDestinodoc.isNew()) {
				System.out.println(">>>>>>>>> Dentro do IF para setar valor ");
				
				mboDestinodoc.setValue("STATUS", "EDITADO/LIBERADO");
				//Incrementado a versao da clausula
				revclausula = mboDestinodoc.getInt("MSNUMREVCLAU");   			
				revclausula++;
				mboDestinodoc.setValue("MSNUMREVCLAU",revclausula);
				
				System.out.println(">>>>>>>>> Valores setados ");
			}
	  
   			if(getMbo().getMboSet("MSTBPO_CLAUSULAS").getMbo(i).getBoolean("MSCKEXCLUIR")){
   				mboDestinodoc.setValue("STATUS", "EXCLUÍDO");
   			}
       }        		
 
    super.save();
       
       
       System.out.println(">>>>>>>>> Metodo save executado");
       return super.SAVE();
   }
   
   @SuppressWarnings("null")
   @Override
   protected void initialize() throws MXException, RemoteException {
	   
	   MboRemote mboSaa;
	   MboRemote mboAnexo;
	   super.initialize();
	   
	   if (getMbo().getString("SITEID").equalsIgnoreCase("SAA")) {
		   
		   System.out.println(">>>>>>>>> Dentro do IF siteid=SAA");
    	   //For para setar flag de registros salvos na tabela de distribuicao do pessoal
    	   for (int i = 0; ((mboSaa = getMbo().getMboSet("MSTBSAA_DISTRIBUICAO").getMbo(i)) != null); i++) {
    		   mboSaa.setValue("FLAG", true);
    		   System.out.println(">>>>>>>>> Dentro do FOR para tabela de distribuicao do pessoal");
    		   
    	   }
    	 //For para setar flag de registros salvos na tabela Anexos mensagens
    	   for (int J = 0; ((mboAnexo = getMbo().getMboSet("MSPECANEXOS").getMbo(J)) != null); J++) {
    		   mboAnexo.setValue("MSFLAGLEITURA", true);
    		   
    	   }
    	   super.SAVE();  	   
       }
	   	   
   }

}

