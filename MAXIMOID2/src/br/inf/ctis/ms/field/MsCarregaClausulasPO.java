package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import com.sun.xml.registry.uddi.bindingsv2.Save_Binding;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author andrel.almeida
 */

public class MsCarregaClausulasPO extends MboValueAdapter {
	
	
	public MsCarregaClausulasPO(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println(">>>>>>>>>>> Dentro da classe MsCarregaClausulasPO, versao 02");
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	
        super.validate();
               
        MboRemote mboDestino = null;                
        MboRemote mboDoc = null;   
        
        
        if (getMboValue().getMbo().getMboSet("MSTBPO_CLAUSULAS").isEmpty()){
        	
        	for (int i = 0; ((mboDoc= getMboValue().getMbo().getMboSet("MSTB_MODELOS").getMbo(0).getMboSet("MSTB_CLAUSULAS").getMbo(i)) !=null); i++) {
       			
            	    			
    			mboDestino = getMboValue().getMbo().getMboSet("MSTBPO_CLAUSULAS").add();
    			
    			mboDestino.setValue("DESCRIPTION", mboDoc.getString("DESCRIPTION"));
    			
    			mboDestino.setValue("MSPOPOSICAO", mboDoc.getString("MSPOSICAO"));
    			
    			mboDestino.setValue("MSPOSICAOPAI", mboDoc.getString("MSPOSICAOPAI"));
    			
    			mboDestino.setValue("MSTB_CLAUSULASID", mboDoc.getInt("MSTB_CLAUSULASID"));	
    			
    			mboDestino.setValue("MSPOCLAUSULA", mboDoc.getString("MSCLAUSULA"));
    			
    			mboDestino.setValue("PONUM", getMboValue().getMbo().getString("PONUM"));
    			
    			//CONDICAO PARA SETAR STATUS DAS CLAUSULAS
				if(mboDoc.getBoolean("MSBLOQUEADO")){					
					mboDestino.setValue("STATUS", "BLOQUEADO");					
				}
				else{
					mboDestino.setValue("STATUS", "LIBERADO");
				}
    			
    		}
        	//Apagando a tabela e inserindo novo conjunto de dados
        }else{
        	 //Apagando
        	 getMboValue().getMbo().getMboSet("MSTBPO_CLAUSULAS").deleteAll();
        	 
        	 //inserindo
        	 for (int i = 0; ((mboDoc= getMboValue().getMbo().getMboSet("MSTB_MODELOS").getMbo(0).getMboSet("MSTB_CLAUSULAS").getMbo(i)) !=null); i++) {
        			
             	     			
     			mboDestino = getMboValue().getMbo().getMboSet("MSTBPO_CLAUSULAS").add();
     			
     			mboDestino.setValue("DESCRIPTION", mboDoc.getString("DESCRIPTION"));
     			
     			mboDestino.setValue("MSPOPOSICAO", mboDoc.getString("MSPOSICAO"));
     			
     			mboDestino.setValue("MSPOSICAOPAI", mboDoc.getString("MSPOSICAOPAI"));

     			mboDestino.setValue("MSTB_CLAUSULASID", mboDoc.getInt("MSTB_CLAUSULASID"));	
     			
     			mboDestino.setValue("MSPOCLAUSULA", mboDoc.getString("MSCLAUSULA"));
     			
     			mboDestino.setValue("PONUM", getMboValue().getMbo().getString("PONUM"));
     			
     			//CONDICAO PARA SETAR STATUS DAS CLAUSULAS
				if(mboDoc.getBoolean("MSBLOQUEADO")){					
					mboDestino.setValue("STATUS", "BLOQUEADO");					
				}
				else{
					mboDestino.setValue("STATUS", "LIBERADO");
				}
     			
     		} 
        }
        
       
    }

}
