package br.inf.id2.me.field;

import br.inf.id2.common.util.Data;
import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Patrick
 */
public class ID2DtInicioVigencia extends MboValueAdapter {

    public ID2DtInicioVigencia(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

    	System.out.println("*** ID2DtInicioVigencia ***");
        super.validate();
        
        Date startDate = getMboValue().getMbo().getDate("MESTARTDATE");
        Date endDate = getMboValue().getMbo().getDate("ENDDATE");
        Date id2CdtAss = getMboValue().getMbo().getDate("ID2CDTASS");
        
        if(id2CdtAss!=null && startDate!=null){
        	System.out.println("*** if1 ***");
	        if (Data.dataInicialMenorFinal(startDate, id2CdtAss)) {
	            throw new MXApplicationException("company", "ID2CDTASS");
	        }
        }
        
        if(startDate!=null && endDate!=null){
        	System.out.println("*** if2 ***");
	        if (Data.dataInicialMenorFinal(endDate, startDate)) {
	            throw new MXApplicationException("company", "StartDateMaiorQueEndDate");
	        }
        }
      
    }
}
