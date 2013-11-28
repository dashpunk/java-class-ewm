package br.inf.id2.mapa.wf;

import java.util.Date;
import psdi.common.action.ActionCustomClass;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;

/**
 *
 * @author Ricardo s Gomes
 */
public class AdicionaRegistroMatbestocoAction implements ActionCustomClass {

    public AdicionaRegistroMatbestocoAction() {
        super();
        System.out.println("____ AdicionaRegistroMatbestoco");
    }

    public void applyCustomAction(MboRemote mbo, java.lang.Object[] params) throws MXException, java.rmi.RemoteException {
        System.out.println("___ AdicionaRegistroMatbestoco");
        MboRemote mboAdicionar;
        mboAdicionar = mbo.getMboSet("MATBESTOCO").add();
        
        System.out.println("___ AdicionaRegistroMatbestoco A");
        mboAdicionar.setValue("MATBESTESTID", mbo.getInt("MATBESTESTID"), MboConstants.NOACCESSCHECK);
        System.out.println("___ AdicionaRegistroMatbestoco B");
        mboAdicionar.setValue("ID2DATOCO", new Date(), MboConstants.NOACCESSCHECK);
        System.out.println("___ AdicionaRegistroMatbestoco C");
        mboAdicionar.setValue("ID2DESOCO", "Foi realizada uma exclusão automática por vencimento de Data de Validade.", MboConstants.NOACCESSCHECK);
        System.out.println("___ AdicionaRegistroMatbestoco antes do save");
        mboAdicionar.getThisMboSet().save();
        System.out.println("___ AdicionaRegistroMatbestoco FIM");

    }
}
