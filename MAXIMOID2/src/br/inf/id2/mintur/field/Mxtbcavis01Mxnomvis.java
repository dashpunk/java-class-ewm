package br.inf.id2.mintur.field;

import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class Mxtbcavis01Mxnomvis extends MboValueAdapter {

    public Mxtbcavis01Mxnomvis(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("___ Mxtbcavis01Mxnomvis");
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        super.validate();
        System.out.println("___ Mxtbcavis01Mxnomvis validate ");

        MboSet visitantes;
        visitantes = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MXTBCAVIS02", getMboValue().getMbo().getUserInfo());

        visitantes.setWhere("MXNOMVIS = '" + getMboValue("MXNOMVIS").getString() + "'");
        visitantes.reset();

        System.out.println("___ visitantes count " + visitantes.count());

        if (visitantes.count() == 0) {
            getMboValue("MXINSVIS").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("MXINDVIS").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("MXSIGPAR").setValueNull(MboConstants.NOACCESSCHECK);
            getMboValue("MXESTPAR").setValueNull(MboConstants.NOACCESSCHECK);
        }

        System.out.println("___ Mxtbcavis01Mxnomvis validate FIM");
    }
}
