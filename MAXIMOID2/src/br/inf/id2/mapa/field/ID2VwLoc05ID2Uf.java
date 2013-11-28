package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class ID2VwLoc05ID2Uf extends MboValueAdapter {

    public ID2VwLoc05ID2Uf(MboValue mbv) {
        super(mbv);
        System.out.println("*** ID2VwLoc05ID2Uf ***");
    }
    
    @Override
    public void action() throws MXException, RemoteException {
    	super.action();
    	
        MboRemote mbo;
        mbo = getMboValue().getMbo().getMboSet("MARL10ADDRESS").getMbo(0);
        if (mbo != null) {
        	System.out.println("*** IF");
            getMboValue().getMbo().setValue("ID2ADDUF", mbo.getString("ID2ADDUF"), MboConstants.NOVALIDATION_AND_NOACTION);
            getMboValue().getMbo().setValue("ADDRESSID", mbo.getString("ADDRESSID"), MboConstants.NOVALIDATION_AND_NOACTION);
        }else{
        	System.out.println("*** ELSE");
	        getMboValue().getMbo().setValueNull("ID2CEPCODE2", MboConstants.NOVALIDATION_AND_NOACTION);
        }
        getMboValue().getMbo().setValueNull("ID2CODMUN", MboConstants.NOVALIDATION_AND_NOACTION);
        super.validate();
    }
}
