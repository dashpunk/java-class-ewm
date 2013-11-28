package br.inf.id2.mapa.bean;

import br.inf.id2.common.util.Data;
import java.nio.ReadOnlyBufferException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;

public class EstabelecimentoEstrangeiro extends AppBean {

    private boolean emitirCircularSave = false;

    public int SAVE()
            throws MXException, RemoteException {
        int retorno = -1;
        String numeroControle = getMbo().getString("ID2NUMCONT");
        String nomeEmpresarial = getMbo().getString("ID2NOMEMP");
        String nomePais = getMbo().getString("ID2PAIS");

        if ((numeroControle.equals("")) || (nomeEmpresarial.equals("")) || (nomePais.equals(""))) {
            throw new MXApplicationException("estEstrangeiro", "camposObrigatorios");
        }

        int idRevisao = getMbo().getInt("ID2REV");

        if (!emitirCircularSave == true) {

            MboRemote mboC;

            for (int j = 0; (mboC = getMbo().getMboSet("ID2LOCPHONE").getMbo(j)) != null; j++) {
                mboC.setValueNull("ID2REV", 2L);
                mboC.setValueNull("MATBCIRCULARID", 2L);
            }

            for (int l = 0; (mboC = getMbo().getMboSet("MATBESTAREA").getMbo(l)) != null; l++) {
                mboC.setValueNull("ID2REV", 2L);
                mboC.setValueNull("MATBCIRCULARID", 2L);
            }

            for (int n = 0; (mboC = getMbo().getMboSet("DOCLINKS").getMbo(n))
                    != null; n++) {
                mboC.setValueNull("ID2REV", 2L);
                mboC.setValueNull("MATBCIRCULARID", 2L);
            }

            getMbo().setValueNull("ID2REV", 2L);

            emitirCircularSave = false;
        }

        MboSet mboa = (MboSet) getMbo().getMboSet("MATBCIRCULAR");
        MboSet mboy = (MboSet) getMbo().getMboSet("MATBESTAREA");
        MboSet mboz = (MboSet) getMbo().getMboSet("ID2LOCPHONE");

        for (int i = 0; mboa.getMbo(i) != null; i++) {
            if (mboa.getMbo(i).getString("TIPCIRCULAR").equals("")) {
                throw new MXApplicationException("estEstrangeiro", "camposObrigatorios");
            }
        }

        for (int i = 0; mboz.getMbo(i) != null; i++) {
            if ((mboz.getMbo(i).getString("ID2TYPE").equals("")) || (mboz.getMbo(i).getString("ID2PHONENUM01").equals(""))) {
                throw new MXApplicationException("estEstrangeiro", "camposObrigatoriosCont");
            }
        }

        for (int i = 0; mboa.getMbo(i) != null; i++) {
            mboa.getMbo().setFieldFlag("TIPCIRCULAR", 7L, true);
        }

        for (int i = 0; mboy.getMbo(i) != null; i++) {
            if ((mboy.getMbo(i).getString("ID2NOME").equals("")) || (mboy.getMbo(i).getString("ID2CAT").equals("")) || (mboy.getMbo(i).getString("ID2FINALIDADE").equals("")) || (mboy.getMbo(i).getString("ID2ESP").equals(""))) {
                throw new MXApplicationException("estEstrangeiro", "camposObrigatoriosArea");
            }
        }

        refreshTable();
        reloadTable();

        retorno = super.SAVE();
        return retorno;
    }

