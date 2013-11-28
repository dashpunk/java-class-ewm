package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.app.ticket.SR;
import psdi.app.ticket.SRSet;
import psdi.mbo.*;
import psdi.util.MXException;

// Referenced classes of package psdi.app.workorder:
//            WOSet, LocationCustom, LocationCustomSetRemote
//psdi.id2.mapa.ID2LocationCustomSet
//psdi.tloam.app.location.TloamLocationSet
//ANTES ESTAVA EXTENDENDO DE LocationSet
public class ID2SRSet extends SRSet
        implements ID2SRSetRemote {


    public ID2SRSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {
        super(mboserverinterface);
    }

    protected Mbo getMboInstance(MboSet mboset)
            throws MXException, RemoteException {
        Uteis.espera("********************** nova instancia");

        return new ID2SR(mboset);
    }
    
    @Override
    protected void addMbo(Mbo mbo) {
        Uteis.espera("********************** add(Mbo) em srset");
        super.addMbo(mbo);
        Uteis.espera("********************** depóis add(Mbo) em srset");
        try {
            Uteis.espera("*************** antes de -- not nul");
            if (getMbo() != null) {
                
                Uteis.espera("*************** --- not nul");
                if (getMbo().toBeAdded()) {

                    Uteis.espera("*************** --- toBeAdded");
                    if ((getMbo().getMboSet("MS_RL01PER") != null)) {
                        if (getMbo().getMboSet("MS_RL01PER").getMbo(0).getString("LOTACAO") != null) {
                            getMbo().setValue("MS_CODCOO", getMbo().getMboSet("MS_RL01PER").getMbo(0).getString("LOTACAO"),  MboConstants.NOVALIDATION_AND_NOACTION);
                            Uteis.espera("*************** SAVE() Entrou 2 " + getMbo().getMboSet("MS_RL01PER").getMbo(0).getString("LOTACAO"));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Uteis.espera("*****************************exceção 1");
        }

    }



}
