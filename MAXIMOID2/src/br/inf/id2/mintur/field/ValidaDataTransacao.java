package br.inf.id2.mintur.field;

import java.util.Date;

import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 * @author Patrick
 */
public class ValidaDataTransacao extends MboValueAdapter {

    public ValidaDataTransacao(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("*** ValidaDataTransacao ***");
    }
    
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
    	super.validate();
    	System.out.println("*** validate");
    	if(!getMboValue().isNull()){
//    		SELECT PERIODSTART FROM FINANCIALPERIODS WHERE CLOSEDATE IS NULL
    		MboSet periodo;
            periodo = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("FINANCIALPERIODS", getMboValue().getMbo().getUserInfo());
            periodo.setWhere("CLOSEDATE IS NULL");
            periodo.reset();
            
            Date dataInicio = periodo.getMbo(0).getDate("PERIODSTART");
            Date dataFim = new Date();
            Date data = getMboValue().getDate();
            
            if(!Data.dataEntreInicialFinal(data, dataInicio, dataFim)){
            	throw new MXApplicationException("data", "dataTransacaoInvalida");
            }
    	}
    }
}
