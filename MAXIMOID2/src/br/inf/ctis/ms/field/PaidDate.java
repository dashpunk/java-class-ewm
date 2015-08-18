package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Calendar;
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
		
		Date dataTeste;
	    Date paidDate;
	    
	    
    	if(!getMboValue("PAIDDATE").isNull()){
    		paidDate = getMboValue("PAIDDATE").getDate();
            
    		if(!getMboValue("ENTERDATE").isNull()){
    			dataTeste = getMboValue("ENTERDATE").getDate();
    			
        		if (!Data.dataInicialMenorIgualFinal(paidDate, dataTeste)) {
        			throw new MXApplicationException("invoice", "PaidDateMaiorEnterDate");
        		}
        	}
    		
    		if(!getMboValue("INVOICEDATE").isNull()){
    			dataTeste = getMboValue("INVOICEDATE").getDate();
    			
        		if (!Data.dataInicialMenorIgualFinal(dataTeste, paidDate)) {
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
