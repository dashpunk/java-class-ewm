package br.inf.id2.ms.mbo;

import java.rmi.RemoteException;
import java.util.Vector;
import psdi.mbo.*;
import psdi.id2.Uteis;

import psdi.plust.app.pr.PlusTPRLineSet;
import psdi.txn.MXTransaction;
import psdi.util.MXException;

/**
 * @author Ricardo S Gomes
 *
 */
public class ID2PRLineSet extends PlusTPRLineSet
        implements ID2PRLineSetRemote {

    public ID2PRLineSet(MboServerInterface mboserverinterface)
            throws MXException, RemoteException {
        super(mboserverinterface);
        //System.out.println("---- prlineset");
    }

    public void validate() throws MXException, RemoteException {
        //Uteis.espera("******************validate ID2PRLineSet");

        if (getMboValueData("PR.ID2DATAPLANO") != null) {
            if ((!getMbo().isNew()) && (!getMboValueData("PR.ID2DATAPLANO").isNull())) {
                Uteis.espera("******************validate alterado");
                getMbo().setValue("ID2STATUS", "ALTERADO");
            }
        }
        //atribuiSubtotais();
        super.validate();
        //Uteis.espera("***************** save() FIM");
    }

    @Override
    public void save() throws MXException, RemoteException {
        //System.out.println("---s");
        //limpaCampos();
        super.save();
    }

    @Override
    public void saveMbos() throws MXException, RemoteException {
        //System.out.println("---sm");
        //limpaCampos();
        super.saveMbos();
    }

    @Override
    public void save(MXTransaction txn) throws MXException, RemoteException {
        //limpaCampos();
        super.save(txn);
    }

    @Override
    public void save(long flags) throws MXException, RemoteException {
        //limpaCampos();
        super.save(flags);
    }

    @Override
    public void save(MXTransaction txn, long flags) throws MXException, RemoteException {
        //limpaCampos();
        super.save(txn, flags);
    }

    private void limpaCampos2() throws MXException, RemoteException {
        //System.out.println("---limparCampos");
        MboRemote mbo;
        MboRemote mbob;
        //System.out.println("---limparCampos count " + count());
        MboSet polineSet;
        polineSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("POLINE", getUserInfo());
        for (int i = 0; ((mbo = getMbo(i)) != null); i++) {
            //System.out.println("---limparCampos i " + i);
            try {
                mbo.setValueNull("PONUM", MboConstants.NOACCESSCHECK);
            } catch (Exception e) {
                //System.out.println("- e " + e.getMessage());
            }
            try {
                mbo.setValueNull("POLINENUM", MboConstants.NOACCESSCHECK);
            } catch (Exception e) {
                //System.out.println("- e " + e.getMessage());
            }
            try {
                /* //System.out.println("polineID a " + mbo.getInt("POLINEID"));
                //System.out.println("polineID b " + mbo.getInt("PRLINEID"));

                polineSet.setWhere("POLINEID = " + mbo.getInt("POLINEID"));
                //System.out.println("antes do reset()");
                polineSet.reset();
                //System.out.println("apos reset() " + polineSet.count());
                for (int j = 0; ((mbob = polineSet.getMbo(j)) != null); j++) {
                //System.out.println("@@@@@@ tentando atribuit polineid a " + mbo.getInt("POLINEID"));
                //System.out.println("@@@@@@ tentando atribuit polineid b " + mbo.getInt("PRLINEID"));
                //System.out.println("@@@@@@ tentando atribuit polineid c " + mbob.getInt("PRLINEID"));
                if (mbo.getInt("POLINEID") > 0) {
                //System.out.println("@@@@@ antes de atribuir");
                mbob.setValue("PRLINEID", mbo.getInt("PRLINEID"), MboConstants.NOACCESSCHECK);
                //System.out.println("@@@@@ apos atribuir");
                }
                }
                //System.out.println("@@@@@ antes de save");
                polineSet.save();
                //System.out.println("@@@@@ antes save");*/
                mbo.setValueNull("POLINEID", MboConstants.NOACCESSCHECK);
            } catch (Exception e) {
                //System.out.println("- e " + e.getMessage());
            }

        }
        //System.out.println("---limparCampos FIM");
    }
}
