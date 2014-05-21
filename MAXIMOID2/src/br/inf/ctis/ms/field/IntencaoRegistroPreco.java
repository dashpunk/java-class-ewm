package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class IntencaoRegistroPreco extends MboValueAdapter {


    public IntencaoRegistroPreco(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        
        if (getMboValue().getString() == null || getMboValue().getString().equals("SIM")) {
        	getMboValue().getMbo().setValueNull("MSJUSTIRP");
        } 
    }

}
