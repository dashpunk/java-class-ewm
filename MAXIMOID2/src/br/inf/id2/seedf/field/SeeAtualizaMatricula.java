package br.inf.id2.seedf.field;

import psdi.mbo.*;

import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class SeeAtualizaMatricula extends MboValueAdapter {

    public SeeAtualizaMatricula(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("*** SeeAtualizaMatricula ***");
        super.validate();

        String personId = getMboValue().getString();
        System.out.println("*** personId " + personId);
        if (personId != null) {
            if (getMboValue().getMbo().getMboSet("SEERL02MATRICULA").count() > 0) {
                System.out.println("*** SEERL02MATRICULA entrou no if *** ");
                String matricula = getMboValue().getMbo().getMboSet("SEERL02MATRICULA").getMbo(0).getString("MATRICULAID");
                System.out.println("*** matricula " + matricula);
                getMboValue("MATRICULAID").setValue(matricula);
            }
        }

    }
}
