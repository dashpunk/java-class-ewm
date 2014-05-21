package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Marcelo
 *
 * 
 */
public class RegistroPreco extends MboValueAdapter {


    public RegistroPreco(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        
        if (getMboValue().getString() == null || getMboValue().getString().equals("NAO")) {
        	getMboValue().getMbo().setValueNull("MSALCODEMBASAMENTOLEGAL");
        	getMboValue().getMbo().setValueNull("MSTBEMBASAMENTOLEGALID");
        	getMboValue().getMbo().setValueNull("MSIRP");
        	getMboValue().getMbo().setValueNull("MSJUSTIRP");
        } else if (getMboValue().getString().equals("SIM")) {
        	getMboValue().getMbo().setValueNull("MS_JUSTIFICATIVA");
        }

    }

}
