package br.inf.id2.seedf.field;

import psdi.mbo.*;


import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class SeeTbGraHorSeeComCur extends MboValueAdapter {

    public SeeTbGraHorSeeComCur(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        System.out.println("---> inicio SeeTbGraHorSeeComCur");

        //System.out.println("a------> getMboValue().getMbo().getString(tcomcurid) = " + getMboValue().getMbo().getString("tcomcurid"));
        //System.out.println("a------> getMboValue().getMbo().getString(turmaid) = " + getMboValue().getMbo().getString("turmaid"));
        //System.out.println("a------> getMboValue().getMbo().getString(assetnum) = " + getMboValue().getMbo().getString("assetnum"));
        //System.out.println("a------> getMboValue().getMbo().getString(commatcurid) = " + getMboValue().getMbo().getString("commatcurid"));


        //System.out.println("---> owner " + getMboValue().getMbo().getOwner().getName());

// ******  ALTERADO POR DYOGO E PATRICK PARA SOLUCIONAR PROBLEMA DE RELACIONAMENTO DIA 20/04 - PEDIDO DO LEONARDO

        /*        int pos1, pos2, pos3;

        if (getMboValue().getMbo().getOwner().getMboSet("SEERL01TOFEIE").count() == 0) {
        throw new MXApplicationException("company", "AuxenciaSEERL01TOFEIE");
        }

        pos1 = getMboValue().getMbo().getOwner().getMboSet("SEERL01TOFEIE").getCurrentPosition();

        if (getMboValue().getMbo().getOwner().getMboSet("SEERL01TOFEIE").getMbo(pos1).getMboSet("SEERL01TCOMCUR").count() == 0) {
        throw new MXApplicationException("company", "AuxenciaProfessorRegente");
        }

        pos2 = getMboValue().getMbo().getOwner().getMboSet("SEERL01TOFEIE").getMbo(pos1).getMboSet("SEERL01TCOMCUR").getCurrentPosition();

        //pos3 = getMboValue().getMbo().getOwner().getMboSet("SEERL01TCOMCUR").getMbo(pos1).getMboSet("SEERL01COMMATCUR").getMbo(pos2).getMboSet("SEERL01COMCUR").getCurrentPosition();

        System.out.println("---pos 1 " + pos1);
        System.out.println("---pos 2 " + pos2);
        ////System.out.println("---pos 3 " + pos3);

        //        System.out.println("---> owner ..tcomcurid " + getMboValue().getMbo().getOwner().getMboSet("SEERL01TOFEIE").getMbo(pos1).getMboSet("SEERL01TCOMCUR").getMbo(pos2).getString("tcomcurid"));
        //        System.out.println("---> owner ..commatcurid " + getMboValue().getMbo().getOwner().getMboSet("SEERL01TOFEIE").getMbo(pos1).getMboSet("SEERL01TCOMCUR").getMbo(pos2).getString("commatcurid"));

        //        getMboValue().getMbo().setValue("tcomcurid", getMboValue().getMbo().getOwner().getMboSet("SEERL01TOFEIE").getMbo(pos1).getMboSet("SEERL01TCOMCUR").getMbo(pos2).getString("tcomcurid"), NOVALIDATION_AND_NOACTION);
        //
        //        getMboValue().getMbo().setValue("commatcurid", getMboValue().getMbo().getOwner().getMboSet("SEERL01TOFEIE").getMbo(pos1).getMboSet("SEERL01TCOMCUR").getMbo(pos2).getString("commatcurid"), NOVALIDATION_AND_NOACTION);


        System.out.println("b------> getMboValue().getMbo().getString(tcomcurid) = " + getMboValue().getMbo().getString("tcomcurid"));
        System.out.println("b------> getMboValue().getMbo().getString(turmaid) = " + getMboValue().getMbo().getString("turmaid"));
        System.out.println("b------> getMboValue().getMbo().getString(assetnum) = " + getMboValue().getMbo().getString("assetnum"));
        System.out.println("b------> getMboValue().getMbo().getString(commatcurid) = " + getMboValue().getMbo().getString("commatcurid"));

        //System.out.println("----- getCompleteWhere = " + getMboValue().getMbo().getMboSet("SEERL01TPROF").getCompleteWhere());

        if (getMboValue().getMbo().getString("tcomcurid").equals("")) {
        throw new MXApplicationException("company", "Auxenciatcomcurid");
        }
        if (getMboValue().getMbo().getString("turmaid").equals("")) {
        throw new MXApplicationException("company", "Auxenciaturmaid");
        }
        if (getMboValue().getMbo().getString("assetnum").equals("")) {
        throw new MXApplicationException("company", "Auxenciaassetnum");
        }
        if (getMboValue().getMbo().getString("commatcurid").equals("")) {
        throw new MXApplicationException("company", "Auxenciacommatcurid");
        }

        if (getMboValue().getMbo().getMboSet("SEERL01TPROF").count() == 0) {
        throw new MXApplicationException("company", "AuxenciaSEERL01TPROF");
        }*/

        if (getMboValue().getMbo().getMboSet("SEERL01TPROF").count() > 0) {
            System.out.println("---> personID = " + getMboValue().getMbo().getMboSet("SEERL01TPROF").getMbo(0).getString("PERSONID"));
            getMboValue().getMbo().setValue("PERSONID", getMboValue().getMbo().getMboSet("SEERL01TPROF").getMbo(0).getString("PERSONID"), NOVALIDATION_AND_NOACTION);
        }


        //System.out.println("---> FIM SeeTbGraHorSeeComCur");

    }
}
