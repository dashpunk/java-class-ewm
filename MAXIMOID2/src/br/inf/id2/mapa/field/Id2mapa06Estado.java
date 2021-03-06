package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Willians Andrade
 *
 */
public class Id2mapa06Estado extends MboValueAdapter {

    public Id2mapa06Estado(MboValue mbv) {
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
        getMboValue("ID2MUN").setValueNull(MboConstants.NOACCESSCHECK);
    }
}
