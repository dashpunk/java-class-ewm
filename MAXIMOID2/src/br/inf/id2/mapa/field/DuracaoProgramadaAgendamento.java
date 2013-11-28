package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 * 
 * @author Dyogo
 *  
 */
public class DuracaoProgramadaAgendamento extends MboValueAdapter {

    public DuracaoProgramadaAgendamento(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        Date inicioProg = getMboValue().getMbo().getDate("MADTPROINI");
        Date terminProg = getMboValue().getMbo().getDate("MADTPROFIM");
        try {
            if (Data.dataInicialMenorFinal(terminProg, inicioProg)) {
                throw new MXApplicationException("workorder", "terminProgMenorQueInicioProg");
            }
            if (inicioProg != null && terminProg != null) {
                getMboValue().getMbo().setFieldFlag("ID2DURPRE", MboConstants.READONLY, false);
                getMboValue().getMbo().setValue("ID2DURPRE", Data.recuperaDiasEntreDatas(inicioProg, terminProg) + 1, MboConstants.NOVALIDATION_AND_NOACTION);
                getMboValue().getMbo().setFieldFlag("ID2DURPRE", MboConstants.READONLY, true);
            }
        } catch (Exception e) {
        }
        super.validate();

    }
}
