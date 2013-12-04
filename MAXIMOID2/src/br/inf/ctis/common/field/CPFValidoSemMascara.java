/**
 * Valida Número de CPF sem Mascara.
 */
package br.inf.ctis.common.field;

import java.rmi.RemoteException;

import psdi.id2.Uteis;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.util.Validar;

/**
 * @author willians.andrade
 */

public class CPFValidoSemMascara extends MboValueAdapter {

	public CPFValidoSemMascara(MboValue mbv) throws MXException {
		super(mbv);
	}

    @Override
    public void validate() throws MXException, RemoteException {
        String valor = new String();
        valor = Uteis.getApenasNumeros(getMboValue().getString());
        super.validate();
        String param[] = {valor};
        if (!Validar.CPF(valor)) {
            //busca mensagem do maximo, grupo mensagem e chave mensagem
            throw new MXApplicationException("company", "CPFInvalido", param);
        }

    }

}
