package br.inf.id2.tj.field;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class MasterViewValorMensal extends MboValueAdapter {

    public MasterViewValorMensal(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();


        if ((!getMboValue().getMbo().isNull("AMVLTL")) && (!getMboValue().getMbo().isNull("ITVIGC"))) {

            double amVlTl = getMboValue().getMbo().getDouble("AMVLTL");
            double itViGc = getMboValue().getMbo().getDouble("ITVIGC");

            //System.out.println("--- " + amVlTl);
            //System.out.println("--- " + itViGc);

            double dcVlm = amVlTl / itViGc;

            //System.out.println("--- " + dcVlm);

            getMboValue().getMbo().setValue("DCVLM", dcVlm);

        } else {
            //System.out.println("--- setNull");
            getMboValue().getMbo().setValueNull("DCVLM");
        }

        if ((!getMboValue().getMbo().isNull("AMVLA")) && (!getMboValue().getMbo().isNull("ITVIGC"))) {

            double AMVLA = getMboValue().getMbo().getDouble("AMVLA");
            double ITVIGC = getMboValue().getMbo().getDouble("ITVIGC");

            //System.out.println("--- AMVLA " + AMVLA);
            //System.out.println("--- ITVIGC " + ITVIGC);

            double AMVLMT = AMVLA / ITVIGC;

            //System.out.println("--- AMVLMT " + AMVLMT);

            getMboValue().getMbo().setValue("AMVLMT", AMVLMT);

        } else {
            //System.out.println("--- setNull AMVLMT");
            getMboValue().getMbo().setValueNull("AMVLMT");
        }

        if ((!getMboValue().getMbo().isNull("AMVLTL")) && (!getMboValue().getMbo().isNull("DCDESM"))) {

            double amVlTl = getMboValue().getMbo().getDouble("AMVLTL");
            double dcDesM = getMboValue().getMbo().getDouble("DCDESM");

            //System.out.println("--- " + amVlTl);
            //System.out.println("--- " + dcDesM);

            double AMVLPR = amVlTl * (dcDesM / 100);

            //System.out.println("--- " + AMVLPR);

            getMboValue().getMbo().setValue("AMVLPR", AMVLPR);

        } else {
            //System.out.println("--- setNull");
            getMboValue().getMbo().setValueNull("AMVLPR");
        }

    }
}
