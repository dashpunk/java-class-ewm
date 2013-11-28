package br.inf.id2.seedf.bean;

import br.inf.id2.common.util.Executa;
import br.inf.id2.common.util.Validar;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.BoundComponentInstance;

/**
 *
 * @author Ricardo S Gomes
 *
 * Aplicado em: seeap01de
 * Objeto principal: SEETBTCOMCUR
 * Relacionamento SEERL01TFECPER: SEETBTFECPER
 * Relacionamento SEERL01FECPER: SEETBFECPER
 *
 */
//TODO: tratar os Exception's
public class SeeAp01De extends psdi.webclient.system.beans.AppBean {

    /**
     *
     */
    public SeeAp01De() {
    }

    @Override
    public synchronized void listenerChangedEvent(DataBean speaker) {
        try {
            //System.out.println("------------- dataChangedEvent " + speaker.getUniqueIdName());
            if (speaker.getUniqueIdName().equalsIgnoreCase("TFECPERID")) {
                validarExclusao();
            }

            validarExclusao2();
            bloquear();
        } catch (MXException ex) {
            //System.out.println("----- e1 " + ex.getMessage());
            Logger.getLogger(SeeAp01De.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //System.out.println("----- e2 " + ex.getMessage());
            Logger.getLogger(SeeAp01De.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
        }
        super.listenerChangedEvent(speaker);

    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        //bloquear();
        calculaNotaFalta();
        validarValores();
        inserirEstudantes();


        for (int i = 0; i < getMbo().getMboSet("SEERL01DEAULA").count(); i++) {

            for (int j = 0; j < getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01MATRICULA").count(); j++) {

                if (getMbo().getMboSet("SEERL01DEAULA").getMbo(i).isNew()) {
                    MboRemote aMbo = getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEPRES").add();

                    //System.out.println("---> TCOMCURID " + getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getLong("TCOMCURID"));
                    //System.out.println("---> DEAULAID " + getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getLong("DEAULAID"));
                    //System.out.println("---> personid " + getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01MATRICULA").getMbo(j).getString("PERSONID"));

                    aMbo.setValue("PERSONID", getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01MATRICULA").getMbo(j).getString("PERSONID"), MboConstants.NOVALIDATION_AND_NOACTION);

                    aMbo.setValue("TCOMCURID", getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getLong("TCOMCURID"), MboConstants.NOVALIDATION_AND_NOACTION);

                    aMbo.setValue("DEAULAID", getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getLong("DEAULAID"), MboConstants.NOVALIDATION_AND_NOACTION);

                }
            }

        }

        return super.SAVE();
    }

    private void validarValores() throws MXException, RemoteException {
        try {
            //System.out.println("------ validate SEERL01TFECPER count " + getMbo().getMboSet("SEERL01TFECPER").count());
            for (int i = 0; i < getMbo().getMboSet("SEERL01TFECPER").count(); i++) {
                //System.out.println("------ i " + i);

                //System.out.println("----- count j " + getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").count());
                for (int j = 0; j < getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").count(); j++) {
                    //System.out.println("----- count j " + j);
                    //System.out.println("------ v1 " + getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").getMbo(j).getDouble("SEETBMENS2"));
                    //System.out.println("------ v2 " + getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getDouble("SEETBMENS"));
                    if (getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").getMbo(j).getDouble("SEETBMENS2")
                            > getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getDouble("SEETBMENS")) {
                        //System.out.println("------ valor inválido ");
                        throw new MXApplicationException("SEERL01FECPER", "SEETBMENS2maiorQueSEETBMENS");
                    }
                }

            }
        } catch (Exception e) {
        }

    }

    private void validarExclusao() throws MXException, RemoteException {
        try {
            //System.out.println("------ validate SEERL01TFECPER count " + getMbo().getMboSet("SEERL01TFECPER").count());
            for (int i = 0; i < getMbo().getMboSet("SEERL01TFECPER").count(); i++) {
                //System.out.println("------ i " + i);

                //System.out.println("----- init tobedeleted");

                if (getMbo().getMboSet("SEERL01TFECPER").getMbo(i).toBeDeleted()) {
                    //System.out.println("---- tobeDeleted() ");
                    if (getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").count() > 0) {
                        //System.out.println("--- count() > 0");
                        getMbo().getMboSet("SEERL01TFECPER").getMbo(i).setDeleted(false);
                        new MXApplicationException("SEERL01TFECPER", "ItensEmSEERL01FECPER");
                    }
                }


            }
        } catch (Exception e) {
        }
    }

    private void validarExclusao2() throws MXException, RemoteException {
        //System.out.println("------ validate deaula count " + getMbo().getMboSet("SEERL01DEAULA").count());
        for (int i = 0; i < getMbo().getMboSet("SEERL01DEAULA").count(); i++) {

            //System.out.println("------ i " + i);

            String periodo = getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getString("SEECAL");

            boolean encontrado = Validar.valorEncontrado(getMbo().getMboSet("SEERL01TFECPER"), "SEECALESC", periodo);

            if (encontrado) {
                if (getMbo().getMboSet("SEERL01DEAULA").getMbo(i).toBeDeleted()) {
                    getMbo().getMboSet("SEERL01DEAULA").getMbo(i).setDeleted(!encontrado);

                }
                Executa.naoPermiteDeletar(getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEPRES"));


                Executa.naoPermiteDeletar(getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEDESEDU"));
                Executa.naoPermiteDeletar(getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEANOT"));
                Executa.naoPermiteDeletar(getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEAVAAP"));

                for (int j = 0; j < getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEANOT").count(); j++) {
                    Executa.naoPermiteDeletar(getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEANOT").getMbo(j).getMboSet("SEERL01DEESTREL"));
                }

                for (int j = 0; j < getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEAVAAP").count(); j++) {
                    Executa.naoPermiteDeletar(getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEAVAAP").getMbo(j).getMboSet("SEERL01DEESTAVA"));
                    for (int k = 0; k < getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEAVAAP").getMbo(j).getMboSet("SEERL01DEESTAVA").count(); k++) {
                        Executa.naoPermiteDeletar(getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEAVAAP").getMbo(j).getMboSet("SEERL01DEESTAVA").getMbo(k).getMboSet("SEERL01DEMENSAVA"));
                    }
                }
            }

        }
    }

    private void inserirEstudantes() throws MXException, RemoteException {

        //System.out.println("------ validate SEERL01TFECPER count " + getMbo().getMboSet("SEERL01TFECPER").count());
        for (int i = 0; i < getMbo().getMboSet("SEERL01TFECPER").count(); i++) {
            //System.out.println("------ i " + i);
            if (getMbo().getMboSet("SEERL01TFECPER").getMbo(i).isNew()) {
                //System.out.println("------- SEERL01MATRICULA count " + getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01MATRICULA").count());
                for (int j = 0; j < getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01MATRICULA").count(); j++) {
                    //System.out.println("----- j " + j);
                    if (!Validar.valorEncontrado(getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER"), "PERSONID", getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01MATRICULA").getMbo(j).getString("PERSONID"))) {
                        //System.out.println("------ before add");
                        MboRemote aMbo = getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").add();
                        //System.out.println("------ after add");
                        aMbo.setValue("PERSONID", getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01MATRICULA").getMbo(j).getString("PERSONID"), MboConstants.NOVALIDATION_AND_NOACTION);
                        aMbo.setValue("MATRICULAID", getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01MATRICULA").getMbo(j).getString("MATRICULAID"), MboConstants.NOVALIDATION_AND_NOACTION);
                        //System.out.println("------ a");
                        aMbo.setValue("TFECPERID", getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getString("TFECPERID"), MboConstants.NOVALIDATION_AND_NOACTION);
                        //System.out.println("------ b");
                        aMbo.setValue("CALESCID", getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getString("CALESCID"), MboConstants.NOVALIDATION_AND_NOACTION);
                        //System.out.println("------ c");
                        aMbo.setValue("SEECALESC", getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getString("SEECALESC"), MboConstants.NOVALIDATION_AND_NOACTION);
                        //System.out.println("------ d");
                        aMbo.setValue("COMCURMODID", getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getString("COMCURMODID"), MboConstants.NOVALIDATION_AND_NOACTION);
                        //System.out.println("------ e");
                        aMbo.setValue("SEETBMENSID1", 461, MboConstants.NOVALIDATION_AND_NOACTION);
                        //System.out.println("------ f");
                        aMbo.setValue("SEETBUNIMEDID1", 322, MboConstants.NOVALIDATION_AND_NOACTION);
                        //System.out.println("------ g");
                        aMbo.setValue("SEETBMENSID2", 501, MboConstants.NOVALIDATION_AND_NOACTION);
                        //System.out.println("------ h");
                        aMbo.setValue("SEETBUNIMEDID1", 7, MboConstants.NOVALIDATION_AND_NOACTION);
                        //System.out.println("------ i");
                    }

                }


            }

        }

    }

    private void bloquear() throws MXException, RemoteException {
        try {
            int i = getMbo().getMboSet("SEERL01DEAULA").getCurrentPosition();
            //System.out.println("------- bloq current " + i);
            if (i == -1) {
                i = 0;
            }

            String periodo = getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getString("SEECAL");
            //System.out.println("-------- periodo " + periodo);
            boolean apenasLeitura = Validar.valorEncontrado(getMbo().getMboSet("SEERL01TFECPER"), "SEECALESC", periodo);

            getMbo().getMboSet("SEERL01DEAULA").getMbo(i).setFlag(MboConstants.READONLY, apenasLeitura);
            getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEPRES").setFlag(MboConstants.READONLY, apenasLeitura);
            getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEDESEDU").setFlag(MboConstants.READONLY, apenasLeitura);
            getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEANOT").setFlag(MboConstants.READONLY, apenasLeitura);

            if (getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEANOT").count() > 0) {
                getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEANOT").getMbo().getMboSet("SEERL01DEESTREL").setFlag(MboConstants.READONLY, apenasLeitura);
            }
            getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEAVAAP").setFlag(MboConstants.READONLY, apenasLeitura);

            if (getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEAVAAP").count() > 0) {
                getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEAVAAP").getMbo().getMboSet("SEERL01DEESTAVA").setFlag(MboConstants.READONLY, apenasLeitura);

                getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEAVAAP").getMbo().getMboSet("SEERL01DEESTAVA").getMbo().getMboSet("SEERL01DEMENSAVA").setFlag(MboConstants.READONLY, apenasLeitura);
            }
        } catch (Exception e) {
            //System.out.println("----- b e " + e.getMessage());
        }
    }

    private void calculoFalta() throws MXException, RemoteException {
        for (int i = 0; i < getMbo().getMboSet("SEERL01DEAULA").count(); i++) {

            int numeroFaltas = Executa.contaRegistros(getMbo().getMboSet("SEERL01DEAULA").getMbo(i).getMboSet("SEERL01DEPRES"), "SEEPRESENCA", false);

        }

    }

    @Override
    public void bindComponent(BoundComponentInstance boundComponent) {
        super.bindComponent(boundComponent);
        try {
            if (boundComponent.getProperty("dataattribute").equalsIgnoreCase("seerl01turma.SEETIPCLA")) {
                bloquear();
            }
            bloquear();
        } catch (MXException ex) {
            Logger.getLogger(SeeAp01De.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(SeeAp01De.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
        }
    }

    private void calculaNotaFalta() {
        try {
            //System.out.println("========= seetbmen1&2 count() " + getMbo().getMboSet("SEERL01TFECPER").count());
            for (int i = 0; i < getMbo().getMboSet("SEERL01TFECPER").count(); i++) {
                //System.out.println("========= seetbmen1&2 i " + i);
                if (!getMbo().getMboSet("SEERL01TFECPER").getMbo(i).isNull("SEEFLGCALCPER")) {
                    //System.out.println("========= seetbmen1 validate b");
                    if (getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getBoolean("SEEFLGCALCPER")) {
                        //System.out.println("========= seetbmen1 validate b 2 count " + getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").count());
                        int tComCurID = getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getInt("TCOMCURID");
                        for (int j = 0; j < getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").count(); j++) {
                            //System.out.println("========= seetbmen1&2 j " + j);
                            //int deAulaID = getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").getMbo(j).getInt("DEAULAID");
                            //System.out.println("========= seetbmen1 validate c");
                            String personID = getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").getMbo(j).getString("PERSONID");
                            //System.out.println("========= v1 " + tComCurID);
                            ////System.out.println("========= v2 " + deAulaID);
                            //System.out.println("========= v3 " + personID);
                            //System.out.println("========= seetbmen1 mboset init");

                            //retirar isso
                            getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").getMbo(j).setValue("SEETBMENS1", 0);
                            /*
                             * amanhã apos ver algumas coisas com o marcelo
                             * 
                            MboSet notas;
                            notas = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("SEETBDEMENSAVA", sessionContext.getUserInfo());
                            //notas.setWhere("SEEPRESENCA = 0 and TCOMCURID = " + tComCurID + " and DEAULAID = " + deAulaID + " and SEETBMENSID = 641 and PERSONID = '" + personID + "'");
                            notas.setWhere("SEEPRESENCA = 0 and TCOMCURID = " + tComCurID + " and SEETBMENSID = 641 and PERSONID = '" + personID + "'");
                            //System.out.println("========= seetbmen1 mboset before reset");
                            notas.reset();
                            //System.out.println("========= seetbmen1 mboset after reset");
                            double nota = 0;
                            for (int k = 0; k < notas.count(); k++) {
                            nota += notas.getMbo(k).getDouble("SEEVALOR");
                            }
                            //System.out.println("========= seetbmen1 notas count() " + notas.count());
                            //System.out.println("========= seetbmen1 nota " + nota);
                            if (notas.count() > 0) {
                            getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").getMbo(j).setValue("SEETBMENS1", nota / notas.count());
                            } else {
                            getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").getMbo(j).setValueNull("SEETBMENS1");
                            }
                            //System.out.println("========= seetbmen1 end");
                             *
                             */

                            //System.out.println("========= seetbmen2 validate b");
                            //System.out.println("========= seetbmen2 mboset init");
                            MboSet faltas;
                            faltas = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("SEETBDEPRES", sessionContext.getUserInfo());
                            ///faltas.setWhere("SEEPRESENCA = 0 and TCOMCURID = " + tComCurID + " and DEAULAID = " + deAulaID + " and PERSONID = '" + personID + "'");
                            faltas.setWhere("SEEPRESENCA = 0 and TCOMCURID = " + tComCurID + " and PERSONID = '" + personID + "'");
                            //System.out.println("========= seetbmen2 mboset before reset");
                            faltas.reset();
                            //System.out.println("========= seetbmen2 mboset after reset");
                            getMbo().getMboSet("SEERL01TFECPER").getMbo(i).getMboSet("SEERL01FECPER").getMbo(j).setValue("SEETBMENS2", faltas.count());
                            //System.out.println("========= seetbmen2 end");
                        }
                    }
                }
            }
        } catch (MXException ex) {
            //System.out.println("====== ex1 " + ex.getMessage());
            Logger.getLogger(SeeAp01De.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //System.out.println("====== ex2 " + ex.getMessage());
            Logger.getLogger(SeeAp01De.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            //System.out.println("======= ex 3 " + e.getMessage());
        }
    }
}
