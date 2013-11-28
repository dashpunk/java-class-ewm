package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Bruno Freitas
 *
 */

public class MaTransEle extends AppBean {

    public MaTransEle() {
    }

    public int verificarExistencia() throws MXException, RemoteException {
    	System.out.print("*****  MaTransEle (verificarExistencia) *****");
    	
    	String localDestino = getMboSet().getString("MACODEXP");
    	
    	MboSet MboLocDes = (MboSet)MXServer.getMXServer().getMboSet("ID2VWLOC04", sessionContext.getUserInfo()); // Conectou a LOC04
    	MboLocDes.setWhere("LOCATION = '" + localDestino + "'"); // Peguei os registro baseado na clausula WHERE
    	MboLocDes.reset(); // Desconectei / RESETEI
        
    	if (localDestino.equals("")){
    		throw new MXApplicationException("maasset", "informeLocDes");
    	} else {
        if (MboLocDes.count() > 0 ) {
        	save();
            throw new MXApplicationException("maasset", "inserirDados");
        } else {
        	throw new MXApplicationException("maasset", "LocDesInvalido");
        }
    	}
    }

    public int confTransferencia() throws MXException, RemoteException {
    	System.out.print("*****  MaTransEle (confTransferencia) *****");
    	
    	MboSet receber = (MboSet) getMbo().getMboSet("ASSET");
    	
    	int totalElementos = 0;
       	
	    	if (getMbo().getString("MACONFTRANS").equals("S")){	
				for (int i = 0; i < receber.count(); i++) {
		            if (receber.getMbo(i).getString("SELECIONADO").equals("S")) {
		    		totalElementos = 1;
		    		
		    		receber.getMbo(i).setValue("STATUS", "TRANSFERIDO", MboConstants.NOACCESSCHECK);
		    		receber.getMbo(i).setValue("STATUS2", "PEND", MboConstants.NOACCESSCHECK);
		    		receber.getMbo(i).setValue("LOCATION", getMbo().getString("MACODEXP"), MboConstants.NOACCESSCHECK);
		    		//receber.getMbo(i).setValue("MACODLOC", "", MboConstants.NOACCESSCHECK);
		    		receber.getMbo(i).setValue("SELECIONADO", "N", MboConstants.NOACCESSCHECK);
		    		
		    		}
		    	}		    	
		    	if (totalElementos > 0){
		    		totalElementos = 0;
			    	reloadTable();
			    	refreshTable();
		    		save();
		    		throw new MXApplicationException("maasset", "todosElementosRecebido");
		    	}
		    	if (totalElementos <= 0){
		    		throw new MXApplicationException("maasset", "semElementosRecebido");
		    	}
	    	} else {
	    		throw new MXApplicationException("maasset", "AceitarTermos");
	    	}
    	return EVENT_HANDLED;
    }
}