package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Date;
import br.inf.ctis.common.util.Data;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/*
 * EMISSAO - INVOICEDATE
 * ATESTO - PAIDDATE
 * ENTRADA NA CEOF - ENTERDATE
 */

public class VerificaDataNotaFiscal extends MboValueAdapter {
	
	public VerificaDataNotaFiscal(MboValue mbv) throws MXException {
	        super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {
		
		super.validate();
		
		/*Date valor = getMboValue().getDate();
        super.validate();
        Date dataAtual = new Date();
        
        if (valor == null || dataAtual == null) {
        	return;
        }

        if (Data.dataInicialMenorFinal(valor, dataAtual)) {
            throw new MXApplicationException("system", "DataMenorQueAtual");
        }*/
	    
	    String campo = getMboValue().getName();
	    Date dataInicial;
	    Date dataFinal;
	    
	    if(campo.equalsIgnoreCase("INVOICEDATE")){
	    	if(!getMboValue("INVOICEDATE").isNull()){
	    		dataInicial = getMboValue("INVOICEDATE").getDate();
	            
	    		if(!getMboValue("ENTERDATE").isNull()){
	    			dataFinal = getMboValue("ENTERDATE").getDate();
	    			
	        		if (!Data.dataInicialMenorIgualFinal(dataInicial, dataFinal)) {
	        			throw new MXApplicationException("invoice", "InvoiceDateMaiorEnterDate");
	        		}
	        	}
	    		
	    		if(!getMboValue("PAIDDATE").isNull()){
	    			dataFinal = getMboValue("PAIDDATE").getDate();
	    			
	        		if (!Data.dataInicialMenorIgualFinal(dataInicial, dataFinal)) {
	        			throw new MXApplicationException("invoice", "InvoiceDateMaiorPaidDate");
	        		}
	        	}
	    	}
	    }
	    
	    if(campo.equalsIgnoreCase("ENTERDATE")){
	    	if(!getMboValue("ENTERDATE").isNull()){
	    		dataFinal = getMboValue("ENTERDATE").getDate();
	            
	    		if(!getMboValue("INVOICEDATE").isNull()){
	    			dataInicial = getMboValue("INVOICEDATE").getDate();
	    			
	        		if (!Data.dataInicialMenorIgualFinal(dataInicial, dataFinal)) {
	        			throw new MXApplicationException("invoice", "InvoiceDateMaiorEnterDate");
	        		}
	        	}
	    		
	    		if(!getMboValue("PAIDDATE").isNull()){
	    			dataInicial = getMboValue("PAIDDATE").getDate();
	    			
	        		if (!Data.dataInicialMenorIgualFinal(dataInicial, dataFinal)) {
	        			throw new MXApplicationException("invoice", "PaidDateMaiorEnterDate");
	        		}
	        	}
	    	}
	    }
	}

}
