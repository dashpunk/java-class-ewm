package br.inf.id2.seedf.field;

import br.inf.id2.common.util.Data;
import psdi.mbo.*;


import java.rmi.RemoteException;
import java.util.Date;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class SeeTbdeaulaSeeDuracao extends MboValueAdapter {

    public SeeTbdeaulaSeeDuracao(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        //System.out.println("---> inicio SeeTbdeaulaSeeDuracao");

        try {
            //System.out.println("---> termino " + getMboValue().getMbo().getDate("SEETERMINO"));
            //System.out.println("---> inicio " + getMboValue().getMbo().getDate("SEEINICIO"));
            Date d1 = getMboValue().getMbo().getDate("SEEINICIO");
            Date d2 = getMboValue().getMbo().getDate("SEETERMINO");
            //System.out.println("---> dif " + Data.getDiferencaHoras(d1, d2));
            getMboValue().getMbo().setValue("SEEDURACAO", Data.getDiferencaHoras(d1, d2), MboConstants.NOVALIDATION_AND_NOACTION);

        } catch (Exception e) {
            System.out.println("-------------------exception " + e.getMessage());
        }

        //System.out.println("---> FIM SeeTbdeaulaSeeDuracao");
    }
}
