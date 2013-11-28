package br.inf.id2.valec.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Dyogo
 *
 */
public class GuiaTramiteBean extends psdi.webclient.system.beans.AppBean {


    public GuiaTramiteBean() {
    	System.out.println("############ Construtor");
    }
    
    @Override
    public int ROUTEWF() throws MXException, RemoteException {
       	System.out.println("################### Tramitando o protocolo! ROUTEWF");
		//if (app.getDataBean("MAINRECORD").getMbo().getMboSet("RL02TRANS") != null && app.getDataBean("MAINRECORD").getMbo().getMboSet("RL02TRANS").count() > 0) {
		//	app.getDataBean("MAINRECORD").getMbo().setValue("DTRES", getMbo().getMboSet("RL02TRANS").getMbo(0).getDate("DTRES"));
		//}
       	
      
       	MboSetRemote mboSetRemote = getMbo().getMboSet("WFTRANSACTION");

       	System.out.println("############ Relacionamento: " + mboSetRemote.toString());
       	System.out.println("############## Quantidade de registros: " + mboSetRemote.count());
       	
        if(mboSetRemote.isEmpty() || mboSetRemote.count() == 0 ) {
        
	    	long lId = getMbo().getUniqueIDValue();
	    	SAVE();
	    	getMboSet().getMboForUniqueId(lId);
	    	

	    	MboSetRemote mboSetTransLin = getMbo().getMboSet("MXRL01TRANSLIN");
	    	if(mboSetTransLin == null || mboSetTransLin.isEmpty()){
	    		throw new MXApplicationException("protocolo", "documentoInexistente");
	    	}

	    	if (mboSetTransLin != null) {
	    		for (int i=0; i < mboSetTransLin.count(); i++) {
	    			System.out.println("################ Iteracao (" + i + ")");
	    			MboSetRemote mboSetAsset = mboSetTransLin.getMbo(i).getMboSet("RL01ASSET");
	    			mboSetAsset.getMbo(0).setValue("MXONDEESTA", getMbo().getString("DSLOCDEST"));
	    			
	    			System.out.println("############## Filho " +  mboSetAsset.count());
	    			MboSetRemote mboSetAssetFilho = mboSetAsset.getMbo(0).getMboSet("ASSETCHILDREN");
	    			if(!mboSetAssetFilho.isEmpty() || mboSetAssetFilho.count() != 0) {
		    			for(int j=0; j < mboSetAssetFilho.count(); j++) {	
			    				mboSetAssetFilho.getMbo(j).setValue("MXONDEESTA", getMbo().getString("DSLOCDEST"));
			    	    }
			    	}
	    		}
	    		System.out.println("########### Terminou a verificacao... Salvando! ");
	    		mboSetTransLin.save();
	    		System.out.println("########### Registro salvo!");
	    	}
        
    	    SAVE();
        }
    	return super.ROUTEWF();
    }
    	
}