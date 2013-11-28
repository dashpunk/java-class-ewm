package br.inf.id2.me.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 * 
 * @author Davi Dias
 *  
 */
public class LimpaCampoMaisID extends MboValueAdapter {

    public LimpaCampoMaisID(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        String campoAtual = getMboValue().getName();

        if (getMboValue().getMbo().getString(campoAtual).length() == 0) {
            getMboValue().getMbo().setValueNull(campoAtual.toUpperCase()+"ID");
          }

    }
}
