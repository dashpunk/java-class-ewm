package br.inf.id2.common.wf;

import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 *
 * @author Ricardo s Gomes
 */
public class AtribuirValorFalseAction implements ActionCustomClass {

    public AtribuirValorFalseAction() {
        super();
        System.out.println("____AtribuirValorFalseAction");
    }

    public void applyCustomAction(MboRemote mbo, java.lang.Object[] params) throws MXException, java.rmi.RemoteException {
        System.out.println("___AtribuirValorFalseAction applyCustomAction");
        System.out.println("___AtribuirValorFalseAction applyCustomAction COUNT " + params.length);
        for (Object object : params) {
            System.out.println("_______ parametro " + (String) object);
            mbo.setValue((String) object, false,  MboConstants.NOACCESSCHECK);
        }
        System.out.println("___AtribuirValorFalseAction applyCustomAction antes do save()");
        mbo.getThisMboSet().save();
        System.out.println("___AtribuirValorFalseAction applyCustomAction FIM");

    }
}
