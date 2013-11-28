package br.inf.id2.mapa.field;

import br.inf.id2.common.field.*;
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class MaproconMaproconMamotcon extends MboValueAdapter {

    public MaproconMaproconMamotcon(MboValue mbv) {
        super(mbv);
        System.out.println("--- MaproconMaproconMamotcon");
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("--- MaproconMaproconMamotcon validate");
        MboRemote mbo;
        int registroCorrente = getMboValue().getMbo().getThisMboSet().getCurrentPosition();
        System.out.println("--- MaproconMaproconMamotcon v1 " + registroCorrente);
        System.out.println("--- MaproconMaproconMamotcon v2 " + getMboValue().getMbo().getThisMboSet().count());
        for (int i = 0; ((mbo = getMboValue().getMbo().getThisMboSet().getMbo(i)) != null); i++) {
            System.out.println("--- i " + i);
            System.out.println("--- v1 " + getMboValue("MAPROCON").getString());
            System.out.println("--- v1 " + getMboValue("MAPROCON").getString());
            System.out.println("--- v2 " + mbo.getString("MAMOTCON"));
            System.out.println("--- v2 " + mbo.getString("MAMOTCON"));
            if (i != registroCorrente && mbo.getString("MAPROCON").equalsIgnoreCase(getMboValue("MAPROCON").getString()) && mbo.getString("MAMOTCON").equalsIgnoreCase(getMboValue("MAMOTCON").getString())) {
                throw new MXApplicationException("maprocon", "valorMAPROCONMAMOTCONDuplicado");
            }

        }

        super.validate();

    }
}
