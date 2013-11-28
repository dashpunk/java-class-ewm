package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.app.ticket.SR;
import psdi.app.ticket.SRRemote;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
class ID2SR extends SR implements SRRemote {

    public ID2SR(MboSet mboset) throws MXException, RemoteException {
        super(mboset);

    }

    @Override
    public void add() throws MXException, RemoteException {
        //Uteis.espera("********************** super add(Mbo) ID2SR ");
        super.add();
        //Uteis.espera("********************** após add(Mbo) ID2SR super.add()");
        if ((getMboSet("MS_RL01PER") != null)) {
            //Uteis.espera("********************** após MS_RL01PER != null");
            if (getMboSet("MS_RL01PER").getMbo(0).getString("LOTACAO") != null) {
                //Uteis.espera("********************** após MS_RL01PER.LOTACAO != null");
                setValue("MS_CODCOO", getMboSet("MS_RL01PER").getMbo(0).getString("LOTACAO"), MboConstants.NOVALIDATION_AND_NOACTION);
                //Uteis.espera("*************** SAVE() Entrou 2 " + getMboSet("MS_RL01PER").getMbo(0).getString("LOTACAO"));
            } else {
                //Uteis.espera("********************** após MS_RL01PER == null");
                setValue("MS_CODCOO", "RICARDO", MboConstants.NOVALIDATION_AND_NOACTION);
            }
        }
    }
}
