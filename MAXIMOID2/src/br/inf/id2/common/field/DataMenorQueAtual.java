package br.inf.id2.common.field;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import java.util.Date;
import br.inf.id2.common.util.Data;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class DataMenorQueAtual extends MboValueAdapter {

    public DataMenorQueAtual(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        Date valor = getMboValue().getDate();
        super.validate();
        Date dataAtual = new Date();
        
        if (valor == null || dataAtual == null) {
        	return;
        }

        if (Data.dataInicialMenorFinal(valor, dataAtual)) {
            throw new MXApplicationException("system", "DataMenorQueAtual");
        }
    }
}
