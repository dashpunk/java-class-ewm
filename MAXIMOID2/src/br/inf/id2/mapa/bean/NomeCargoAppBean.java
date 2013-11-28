/*
 * Aplicado em ma_reg03, ma_vis04, ma_apr06, MA_REG07,ma_vis03, ma_apr03,MA_REG04, MA_REG06, MA_REG05, MA_REG07, ma_apr04,MA_APR02, MA_REG08, ma_apr05
 * 
 */
package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.controller.BoundComponentInstance;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.session.WebClientSession;
import br.inf.id2.common.util.Uteis;
import br.inf.id2.common.util.Validar;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class NomeCargoAppBean extends psdi.webclient.system.beans.AppBean {

    /**
     *
     */
    String nome = "---";
    String cargo = "---";
    String grupo = "---";
    boolean carnes = false;
    boolean mel = false;
    boolean ovos = false;
    boolean leite = false;
    boolean pescados = false;
    String camposObrigatorios = "";
    String relacionamentosObrigatorios = "";

    @Override
    public synchronized boolean fetchRecordData() throws MXException, RemoteException {
        System.out.println("--- valores fetchRecordData " + app.getApp().equalsIgnoreCase("MA_REG02"));
        MboRemote mbo;
        if (app.getApp().equalsIgnoreCase("MA_RETAG")) {
            if ((mbo = getMbo().getMboSet("MARL02WOR").getMbo(getMbo().getMboSet("MARL02WOR").count()-1)) != null) {
                getMbo().setValue("ID2NUMOS", mbo.getString("WONUM"), MboConstants.NOACCESSCHECK);
                getMbo().getThisMboSet().save();
            }
        }


        if (app.getApp().equalsIgnoreCase("MA_REG02")) {
            System.out.println("--- valores fetchRecordData count " + getMbo().getMboSet("MARL05WOR03").count());
            if ((mbo = getMbo().getMboSet("MARL05WOR03").getMbo(getMbo().getMboSet("MARL05WOR03").count()-1)) != null) {
                System.out.println("--- valores fetchRecordData v1 " + mbo.getString("WONUM"));
                getMbo().setValue("ID2NUMOS", mbo.getString("WONUM"), MboConstants.NOACCESSCHECK);
                //System.out.println("--- valores fetchRecordData apos setValue "+mbo.getString("ID2NUMOS"));
                System.out.println("--- valores fetchRecordData apos setvalue ");
                getMbo().getThisMboSet().save();
                System.out.println("--- valores fetchRecordData apos save ");
            }
            System.out.println("--- valores fetchRecordData FIM");
        }
        
        if(app.getApp().equalsIgnoreCase("ma_vis03") && (mbo = getMbo().getMboSet("MARL01CLAEST02").getMbo(0)) != null) {
            validaReadOnly("MARL01CLAEST02", "ID2TIPESTA");
        	refreshTable();
        	reloadTable();
        }
        
        return super.fetchRecordData();
    }

    public NomeCargoAppBean() {
        System.out.println("###### NomeCargoAppBean ######");
    }

    @Override
    public void bindComponent(BoundComponentInstance boundComponent) {

        super.bindComponent(boundComponent);

        String[] campos = {""};
        try {
            obterValores();
        } catch (RemoteException ex) {
            Logger.getLogger(NomeCargoAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (app.getApp().equalsIgnoreCase("ma_apr06")) {
            if (carnes) {
                campos = new String[]{"ID2RESAPRDIPPROJ"};
            }
            if (leite) {
                campos = new String[]{"ID2RESAPRLEI"};
            }
            if (mel) {
                campos = new String[]{"ID2RESAPRMEL"};
            }
            if (pescados) {
                campos = new String[]{"ID2RESAPRPES"};
            }
            if (ovos) {
                campos = new String[]{"ID2RESAPROVO"};
            }

            //campos = new String[]{"ID2RESAPRDIPPROJ", "ID2RESAPRLEI", "ID2RESAPRMEL", "ID2RESAPRPES", "ID2RESAPROVO"};

        } else if (app.getApp().equalsIgnoreCase("MA_REG07")) {
            if (carnes) {
                campos = new String[]{"ID2RESAPRTECDIPHST"};
            }
            if (leite) {
                campos = new String[]{"ID2RESAPRHSTLEI"};
            }
            if (mel) {
                campos = new String[]{"ID2RESAPRHSTMEL"};
            }
            if (pescados) {
                campos = new String[]{"ID2RESAPRHSTPES"};
            }
            if (ovos) {
                campos = new String[]{"ID2RESAPRHSTOVO"};
            }
            //campos = new String[]{"ID2RESAPRTECDIPHST", "ID2RESAPRHSTLEI", "ID2RESAPRHSTMEL", "ID2RESAPRHSTPES", "ID2RESAPRHSTOVO"};
        } else if (app.getApp().equalsIgnoreCase("ma_vis04")) {

            //System.out.println("---------------- ma_vis04 " + nome);
            campos = new String[]{"ID2RESAPRLAUVIS"};
        } else if (app.getApp().equalsIgnoreCase("MA_REG03")) {
            campos = new String[]{"ID2RESLAUHST"};
        } else if (app.getApp().equalsIgnoreCase("MA_VIS03")) {
            campos = new String[]{"ID2RESLAUVIS"};
        } else if (app.getApp().equalsIgnoreCase("ma_apr03")) {
            campos = new String[]{"ID2RESPARSIP"};
        } else if (app.getApp().equalsIgnoreCase("MA_REG04")) {
            campos = new String[]{"ID2RESTECSIPHST"};
        } else if (app.getApp().equalsIgnoreCase("MA_REG06")) {
            if (carnes) {
                campos = new String[]{"ID2RESTECDIPHST"};
            }
            if (leite) {
                campos = new String[]{"ID2RESPARHSTLEI"};
            }
            if (mel) {
                campos = new String[]{"ID2RESPARHSTMEL"};
            }
            if (pescados) {
                campos = new String[]{"ID2RESPARHSTPES"};
            }
            if (ovos) {
                campos = new String[]{"ID2RESPARHSTOVO"};
            }
            //campos = new String[]{"ID2RESTECDIPHST", "ID2RESPARHSTMEL", "ID2RESPARHSTLEI", "ID2RESPARHSTPES", "ID2RESPARHSTOVO"};
        } else if (app.getApp().equalsIgnoreCase("MA_REG05")) {
            campos = new String[]{"ID2RESAPRTECSIPHST"};
        } else if (app.getApp().equalsIgnoreCase("ma_apr04")) {
            campos = new String[]{"ID2RESAPRSIP"};
        } else if (app.getApp().equalsIgnoreCase("MA_APR02")) {
            campos = new String[]{"ID2RESLEG"};
        } else if (app.getApp().equalsIgnoreCase("MA_REG08")) {
            campos = new String[]{"ID2RESCONHST"};
        } else if (app.getApp().equalsIgnoreCase("ma_apr05")) {
            if (carnes) {
                campos = new String[]{"ID2RESPARTECDIPRPOJ"};
            }
            if (leite) {
                campos = new String[]{"ID2RESPARLEI"};
            }
            if (mel) {
                campos = new String[]{"ID2RESPARMEL"};
            }
            if (pescados) {
                campos = new String[]{"ID2RESPARPES"};
            }
            if (ovos) {
                campos = new String[]{"ID2RESPAROVO"};
            }

            //campos = new String[]{"ID2RESPARTECDIPRPOJ", "ID2RESPARLEI", "ID2RESPARMEL", "ID2RESPARPES", "ID2RESPAROVO"};

        } else if (app.getApp().equalsIgnoreCase("MA_RET02")) {
            campos = new String[]{"ID2RESIF"};
        } else if (app.getApp().equalsIgnoreCase("MA_RET03")) {
            campos = new String[]{"ID2RESTECSIP"};
        } else if (app.getApp().equalsIgnoreCase("MA_RET04")) {
            campos = new String[]{"ID2RESAPRSIP"};
        } else if (app.getApp().equalsIgnoreCase("MA_CAN02")) {
            campos = new String[]{"ID2RESTEC"};
        } else if (app.getApp().equalsIgnoreCase("MA_CAN03")) {
            campos = new String[]{"ID2RESTECSIP"};
        } else if (app.getApp().equalsIgnoreCase("MA_CAN04")) {
            campos = new String[]{"ID2RESCHFSIP"};
            System.out.println("--- campos " + campos[0]);
        } else if (app.getApp().equalsIgnoreCase("MA_CAN05")) {
            campos = new String[]{"ID2RESTECDIP"};
        } else if (app.getApp().equalsIgnoreCase("MA_CAN06")) {
            campos = new String[]{"ID2RESCHFDIP"};
        } else if (app.getApp().equalsIgnoreCase("MA_CAN07")) {
            campos = new String[]{"ID2RESCON"};
        } else if (app.getApp().equalsIgnoreCase("MA_TRA02")) {
            campos = new String[]{"ID2RESIF"};
        } else if (app.getApp().equalsIgnoreCase("MA_TRA03")) {
            campos = new String[]{"ID2RESSIP"};
        } else if (app.getApp().equalsIgnoreCase("MA_TRA04")) {
            campos = new String[]{"ID2RESAPRSIP"};
        } else if (app.getApp().equalsIgnoreCase("MA_TRA05")) {
            campos = new String[]{"ID2RESCONF"};
        }



        if (Uteis.valorExiste(boundComponent.getProperty("dataattribute"), campos)) {
            //System.out.println("-------campos " + campos);

            atualizaAtributos(campos, new String[]{""});
        }
    }

    private void atualizaAtributos(String[] atributoNome, String[] atributoCargo) {

        try {
            System.out.println("--------------atualizaAtributos " + atributoNome[0]);
            //System.out.println("--------------atualizaAtributos " + atributoCargo[0]);
            obterValores();

            getMbo().setFieldFlag(atributoNome, MboConstants.READONLY, false);

            for (int i = 0; i < atributoNome.length; i++) {
                System.out.println("--- antes do set " + nome);
                getMbo().setValue(atributoNome[i], nome, MboConstants.NOVALIDATION_AND_NOACTION);
                System.out.println("--- apos do set " + getMbo().getString(atributoNome[i]));

            }
            getMbo().setFieldFlag(atributoNome, MboConstants.READONLY, true);
        } catch (MXException ex) {
            //System.out.println("---->>>>> ex1 " + ex.getMessage());
            Logger.getLogger(NomeCargoAppBean.class.getName()).log(Level.SEVERE, null, ex);

        } catch (RemoteException ex) {
            //System.out.println("---->>>>> ex2 " + ex.getMessage());
            Logger.getLogger(NomeCargoAppBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void obtemValores2() {
        if (nome.equalsIgnoreCase("---")) {
            try {
                carnes = false;
                mel = false;
                ovos = false;
                leite = false;
                pescados = false;


                MboSet pessoa;
                pessoa = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWMAXUSER", sessionContext.getUserInfo());

                pessoa.setWhere("personid = '" + sessionContext.getUserInfo().getUserName() + "'");
                pessoa.reset();


                MboSet grupo;
                grupo = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("GROUPUSER", sessionContext.getUserInfo());

                grupo.setWhere("personid = '" + sessionContext.getUserInfo().getUserName() + "'");
                grupo.reset();

                //System.out.println("after reset");
                //System.out.println("------------- total ID2VWMAXUSER ");
                nome = pessoa.getMbo(0).getString("DISPLAYNAME");
                cargo = pessoa.getMbo(0).getString("TITLE");

                //System.out.println("-----obterValores " + nome);
                //System.out.println("-----obterValores p" + pessoa.count());
                //System.out.println("-----obterValores g" + grupo.count());

                for (int i = 0; i < grupo.count(); i++) {
                    carnes = (grupo.getMbo(i).getString("GROUPNAME").equalsIgnoreCase("ID2GRP12"));
                    mel = (grupo.getMbo(i).getString("GROUPNAME").equalsIgnoreCase("ID2GRP13"));
                    ovos = (grupo.getMbo(i).getString("GROUPNAME").equalsIgnoreCase("ID2GRP14"));
                    leite = (grupo.getMbo(i).getString("GROUPNAME").equalsIgnoreCase("ID2GRP15"));
                    pescados = (grupo.getMbo(i).getString("GROUPNAME").equalsIgnoreCase("ID2GRP16"));

                }

                //System.out.println("------ inicio");
                if (carnes) {
                    //System.out.println("-----carnes");
                }
                if (mel) {
                    //System.out.println("-----mel");
                }
                if (ovos) {
                    //System.out.println("-----ovos");
                }
                if (leite) {
                    //System.out.println("-----leite");
                }
                if (pescados) {
                    //System.out.println("-----pescados");
                }
                //System.out.println("------ fim");

            } catch (MXException ex) {
                Logger.getLogger(NomeCargoAppBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(NomeCargoAppBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

    @Override
    public int ROUTEWF() throws MXException, RemoteException {

        getMbo().getThisMboSet().save();
        
        camposObrigatorios = "";
        relacionamentosObrigatorios = "";

        MboRemote mbo;
        MboRemote mbob;
        MboRemote mboc;


        //TODO refazer esses 2 proximos if's
        if (app.getApp().equalsIgnoreCase("MA_VIS01") || app.getApp().equalsIgnoreCase("MA_VIS03") || app.getApp().equalsIgnoreCase("MA_APR01")) {
            System.out.println("----------- MARL03COOEXT " + getMbo().getMboSet("MARL03COOEXT").count());
            if (getMbo().getMboSet("MARL03COOEXT").count() == 0) {
                setResultadoRelacionamentoObrigatorio("Coordenagas Geográficas");
                //throw new MXApplicationException("vistoria", "MARL03COOEXTisEmpty");
            }
            System.out.println("----------- MARL02AGUABA " + getMbo().getMboSet("MARL02AGUABA").count());
            if (getMbo().getMboSet("MARL02AGUABA").count() == 0) {
                setResultadoRelacionamentoObrigatorio("Água de Abastecimento");
                //throw new MXApplicationException("vistoria", "MARL02AGUABAisEmpty");
            }
        }

        if (app.getApp().equalsIgnoreCase("MA_VIS03")) {
            //Forma Acesso
            /*if (getMbo().isNull("ID2FORACEFIS")) {
            setResultadoCampoObrigatorio("Forma Acesso");
            //throw new MXApplicationException("vistoria", "ID2FORACEFISIsNull");
            }
            //Parecer
            if (getMbo().isNull("ID2SUGFIS")) {
            setResultadoCampoObrigatorio("Parecer");
            //throw new MXApplicationException("vistoria", "ID2SUGFISIsNull");
            }
             */
            for (int i = 0; ((mbo = getMbo().getMboSet("MARL05WOR1").getMbo(i)) != null); i++) {
            	if (mbo.isNull("MADTEXEINI")) {
                    setResultadoCampoObrigatorio("Início Efetivo (na aba Ordem de Serviço)");
                    sessionContext.queueRefreshEvent();
                    Utility.sendEvent(new WebClientEvent("nexttab", "maintabs", null, sessionContext));
                 
                }
                if (mbo.isNull("MADTEXEFIM")) {
                    setResultadoCampoObrigatorio("Término Efetivo (na aba Ordem de Serviço)");
                    sessionContext.queueRefreshEvent();
                    Utility.sendEvent(new WebClientEvent("nexttab", "maintabs", null, sessionContext));
                }

            }
        }

        if (app.getApp().equalsIgnoreCase("MA_VIS01") || app.getApp().equalsIgnoreCase("MA_VIS03") || app.getApp().equalsIgnoreCase("MA_APR01")) {
            MboSetRemote mboSR = getMbo().getMboSet("MARL01CLAEST02");
            int iTamanho = mboSR.count();
            System.err.println("############ MARL01CLAEST02: " + iTamanho);
            if (iTamanho > 0) {
                boolean bEvento = false;
                for (int i = 0; i < iTamanho; i++) {
                    if (mboSR.getMbo(i).getString("ID2ESTPRI").trim().equals("S") && bEvento == true) {
                        throw new MXApplicationException("vistoria", "SoPermiteUmEventoPrincipal");
                    }
                    if (mboSR.getMbo(i).getString("ID2ESTPRI").trim().equals("S")) {
                        bEvento = true;
                    }
                }

                if (bEvento == false) {
                    throw new MXApplicationException("vistoria", "DeveTerUmEventoPrincipal");
                }

            } else {
                throw new MXApplicationException("vistoria", "DeveTerPeloMenosUmaCategoria");
            }
        }

        if (app.getApp().equalsIgnoreCase("MA_VIS01") || app.getApp().equalsIgnoreCase("MA_VIS03") || app.getApp().equalsIgnoreCase("MA_APR01")) {
            for (int i = 0; ((mbo = getMbo().getMboSet("MARL03COOEXT").getMbo(i)) != null); i++) {
                //Latitude
                if (mbo.isNull("ID2ORILAT")) {
                    setResultadoCampoObrigatorio("Latitude");
                    //throw new MXApplicationException("vistoria", "ID2ORILATIsNull");
                }
                //Graus
                if (mbo.isNull("ID2GRALAT")) {
                    setResultadoCampoObrigatorio("Graus");
                    //throw new MXApplicationException("vistoria", "ID2GRALATIsNull");
                }
                //Minutos
                if (mbo.isNull("ID2MINLAT")) {
                    setResultadoCampoObrigatorio("Minutos");
                    //throw new MXApplicationException("vistoria", "ID2MINLATIsNull");
                }
                //Segundos
                if (mbo.isNull("ID2SEGLAT")) {
                    setResultadoCampoObrigatorio("Segundos");
                    //throw new MXApplicationException("vistoria", "ID2SEGLATIsNull");
                }
                //Utm Latitude
                if (mbo.isNull("ID2UTMLAT")) {
                    setResultadoCampoObrigatorio("Utm Latitude");
                    //throw new MXApplicationException("vistoria", "ID2UTMLATIsNull");
                }
                //Longitude
                if (mbo.isNull("ID2ORILON")) {
                    setResultadoCampoObrigatorio("Longitude");
                    //throw new MXApplicationException("vistoria", "ID2ORILONIsNull");
                }
                //Graus
                if (mbo.isNull("ID2GRALON")) {
                    setResultadoCampoObrigatorio("Graus");
                    //throw new MXApplicationException("vistoria", "ID2GRALONIsNull");
                }
                //Minutos
                if (mbo.isNull("ID2MINLON")) {
                    setResultadoCampoObrigatorio("Minutos");
                    //throw new MXApplicationException("vistoria", "ID2MINLONIsNull");
                }
                //Segundos
                if (mbo.isNull("ID2SEGLON")) {
                    setResultadoCampoObrigatorio("Segundos");
                    //throw new MXApplicationException("vistoria", "ID2SEGLONIsNull");
                }
                //Utm Longitude
                if (mbo.isNull("ID2UTMLON")) {
                    setResultadoCampoObrigatorio("Utm Longitude");
                    //throw new MXApplicationException("vistoria", "ID2UTMLONIsNull");
                }
                //Local da coordenada
                if (mbo.isNull("DESCRIPTION")) {
                    setResultadoCampoObrigatorio("Local da coordenada");
                    //throw new MXApplicationException("vistoria", "DESCRIPTIONIsNull");
                }
            }
        }

        if (app.getApp().equalsIgnoreCase("MA_VIS01") || app.getApp().equalsIgnoreCase("MA_VIS03") || app.getApp().equalsIgnoreCase("MA_APR01")) {
            for (int i = 0; ((mbo = getMbo().getMboSet("MARL02AGUABA").getMbo(i)) != null); i++) {
                //Fonte Produtora
                if (mbo.isNull("ID2FONPRO")) {
                    setResultadoCampoObrigatorio("Fonte Produtora");
                    //throw new MXApplicationException("vistoria", "ID2FONPROIsNull");
                }
                //Abundância Provável (L³/hora)
                if (mbo.isNull("ID2ABUPRO")) {
                    setResultadoCampoObrigatorio("Abundância Provável (L³/hora)");
                    //throw new MXApplicationException("vistoria", "ID2ABUPROIsNull");
                }
                //Possibilidade de Poluição
                if (mbo.isNull("ID2POSPOL")) {
                    setResultadoCampoObrigatorio("Possibilidade de Poluição");
                    //throw new MXApplicationException("vistoria", "ID2POSPOLIsNull");
                }
                //Possibilidade de Poluição
                if (mbo.isNull("ID2POSPOL")) {
                    setResultadoCampoObrigatorio("Possibilidade de Poluição");
                    //throw new MXApplicationException("vistoria", "ID2POSPOLIsNull");
                }

            }

        }

        if (app.getApp().equalsIgnoreCase("MA_VIS01") || app.getApp().equalsIgnoreCase("MA_VIS03") || app.getApp().equalsIgnoreCase("MA_APR01")) {
            //Coordenada Principal
            if (!Validar.preencimentoObrigatorio((MboSet) getMbo().getMboSet("MARL03COOEXT"), "MAIN", 1)) {
                throw new MXApplicationException("vistoria", "MAINDifUm");
            }
        }
        /*
        if (app.getApp().equalsIgnoreCase("MA_VIS04")) {
        //Parecer
        System.out.println("-----ID2APRLAUCHE =  " + getMbo().getString("ID2APRLAUCHE"));
        if (getMbo().isNull("ID2APRLAUCHE")) {
        setResultadoCampoObrigatorio("Parecer");
        //throw new MXApplicationException("vistoria", "ID2APRLAUCHEIsNull");
        }
        }
        if (app.getApp().equalsIgnoreCase("MA_APR02")) {
        if (getMbo().isNull("ID2JUSRESLEG")) {
        setResultadoCampoObrigatorio("Observações");
        }
        }
        if (app.getApp().equalsIgnoreCase("MA_APR03")) {
        if (getMbo().isNull("ID2STAPARTECSIPPROJ")) {
        setResultadoCampoObrigatorio("Status do parecer técnico do SIPOA/SISA/SIFISA de aprovar projeto");
        }
        if (getMbo().isNull("ID2JUSPARTECSIPPROJ")) {
        setResultadoCampoObrigatorio("Status do parecer técnico do SIPOA/SISA/SIFISA de aprovar projeto");
        }
        }
        if (app.getApp().equalsIgnoreCase("MA_APR04")) {
        if (getMbo().isNull("ID2STAAPRPARTECSIPPROJ")) {
        setResultadoCampoObrigatorio("Status da aprovação do parecer técnico SIPOA/SISA/SIFISA de aprovar projeto");
        }
        if (getMbo().isNull("ID2JUSAPRPARTECSIPPROJ")) {
        setResultadoCampoObrigatorio("Observações");
        }
        }
        if (app.getApp().equalsIgnoreCase("MA_APR05")) {
        if (getMbo().isNull("ID2STAPARTECDIPPROJ")) {
        setResultadoCampoObrigatorio("Parecer DICAR");
        }
        if (getMbo().isNull("ID2STAPARLEI")) {
        setResultadoCampoObrigatorio("Parecer DILEI");
        }
        if (getMbo().isNull("ID2STAPARMEL")) {
        setResultadoCampoObrigatorio("PARECER DICS");
        }
        if (getMbo().isNull("ID2STAPARPES")) {
        setResultadoCampoObrigatorio("PARECER DIPES");
        }
        if (getMbo().isNull("ID2STAPAROVO")) {
        setResultadoCampoObrigatorio("PARECER DICAO");
        }
        }

        if (app.getApp().equalsIgnoreCase("MA_APR06")) {
        if (getMbo().isNull("ID2STAAPRDIPPROJ")) {
        setResultadoCampoObrigatorio("Parecer DICAR");
        }
        if (getMbo().isNull("ID2STAAPRLEI")) {
        setResultadoCampoObrigatorio("Parecer DILEI");
        }
        if (getMbo().isNull("ID2STAAPRMEL")) {
        setResultadoCampoObrigatorio("PARECER DICS");
        }
        if (getMbo().isNull("ID2STAAPRPES")) {
        setResultadoCampoObrigatorio("PARECER DIPES");
        }
        if (getMbo().isNull("ID2STAAPROVO")) {
        setResultadoCampoObrigatorio("PARECER DICAO");
        }
        }
         */

        if (app.getApp().equalsIgnoreCase("MA_REG02")) {
            
            System.out.println("*** MA_REG02");

            if (getMbo().isNull("ID2REGAGECHE")) {
                System.out.println("*** IF ID2REGAGECHE");
                setResultadoCampoObrigatorio("Status");
            }

            System.out.println("*** MARL05WOR03).getSize() " + getMbo().getMboSet("MARL05WOR03").getSize());
            for (int i = 0; ((mbo = getMbo().getMboSet("MARL05WOR03").getMbo(i)) != null); i++) {
                if (mbo.isNull("MADTPROINI")) {
                    System.out.println("*** IF MADTPROINI");
                    setResultadoCampoObrigatorio("Início Programado");
                }
                if (mbo.isNull("MADTPROFIM")) {
                    System.out.println("*** IF MADTPROFIM");
                    setResultadoCampoObrigatorio("Término Programado");
                }

//                if (mbo.getMboSet("MARL05MEMOS").isEmpty()) {
//                    System.out.println("*** IF1 ID2LID");
//                    setResultadoRelacionamentoObrigatorio("Deve selecionar um Líder");
//                }
                if (!Validar.preencimentoObrigatorio((MboSet) mbo.getMboSet("MARL05MEMOS"), "ID2LID", 1)) {
                    System.out.println("*** IF2 ID2LID");
                    setResultadoRelacionamentoObrigatorio("Deve selecionar um Líder");
                }

                /*for (int j = 0; ((mbob = mbo.getMboSet("MARL05MEMOS").getMbo(j)) != null); j++) {

                if (mbob.isNull("PERSONID")) {
                setResultadoCampoObrigatorio("CPF");
                }

                for (int k = 0; ((mboc = mbo.getMboSet("MARLPER01").getMbo(k)) != null); k++) {

                if (mboc.isNull("DISPLAYNAME")) {
                setResultadoCampoObrigatorio("Nome");
                }

                }
                
                }
                 */
            }
        }

        if (app.getApp().equalsIgnoreCase("MA_REG03")) {
            System.out.println("*** MA_REG03");

            System.out.println("*** MARL05WOR03).getSize() " + getMbo().getMboSet("MARL05WOR03").getSize());

            for (int i = 0; ((mbo = getMbo().getMboSet("MARL05WOR03").getMbo(i)) != null); i++) {
                if (mbo.isNull("MADTEXEINI")) {
                    System.out.println("*** IF MADTEXEINI");
                    setResultadoCampoObrigatorio("Início Efetivo");
                    sessionContext.queueRefreshEvent();
                    Utility.sendEvent(new WebClientEvent("nexttab", "maintabs", null, sessionContext));
                }
                if (mbo.isNull("MADTEXEFIM")) {
                    System.out.println("*** IF MADTEXEFIM");
                    setResultadoCampoObrigatorio("Término Efetivo");
                    sessionContext.queueRefreshEvent();
                    Utility.sendEvent(new WebClientEvent("nexttab", "maintabs", null, sessionContext));
                }
            }

            MboRemote mboA;
            for (int i = 0; ((mboA = getMbo().getMboSet("MARL08INSIND").getMbo(i)) != null); i++) {
                if (mboA.isNull("ID2CON") && mboA.isNull("ID2CONS")) {
                    System.out.println("*** ID2CON && ID2CONS");
                    setResultadoCampoObrigatorio("Conformidade e Considerações obrigatórios.");
                }
            }

        }

        if (app.getApp().equalsIgnoreCase("MA_RET02")) {
            System.out.println("*** MA_RET02");

            System.out.println("*** MARL04WOR).getSize()" + getMbo().getMboSet("MARL04WOR").getSize());

            if (getMbo().getMboSet("MARL04WOR").isEmpty()) {
                setResultadoRelacionamentoObrigatorio("Equipe");
            }

            for (int i = 0; ((mbo = getMbo().getMboSet("MARL04WOR").getMbo(i)) != null); i++) {
                if (mbo.isNull("MADTEXEINI")) {
                    System.out.println("*** IF MADTEXEINI");
                    setResultadoCampoObrigatorio("Início Efetivo");
                    sessionContext.queueRefreshEvent();
                    Utility.sendEvent(new WebClientEvent("nexttab", "maintabs", null, sessionContext));
                }
                if (mbo.isNull("MADTEXEFIM")) {
                    System.out.println("*** IF MADTEXEFIM");
                    setResultadoCampoObrigatorio("Término Efetivo");
                    sessionContext.queueRefreshEvent();
                    Utility.sendEvent(new WebClientEvent("nexttab", "maintabs", null, sessionContext));
                }
            }
        }

        if (app.getApp().equalsIgnoreCase("MA_RETAG")) {
            System.out.println("*** MA_RETAG ***");

            if (getMbo().isNull("ID2AGOS")) {
                System.out.println("*** Dentro IF ID2AGOS");
                setResultadoCampoObrigatorio("Status");
            }

            System.out.println("*** getMbo().getMboSet(MARL05WOR03).getSize() - " + getMbo().getMboSet("MARL05WOR03").getSize());

            if (getMbo().getMboSet("MARL05WOR03").isEmpty()) {
                setResultadoRelacionamentoObrigatorio("Equipe");
            }

            MboRemote mboA;
            for (int i = 0; ((mboA = getMbo().getMboSet("MARL05WOR03").getMbo(i)) != null); i++) {
                System.out.println("*** MARL05MEMOS).getSize() " + mboA.getMboSet("MARL05MEMOS").getSize());
                if (!Validar.preencimentoObrigatorio((MboSet) mboA.getMboSet("MARL05MEMOS"), "ID2LID", 1)) {
                    setResultadoRelacionamentoObrigatorio("Deve selecionar um Líder");
                }
            }

            MboRemote mboB;
            System.out.println("*** getMbo().getMboSet(MARL02WOR).getSize() - " + getMbo().getMboSet("MARL02WOR").getSize());
            for (int i = 0; ((mboB = getMbo().getMboSet("MARL02WOR").getMbo(i)) != null); i++) {

                if (mboB.isNull("MADTPROINI")) {
                    System.out.println("*** Dentro IF MADTPROINI");
                    setResultadoCampoObrigatorio("Início Programado");
                }
                if (mboB.isNull("MADTPROFIM")) {
                    System.out.println("*** Dentro IF MADTPROFIM");
                    setResultadoCampoObrigatorio("Término Programado");
                }
            }
        }

        /*if (app.getApp().equalsIgnoreCase("MA_REG04")) {
        if (getMbo().isNull("ID2PARTECSIPHST")) {
        setResultadoCampoObrigatorio("Parecer");
        }
        }
        if (app.getApp().equalsIgnoreCase("MA_REG05")) {
        if (getMbo().isNull("ID2APRTECSIPHST")) {
        setResultadoCampoObrigatorio("Parecer");
        }
        }

        if (app.getApp().equalsIgnoreCase("MA_REG06")) {
        if (getMbo().isNull("ID2PARTECDIPHST")) {
        setResultadoCampoObrigatorio("Parecer DICAR");
        }
        if (getMbo().isNull("ID2STAPARHSTLEI")) {
        setResultadoCampoObrigatorio("Parecer DILEI");
        }
        if (getMbo().isNull("ID2STAPARHSTMEL")) {
        setResultadoCampoObrigatorio("PARECER DICS");
        }
        if (getMbo().isNull("ID2STAPARHSTPES")) {
        setResultadoCampoObrigatorio("PARECER DIPES");
        }
        if (getMbo().isNull("ID2STAPARHSTOVO")) {
        setResultadoCampoObrigatorio("PARECER DICAO");
        }
        }

        if (app.getApp().equalsIgnoreCase("MA_REG07")) {
        if (getMbo().isNull("ID2APRTECDIPHST")) {
        setResultadoCampoObrigatorio("Parecer DICAR");
        }
        if (getMbo().isNull("ID2STAAPRHSTLEI")) {
        setResultadoCampoObrigatorio("Parecer DILEI");
        }
        if (getMbo().isNull("ID2STAAPRHSTMEL")) {
        setResultadoCampoObrigatorio("PARECER DICS");
        }
        if (getMbo().isNull("ID2STAAPRHSTPES")) {
        setResultadoCampoObrigatorio("PARECER DIPES");
        }
        if (getMbo().isNull("ID2STAAPRHSTOVO")) {
        setResultadoCampoObrigatorio("PARECER DICAO");
        }
        }

        if (app.getApp().equalsIgnoreCase("MA_REG08")) {
        if (getMbo().isNull("ID2NUMCONT")) {
        setResultadoCampoObrigatorio("Número SIF");
        }
        }
         * 
         */

        //TODO validar primeiro
       /* if (app.getApp().equalsIgnoreCase("MA_APR05")) {
        if (getMbo().isNull("ID2STAPARTECDIPPROJ")) {
        setResultadoCampoObrigatorio("Status do parecer técnico do DIPOA de aprovar projeto");
        }
        if (getMbo().isNull("ID2JUSPARTECDIPPROJ")) {
        setResultadoCampoObrigatorio("Observações");
        }
        }
        if (app.getApp().equalsIgnoreCase("MA_APR06")) {
        if (getMbo().isNull("ID2STAAPRDIPPROJ")) {
        setResultadoCampoObrigatorio("Status da aprovação do parecer técnico do DIPOA");
        }
        if (getMbo().isNull("ID2JUSAPRDIPPROJ")) {
        setResultadoCampoObrigatorio("Observações");
        }
        }
        if (app.getApp().equalsIgnoreCase("MA_REG02")) {
        if (getMbo().isNull("MADTPROINI")) {
        setResultadoCampoObrigatorio("Início Programado");
        }
        if (getMbo().isNull("MADTPROFIM")) {
        setResultadoCampoObrigatorio("Término Programado");
        }
        if (getMbo().isNull("ID2APRAGECHE")) {
        setResultadoCampoObrigatorio("Aprovação do agendamento pelo chefe");
        }


        for (int i = 0; ((mbo = getMbo().getMboSet("MARL05WOR").getMbo(i)) != null); i++) {
        MboRemote mbob;
        if (Validar.preencimentoObrigatorio((MboSet) mbo.getMboSet("MARL05MEMOS"), "ID2LID", 1)) {
        throw new MXApplicationException("agendarvistoriaterreno", "RelacionamentoMARL05MEMOSLider");
        }321ewq
        if (Validar.getMboSetTamanho(mbo.getMboSet("MARL05MEMOS")) == 0) {
        setResultadoRelacionamentoObrigatorio("Equipe");
        }
        for (int j = 0; ((mbob = mbo.getMboSet("MARL05MEMOS").getMbo(j)) != null); j++) {
        //CPF
        if (mbob.isNull("ID2CPF")) {
        setResultadoCampoObrigatorio("CPF");
        }
        MboRemote mboc;
        for (int k = 0; ((mboc = mbob.getMboSet("MARLPER01").getMbo(k)) != null); k++) {
        //Nome
        if (mboc.isNull("DISPLAYNAME")) {
        setResultadoCampoObrigatorio("Nome");
        }
        }
        }
        }
        }
         *
         */

        if (app.getApp().equalsIgnoreCase("MA_TRA02")) {
            System.out.println("--- MA_TRA02");

            if (getMbo().getString("ID2STAIF").length() == 0) {
                setResultadoCampoObrigatorio("Parecer");
            }

            if (getMbo().getString("ID2OBSIF").length() == 0) {
                setResultadoCampoObrigatorio("Observações");
            }

        }

        if (app.getApp().equalsIgnoreCase("MA_TRA03")) {
            System.out.println("--- MA_TRA03");

            if (getMbo().getString("ID2STASIP").length() == 0) {
                setResultadoCampoObrigatorio("Parecer");
            }

            if (getMbo().getString("ID2OBSSIP").length() == 0) {
                setResultadoCampoObrigatorio("Observações");
            }

        }

        if (app.getApp().equalsIgnoreCase("MA_TRA04")) {
            System.out.println("--- MA_TRA04");

            if (getMbo().getString("ID2STAAPRSIP").length() == 0) {
                setResultadoCampoObrigatorio("Parecer");
            }

            if (getMbo().getString("ID2OBSAPRSIP").length() == 0) {
                setResultadoCampoObrigatorio("Observações");
            }

        }

        if (!camposObrigatorios.equals("")) {
            throw new MXApplicationException("id2message", "nulo", new String[]{camposObrigatorios});
        }
        if (!relacionamentosObrigatorios.equals("")) {
            throw new MXApplicationException("system", "relacionamentoVazio", new String[]{relacionamentosObrigatorios});
        }

        
        return super.ROUTEWF();
    }

    private void obterValores() throws RemoteException {
        //System.out.println("################ obtervalores");
        if (nome.equalsIgnoreCase("---")) {
            Properties prop;
            prop = MXServer.getMXServer().getConfig();
            byte[] bytes = null;
            String driver = prop.getProperty("mxe.db.driver", "oracle.jdbc.OracleDriver");
            String url = prop.getProperty("mxe.db.url", "jdbc:oracle:thin:@192.168.1.215:1521:dftop1");
            String username = prop.getProperty("mxe.db.user", "dbmaximo");
            String password = prop.getProperty("mxe.db.password", "id2maximo");
            try {
                Class.forName(driver).newInstance();
                java.sql.Connection conexao = DBConnect.getConnection(url, username, password, prop.getProperty("mxe.db.schemaowner", "dbmaximo"));
                Statement stmt = conexao.createStatement();
                PreparedStatement ps = conexao.prepareStatement("SELECT DISPLAYNAME FROM PERSON WHERE PERSONID = ?");


                ps.setString(1, sessionContext.getUserInfo().getUserName());

                ResultSet rs = ps.executeQuery();
                rs.next();

                nome = rs.getString("DISPLAYNAME");
                //System.out.println("############ pessoa = " + sessionContext.getUserInfo().getUserName());
                //System.out.println("############ DISPLAYNAME " + nome);
                //Thread.sleep(5000);

                //carnes
                ps = conexao.prepareStatement("select COUNT(*) TOTAL from ID2VWPER03 where MADEP = 'DICAR/CGI' AND PESSOA = ?");
                ps.setString(1, sessionContext.getUserInfo().getUserName());
                rs = ps.executeQuery();
                rs.next();
                carnes = (rs.getInt("TOTAL") > 0);
                //System.out.println("############ carnes " + carnes);
                //Thread.sleep(5000);

                //ovos
                ps = conexao.prepareStatement("select COUNT(*) TOTAL from ID2VWPER03 where MADEP = 'DICAO/CGI' AND PESSOA = ?");
                ps.setString(1, sessionContext.getUserInfo().getUserName());
                rs = ps.executeQuery();
                rs.next();
                ovos = (rs.getInt("TOTAL") > 0);
                //System.out.println("############ ovos " + ovos);
                //Thread.sleep(5000);

                //leite
                ps = conexao.prepareStatement("select COUNT(*) TOTAL from ID2VWPER03 where MADEP = 'DILEI/CGI' AND PESSOA = ?");
                ps.setString(1, sessionContext.getUserInfo().getUserName());
                rs = ps.executeQuery();
                rs.next();
                leite = (rs.getInt("TOTAL") > 0);
                //System.out.println("############ leite " + leite);
                //Thread.sleep(5000);

                //leite
                ps = conexao.prepareStatement("select COUNT(*) TOTAL from ID2VWPER03 where MADEP = 'DICS/CGI' AND PESSOA = ?");
                ps.setString(1, sessionContext.getUserInfo().getUserName());
                rs = ps.executeQuery();
                rs.next();
                mel = (rs.getInt("TOTAL") > 0);
                //System.out.println("############ mel " + mel);
                //Thread.sleep(5000);

                //leite
                ps = conexao.prepareStatement("select COUNT(*) TOTAL from ID2VWPER03 where MADEP = 'DIPES/CGI' AND PESSOA = ?");
                ps.setString(1, sessionContext.getUserInfo().getUserName());
                rs = ps.executeQuery();
                rs.next();
                pescados = (rs.getInt("TOTAL") > 0);
                //System.out.println("############ pescados " + pescados);
                //Thread.sleep(5000);

            } catch (Exception ex) {
                //System.out.println("------------- ex " + ex.getMessage());
                ex.getStackTrace();
            }
            //Thread.sleep(5000);

        }
    }

    private void setResultadoCampoObrigatorio(String string) {
        if (string.equals("")) {
            camposObrigatorios = "";
        } else {
            if (camposObrigatorios.indexOf(string) == -1) {
                camposObrigatorios += "\n" + string;
            }
        }
    }

    private void setResultadoRelacionamentoObrigatorio(String string) {
        if (string.equals("")) {
            relacionamentosObrigatorios = "";
        } else {
            if (relacionamentosObrigatorios.indexOf(string) == -1) {
                relacionamentosObrigatorios += "\n" + string;
            }
        }
    }
    
    private void validaReadOnly(String mboSet, String campo)
            throws MXException, RemoteException
        {
            MboSetRemote mboSR = getMbo().getMboSet(mboSet);
            int iTamanho = mboSR.count();
            if(iTamanho > 0)
                getMbo().setFieldFlag(campo, 7L, true);
            else
                getMbo().setFieldFlag(campo, 7L, false);
        }
}