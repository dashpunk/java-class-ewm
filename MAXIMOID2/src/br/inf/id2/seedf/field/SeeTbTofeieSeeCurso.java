package br.inf.id2.seedf.field;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class SeeTbTofeieSeeCurso extends MboValueAdapter {

    public SeeTbTofeieSeeCurso(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void action() throws MXException, RemoteException {

        //System.out.println("a---> assetnum   = " + getMboValue().getMbo().getString("assetnum"));
        //System.out.println("a---> seecursoid = " + getMboValue().getMbo().getString("seecursoid"));
        //System.out.println("a---> TURMAID    = " + getMboValue().getMbo().getString("TURMAID"));



        //System.out.println("---> action SeeTbGraHorSeeComCur");
        String curso = getMboValue().getMbo().getString("SEECURSO");
        //System.out.println("---> 	SEETBTOFEIE.SEECURSO = " + curso);
        //System.out.println("---> TAMANHO DO SEERL01TCOMCUR = " + getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").count());
        if (curso != null && !curso.trim().equals("")) {
            //System.out.println("################ Foi alterado");
            int iQtdAtual = getMboValue().getMbo().getMboSet("SEERL01COMMATCUR").count(); //ESSE
            if (iQtdAtual != 0) {
                MboRemote mbo = getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").add();
                //System.out.println("############ MBO Criado : " + mbo);
                //mbo.setValue("xxx", "'");
                //mbo.setValue("xxx", "'");
                //mbo.setValue("xxx", "'");
            }
            //System.out.println("---> COMCUR (Atual) = " + getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").count());
            //System.out.println("---> COMMATCUR (Todos) = " + getMboValue().getMbo().getMboSet("SEERL01COMMATCUR").count());

        }
        super.action();
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        //System.out.println("V---> assetnum   = " + getMboValue().getMbo().getString("assetnum"));
        //System.out.println("V---> seecursoid = " + getMboValue().getMbo().getString("seecursoid"));
        //System.out.println("V---> TURMAID    = " + getMboValue().getMbo().getString("TURMAID"));
        String curso = getMboValue().getMbo().getString("SEECURSO");
        //System.out.println("V---> SEETBTOFEIE.SEECURSO = " + curso);

        ////System.out.println("---> inicio SeeTbGraHorSeeComCur");

        ////System.out.println("----------- owner " + getMboValue().getMbo().getOwner().getName());
        ////System.out.println("----------- tofeieid " + getMboValue().getMbo().getString("tofeieid"));
        ////System.out.println("----------- turmaid " + getMboValue().getMbo().getString("turmaid"));

        ////System.out.println("#################### Tamanho de SEERL01COMMATCUR: " + getMboValue().getMbo().getMboSet("SEERL01COMMATCUR").count());
        ////System.out.println("################# Relacionamento SEETBTCOMCUR" + getMboValue().getMbo().getMboSet("SEERL01TCOMCUR"));
        //SEETBTCOMCUR
        //SEECOMCUR
        //COMMATCURID

        /*//System.out.println("---- total linhas SEERL01TCOMCUR " + getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").count());

        //System.out.println("---- SEERL01TCOMCUR.commatcurid " + getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").getMbo(0).getString("commatcurid"));

        //System.out.println("---- total linhas SEERL01TCOMCUR.SEERL01COMMATCUR " + getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").getMbo(0).getMboSet("SEERL01COMMATCUR").count());

        String seecomcur = getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").getMbo(0).getMboSet("SEERL01COMMATCUR").getMbo(0).getString("SEECOMCUR");
        String commatcurid = getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").getMbo(0).getMboSet("SEERL01COMMATCUR").getMbo(0).getString("COMMATCURID");

        //System.out.println("---> seecomcur   = " + seecomcur);
        //System.out.println("---> commatcurid = " + commatcurid);

        getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").getMbo(0).setValue("SEECOMCUR", seecomcur);
        getMboValue().getMbo().getMboSet("SEERL01TCOMCUR").getMbo(0).setValue("COMMATCURID", commatcurid);

        //System.out.println("---> FIM SeeTbGraHorSeeComCur");*/
    }
}
