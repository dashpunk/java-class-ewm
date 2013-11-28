package br.inf.ctis.common.field;

import java.rmi.RemoteException;

import psdi.id2.Uteis;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.util.Validar;


/**
 * @author Marcelo
 */

public class CPFCNPJValidoSemMascara extends MboValueAdapter {

    public CPFCNPJValidoSemMascara(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        String valor = new String();
        valor = Uteis.getApenasNumeros(getMboValue().getString());
        super.validate();
        String param[] = {valor};
        if (!Validar.CNPJ(valor)) {
            //busca mensagem do maximo, grupo mensagem e chave mensagem
            throw new MXApplicationException("company", "CNPJInvalido", param);
        }
    }
}
