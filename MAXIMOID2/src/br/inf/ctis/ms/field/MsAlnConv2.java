package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsAlnConv2 extends MboValueAdapter {


    public MsAlnConv2(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        
        if (getMboValue().getString() == null) {
			 getMboValue().getMbo().setValueNull("MSALNCONVENIO");
		 } else if (getMboValue().getString().equalsIgnoreCase("NÃO")) {
			 getMboValue().getMbo().setValueNull("MSALNCONVENIO");
		 }
     

    }

}