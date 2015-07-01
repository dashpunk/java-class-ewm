package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.id2.Uteis;
import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class MsAlCodSiteId extends MboValueAdapter {

    public MsAlCodSiteId(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	
    	System.out.println("########### SItE:" + getMboValue("MSALCODSITEID").getString());
        getMboValue().getMbo().setValue("SITEID", getMboValue("MSALCODSITEID").getString(), MboConstants.NOACCESSCHECK);
        
        super.validate();
        
        
    }
}
