package br.inf.id2.mintur.bean;

import br.inf.id2.common.util.Uteis;
import br.inf.id2.common.util.Validar;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author ricardo s gomes
 */
public class Mtuser01 extends psdi.tlom.webclient.beans.license.TloamUserBean {

    public Mtuser01() {
        System.out.println("--- Mtuser01 11:56");
    }

    @Override
    public int SAVE() throws MXException, RemoteException {

        if (!Validar.CPF(Uteis.getApenasNumeros(getMbo().getString("USERID")))) {
            throw new MXApplicationException("id2message", "CpfInvalido");
        }
        
        int retorno = super.SAVE();
        
        System.out.println("--- Mtuser01.SAVE()");

        System.out.println("--- Mtuser01.SAVE() USERID " + getMbo().getString("USERID"));

        System.out.println("--- relacionamento " + getMbo().getMboSet("MTRL01GROUPUSER").count());

        boolean permissaoOperador = false;
        MboRemote mboP;
        for (int i = 0; ((mboP = getMbo().getMboSet("MTRL01GROUPUSER").getMbo(i)) != null); i++) {
            System.out.println("i " + 1 + " " + mboP.getString("GROUPNAME"));
            if (mboP.getString("GROUPNAME").equalsIgnoreCase("MTURGRP30.1")) {
                permissaoOperador = true;
            }
        }
        if (!permissaoOperador) {
            MboRemote mbo;

            System.out.println("--- Mtuser01.SAVE() add");
            mbo = getMbo().getMboSet("MTRL01GROUPUSER").add();

            System.out.println("--- Mtuser01.SAVE() vals gn");
            mbo.setValue("GROUPNAME", "MTURGRP30.1", MboConstants.NOACCESSCHECK);
            System.out.println("--- Mtuser01.SAVE() vals gid");
            mbo.setValue("USERID", getMbo().getString("USERID"), MboConstants.NOACCESSCHECK);
            System.out.println("--- Mtuser01.SAVE() save");
            getMbo().getMboSet("MTRL01GROUPUSER").save(MboConstants.NOACCESSCHECK);
            System.out.println("--- Mtuser01.SAVE() FIM");
            refreshTable();
            reloadTable();
        } else {
        }

        return retorno;
    }
}
