package br.inf.ctis.common.field;

import java.rmi.RemoteException;

import br.inf.id2.common.util.Validar;
import psdi.id2.Uteis;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author Marcelo
 */

public class CPFCNPJValido extends MboValueAdapter {

    public CPFCNPJValido(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        String valor = new String();
        valor = Uteis.getApenasNumeros(getMboValue().getString());
        super.validate();
        String param[] = {valor};
        if (Validar.CPF(valor)) {
        	getMboValue().setValue(Uteis.getValorMascarado("###.###.###-##", valor, false));
        } else if (Validar.CNPJ(valor)){
          	getMboValue().setValue(Uteis.getValorMascarado("##.###.###/####-##", valor, false));
        } else {
        	throw new MXApplicationException("company", "CPFCNPJInvalido");
        }
        
    }

}
