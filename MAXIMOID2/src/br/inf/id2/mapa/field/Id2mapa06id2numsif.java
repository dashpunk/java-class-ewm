package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Willians Andrade
 *  
 */

public class Id2mapa06id2numsif extends MboValueAdapter {

    public Id2mapa06id2numsif (MboValue mbv) {
        super(mbv);
    }

  
            
            
    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        String valor = getMboValue().getString();
        if (valor.equalsIgnoreCase("")) {
            limpaCampos();
        }
        
    }

    private void limpaCampos() throws RemoteException, MXException {
        getMboValue().getMbo().setValueNull("ID2ESTADO");
        System.out.println("Chegou!!");
        getMboValue().getMbo().setValueNull("ID2NUMSIF");
    }
}
