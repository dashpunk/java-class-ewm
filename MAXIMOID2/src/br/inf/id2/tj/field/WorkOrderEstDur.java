package br.inf.id2.tj.field;

import java.rmi.RemoteException;
import java.util.Date;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;
import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;

/**
 *
 * @author Ricardo S Gomes
 */
public class WorkOrderEstDur extends MboValueAdapter {

    public WorkOrderEstDur(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        Date dataInicial = getMboValue().getMbo().getDate("TARGSTARTDATE");
        if (dataInicial != null) {
            Double duracao = getMboValue().getMbo().getDouble("ESTDUR");

            String b = "" + duracao;
            String[] valor = new String[2];
            valor = b.split("\\.");
            Double x = Double.valueOf("0." + valor[1]) * 60;

            //System.out.println(" x " + Math.round(x));

            int h = Integer.valueOf(valor[0]);
            int m = (int) Math.round(x);
            int s = 0;

            //System.out.println("======== TARGSTARTDATE " + dataInicial);
            //System.out.println("======== ESTDUR " + duracao);
            //System.out.println("======== h " + h);
            //System.out.println("======== m " + m);
            //System.out.println("======== s " + s);
            //System.out.println("======== acresc " + Data.getAcrescimoHoras(dataInicial, h, m, s));

            getMboValue().getMbo().setValue("TARGCOMPDATE", Data.getAcrescimoHoras(dataInicial, h, m, s), MboConstants.NOVALIDATION_AND_NOACTION);
            //System.out.println("======== End");
        }
    }
}
