package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class ID2VwLoc05ID2Loc extends MboValueAdapter {

    public ID2VwLoc05ID2Loc(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        String valor = getMboValue().getString();
        if (valor.equalsIgnoreCase("Rural")) {
            limpaUrbana();
            limpaSubUrbana();
        }

        if (valor.equalsIgnoreCase("Urbana")) {
            limpaRural();
            limpaSubUrbana();
        }
        if (valor.equalsIgnoreCase("Suburbana")) {
            limpaUrbana();
            limpaRural();
        }
    }

    private void limpaSubUrbana() throws RemoteException, MXException {
        getMboValue().getMbo().setValueNull("ID2CEPCODE2");
        getMboValue().getMbo().setValueNull("ID2ADDNUM");
        getMboValue().getMbo().setValueNull("ID2ADDCOM");
        getMboValue().getMbo().setValueNull("ID2ADDUF");
        getMboValue().getMbo().setValueNull("ID2MUN");
    }

    private void limpaUrbana() throws RemoteException, MXException {
        getMboValue().getMbo().setValueNull("ID2CEPCODE");
        getMboValue().getMbo().setValueNull("ID2ADDNUM");
        getMboValue().getMbo().setValueNull("ID2ADDCOM");
        getMboValue().getMbo().setValueNull("ID2ADDUF");
        getMboValue().getMbo().setValueNull("ID2MUN");
    }

    private void limpaRural() throws RemoteException, MXException {
        getMboValue().getMbo().setValueNull("ID2LOCUF");
        getMboValue().getMbo().setValueNull("ID2CODMUN");
        getMboValue().getMbo().setValueNull("ID2DESCLOCVIS");
        getMboValue().getMbo().setValueNull("ID2ADDUF");
        getMboValue().getMbo().setValueNull("ID2MUN");
    }
}
