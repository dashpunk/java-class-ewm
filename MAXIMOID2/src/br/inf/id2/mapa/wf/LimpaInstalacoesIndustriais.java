package br.inf.id2.mapa.wf;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class LimpaInstalacoesIndustriais implements ActionCustomClass {
	
	public LimpaInstalacoesIndustriais() {
		super();
		System.out.println("LimpaInstalaçõesIndustriais");
	}
	
	
	public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
			throws MXException, java.rmi.RemoteException {
			System.out.println("applyCustomAction");
		    
			MboRemote aMbo;
		     for (int i = 0; ((aMbo = mbo.getMboSet("MARL08INSIND").getMbo(i)) != null); i++) {
		    	   
		    	    aMbo.setValueNull("ID2CON", MboConstants.NOACCESSCHECK);
					aMbo.setValueNull("ID2CONS", MboConstants.NOACCESSCHECK);
					
					
	         }	   	
		     mbo.getMboSet("MARL08INSIND").save(MboConstants.NOACCESSCHECK);
    }	
}
