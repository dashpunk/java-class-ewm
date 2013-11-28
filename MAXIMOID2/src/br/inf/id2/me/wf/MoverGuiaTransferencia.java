package br.inf.id2.me.wf;

import psdi.app.asset.AssetRemote;
import psdi.app.asset.AssetSetRemote;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MoverGuiaTransferencia implements ActionCustomClass {

    public MoverGuiaTransferencia() {
    	super();
    	System.out.println("############################### ENTROU NO CONSTRUTOR()");
        //super();
    }


    public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
            throws MXException, java.rmi.RemoteException {
    	
    	System.out.println("############################### ENTROU NO APPLYCUSTOMACTION()");

		//Se o Status for Finalizado, deve mover as ASSETs
		System.out.println("################# Status atual=" + mbo.getString("DSSTATUS"));
		if (mbo.getString("DSSTATUS") != null && 
			(mbo.getString("DSSTATUS").trim().equals("13") ||
					mbo.getString("DSSTATUS").trim().equals("FINALIZADO"))) {

			//Recupera o Local de Destino. Se ainda nï¿½o tiver sido preenchido, criticar 
			String locDest = mbo.getString("NUCODUADEST");
			System.out.println("################ LOCAL DESTINO=" + locDest);
			if (locDest == null || locDest.trim().equals("")) {
				throw new MXApplicationException("protocolo", "LocalDestinoObrigatorio");
			}
			//Mover ASSETs
			MboSetRemote remote = mbo.getMboSet("RL01TRANSLIN");
			for (int i=0; i < remote.count();i++) {
				MboRemote mboTemp = remote.getMbo(i);
				System.out.println("########### REGISTRO(" + i + "): " + mboTemp.getString("DSNUMDOC"));
				System.out.println("*** PPNUFLGACEITO fora "+ mboTemp.getBoolean("PPNUFLGACEITO"));
				if(mboTemp.getBoolean("PPNUFLGACEITO")){
					System.out.println("*** PPNUFLGACEITO dentro "+ mboTemp.getBoolean("PPNUFLGACEITO"));
					AssetSetRemote assetSet = (AssetSetRemote) mboTemp.getMboSet("RL01ASSET");
					
					assetSet.getMbo(0).setValue("NUCODUAID", locDest);
					//NUCODUADEST
					assetSet.save();
					
					//TODO Observação...
					//Por solicitação do ME a partir de agora não é mais feita a movimentação, apenas setar o campo NUCODUAID
					//Modificado em 14/03/2012 por necessidade de colocar em produção o Protocolo (Jessé/Leonardo)
					//assetSet.setMoveAssetPageFlag(true);
					//System.out.println("versï¿½o 1");
					//System.out.println("############### SET=" + assetSet.count());
					//System.out.println("############### destino=" + mbo.getString("DSLOCDEST"));
					
					//System.out.println("############### newsite=" + assetSet.getMbo(0).getString("NEWSITE"));
					//assetSet.getMbo(0).setValueNull("NEWSITE");
					//System.out.println("############### newsite=" + assetSet.getMbo(0).getString("NEWSITE"));
	
					//assetSet.getMbo(0).setValue("NEWSITE",assetSet.getMbo(0).getString("SITEID"));
					//System.out.println("############### newsite=" + assetSet.getMbo(0).getString("NEWSITE"));
	
					//System.out.println("############### newlocation=" + assetSet.getMbo(0).getString("NEWLOCATION"));
					//assetSet.getMbo(0).setFieldFlag("NEWLOCATION", MboConstants.READONLY, false);
					//assetSet.getMbo(0).setValue("NEWLOCATION", locDest);
					//assetSet.getMbo(0).setFieldFlag("NEWLOCATION", MboConstants.READONLY, true);
					//System.out.println("############### newlocation=" + assetSet.getMbo(0).getString("NEWLOCATION"));
								
					//assetSet.setLastESigTransId(mbo.getThisMboSet().getESigTransactionId());
					//System.out.println("########### Setou LastESigTransId");
					//assetSet.moveAsset();
					//System.out.println("########### Moveu a ASSET");
					//assetSet.setMoveAssetPageFlag(false);
					//System.out.println("########### Setou a Flag de Movido");
					
					//Agora, seta o campo NUCODUAID com o valor
					
					//assetSet.save();
					System.out.println("########### Finalizado...");
				}
			}
		}
     }
}

