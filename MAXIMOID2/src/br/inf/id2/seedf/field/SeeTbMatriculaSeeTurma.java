package br.inf.id2.seedf.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class SeeTbMatriculaSeeTurma extends MboValueAdapter {

    public SeeTbMatriculaSeeTurma(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        //System.out.println("---> inicio SeeTbMatriculaSeeTurma");

        //System.out.println("--> linhas em SEERL01MTCOMCUR = " + getMboValue().getMbo().getMboSet("SEERL01MTCOMCUR").count());
        //System.out.println("--> linhas em SEERL01TCOMCUR = " + getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").count());

        if (getMboValue().getMbo().getMboSet("SEERL01MTCOMCUR").count() > 0) {
            if (getMboValue().getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(0).getMboSet("SEERL01CCMENS").count() > 0
                    || getMboValue().getMbo().getMboSet("SEERL01MTCOMCUR").getMbo(0).getMboSet("SEERL01FECPER").count() > 0) {

                MboSetRemote mtConcur = getMboValue().getMbo().getMboSet("SEERL01MTCOMCUR");

                for (int j = 0; j < getMboValue().getMbo().getMboSet("SEERL01MTCOMCUR").count(); j++) {
                    mtConcur.getMbo(j).setValue("SEESTATUS", "INATIVO", MboConstants.NOVALIDATION_AND_NOACTION);
                }

            } else {
                getMboValue().getMbo().getMboSet("SEERL01MTCOMCUR").deleteAndRemoveAll();
            }
        }

        for (int i = 0; i < getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").count(); i++) {
            //System.out.println("---> inicio FOR");
            MboRemote mbo = getMboValue().getMbo().getMboSet("SEERL01MTCOMCUR").add();
            mbo.setValue("MATRICULAID", getMboValue().getMbo().getLong("MATRICULAID"), MboConstants.NOVALIDATION_AND_NOACTION);
            mbo.setValue("SOLVAID", getMboValue().getMbo().getLong("SOLVAID"), MboConstants.NOVALIDATION_AND_NOACTION);

            String seeComCurMod = getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").getMbo(i).getMboSet("SEERL01COMCURMOD").getMbo(0).getString("SEECOMCUR");
            Long comCurModId = getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").getMbo(i).getMboSet("SEERL01COMCURMOD").getMbo(0).getLong("COMCURMODID");

            mbo.setValue("SEECOMCURMOD", seeComCurMod, MboConstants.NOVALIDATION_AND_NOACTION);
            mbo.setValue("COMCURMODID", comCurModId, MboConstants.NOVALIDATION_AND_NOACTION);

            //System.out.println("---> contador i = " + i);
            //System.out.println("---> SEECOMCURMOD   = " + seeComCurMod);
            //System.out.println("---> COMCURMODID = " + comCurModId);
            //System.out.println("---> fim FOR");
        }

        getMboValue().getMbo().getMboSet("SEERL01MTCOMCUR").save();

        //System.out.println("--> linhas em SEERL01COMCURMOD = " + getMboValue().getMbo().getMboSet("SEERL01COMCURMOD").count());
        //System.out.println("--> linhas em SEERL01MTCOMCUR = " + getMboValue().getMbo().getMboSet("SEERL01MTCOMCUR").count());


        super.validate();

        //System.out.println("---> FIM SeeTbMatriculaSeeTurma");
    }
}
