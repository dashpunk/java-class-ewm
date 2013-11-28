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
 * @author Dyogo
 *  
 */
//public class DuracaoEfetivaExecucao extends psdi.app.workorder.FldWOActualDate {
public class DuracaoEfetivaExecucao extends MboValueAdapter {

    public DuracaoEfetivaExecucao(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	super.validate();
    	if (getMboValue().getMbo().isNull("MADTEXEINI") || getMboValue().getMbo().isNull("MADTEXEFIM")) {
    		return;
    	}
    	
        Date inicioEfetivo = getMboValue().getMbo().getDate("MADTEXEINI");
        Date terminEfetivo = getMboValue().getMbo().getDate("MADTEXEFIM");


//      if (Data.dataInicialMenorFinal(inicioEfetivo, new Date())) {
//            throw new MXApplicationException("workorder", "inicioEfetivoMenorQueDataAtual");
//      }
        
        if (Data.dataInicialMenorFinal(terminEfetivo, inicioEfetivo)) {
            System.out.println("-------------- throw");
            throw new MXApplicationException("workorder", "terminEfetivoMenorQueInicioEfetivo");
        }
        if (inicioEfetivo != null && terminEfetivo != null) {
            getMboValue().getMbo().setFieldFlag("ID2DUREXE", MboConstants.READONLY, false);
            getMboValue().getMbo().setValue("ID2DUREXE", Data.recuperaDiasEntreDatas(inicioEfetivo, terminEfetivo) + 1, MboConstants.NOVALIDATION_AND_NOACTION);
            getMboValue().getMbo().setFieldFlag("ID2DUREXE", MboConstants.READONLY, true);
        }
    }
}
