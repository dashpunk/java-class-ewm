package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Andre Almeida
 *  
 */
public class ID2VwLoc05ID2LocUf extends MboValueAdapter {

    public ID2VwLoc05ID2LocUf(MboValue mbv) {
        super(mbv);
        System.out.println("*** ID2VwLoc05ID2LocUf ***");
    }
    
    public void validate() throws MXException, RemoteException {
        super.validate();
        getMboValue().getMbo().setValueNull("ID2CODMUN", MboConstants.NOVALIDATION_AND_NOACTION);
        getMboValue().getMbo().setValueNull("ID2CEPCODE", MboConstants.NOVALIDATION_AND_NOACTION);
    }
    
 }

