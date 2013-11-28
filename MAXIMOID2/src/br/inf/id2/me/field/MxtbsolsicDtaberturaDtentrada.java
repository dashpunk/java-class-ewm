package br.inf.id2.me.field;

import java.util.Date;

import br.inf.id2.common.util.Data;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @author Patrick L. Silva
 */
public class MxtbsolsicDtaberturaDtentrada extends MboValueAdapter {

    public MxtbsolsicDtaberturaDtentrada(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("*** MxtbsolsicDtaberturaDtentrada ***");
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
    	System.out.println("*** validate");
    	
    	Date  dataAbertura = getMboValue("DTABERTURA").getDate();
    	Date  dataEntrada = getMboValue("DTENTRADA").getDate();
    	
    	System.out.println("*** dataAbertura "+dataAbertura);
    	System.out.println("*** dataEntrada "+dataEntrada);
    	
    	System.out.println("*** dataInicialMenorFinal(dataAbertura, dataEntrada) "+Data.dataInicialMenorFinal(dataAbertura, dataEntrada));
    	if(Data.dataInicialMenorFinal(dataAbertura, dataEntrada)){
    		throw new MXApplicationException("mxtbsolsic", "DataEntradaMaiorAbertura");
    	}
    	
    	System.out.println("*** adicionaDiasData(dataAbertura, 10) "+Data.adicionaDiasData(dataAbertura, 10));
    	getMboValue("DTPRZRES").setValue(Data.adicionaDiasData(dataAbertura, 10));
        
    	System.out.println("*** FIM");
        super.validate();
    }
}