    public void emitirCircular() throws RemoteException, MXException {
        boolean paraValidar = false;

        MboRemote mboA;
        for (int i = 0; (mboA = getMbo().getMboSet("MATBCIRCULAR").getMbo(i)) != null; i++) {
            if (!mboA.getBoolean("ID2CHECKCIRC")) {
                paraValidar = true;
            }
        }

        if (paraValidar) {
            emitirCircularSave = true;

            String numeroControle = getMbo().getString("ID2NUMCONT");
            String nomeEmpresarial = getMbo().getString("ID2NOMEMP");
            String nomePais = getMbo().getString("ID2PAIS");
            Boolean temValidade = Boolean.valueOf(getMbo().getBoolean("ID2VALIDO"));
            String valorValidade = getMbo().getString("ID2DATVAL");

            if ((temValidade.booleanValue()) && (valorValidade.equals(""))) {
                throw new MXApplicationException("estEstrangeiro", "informeValidadee");
            }

            if ((numeroControle.equals("")) || (nomeEmpresarial.equals("")) || (nomePais.equals(""))) {
                throw new MXApplicationException("estEstrangeiro", "camposObrigatorios");
            }
            
            MboSet mbox = (MboSet) getMbo().getMboSet("MATBCIRCULAR");
            
            int id2rev_x = 0;
            
            for (int k = 0; mbox.getMbo(k) != null; k++) {
                System.out.println("wwwwww: FOR    " + id2rev_x);
                if (mbox.getMbo(k).getInt("ID2REV") > id2rev_x) {
                    id2rev_x = mbox.getMbo(k).getInt("ID2REV");
                System.out.println("wwwwww: IF    " + id2rev_x);
                }
            }
            
            //int idRevisao = getMbo().getInt("ID2REV");
            int idRevisao = id2rev_x;
            System.out.println("wwwwww: ATUAL   " + id2rev_x);


            MboSet mboy = (MboSet) getMbo().getMboSet("MATBESTAREA");
            MboSet mboz = (MboSet) getMbo().getMboSet("ID2LOCPHONE");

            if (mboy.isEmpty()) {
                throw new MXApplicationException("estEstrangeiro", "insiraAreaAtuacao");
            }

            for (int i = 0; mboz.getMbo(i) != null; i++) {
                if ((!mboz.getMbo(i).getString("ID2TYPE").equals("")) && (!mboz.getMbo(i).getString("ID2PHONENUM01").equals(""))) {
                    continue;
                }
                throw new MXApplicationException("estEstrangeiro", "camposObrigatoriosCont");
            }

            for (int i = 0; mboy.getMbo(i) != null; i++) {
                if ((!mboy.getMbo(i).getString("ID2NOME").equals("")) && (!mboy.getMbo(i).getString("ID2CAT").equals("")) && (!mboy.getMbo(i).getString("ID2FINALIDADE").equals("")) && (!mboy.getMbo(i).getString("ID2ESP").equals(""))) {
                    continue;
                }
                throw new MXApplicationException("estEstrangeiro", "camposObrigatoriosArea");
            }

            Boolean validouCircular = Boolean.valueOf(true);
            for (int i = 0; mbox.getMbo(i) != null; i++) {
                if ((mbox.getMbo(i).getString("TIPCIRCULAR").equals("")) || (mbox.getMbo(i).getString("SETOR").equals("")) || (mbox.getMbo(i).getString("ANO").equals("")) || (mbox.getMbo(i).getString("NUMCIRCULAR").equals(""))) {
                    throw new MXApplicationException("estEstrangeiro", "camposObrigatoriosCircular");
                }

                if ((mbox.getMbo(i).getBoolean("ID2CHECKCIRC")) || (mbox.getCurrentPosition() != i)) {
                    continue;
                }
                idRevisao++;
                MboRemote mbo;
                for (int m = 0; (mbo = getMbo().getMboSet("MATBCIRCULAR").getMbo(m)) != null; m++) {
                    if (!mbo.getBoolean("ID2CHECKCIRC")) {
                        mbo.setValue("ID2REV", idRevisao, 2L);
                    }
                }
                MboRemote mboC;
                for (int j = 0; (mboC = getMbo().getMboSet("ID2LOCPHONE").getMbo(j)) != null; j++) {
                    mboC.setValue("ID2REV", idRevisao, 2L);
                    mboC.setValue("MATBCIRCULARID", mbox.getMbo(i).getString("MATBCIRCULARID"), 2L);
                }

                for (int l = 0; (mboC = getMbo().getMboSet("MATBESTAREA").getMbo(l)) != null; l++) {
                    mboC.setValue("ID2REV", idRevisao, 2L);
                    mboC.setValue("MATBCIRCULARID", mbox.getMbo(i).getString("MATBCIRCULARID"), 2L);
                }

                for (int n = 0; (mboC = getMbo().getMboSet("DOCLINKS").getMbo(n)) != null; n++) {
                    mboC.setValue("ID2REV", idRevisao, 2L);
                    mboC.setValue("MATBCIRCULARID", mbox.getMbo(i).getString("MATBCIRCULARID"), 2L);
                }

                mbox.setValue("ID2CHECKCIRC", true);

                getMbo().setValue("ID2REV", idRevisao);

                MboSet mboSetOcorrencia = (MboSet) getMbo().getMboSet("MATBESTOCO");

                MboRemote mboOcorrencia = mboSetOcorrencia.add();

                mboOcorrencia.setValue("ID2DATOCO", new Date(), 2L);
                mboOcorrencia.setValue("NUMOCORRENCIA", mbox.getMbo(i).getString("NUMCIRCULAR")+  "/" + mbox.getMbo(i).getString("ANO") + "/" + mbox.getMbo(i).getString("SETOR"));
                mboOcorrencia.setValue("MATBESTESTID", getMbo().getInt("MATBESTESTID"));
                mboOcorrencia.setValue("DATOCORRENCIA", mbox.getMbo(i).getDate("DATCIRCULAR"));
                mboOcorrencia.setValue("TIPOCORRENCIA", mbox.getMbo(i).getString("TIPCIRCULAR"));
                mboOcorrencia.setValue("MATBCIRCULARID", mbox.getMbo(i).getString("MATBCIRCULARID"));
                mboOcorrencia.setValue("ID2REV", idRevisao);
                mboOcorrencia.setValue("ID2DESOCO", "Foi Realizada um(a) " + mbox.getMbo(i).getString("TIPCIRCULAR") + " por " + this.sessionContext.getUserInfo().getLoginUserName() + " - " + this.sessionContext.getUserInfo().getDisplayName());

                if (mbox.getMbo(i).getString("TIPCIRCULAR").substring(0, 2).equalsIgnoreCase("Ha")) {
                    getMbo().setValue("STATUS", "2", 2L);
                } else if (mbox.getMbo(i).getString("TIPCIRCULAR").substring(0, 2).equalsIgnoreCase("Ex")) {
                    getMbo().setValue("STATUS", "3", 2L);
                }

                refreshTable();
                reloadTable();

                validouCircular = Boolean.valueOf(true);
            }

            if (getMbo() != null) {
                MboRemote mbo;
                for (int i = 0; (mbo = getMbo().getMboSet("MATBCIRCULAR").getMbo(i)) != null; i++) {
                    if (!mbo.getBoolean("ID2CHECKCIRC")) {
                        continue;
                    }
                    mbo.setFieldFlag(new String[]{"NUMCIRCULAR", "ANO", "SETOR", "TIPCIRCULAR", "DATCIRCULAR"}, 7L, true);
                }
            }

            // Muda de Aba
            sessionContext.queueRefreshEvent();
            Utility.sendEvent(new WebClientEvent("nexttab", "maintabs", null, sessionContext));
            //Fim Mudança de Aba

            SAVE();
            emitirCircularSave = false;
            if (validouCircular.booleanValue()) {
                throw new MXApplicationException("estEstrangeiro", "CircularValidadaSucesso");
            }

        } else {
            throw new MXApplicationException("estEstrangeiro", "SemCircular");
        }
    }

