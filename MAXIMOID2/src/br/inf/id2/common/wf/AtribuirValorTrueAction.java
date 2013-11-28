package br.inf.id2.common.wf;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 *
 * @author Ricardo s Gomes
 */
public class AtribuirValorTrueAction implements ActionCustomClass {

    public AtribuirValorTrueAction() {
        super();
        System.out.println("___AtribuirValorTrueAction");
    }

    public void applyCustomAction(MboRemote mbo, java.lang.Object[] params) throws MXException, java.rmi.RemoteException {
        System.out.println("___AtribuirValorTrueAction applyCustomAction");
        System.out.println("___AtribuirValorTrueAction applyCustomAction COUNT " + params.length);
        for (Object object : params) {
            System.out.println("_______ parametro " + (String) object);
            mbo.setValue((String) object, true,  MboConstants.NOACCESSCHECK);
        }
        System.out.println("___AtribuirValorTrueAction applyCustomAction antes do save()");
        mbo.getThisMboSet().save();
        System.out.println("___AtribuirValorTrueAction applyCustomAction FIM");

    }
}
