package br.inf.id2.valec.wf;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public class MudarStatusFluxo implements ActionCustomClass {

    public MudarStatusFluxo() {
    	super();
    	System.out.println("############################### ENTROU NO CONSTRUTOR()");
        //super();
    }


    public void applyCustomAction(MboRemote mbo, java.lang.Object[] params)
            throws MXException, java.rmi.RemoteException {
    	
    	System.out.println("############################### ENTROU NO APPLYCUSTOMACTION() - MudarStatusFluxo");
    	if (mbo.getMboSet("RL01CONCOR").count() > 0) {
    		mbo.getMboSet("RL01CONCOR").getMbo(0).setValue("MXSTATUS", "EXECUTADO");
    	}
    	mbo.getMboSet("RL01CONCOR").save();
    }
}

