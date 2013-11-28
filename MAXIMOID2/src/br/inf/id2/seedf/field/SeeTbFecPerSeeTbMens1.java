package br.inf.id2.seedf.field;

import java.rmi.RemoteException;
import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;

import psdi.util.MXException;

/**
 * @author Ricardo S Gomes
 */
public class SeeTbFecPerSeeTbMens1 extends MboValueAdapter {

    public SeeTbFecPerSeeTbMens1(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        //System.out.println("========= seetbmen1 validate a");
        if (!getMboValue().getMbo().isNull("SEEFLGCALCPER")) {
            //System.out.println("========= seetbmen1 validate b");
            if (getMboValue().getMbo().getBoolean("SEEFLGCALCPER")) {
                //System.out.println("========= seetbmen1 validate c");
                int tComCurID = getMboValue().getMbo().getInt("TCOMCURID");
                int deAulaID = getMboValue().getMbo().getInt("DEAULAID");
                String personID = getMboValue().getMbo().getString("PERSONID");
                //System.out.println("========= v1 " + tComCurID);
                //System.out.println("========= v2 " + deAulaID);
                //System.out.println("========= v3 " + personID);


                //System.out.println("========= seetbmen1 mboset init");
                MboSet notas;
                notas = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("SEETBDEMENSAVA", getMboValue().getMbo().getUserInfo());

                notas.setWhere("SEEPRESENCA = 0 and TCOMCURID = " + tComCurID + " and DEAULAID = " + deAulaID + " and SEETBMENSID = 641 and PERSONID = '" + personID + "'");
                //System.out.println("========= seetbmen1 mboset before reset");
                notas.reset();
                //System.out.println("========= seetbmen1 mboset after reset");

                double nota = 0;

                for (int i = 0; i < notas.count(); i++) {
                    nota += notas.getMbo(i).getDouble("SEEVALOR");
                }


                //System.out.println("========= seetbmen1 notas count() " + notas.count());
                //System.out.println("========= seetbmen1 nota " + nota);

                if (notas.count() > 0) {
                    getMboValue().setValue(nota / notas.count());
                }

                //System.out.println("========= seetbmen1 end");

            }
        }
    }
}
