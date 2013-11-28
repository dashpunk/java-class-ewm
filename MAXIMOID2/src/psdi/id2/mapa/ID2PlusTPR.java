package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.mbo.MboSet;
import psdi.plust.app.pr.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
class ID2PlusTPR extends PlusTPR implements PlusTPRRemote {

    public ID2PlusTPR(MboSet mboset) throws MXException, RemoteException {
        super(mboset);
    }

    @Override
    public void add() throws MXException, RemoteException {
        //Uteis.espera("********************** add(Mbo)");
    	System.out.println("################# Alterado ID2DIR");
        super.add();
        if ((getMboSet("MS_RL04PER") != null) && (isNull("ID2CODCOO"))) {
            /*
            Uteis.espera("*************** SAVE() Entrou total de linhas do relacionamento = " + getMboSet("MS_RL04PER").count());
            if (getMboSet("MS_RL04PER").getMbo(0).getString("SECRETARIA") != null) {
            setValue("ID2CODSEC", getMboSet("MS_RL04PER").getMbo(0).getString("SECRETARIA"));
            Uteis.espera("*************** SAVE() Entrou 1 " + getMboSet("MS_RL04PER").getMbo(0).getString("SECRETARIA"));
            }
             */
            if (getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO") != null) {
                setValue("ID2CODCOO", getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO"));
                setValue("ID2SEC", getMboSet("MS_RL04PER").getMbo(0).getString("ID2SEC"));
                setValue("ID2DIR", getMboSet("MS_RL04PER").getMbo(0).getString("ID2DIR"));
                //Uteis.espera("*************** SAVE() Entrou 2 " + getMboSet("MS_RL04PER").getMbo(0).getString("ID2LOTACAO"));
            }
        }
    }
}
