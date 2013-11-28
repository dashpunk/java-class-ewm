package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;

import psdi.app.asset.AssetSetRemote;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Dyogo
 *
 */
public class TermoTransferencia extends psdi.webclient.system.beans.AppBean {

    public TermoTransferencia() {
  
    }

	public int TRANSFER() throws MXException, RemoteException {
		// TODO Auto-generated method stub
		
		System.out.println("#################### SALVANDO ANTES");
		super.SAVE();
		
		MboRemote mbo = getMbo();
		if (mbo.getString("DSSTATUS") != null && 
			mbo.getString("DSSTATUS").trim().equals("NOVO")) {

			String locDest = mbo.getString("DSLOCDEST");
			if (locDest == null || locDest.trim().equals("")) {
				throw new MXApplicationException("patrimonio", "LocalDestinoObrigatorio");
			}
			
			MboSetRemote remote = mbo.getMboSet("MXRL01TERTRALIN");
			for (int i=0; i < remote.count();i++) {
				MboRemote mboTemp = remote.getMbo(i);
				
				AssetSetRemote assetSet = (AssetSetRemote) mboTemp.getMboSet("MXRL01ASSET");
				System.out.println("################ SALVANDO DURANTE");
				assetSet.save();
				assetSet.setMoveAssetPageFlag(true);
				
				//assetSet.getMbo(0).setValueNull("NEWSITE");
				assetSet.getMbo(0).setValue("NEWSITE",assetSet.getMbo(0).getString("SITEID"));

				assetSet.getMbo(0).setFieldFlag("NEWLOCATION", MboConstants.READONLY, false);
				assetSet.getMbo(0).setValue("NEWLOCATION", locDest);
				assetSet.getMbo(0).setFieldFlag("NEWLOCATION", MboConstants.READONLY, true);
							
				assetSet.setLastESigTransId(mbo.getThisMboSet().getESigTransactionId());
				assetSet.moveAsset();
				assetSet.save();
				assetSet.setMoveAssetPageFlag(false);
				System.out.println("####################### SALVANDOD DEPOIS");
				
			}
		}
		
		mbo.setValue("DSSTATUS","REALIZADO");
		
		super.SAVE();
		
		return 0;
		
	}

        	
}
