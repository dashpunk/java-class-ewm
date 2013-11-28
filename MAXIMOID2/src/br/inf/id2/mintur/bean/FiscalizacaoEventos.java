package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.beans.contpurch.ContPurchAppBean;
import br.inf.id2.common.util.Executa;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.webclient.system.beans.DataBean;

/**
 * @author Ricardo S Gomes
 */
public class FiscalizacaoEventos extends ContPurchAppBean {

    public FiscalizacaoEventos() {
    }

    @Override
    public int INSERT() throws MXException, RemoteException {

        System.out.println("*** INSERT 1120");

        MboSet coord;
        coord = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("GROUPUSER", sessionContext.getUserInfo());
        coord.setWhere("groupname = 'MTURGRP07.2.1'");
        coord.reset();



        System.out.println("--- INSERT count " + coord.count());
        System.out.println("--- INSERT usuario da sessão" + sessionContext.getUserInfo().getUserName());

        boolean existe = false;

        MboRemote mbo;

        for (int i = 0; (mbo = coord.getMbo(i)) != null; i++) {
            System.out.println("--- nome no relacionamento " + mbo.getString("USERID"));
            if (mbo.getString("USERID").equalsIgnoreCase(sessionContext.getUserInfo().getUserName())) {
                System.out.println("*** IF UserName- " + sessionContext.getUserInfo().getUserName() + " mbo.USERID- " + mbo.getString("USERID"));
                existe = true;
                break;
            }
        }
        if (!existe) {
            throw new MXApplicationException("id2message", "noINSERT");
        }
        return super.INSERT();

//        System.out.println("--- INSERT");
//        System.out.println("--- INSERT count " + getMbo().getMboSet("MTURGRP0721").count());
//        System.out.println("--- INSERT usuario " + getMbo().getUserInfo().getUserName());
//        boolean existe = false;
//        MboRemote mbo;
//        for (int i = 0; ((mbo = getMbo().getMboSet("MTURGRP0721").getMbo(i)) != null); i++) {
//            System.out.println("--- nome no relacionamento "+mbo.getString("USERID"));
//
//            if (mbo.getString("USERID").equalsIgnoreCase(getMbo().getUserInfo().getUserName())) {
//                existe = true;
//                break;
//            }
//
//        }
//
//
//        if (!existe) {
//            throw new MXApplicationException("id2message", "noINSERT");
//        }
//        return super.INSERT();
    }

