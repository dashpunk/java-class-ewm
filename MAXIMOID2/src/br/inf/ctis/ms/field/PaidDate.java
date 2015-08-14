package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import br.inf.ctis.common.util.Data;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class PaidDate extends MboValueAdapter{
	
	public PaidDate(MboValue mbv) throws MXException {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		super.validate();
		
		Date valor = getMboValue().getDate();
        super.validate();
        Date dataAtual = new Date();
        
        if (valor == null || dataAtual == null) {
        	return;
        }

        if (!Data.dataInicialMenorIgualFinal(valor, dataAtual)) {
            throw new MXApplicationException("system", "DataMaiorQueAtual");
        }
		
		Date dataInicial;
	    Date dataFinal;
	    
	    
    	if(!getMboValue("PAIDDATE").isNull()){
    		dataFinal = getMboValue("PAIDDATE").getDate();
            
    		if(!getMboValue("ENTERDATE").isNull()){
    			dataInicial = getMboValue("ENTERDATE").getDate();
    			
        		if (!Data.dataInicialMenorIgualFinal(dataInicial, dataFinal)) {
        			throw new MXApplicationException("invoice", "EnterDateMaiorPaidDate");
        		}
        	}
    		
    		if(!getMboValue("INVOICEDATE").isNull()){
    			dataInicial = getMboValue("INVOICEDATE").getDate();
    			
        		if (!Data.dataInicialMenorIgualFinal(dataInicial, dataFinal)) {
        			throw new MXApplicationException("invoice", "InvoiceDateMaiorPaidDate");
        		}
        	}
    	}
	    
		
		Date data = getMboValue().getDate();
		Calendar dataAtesto = Calendar.getInstance(); 
		dataAtesto.setTime(data);
		String mesAno;
		
		System.out.println("########## dataAtesto = " + data);
		System.out.println("########## dataAtesto.MONTH = " + (dataAtesto.get(Calendar.MONTH)+1));
		System.out.println("########## dataAtesto.YEAR = " + dataAtesto.get(Calendar.YEAR));
		if(dataAtesto.get(Calendar.MONTH) < 9) {
			mesAno = "0"+(dataAtesto.get(Calendar.MONTH)+1)+"/"+dataAtesto.get(Calendar.YEAR);
		} else {
			mesAno = (dataAtesto.get(Calendar.MONTH)+1)+"/"+dataAtesto.get(Calendar.YEAR);
		}
		System.out.println("########## mesAno = " + mesAno);
		getMboValue("MSALCODMESANO").setValue(mesAno);
		
	}

}
