package br.inf.id2.common.field;

import br.inf.id2.common.util.Uteis;
import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXException;

public class SemCaractereEspecial extends MboValueAdapter {


    public SemCaractereEspecial(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        String valor = Uteis.retiraCaracteresEspeciais(getMboValue().getString());
        getMboValue().setValue(valor);
    }

 
}
