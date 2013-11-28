package br.inf.id2.seedf.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class SeeTbTofeieSeeCursoId extends MboValueAdapter {

    public SeeTbTofeieSeeCursoId(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        //System.out.println("a EM VALIDATE CURSOID---> assetnum   = " + getMboValue().getMbo().getString("assetnum"));
        //System.out.println("a EM VALIDATE CURSOID---> seecursoid = " + getMboValue().getMbo().getString("seecursoid"));
        //System.out.println("a EM VALIDATE CURSOID---> TURMAID    = " + getMboValue().getMbo().getString("TURMAID"));



        //System.out.println(" EM VALIDATE CURSOID---> TAMANHO DO SEERL01TCOMCUR = " + getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").count());
        //System.out.println(" EM VALIDATE CURSOID---> COMMATCUR (Todos) = " + getMboValue().getMbo().getMboSet("SEERL01COMMATCUR").count());

        getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").deleteAndRemoveAll();

        for (int i = 0; i < getMboValue().getMbo().getMboSet("SEERL01COMMATCUR").count(); i++) {

            MboRemote mbo = getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").add();
            //System.out.println("############ MBO Criado " + i);

            mbo.setValue("ASSETNUM", getMboValue().getMbo().getString("ASSETNUM"), MboConstants.NOVALIDATION_AND_NOACTION);
            mbo.setValue("TURMAID", getMboValue().getMbo().getInt("TURMAID"), MboConstants.NOVALIDATION_AND_NOACTION);
            mbo.setValue("TOFEIEID", getMboValue().getMbo().getInt("TOFEIEID"), MboConstants.NOVALIDATION_AND_NOACTION);
            mbo.setValue("SEECURSOID", getMboValue().getMbo().getInt("SEECURSOID"), MboConstants.NOVALIDATION_AND_NOACTION);
            mbo.setValue("COMMATCURID", getMboValue().getMbo().getMboSet("SEERL01COMMATCUR").getMbo(i).getInt("COMMATCURID"), MboConstants.NOVALIDATION_AND_NOACTION);
            mbo.setValue("SEECOMCUR", getMboValue().getMbo().getMboSet("SEERL01COMMATCUR").getMbo(i).getString("SEECOMCUR"), MboConstants.NOVALIDATION_AND_NOACTION);
            mbo.setValue("COMCURMODID", getMboValue().getMbo().getMboSet("SEERL01COMMATCUR").getMbo(i).getInt("COMCURMODID"), MboConstants.NOVALIDATION_AND_NOACTION);

            //System.out.println("------------------------------------------------------");
            //System.out.println("---->>>> TURMAID     = "+mbo.getString("TURMAID"));
            //System.out.println("---->>>> TOFEIEID    = "+mbo.getString("TOFEIEID"));
            //System.out.println("---->>>> SEECURSOID  = "+mbo.getString("SEECURSOID"));
            //System.out.println("---->>>> COMMATCURID = "+mbo.getString("COMMATCURID"));
            //System.out.println("---->>>> SEECOMCUR   = "+mbo.getString("SEECOMCUR"));
            //System.out.println("---->>>> COMCURMODID = "+mbo.getString("COMCURMODID"));
            //System.out.println("------------------------------------------------------");
        }
        //System.out.println(" EM VALIDATE CURSOID---> COMCUR (Atual) = " + getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").count());
        //System.out.println(" EM VALIDATE CURSOID---> COMMATCUR (Todos) = " + getMboValue().getMbo().getMboSet("SEERL01COMMATCUR").count());

        getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").save();

        super.validate();


    }
}
