package br.inf.id2.mintur.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 * @author Patrick Silva
 */
public class CttbacaoeveCtdtdtafimreal extends MboValueAdapter {

    public CttbacaoeveCtdtdtafimreal(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("*** CttbacaoeveCtdtdtafimreal ***");
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        String atributoAtual = getMboValue().getName();
        String atributoPrefixo;
        if (atributoAtual.indexOf("INI") != -1) {
            atributoPrefixo = atributoAtual.substring(0, atributoAtual.indexOf("INI"));
        } else {
            atributoPrefixo = atributoAtual.substring(0, atributoAtual.indexOf("FIMREAL"));
        }

        if (!getMboValue(atributoPrefixo.concat("INI")).isNull() && !getMboValue(atributoPrefixo.concat("FIMREAL")).isNull()) {
            Date dataInicial = getMboValue(atributoPrefixo.concat("INI")).getDate();
            Date dataFinal = getMboValue(atributoPrefixo.concat("FIMREAL")).getDate();
            if (dataInicial != null && dataFinal != null) {
                if (Data.dataInicialMenorFinal(dataFinal, dataInicial)) {
                    throw new MXApplicationException("company", "DataInicialMenorQueFinalReal");

                }
            }
        }
    }
}
