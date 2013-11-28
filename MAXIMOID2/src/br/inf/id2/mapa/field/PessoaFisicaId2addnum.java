package br.inf.id2.mapa.field;

import br.inf.id2.common.util.Uteis;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author Willians Andrade
 */

//Solicitado pela Anna Kelly, Dia: 06/08/2012
public class PessoaFisicaId2addnum extends MboValueAdapter {

    public PessoaFisicaId2addnum(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        if (getMboValue().getMbo().getThisMboSet().getApp().equalsIgnoreCase("ID2PER01")) {
            super.validate();
            String valor = Uteis.getApenasNumeros(getMboValue().getString());
            getMboValue().setValue(valor, MboConstants.NOVALIDATION_AND_NOACTION);
            throw new MXApplicationException("id2vwper01", "ApenasNumeros");
        }
    }
}