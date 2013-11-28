package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

public class PoId2finalidade extends MboValueAdapter {

    public PoId2finalidade(MboValue mbv) {
        super(mbv);
    }

    public void validate() throws MXException, RemoteException {
        super.validate();
        getMboValue("ID2TIPOLOCAL").setValueNull(2L);
        getMboValue("ID2TIPOLOCALDEST").setValueNull(2L);

        limparAnimais();

    }

    private void limparAnimais() throws MXException, RemoteException {
        if (getMboValue().getMbo().getMboSet("POLINE").count() > 0) {
            getMboValue().getMbo().getMboSet("POLINE").deleteAll(MboConstants.NOACCESSCHECK);
        }
    }
}