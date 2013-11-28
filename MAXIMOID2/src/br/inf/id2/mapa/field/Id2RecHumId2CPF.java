package br.inf.id2.mapa.field;

import java.rmi.RemoteException;

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
public class Id2RecHumId2CPF extends MboValueAdapter {

    public Id2RecHumId2CPF(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	super.validate();
    	MboSet mboSetPerson = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWPER01", getMboValue().getMbo().getUserInfo());
    	MboSet mboSetPersonRecHum = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2RECHUM", getMboValue().getMbo().getUserInfo());
    	
    	mboSetPersonRecHum.setWhere("ID2CPF = \'"+getMboValue().getMbo().getString("ID2CPF")+"\'");
    	mboSetPersonRecHum.reset();
    	
    	
    	if (mboSetPersonRecHum.count() > 0 ) {
    		throw new MXApplicationException("id2rechum", "existemRegistrosEmDuplicidade");
    	}
    	
    	mboSetPerson.setWhere("PERSONID = \'"+getMboValue().getMbo().getString("ID2CPF")+"\'");
    	mboSetPerson.reset();

    	if (mboSetPerson.count() == 0 ) {
    		throw new MXApplicationException("id2rechum", "cpfNaoExiste");
    	}
    	
    
    }
}
