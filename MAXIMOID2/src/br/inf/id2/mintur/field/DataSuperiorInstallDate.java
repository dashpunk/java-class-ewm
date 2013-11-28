package br.inf.id2.mintur.field;

import java.util.Date;

import br.inf.id2.common.util.Data;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Dyogo Dantas
 * 
 */
public class DataSuperiorInstallDate extends MboValueAdapter {

    public DataSuperiorInstallDate(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        Date valor = getMboValue().getDate();
        System.out.println("####### Data MTDATTRA =" +valor);
        Date installDate = getMboValue().getMbo().getDate("INSTALLDATE");
        System.out.println("####### Data INSTALLDATE =" +installDate);
        if (valor != null && installDate != null) {
	        System.out.println("########## EH MENOR=" + Data.dataInicialMenorFinal(valor, installDate));
	        if (Data.dataInicialMenorFinal(valor, installDate)) {
	        	throw new MXApplicationException("asset", "DataMenorQueInstallDate");
	        }
        }
        super.validate();
    }
}
