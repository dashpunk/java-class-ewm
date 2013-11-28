package br.inf.id2.valec.field;

import java.rmi.RemoteException;
import java.util.Date;

import br.inf.id2.common.util.Data;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Dyogo
 *  
 */
public class CalculaDataTermino extends MboValueAdapter {

    public CalculaDataTermino(MboValue mbv) {
        super(mbv);
        System.out.println("############# CONSTRUTOR Calcula Data término");
    }

    @Override
    public void validate() throws MXException, RemoteException {

    	System.out.println("############# Entrou no Validate - CalculaDataTermino");
    	int dias = getMboValue().getMbo().getInt("MXDIAPRA");
    	Date dataIni = getMboValue().getMbo().getDate("MXDATINI");
    	if (dataIni != null) {
	    	Date dataFim = Data.adicionaDiasData(dataIni, dias);
	    	getMboValue().getMbo().setValue("MXDATTER", dataFim);
    	}
        super.validate();
        
    }
}
