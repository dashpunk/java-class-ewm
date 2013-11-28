package br.inf.id2.common.field;

import psdi.mbo.*;
import psdi.util.MXApplicationException;
import java.rmi.RemoteException;

import br.inf.id2.common.util.Data;
import java.util.Date;
import psdi.util.MXException;

/**
 *
 * @author Dyogo
 * 
 */
public class DataInicialMenorFinalEAtual extends MboValueAdapter {

    public DataInicialMenorFinalEAtual(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        String atributoAtual = getMboValue().getName();
        String atributoPrefixo;
        if (atributoAtual.indexOf("INI") != -1) {
            atributoPrefixo = atributoAtual.substring(0, atributoAtual.indexOf("INI"));
        } else {
            atributoPrefixo = atributoAtual.substring(0, atributoAtual.indexOf("FIM"));
        }
        System.out.println("*** atributoAtual " + atributoAtual);
        System.out.println("*** atributoPrefixo " + atributoPrefixo);
//        System.out.println("*** Data INI "+getMboValue(atributoPrefixo.concat("INI")).getDate());
//        System.out.println("*** Data FIM "+getMboValue(atributoPrefixo.concat("FIM")).getDate());

        if (!getMboValue(atributoPrefixo.concat("INI")).isNull() && !getMboValue(atributoPrefixo.concat("FIM")).isNull()) {
            Date dataInicial = getMboValue(atributoPrefixo.concat("INI")).getDate();
            Date dataFinal = getMboValue(atributoPrefixo.concat("FIM")).getDate();
            System.out.println("*** Dentro do IF - ninguem null");
            System.out.println(dataInicial);
            System.out.println(dataFinal);
            if (dataInicial != null && dataFinal != null) {
				if (Data.dataInicialMenorFinal(dataFinal, dataInicial)) {
					// if
					// (getMboValue(atributoPrefixo.concat("FIM")).getDate().before(getMboValue(atributoPrefixo.concat("INI")).getDate()))
					// {
					throw new MXApplicationException("company","DataInicialMenorQueFinal");

				}
            
	            if (Data.dataInicialMenorFinal(dataInicial, new Date())) {
	            	throw new MXApplicationException("company","DataInicialMenorQueAtual");
	            }
	            
	            if (Data.dataInicialMenorFinal(dataFinal, new Date())) {
	            	throw new MXApplicationException("company","DataFinalMenorQueAtual");
	            }
            }
        }
    }
}
