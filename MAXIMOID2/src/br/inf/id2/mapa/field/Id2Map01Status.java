package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.Set;
import java.util.TreeSet;

import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Leysson Barbosa Moreira
 *  
 */
public class Id2Map01Status extends MboValueAdapter {

    public Id2Map01Status(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	super.validate();
    	
    	
    	//Regras para mapa de abate(id2map01)
    	if(getMboValue().getMbo().getThisMboSet().getApp().equalsIgnoreCase("ID2MAP01")) {
	    	MboSet matbLotaba;
	    	MboSet matbSalaba;
	    	matbLotaba = (MboSet) getMboValue().getMbo().getMboSet("MATBLOTABA");
	    	matbSalaba = (MboSet) getMboValue().getMbo().getMboSet("MATBSALABA");
	    	
	    	if (getMboValue().getString().equals("02") && !getMboValue("ID2SEMREG").getBoolean()) {
	    		
		    	if (getMboValue().getMbo().getMboSet("MATBLOTABA").count() == 0) {

		    		throw new MXApplicationException("madataba", "lotesDeAbateVazio");
		    	}
		    	
		    	Set<String> setExiste = new TreeSet<String>();
	    	    	
		    	for (int i = 0; ((matbLotaba.getMbo(i)) != null); i++) {
		    			if (setExiste.contains(matbLotaba.getMbo(i).getString("MANUMLOT") + "#" + matbLotaba.getMbo(i).getString("MATIPLOT"))) {
		    				throw new MXApplicationException("mtlotaba", "ManumlotJaExiste");
		    			} else {
		    				setExiste.add(matbLotaba.getMbo(i).getString("MANUMLOT") + "#" + matbLotaba.getMbo(i).getString("MATIPLOT"));
		    			}
		    		
	    				if(matbLotaba.getMbo(i).getString("MANUMLOT").equals("")) {
	    		    		throw new MXApplicationException("mtlotaba", "manumlotIsNull");
	    		    	}
	    				
	    		    	if(matbLotaba.getMbo(i).getInt("MAQTDFEMMOR") == 0 && matbLotaba.getMbo(i).getInt("MAQTDFEM") > 0) {
	    		    		throw new MXApplicationException("mtlotaba", "femMorIsNull");
	    		    	}

	    		    	if(matbLotaba.getMbo(i).getInt("MAQTDMACMOR") == 0 && matbLotaba.getMbo(i).getInt("MAQTDMAC") > 0) {
	    		    		throw new MXApplicationException("mtlotaba", "macMorIsNull");
	    		    	}

	    		    	if(matbLotaba.getMbo(i).getInt("MAQTDFEM") == 0 && matbLotaba.getMbo(i).getInt("MAQTDMAC") == 0) {
    		    				throw new MXApplicationException("mtlotaba", "femeaIsNullOuMachoIsNull");
    		    		}
		    	}
	    	}
		}
    }
}
