package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author marcelosydney.lima
 *  
 */
public class MsNuFlgPagaTributo extends MboValueAdapter {

    public MsNuFlgPagaTributo(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();

        if (!getMboValue().getBoolean()) {
        	getMboValue().getMbo().setValueNull("TAX1CODE");
        }

    }
}
