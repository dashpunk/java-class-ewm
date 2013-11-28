package br.inf.id2.mapa.wf;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

public class HistoricoMovimentacao implements ActionCustomClass {

	public HistoricoMovimentacao() {
		super();
		System.out.println("*** HistoricoMovimentacao ***");
	}

	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
	throws MXException, java.rmi.RemoteException {

		System.out.println("*** applyCustomAction");
		
		System.out.println("*** antes de setar ID2REV na LOCATION "+mbo.getInt("ID2REV"));
		mbo.setValue("ID2REV", (mbo.getInt("ID2REV")+1));
		System.out.println("*** depois de setar ID2REV na LOCATION "+mbo.getInt("ID2REV"));
		
		System.out.println("*** mbo.LOCATION "+mbo.getString("LOCATION"));
		String [] tabela = {"MATBCLAEST","ID2COOEXT","MATBAGUABA","MATBCAPEST","MATBMAQEQU","MATBINSIND","MATBPRO","MATBMESAGUABA","WORKORDER"};
		
		String wonum = "";
		MboSetRemote mboSet;
		
		for(int i=0; i < tabela.length; i++){
			
			System.out.println("*** i "+i);
			mboSet = psdi.server.MXServer.getMXServer().getMboSet(tabela[i], mbo.getUserInfo());
			mboSet.setWhere("LOCATION = '" + mbo.getString("LOCATION") + "'");
			mboSet.reset();
			mboSet.save();
			
			System.out.println("*** mboSet "+mboSet.count());
			for(int j=0; j< mboSet.count(); j++){
				System.out.println("*** j "+j);
				System.out.println("*** ID2REV "+mboSet.getMbo(j).getName()+" antes "+mboSet.getMbo(j).getInt("ID2REV"));
				mboSet.getMbo(j).setValue("ID2REV", mboSet.getMbo(j).getInt("ID2REV")+1);
				System.out.println("*** ID2REV "+mboSet.getMbo(j).getName()+" depois "+mboSet.getMbo(j).getInt("ID2REV"));
			}
			System.out.println("*** antes do SAVE "+mboSet.getMbo(0).getName());
			mboSet.save(MboConstants.NOACCESSCHECK);
			System.out.println("*** depois do SAVE "+mboSet.getMbo(0).getName());
			
			if(i==8){
				System.out.println("*** "+mboSet.getName()+" "+mboSet.count());
				for(int l=0; l<mboSet.count(); l++){
					System.out.println("*** l "+l);
					wonum = mboSet.getMbo(l).getString("WONUM");
					System.out.println("*** wonum "+wonum);
					
					mboSet = psdi.server.MXServer.getMXServer().getMboSet("MATBMEMOS", mbo.getUserInfo());
					mboSet.setWhere("WONUM = '" + wonum + "'");
					mboSet.reset();
					
					System.out.println("*** MATBMEMOS "+mboSet.count());
					for(int k=0; k<mboSet.count(); k++){
						System.out.println("*** k "+k);
						System.out.println("*** ID2REV "+mboSet.getMbo(k).getName()+" antes "+mboSet.getMbo(k).getInt("ID2REV"));
						mboSet.getMbo(k).setValue("ID2REV", mboSet.getMbo(k).getInt("ID2REV")+1);
						System.out.println("*** ID2REV "+mboSet.getMbo(k).getName()+" antes "+mboSet.getMbo(k).getInt("ID2REV"));
					}
					mboSet.save(MboConstants.NOACCESSCHECK);
				}
			}
		}
	}

}