    /**
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {
        System.out.println("*** FiscalizacaoEventos ***");


        //controle do tipo ação MTTIPACAO
        if (getMbo().isNull("MTTIPACAO")) {
            new MXApplicationException("fisvis", "MTTIPACAOIsNull");
        } else {
            System.out.println(">>>> tipo " + getMbo().getString("MTTIPACAO"));
            /*
             * caso não seja fiscalização
             *
             * percorrer os seguintes relacionamentos: MXRL01VW02FISVIS
             * MXRL01VW02FISVIS.MTVW02EVOSER MXRL01VW02FISVIS.MXRL01FISVISPLA
             * MXRL01VW02FISVIS.MXRL01FISVISAVAL
             * MXRL01VW02FISVIS.MXRL01FISVISPAR
             * MXRL01VW02FISVIS.MXRL01FISVISETAP
             * MXRL01VW02FISVIS.MXRL01FISVISMETA
             */
            if (!getMbo().getString("MTTIPACAO").substring(0, 4).equalsIgnoreCase("FISC")) {
                MboRemote mboFisVis;
                int totalLinhasFisVis = getMbo().getMboSet("MXRL01VW02FISVIS").count();
                System.out.println(">>> apagar fiscalizacao " + totalLinhasFisVis);
                for (int i = totalLinhasFisVis - 1; ((mboFisVis = getMbo().getMboSet("MXRL01VW02FISVIS").getMbo(i)) != null); i--) {
                    System.out.println(">>>> i " + i);
                    mboFisVis.getMboSet("MTVW02EVOSER").deleteAll();
                    mboFisVis.getMboSet("MXRL01FISVISPLA").deleteAll();
                    mboFisVis.getMboSet("MXRL01FISVISAVAL").deleteAll();
                    mboFisVis.getMboSet("MXRL01FISVISPAR").deleteAll();
//                    mboFisVis.getMboSet("MXRL01FISVISETAP").deleteAll();
                    mboFisVis.getMboSet("MXRL01FISVISMETA").deleteAll();
                    mboFisVis.delete();
                }
            } else {
                getMbo().getMboSet("MXRL01VW02EVOSER");
            }
        }

        //MXRL01VW02FISVIS
        MboRemote mbo;

        System.out.println("--- total de itens relacinamento C " + getMbo().getMboSet("MXRL01VW02FISVIS").count());
        for (int i = 0; ((mbo = getMbo().getMboSet("MXRL01VW02FISVIS").getMbo(i)) != null); i++) {
            System.out.println("---i + " + i);
            System.out.println("---personid = " + mbo.getString("PERSONID"));
            System.out.println("---count 1o relacionamento " + mbo.getMboSet("MTVW02EVOSER").count());
            //Item 01 ---Inicio---
            //MTTBFISCON.MTTOTITENS: a classe desse campo deve apresentar a somatória das colunas MTVW02EVOSER.MTVLRTOTITEM
            double soma = Executa.somaValor("MTVLRTOTITEM", mbo.getMboSet("MTVW02EVOSER"));
            System.out.println("*** soma " + soma);
            mbo.setValue("MTTOTITENS", soma, MboConstants.NOVALIDATION_AND_NOACTION);
            System.out.println("*** MTTBFISCON.MTTOTITENS " + mbo.getDouble("MTTOTITENS"));

//            double somaMedia = Executa.somaValor("MTPEREXEC", mbo.getMboSet("MTVW02EVOSER"));
//            System.out.println("*** somaMedia " + somaMedia);
//            int qtd = mbo.getMboSet("MTVW02EVOSER").count();
//            System.out.println("*** qtd " + qtd);
//            mbo.setValue("MTTOTPEREXE", somaMedia / qtd, MboConstants.NOVALIDATION_AND_NOACTION);
//            System.out.println("*** somaMedia/qtd " + somaMedia / qtd);

            double soma2 = Executa.somaValor("MTVLREXEC", mbo.getMboSet("MTVW02EVOSER"));
            System.out.println("*** soma2 " + soma2);
            mbo.setValue("MTTOTVLREXE", soma2, MboConstants.NOVALIDATION_AND_NOACTION);
            System.out.println("*** MTTBFISCON.MTTOTVLREXE " + mbo.getDouble("MTTOTVLREXE"));
            if (soma > 0 && soma2 > 0) {
                mbo.setValue("MTTOTPEREXE", (soma2 / soma) * 100, MboConstants.NOVALIDATION_AND_NOACTION);
            } else {
                mbo.setValue("MTTOTPEREXE", 0, MboConstants.NOVALIDATION_AND_NOACTION);
            }
        }
        System.out.println("---- fim da classe");
        return super.SAVE();
    }

    @Override
    public synchronized void fireDataChangedEvent() {
        try {
            super.fireDataChangedEvent();
            System.out.println(">>> fire");
            System.out.println(">>>> tipo ação " + getMbo().getString("MTTIPACAO"));
            if (!getMbo().getString("MTTIPACAO").substring(0, 4).equalsIgnoreCase("FISC")) {
                adicionaMonitoramento();
            }
            if (!getMbo().getString("UPNUMCON").equals("")) {
                copiaImagens();
            }
            System.out.println(">>> fire FIM");
        } catch (MXException ex) {
            Logger.getLogger(FiscalizacaoEventos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(FiscalizacaoEventos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void copiaImagens() {

        try {
            System.out.println(">>> copiaImagens 1052");
            if (getMbo().isNull("NUMPROFIS") && !getMbo().isNull("UPNUMCON")) {
                Connection connection = null;
                Class.forName("org.postgresql.Driver");
                String user = "fiscon_id2_usr";
                String pass = "sys@f1sc0n_1d2";
                connection = DriverManager.getConnection("jdbc:postgresql://192.168.1.40:5432/mtur", user, pass);

                ResultSet rs;
                String sql;
                PreparedStatement instrucaoSql;
                DataBean sectionbean;
                Statement stmt = connection.createStatement();
                String convenio = getMbo().getString("UPNUMCON").substring(0, 11);
//            sql = "select b.nu_proposta,b.numero_convenio, b.co_cnpj, a.im_foto, a.tx_observacao, a.dt_registro  from fiscon.vw_foto as a, fiscon.vw_proposta as b "
//                    + "WHERE a.nu_proposta = b.nu_proposta "
//                    + " and b.numero_convenio = \'755472/2011\'";

                sql = "select nu_proposta from fiscon.vw_qtd_foto "
                        + "where numero_convenio = \'" + convenio + "\'";
                System.out.println(sql);


                System.out.println(">>> convenio " + convenio);
                rs = stmt.executeQuery(sql);
                System.out.println(">>> copiaImagens exec");

//            MboRemote imagem;
//            MboSet imagens;
//            imagens = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("mttbfisfoto", sessionContext.getUserInfo());
//            imagens.setWhere("nuconvenio = '" + convenio + "'");
//            imagens.reset();
//            System.out.println(">>> imagens count " + imagens.count());
//            imagens.deleteAll();
//            imagens.save();
//            System.out.println(">>> imagens count " + imagens.count());
                int contador = 0;
                System.out.println(">>> " + contador++);
                if (rs.next()) {
                    System.out.println(">>> " + contador++);
                    System.out.println("--->>> " + rs.getInt("nu_proposta"));
                    getMbo().setValue("NUMPROFIS", rs.getInt("nu_proposta"), MboConstants.NOACCESSCHECK);

//                System.out.println(">>> copiaImagens while");
//
//
//                imagem = imagens.add();
//                System.out.println(">>> copiaImagens vals");
//                imagem.setValue("DESCRIPTION", (rs.getString("tx_observacao") == null ? "" : rs.getString("tx_observacao">>>);
//                imagem.setValue("NUPROPOSTA", (rs.getString("nu_proposta") == null ? "" : rs.getString("nu_proposta">>>);
//                imagem.setValue("NUCONVENIO", (rs.getString("numero_convenio") == null ? "" : rs.getString("numero_convenio">>>);
//                imagem.setValue("DTREGISTRO", (rs.getDate("dt_registro") == null ? new java.util.Date() : rs.getDate("dt_registro">>>);
//                if (rs.getBytes("im_foto") != null) {
//                    imagem.setValue("DTREGISTRO", rs.getBytes("im_foto">>>;
//                }
//                System.out.println(">>> copiaImagens vals fim");

                }

//            System.out.println(">>> save b");
//            imagens.save();
//            System.out.println(">>> save a");
            } else {
                System.out.println(">>> valor ja existe " + getMbo().getInt("NUMPROFIS"));
            }

        } catch (MXException ex) {
            ex.getStackTrace();
            Logger.getLogger(FiscalizacaoEventos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            ex.getStackTrace();
            Logger.getLogger(FiscalizacaoEventos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            ex.getStackTrace();
            Logger.getLogger(FiscalizacaoEventos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            ex.getStackTrace();
            Logger.getLogger(FiscalizacaoEventos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized boolean fetchRecordData() throws MXException, RemoteException {
        try {
            copiaImagens();
        } catch (Exception e) {
        }
        return super.fetchRecordData();
    }

    private void adicionaMonitoramento() {
        try {

            System.out.println(">>>> adicionaMonitoramento ");
            System.out.println(">>>> adicionaMonitoramento count " + getMbo().getMboSet("MXRL01VW02EVOSER").count());
            if (getMbo().getMboSet("MXRL01VW02EVOSER").count() == 0) {
                System.out.println(">>> itens count  " + getMbo().getMboSet("MXRL01VW02ITEFIS").count());
                MboRemote mboItem;
                MboRemote mboItemDestino;
                for (int i = 0; ((mboItem = app.getDataBean("MAINRECORD").getMbo().getMboSet("MXRL01VW02ITEFIS").getMbo(i)) != null); i++) {
                    System.out.println(">>> itens i " + i);
                    mboItemDestino = getMbo().getMboSet("MXRL01VW02EVOSER").add();
                    mboItemDestino.setValue("MTTBFISCONID", getMbo().getInt("MTTBFISCONID"), MboConstants.NOACCESSCHECK);
                    mboItemDestino.setValue("mttbitefisid", mboItem.getInt("mttbitefisid"), MboConstants.NOACCESSCHECK);
                    mboItemDestino.setValue("mtcoditem", mboItem.getString("mtcoditem"), MboConstants.NOACCESSCHECK);
                    mboItemDestino.setValue("description", mboItem.getString("description"), MboConstants.NOACCESSCHECK);
                    mboItemDestino.setValue("mtitemcla", mboItem.getString("mtitemcla"), MboConstants.NOACCESSCHECK);
                    mboItemDestino.setValue("mtitemsub", mboItem.getString("mtitemsub"), MboConstants.NOACCESSCHECK);
                }
            } else {
                System.out.println(">>>> itens monitoramento encontrados");
            }
        } catch (MXException ex) {
            ex.getStackTrace();
        } catch (RemoteException ex) {
            ex.getStackTrace();
        }
    }
}
