package br.inf.id2.seedf.bean;

import java.rmi.RemoteException;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 * Classe para a aplicação seecl02ass
 */
public class InstituicaoEducacional extends psdi.webclient.beans.asset.AssetAppBean {

    /**
     *
     */
    public InstituicaoEducacional() {
    }

    /**
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {
        System.out.println("");
        for (int i = 0; i < getMbo().getMboSet("SEERL01AMB").count(); i++) {
            if (!(getMbo().getMboSet("SEERL01AMB").getMbo(i).getDouble("SEEAREA") > 0D)) {
                throw new MXApplicationException("company", "seeAreiaIgualZero");
            }
            getMbo().getMboSet("SEERL01AMB").getMbo(i).setValue("INSERTING", false);

        }
        return super.SAVE();
    }
}
