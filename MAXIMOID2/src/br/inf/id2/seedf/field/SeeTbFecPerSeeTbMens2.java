package br.inf.id2.seedf.field;

import java.rmi.RemoteException;
import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Ricardo S Gomes
 */
public class SeeTbFecPerSeeTbMens2 extends MboValueAdapter {

    public SeeTbFecPerSeeTbMens2(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        //System.out.println("========= seetbmen2 validate");
        if (!getMboValue().getMbo().isNull("SEEFLGCALCPER")) {
            //System.out.println("========= seetbmen2 validate a");
            if (getMboValue().getMbo().getBoolean("SEEFLGCALCPER")) {
                //System.out.println("========= seetbmen2 validate b");

                int tComCurID = getMboValue().getMbo().getInt("TCOMCURID");
                int deAulaID = getMboValue().getMbo().getInt("DEAULAID");
                String personID = getMboValue().getMbo().getString("PERSONID");
                //System.out.println("========= v1 " + tComCurID);
                //System.out.println("========= v2 " + deAulaID);
                //System.out.println("========= v3 " + personID);

                //System.out.println("========= seetbmen2 mboset init");
                MboSet faltas;
                faltas = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("SEETBDEPRES", getMboValue().getMbo().getUserInfo());

                faltas.setWhere("SEEPRESENCA = 0 and TCOMCURID = " + tComCurID + " and DEAULAID = " + deAulaID + " and PERSONID = '" + personID + "'");
                //System.out.println("========= seetbmen2 mboset before reset");
                faltas.reset();
                //System.out.println("========= seetbmen2 mboset after reset");

                getMboValue().setValue(faltas.count());
                //System.out.println("========= seetbmen2 end");
            }
        }
    }
}
