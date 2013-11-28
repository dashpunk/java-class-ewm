package br.inf.id2.seedf.field;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class SeeTbDeAulaSeeCal extends MboValueAdapter {

    public SeeTbDeAulaSeeCal(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

//        System.out.println("---> inicio SeeTbDeAulaSeeCal");
//        System.out.println("---> count de seerl02calesc " + getMboValue().getMbo().getMboSet("SEERL02CALESC").count());

        if (!getMboValue().getMbo().isNull("SEEDATA")) {

            if (getMboValue().getMbo().getMboSet("SEERL02CALESC").count() > 0) {

                String seeCal = getMboValue().getMbo().getMboSet("SEERL02CALESC").getMbo(0).getString("DESCRIPTION");
                String calEscId = getMboValue().getMbo().getMboSet("SEERL02CALESC").getMbo(0).getString("CALESCID");

                if (seeCal == null) {
                    getMboValue().getMbo().setValueNull("CALESCID");
                    getMboValue().getMbo().setValueNull("SEECAL");
                } else {
//					System.out.println("---> ATRIBUINDO");
//					System.out.println("seeCal ---> " + seeCal);
//					System.out.println("calEscId ---> " + calEscId);

                    getMboValue().getMbo().setValue("SEECAL", seeCal, MboConstants.NOVALIDATION_AND_NOACTION);
                    getMboValue().getMbo().setValue("CALESCID", calEscId, MboConstants.NOVALIDATION_AND_NOACTION);
                }
            } else {
//				System.out.println("---> SEERL02CALESC vazio ");

                getMboValue().getMbo().setValueNull("CALESCID");
                getMboValue().getMbo().setValueNull("SEECAL");
            }
        } else {
//        	System.out.println("---> SEEDATA null ");

            getMboValue().getMbo().setValueNull("CALESCID");
            getMboValue().getMbo().setValueNull("SEECAL");
        }
//        System.out.println("---> FIM SeeTbDeAulaSeeCal");
    }
}
