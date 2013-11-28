package br.inf.id2.me.field;

import java.rmi.RemoteException;
import java.util.Date;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class MEDatEmiMaiorQueBirthDate extends MboValueAdapter {

    public MEDatEmiMaiorQueBirthDate(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();

        if (!getMboValue().getMbo().isNull("BIRTHDATE")) {

            Date dataNascimento = getMboValue().getMbo().getDate("BIRTHDATE");

            int qtMeses = Data.getMeses(dataNascimento);

            if (qtMeses < 168 || qtMeses > 1200) {
                throw new MXApplicationException("person", "BirthDateEntre4e100");
            }

            if ((!getMboValue().getMbo().isNull("MEDATEMI"))) {

                Date valor = getMboValue().getMbo().getDate("MEDATEMI");

                if (!Data.dataInicialMenorFinal(dataNascimento, valor)) {
                    throw new MXApplicationException("company", "MEDATEMIMaiorQueNascimento");
                }


                Date dataAtual = new Date();
                //System.out.println("################ DATA ATUAL" + dataAtual);
                //System.out.println("################ DATA CAMPO" + valor);
                //System.out.println("################ DataInicialMenorQueFinal(" + Data.dataInicialMenorFinal(dataAtual, valor) + ")");

                if (Data.dataInicialMenorFinal(dataAtual, valor)) {
                    throw new MXApplicationException("company", "MEDATEMIMaiorQueDataAtual");
                }
            }

        }
    }
}
