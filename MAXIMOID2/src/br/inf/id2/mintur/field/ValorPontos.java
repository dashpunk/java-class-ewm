package br.inf.id2.mintur.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author Dyogo
 */
public class ValorPontos extends MboValueAdapter {


    public ValorPontos(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("########### ValorPontos");
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	
    	System.out.println("############# validate");
    	Integer vlrPontos = getMboValue().getInt();
    	System.out.println("############## Valor dos Pontos = " + vlrPontos);
    	
    	Integer vlrPontosRel = getMboValue().getMbo().getMboSet("RHRLQUES01").getMbo(0).getInt("RHNUVLRMAXIMO");
    	System.out.println("############## Valor dos Pontos do Rel = " + vlrPontosRel);
    	
    	if (vlrPontosRel != null && vlrPontos != 0) {
    		if (vlrPontos > vlrPontosRel) {
    			throw new MXApplicationException("avaliacao", "ValorPontosMaior");
    		}
    	}
    	

    	
    } 
}
