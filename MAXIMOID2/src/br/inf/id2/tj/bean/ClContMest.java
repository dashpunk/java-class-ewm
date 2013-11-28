package br.inf.id2.tj.bean;

import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class ClContMest extends psdi.webclient.beans.contmaster.ContMasterAppBean {

    public ClContMest() {
        super();
    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        //System.out.println("---- inicico ClContMest");
        double amSomEmp = Executa.somaValor("AMVALOR", getMbo().getMboSet("RL01TBCONEMP"));

        //System.out.println("--------- valor amSomEmp" + amSomEmp);

        double amSomPag = Executa.somaValor("AMVALNOTFIS", getMbo().getMboSet("RL01TBPROPAG"));
        //System.out.println("--------- valor amSomPag" + amSomPag);
        getMbo().setValue("AMSOMEMP", amSomEmp);
        getMbo().setValue("AMSOMPAG", amSomPag);

        if (getMbo().getMboSet("RL01TBTERADI").count() > 0) {
            //System.out.println("--- RL01TBTERADI "+getMbo().getMboSet("RL01TBTERADI").count());
            getMbo().setValue("AMVLA", getMbo().getMboSet("RL01TBTERADI").getMbo(getMbo().getMboSet("RL01TBTERADI").count()-1).getDouble("AMNVVLGLO"), MboConstants.NOVALIDATION_AND_NOACTION);
        } else {
            //System.out.println("--- amvla");
            getMbo().setValue("AMVLA", getMbo().getDouble("AMVLTL"), MboConstants.NOVALIDATION_AND_NOACTION);
        }

        //System.out.println("---- fim ClContMest");


        return super.SAVE();

    }
}
