package br.inf.id2.mapa.field;
//package br.inf.id2.mapa.field.Id2mapa08id2numcon
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
public class Id2mapa08id2numcon extends MboValueAdapter {

    public Id2mapa08id2numcon(MboValue mbv) {
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
        getMboValue("ID2NUMCON").setValueNull(MboConstants.NOACCESSCHECK);
        getMboValue("ID2ESTEST").setValueNull(MboConstants.NOACCESSCHECK);
    }
}
