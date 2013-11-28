package br.inf.id2.mintur.field;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Dyogo Dantas
 * 
 */
public class StatusAsset extends MboValueAdapter {

    public StatusAsset(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        String valor = getMboValue().getString();
        System.out.println("### VALOR DO STATUS=" + valor);
        getMboValue().getMbo().setValueNull("MTDATTRA");
        System.out.println("########## Zerando valor da Data");
        super.validate();
    }
    
    
}
