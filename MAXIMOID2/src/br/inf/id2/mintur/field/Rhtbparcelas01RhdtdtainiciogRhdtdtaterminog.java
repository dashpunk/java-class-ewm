package br.inf.id2.mintur.field;

import java.util.Date;

import br.inf.id2.common.util.Data;
import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class Rhtbparcelas01RhdtdtainiciogRhdtdtaterminog extends MboValueAdapter {
    
    public Rhtbparcelas01RhdtdtainiciogRhdtdtaterminog(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("... Rhtbparcelas01RhdtdtainiciogRhdtdtaterminog");
    }
    
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        System.out.println("... validate");
        super.validate();
        if (!getMboValue("RHDTDTAINICIOG").isNull() && !getMboValue("RHDTDTATERMINOG").isNull()) {
            
            Date dataInicio = getMboValue("RHDTDTAINICIOG").getDate();
            Date dataTermino = getMboValue("RHDTDTATERMINOG").getDate();
            
            System.out.println("... d1 "+getMboValue("RHDTDTAINICIOG").getDate());
            System.out.println("... d2 "+getMboValue("RHDTDTATERMINOG").getDate());
           
            if (Data.dataInicialMenorFinal(dataTermino, dataInicio)) {
                throw new MXApplicationException("rhtbparcelas01", "rhdtdtainiciogMenorQueRhdtdtaterminog");
            }
            int dias = Data.recuperaDiasEntreDatas(dataInicio, dataTermino) + 1;
            
            System.out.println("... dias "+dias);
            
            getMboValue("RHNUNUMDIASPARCELA").setValue(dias, MboConstants.NOACCESSCHECK);
            
            System.out.println("... Rhtbparcelas01RhdtdtainiciogRhdtdtaterminog FIM");
        } else {
            getMboValue("RHNUNUMDIASPARCELA").setValueNull(MboConstants.NOACCESSCHECK);
        }
    }
}
