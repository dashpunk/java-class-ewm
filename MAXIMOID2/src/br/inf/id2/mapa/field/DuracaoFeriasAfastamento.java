package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;

/**
 * 
 * @author Leysson Barbosa Moreira
 *  
 */
public class DuracaoFeriasAfastamento extends MboValueAdapter {

    public DuracaoFeriasAfastamento(MboValue mbv) {
        super(mbv);

    }

    @Override
    public void validate() throws MXException, RemoteException {

        Date inicioFeriasAfastamento = getMboValue().getMbo().getDate("ID2DATINI");
        Date terminFeriasAfastamento = getMboValue().getMbo().getDate("ID2DATFIM");
        

       //if (Data.dataInicialMenorFinal(inicioFeriasAfastamento, new Date())) {
       //    throw new MXApplicationException("workorder", "dataMenorQueInicioFerias");
       // }
        if (Data.dataInicialMenorFinal(terminFeriasAfastamento, inicioFeriasAfastamento)) {
            System.out.println("-------------- throw");
            throw new MXApplicationException("workorder", "dataInicialMenorFinalFerias");
        }
        if (inicioFeriasAfastamento != null && terminFeriasAfastamento != null) {
            getMboValue().getMbo().setFieldFlag("ID2TEMP", MboConstants.READONLY, false);
            getMboValue().getMbo().setValue("ID2TEMP", Data.recuperaDiasEntreDatas(inicioFeriasAfastamento, terminFeriasAfastamento) + 1, MboConstants.NOVALIDATION_AND_NOACTION);
            getMboValue().getMbo().setFieldFlag("ID2TEMP", MboConstants.READONLY, true);
        }

        super.validate();

    }
}
