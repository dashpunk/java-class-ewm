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
public class ID2VwLoc05ID2UfMunicipio extends MboValueAdapter {

    public ID2VwLoc05ID2UfMunicipio(MboValue mbv) {
        super(mbv);
        System.out.println("*** ID2VwLoc05ID2UfMunicipio ***");
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	
    	try {
	        MboRemote mbo;
	        mbo = getMboValue().getMbo().getMboSet("MARLID2VWLOC23").getMbo(0);
	        if (mbo != null) {
	        	System.out.println("*** IF");
	            getMboValue().getMbo().setValue("ID2ADDUF", mbo.getString("ID2ADDUF"), MboConstants.NOVALIDATION_AND_NOACTION);
	            getMboValue().getMbo().setValue("ID2MUN", mbo.getString("DESCRIPTION"), MboConstants.NOVALIDATION_AND_NOACTION);
	        }else{
	        	System.out.println("*** ELSE");
		        getMboValue().getMbo().setValueNull("ID2CODMUN", MboConstants.NOVALIDATION_AND_NOACTION);
	        }
        } catch (Exception e) {
        	System.out.println("*** e " + e.getMessage());
        }
        super.validate();
    }
}