    public void novaCircular()
            throws RemoteException, MXException {

        emitirCircularSave = false;

        String circularHabilitado = "";
        int idCircular = getMbo().getInt("MATBESTESTID");

        MboSet mboa = (MboSet) getMbo().getMboSet("MATBCIRCULAR");

        if (mboa.count() <= 0) {
            circularHabilitado = "Habilitação";
        }

        for (int i = 0; mboa.getMbo(i) != null; i++) {
            if ((!mboa.getMbo(i).getBoolean("ID2CHECKCIRC")) && (mboa.getCurrentPosition() == i)) {
                throw new MXApplicationException("estEstrangeiro", "emitirAntesDeAdicionar");
            }
        }

        mboa.add();

        mboa.setValue("MATBESTESTID", idCircular);
        mboa.setValue("DATCIRCULAR", new Date());
        mboa.setValue("TIPCIRCULAR", circularHabilitado);

        mboa.setValue("ANO", String.valueOf(Data.getAno(new Date())), 2L);

        this.app.getDataBean("MAINRECORD").reloadTable();
        this.app.getDataBean("MAINRECORD").refreshTable();
        executaOcorrencia();
    }

    public synchronized void listenerChangedEvent(DataBean speaker) {
        super.listenerChangedEvent(speaker);
        try {
            if (speaker.getUniqueIdName().equals("MATBCIRCULARID")) {
                if (!getMbo().getMboSet("MATBCIRCULAR").getMbo(getMbo().getMboSet("MATBCIRCULAR").getCurrentPosition()).isNew()) {
                    String valor = getMbo().getMboSet("MATBCIRCULAR").getMbo(getMbo().getMboSet("MATBCIRCULAR").getCurrentPosition()).getString("TIPCIRCULAR");
                    String valor2 = (String) getMbo().getMboSet("MATBCIRCULAR").getMbo(getMbo().getMboSet("MATBCIRCULAR").getCurrentPosition()).getDatabaseValue("TIPCIRCULAR");

                    if (!valor2.equals(valor)) {
                        getMbo().getMboSet("MATBCIRCULAR").getMbo(getMbo().getMboSet("MATBCIRCULAR").getCurrentPosition()).setValue("TIPCIRCULAR", valor2, 2L);
                    }
                }

                executaOcorrencia();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MXException e) {
            e.printStackTrace();
        }
    }

    private void executaOcorrencia() throws RemoteException, MXException {
        MboSet mboCircular = (MboSet) getMbo().getMboSet("MATBCIRCULAR");

        int contadorHabilitacao = 0;
        int contadorCancelamento = 0;

        boolean reload = false;

        for (int i = mboCircular.count() - 1; i > -1; i--) {
            if ((!mboCircular.getMbo(i).isNull("TIPCIRCULAR")) && (mboCircular.getMbo(i).getString("TIPCIRCULAR").substring(0, 2).equalsIgnoreCase("Ha"))) {
                if (!getMbo().getString("STATUS").equalsIgnoreCase("3")) {
                    contadorHabilitacao++;
                    mboCircular.getMbo(i).setFieldFlag("TIPCIRCULAR", 7L, true);

                    if ((contadorHabilitacao > contadorCancelamento + 1) && (!mboCircular.getMbo(i).isNull("TIPCIRCULAR")) && (mboCircular.getMbo(i).getString("TIPCIRCULAR").substring(0, 2).equalsIgnoreCase("Ha"))) {
                        reload = true;
                        mboCircular.remove(i);
                    }
                }
            } else {
                if ((!mboCircular.getMbo(i).isNull("TIPCIRCULAR")) && (mboCircular.getMbo(i).getString("TIPCIRCULAR").substring(0, 2).equalsIgnoreCase("Ex"))) {
                    mboCircular.getMbo(i).setFieldFlag("TIPCIRCULAR", 7L, true);
                    contadorCancelamento++;
                }

                if ((contadorCancelamento > contadorHabilitacao) && (!mboCircular.getMbo(i).isNull("TIPCIRCULAR")) && (mboCircular.getMbo(i).getString("TIPCIRCULAR").substring(0, 2).equalsIgnoreCase("Ex"))) {
                    reload = true;
                }

            }

            if ((!mboCircular.getMbo(i).isNull("TIPCIRCULAR")) && (mboCircular.getMbo(i).getString("TIPCIRCULAR").substring(0, 2).equalsIgnoreCase("Al"))) {
                mboCircular.getMbo(i).setFieldFlag("TIPCIRCULAR", 7L, true);
            }

            if ((!mboCircular.getMbo(i).isNew()) || (mboCircular.getMbo(i).getString("TIPCIRCULAR").equals("")) || (mboCircular.getCurrentPosition() != i)
                    || ((!mboCircular.getMbo(i).isNull("TIPCIRCULAR")) && (mboCircular.getMbo(i).getString("TIPCIRCULAR").substring(0, 2).equalsIgnoreCase("Ha")) && (contadorHabilitacao > 1))
                    || (mboCircular.getMbo(i).isNull("TIPCIRCULAR")) || (!mboCircular.getMbo(i).getString("TIPCIRCULAR").substring(0, 2).equalsIgnoreCase("Ex")) || (contadorCancelamento <= 1)) {
                continue;
            }
        }
        if (reload) {
            reloadTable();
            refreshTable();
        }
    }

    public synchronized boolean fetchRecordData()
            throws MXException, RemoteException {
        if (getMbo() != null) {
            MboRemote mbo;
            for (int i = 0; (mbo = getMbo().getMboSet("MATBCIRCULAR").getMbo(i)) != null; i++) {
                if (!mbo.getBoolean("ID2CHECKCIRC")) {
                    continue;
                }
                mbo.setFieldFlag(new String[]{"NUMCIRCULAR", "ANO", "SETOR", "TIPCIRCULAR", "DATCIRCULAR"}, 7L, true);
            }

        }

        return super.fetchRecordData();
    }

    public void bindComponent(BoundComponentInstance boundComponent) {
        super.bindComponent(boundComponent);
        try {
            if (getMbo() != null) {

                MboRemote mbo;
                for (int i = 0; (mbo = getMbo().getMboSet("MATBCIRCULAR").getMbo(i)) != null; i++) {
                    if (!mbo.getBoolean("ID2CHECKCIRC")) {
                        continue;
                    }
                    mbo.setFieldFlag(new String[]{"NUMCIRCULAR", "ANO", "SETOR", "TIPCIRCULAR", "DATCIRCULAR"}, 7L, true);
                }

                reloadTable();
                refreshTable();
            }
        } catch (MXException ex) {
            Logger.getLogger(EstabelecimentoEstrangeiro.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(EstabelecimentoEstrangeiro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
