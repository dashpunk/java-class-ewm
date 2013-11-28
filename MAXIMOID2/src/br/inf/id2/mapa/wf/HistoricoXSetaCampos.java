package br.inf.id2.mapa.wf;

import java.rmi.RemoteException;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class HistoricoXSetaCampos implements ActionCustomClass {

	public HistoricoXSetaCampos() {
		super();
		System.out.println("*** HistoricoXSetaCampos ***");
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
        
		setaCamposNull(mbo);
	}
	
	private void setaCamposNull(MboRemote mbo) throws RemoteException, MXException{
		
		System.out.println("*** setaCamposNull");
        System.out.println("*** LOCATION - "+mbo.getString("LOCATION"));
        
        mbo.setValueNull("ID2STAPARMEL",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2STAPAROVO",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2STAPARPES",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2STAPARLEI",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2STAPARTECDIPPROJ",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2STAAPRPES",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2STAAPROVO",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2STAAPRDIPPROJ",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2STAAPRLEI",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2STAAPRMEL",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2JUSRESLEG",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2JUSPARTECSIPPROJ",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2JUSAPRPARTECSIPPROJ",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2JUSPARTECDIPPROJ",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2OBSPARLEI",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2OBSPARMEL",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2OBSPARPES",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2OBSPAROVO",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2JUSAPRDIPPROJ",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2OBSAPRLEI",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2OBSAPRMEL",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2OBSAPRPES",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2OBSAPROVO",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESLEG",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESPARSIP",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESAPRSIP",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESPARTECDIPRPOJ",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESPARLEI",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESPARMEL",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESPARPES",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESPAROVO",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESAPRDIPPROJ",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESAPRLEI",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESAPRMEL",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESAPRPES",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2RESAPROVO",MboConstants.NOACCESSCHECK);
        
        
        //Solicitado pelo Willians dia 07-12-2011
        mbo.setValueNull("ID2STAPARTECSIPPROJ",MboConstants.NOACCESSCHECK);
        mbo.setValueNull("ID2STAAPRPARTECSIPPROJ",MboConstants.NOACCESSCHECK);
        
        mbo.setValue("id2ret",false, MboConstants.NOVALIDATION_AND_NOACTION);
        mbo.setValue("id2ckbox05car",false, MboConstants.NOVALIDATION_AND_NOACTION);
        mbo.setValue("id2ckbox06car",false, MboConstants.NOVALIDATION_AND_NOACTION);
        mbo.setValue("id2ckbox05lei",false, MboConstants.NOVALIDATION_AND_NOACTION);
        mbo.setValue("id2ckbox06lei",false, MboConstants.NOVALIDATION_AND_NOACTION);
        mbo.setValue("id2ckbox05mel",false, MboConstants.NOVALIDATION_AND_NOACTION);
        mbo.setValue("id2ckbox06mel",false, MboConstants.NOVALIDATION_AND_NOACTION);
        mbo.setValue("id2ckbox05ovo",false, MboConstants.NOVALIDATION_AND_NOACTION);
        mbo.setValue("id2ckbox06ovo",false, MboConstants.NOVALIDATION_AND_NOACTION);
        mbo.setValue("id2ckbox05pes",false, MboConstants.NOVALIDATION_AND_NOACTION);
        mbo.setValue("id2ckbox06pes",false, MboConstants.NOVALIDATION_AND_NOACTION);
        mbo.setValue("id2retsip",false, MboConstants.NOVALIDATION_AND_NOACTION);
//        mbo.setValue("id2revapr",false, MboConstants.NOVALIDATION_AND_NOACTION);
        
        System.out.println("*** antes do save");
        mbo.getThisMboSet().save();
        System.out.println("*** depois do save");
	}

}
