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
public class DataMaiorQueAtual extends MboValueAdapter {

    public DataMaiorQueAtual(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        Date valor = getMboValue().getDate();
        super.validate();
        Date dataAtual = new Date();

        if (dataAtual != null && valor != null) {
            if (Data.dataInicialMenorFinal(dataAtual, valor)) {

                throw new MXApplicationException("company", "DataMaiorQueAtual");
            }
        }
    }
}
