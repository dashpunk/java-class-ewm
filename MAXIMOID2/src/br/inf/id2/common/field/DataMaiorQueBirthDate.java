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
public class DataMaiorQueBirthDate extends MboValueAdapter {

    public DataMaiorQueBirthDate(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        Date valor = getMboValue().getDate();
        super.validate();
        Date dataNascimento = getMboValue().getMbo().getDate("BIRTHDATE");

        if (Data.dataInicialMenorFinal(dataNascimento, valor)) {
            throw new MXApplicationException("company", "DataMaiorQueNascimento");
        }
    }
}
