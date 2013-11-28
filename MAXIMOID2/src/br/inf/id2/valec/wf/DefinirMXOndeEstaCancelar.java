package br.inf.id2.valec.wf;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

public class DefinirMXOndeEstaCancelar implements ActionCustomClass {

    public DefinirMXOndeEstaCancelar() {
    	super();
    	System.out.println("############################### ENTROU NO CONSTRUTOR()");
        //super();
    }


    public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
            throws MXException, java.rmi.RemoteException {
    	
    	System.out.println("############################### ENTROU NO APPLYCUSTOMACTION() - DefinirMXOndeEsta");
    	MboSetRemote mboSetTransLin = mbo.getMboSet("MXRL01TRANSLIN");
    	System.out.println("########### MBOSetTransLin = " + mboSetTransLin);
    	if (mboSetTransLin != null) {
    		for (int i=0; i < mboSetTransLin.count(); i++) {
    			System.out.println("################ Iteracao (" + i + ")");
    			MboSetRemote mboSetAsset = mboSetTransLin.getMbo(i).getMboSet("RL01ASSET");
    			mboSetAsset.getMbo(0).setValue("MXONDEESTA", mbo.getString("DSLOCORIG"));
    			
    			MboSetRemote mboSetAssetFilho = mboSetAsset.getMbo(0).getMboSet("ASSETCHILDREN");
    			if(!mboSetAssetFilho.isEmpty() || mboSetAssetFilho.count() != 0) {
	    			for(int j=0; j < mboSetAssetFilho.count(); j++) {	
		    				mboSetAssetFilho.getMbo(j).setValue("MXONDEESTA", mbo.getString("DSLOCORIG"));
		    	    }
		    	}
    		}
    		System.out.println("########### Terminou a verificao... Salvando! ");
    		mboSetTransLin.save();
    		System.out.println("########### Registro salvo!");
    	}
    	
    	//System.out.println("########## Definindo status...");
    	//mbo.setValue("DSSTATUS", "CANCELADA");
    	//mbo.getThisMboSet().save();
    	//System.out.println("########## Status definido!");
    }
}

