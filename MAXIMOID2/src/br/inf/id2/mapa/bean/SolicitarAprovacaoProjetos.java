package br.inf.id2.mapa.bean;

import br.inf.id2.common.util.Validar;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;

import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.BoundComponentInstance;

public class SolicitarAprovacaoProjetos extends psdi.webclient.system.beans.AppBean {

    String camposObrigatorios = "";
    String relacionamentosObrigatorios = "";

    /**
     *
     */
    public SolicitarAprovacaoProjetos() {
        System.out.println("--- SolicitarAprovacaoProjetos 1524");
    }

    @Override
    public int ROUTEWF() throws MXException, RemoteException {
        camposObrigatorios = "";
        relacionamentosObrigatorios = "";


        if (getMbo().isNull("ID2RESTECFIS")) {
            setResultadoCampoObrigatorio("Responsável Técnico pelo Projeto");
        }

        if (getMbo().getMboSet("MARL03COOEXT").count() == 0) {
            setResultadoRelacionamentoObrigatorio("Coordenagas Geográficas");
            //throw new MXApplicationException("vistoria", "MARL03COOEXTisEmpty");
        }

        if (getMbo().isNull("ID2RESTECRT")) {
            //setResultadoCampoObrigatorio("Registro de Classe do RT pelo Projeto");
            ;
        }
        if (getMbo().isNull("ID2DELPER")) {
            setResultadoCampoObrigatorio("Delimitação do Perímetro Industrial");
        }
        if (getMbo().isNull("ID2TIPPAV")) {
            setResultadoCampoObrigatorio("Tipo de Pavimentação da Área Externa");
        }
        if (getMbo().isNull("ID2LOCRESAGU")) {
            setResultadoCampoObrigatorio("Localização do Reservatório de Água");
        }
        System.out.println("------ma_apr01 gln " + getMbo().getMboValueData("ID2MATPRI").getLookupName());
        if (getMbo().isNull("ID2MATPRI")) {
            setResultadoCampoObrigatorio("Descrição da Matéria Prima");
        }
        if (getMbo().isNull("ID2AGURES")) {
            setResultadoCampoObrigatorio("Destino das Águas Residuais");
        }
        if (getMbo().isNull("ID2DEC")) {
            setResultadoCampoObrigatorio("Declividade nos Pisos/Escoamento de Águas Residuais");
        }



        if (getMbo().isNull("ID2PROABA")) {
            setResultadoCampoObrigatorio("Processo de Abate/Produção");
        }

        if (getMbo().isNull("ID2BARFIS")) {
            setResultadoCampoObrigatorio("Barreiras Físicas Contra Pragas");
        }

        //Solicitado pelo André Luiz, no dia 08/03/2012
//        if (getMbo().isNull("ID2OBSMES")) {
//            setResultadoCampoObrigatorio("Observações Complementares");
//        }

        if (getMbo().getBoolean("ID2POSDEP")) {
            if (getMbo().isNull("ID2DEP")) {
                setResultadoCampoObrigatorio("Dependência Para Elaboração de Produtos Não Comestíveis");
            }
        }

        MboRemote mbo;
        for (int i = 0; ((mbo = getMbo().getMboSet("MARL01CLAEST").getMbo(i)) != null); i++) {
            if (!mbo.isNull("ID2ESP")) {
                if (mbo.isNull("ID2CAPABA")) {
                    setResultadoCampoObrigatorio("Capacidade Abate/Processamento (Dia)");
                }
                if (mbo.isNull("ID2VELMAX")) {
                    setResultadoCampoObrigatorio("Velocidade Máxima Abate (Animais/Hora)");
                }
            }
        }

        if (Validar.getMboSetTamanho(getMbo().getMboSet("MARL03COOEXT")) == 0) {
            setResultadoRelacionamentoObrigatorio("Coordenadas Geográficas");
        }
        if (Validar.getMboSetTamanho(getMbo().getMboSet("MARL10MESAGUABA")) == 0) {
            setResultadoRelacionamentoObrigatorio("Águas de Abastecimento");
        }
        if (Validar.getMboSetTamanho(getMbo().getMboSet("MARL01CLAEST")) == 0) {
            setResultadoRelacionamentoObrigatorio("Tipo de Estabelecimento");
        }
        if (Validar.getMboSetTamanho(getMbo().getMboSet("MARL06CAPEST")) == 0) {
            setResultadoRelacionamentoObrigatorio("Capacidade do Estabelecimento");
        }
        if (Validar.getMboSetTamanho(getMbo().getMboSet("MARL07MAQEQU")) == 0) {
            setResultadoRelacionamentoObrigatorio("Máquinas e Equipamentos");
        }
        if (Validar.getMboSetTamanho(getMbo().getMboSet("MARL08INSIND")) == 0) {
            setResultadoRelacionamentoObrigatorio("Instalações Industriais");
        }
        if (Validar.getMboSetTamanho(getMbo().getMboSet("MARL09PRO")) == 0) {
            setResultadoRelacionamentoObrigatorio("Produtos que Pretende Fabricar");
        }

        if (!camposObrigatorios.equals("")) {
            throw new MXApplicationException("system", "null", new String[]{camposObrigatorios});
        }
        if (!relacionamentosObrigatorios.equals("")) {
            throw new MXApplicationException("system", "relacionamentoVazio", new String[]{relacionamentosObrigatorios});
        }

        if (!Validar.preencimentoObrigatorio((MboSet) getMbo().getMboSet("MARL03COOEXT"), "MAIN", 1)) {
            throw new MXApplicationException("vistoria", "MAINDifUm");
        }


        MboSetRemote mboSR = getMbo().getMboSet("MARL01CLAEST");
        int iTamanho = mboSR.count();
        System.err.println("############ MARL01CLAEST: " + iTamanho);
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

        /*

        // Regra 1. Deve ter pelo menos um atributos das seguintes categorias:
        //-Capacidade de estabelecimento MARL06CAPEST
        //-Maquinas e Equipamentos MARL07MAQEQU
        //-Instalacoes industriais MARL08INSIND
        //-Produtos que pretende fabricar MARL09PRO

        MboSetRemote mboSR;
        int iTamanho = 0;

        mboSR = getMbo().getMboSet("MARL06CAPEST");
        iTamanho = mboSR.count();
        if (iTamanho <= 0) {
        throw new MXApplicationException("vistoria", "AoMenosUmaCapacidadeEstabelecimento");
        }

        mboSR = getMbo().getMboSet("MARL07MAQEQU");
        iTamanho = mboSR.count();
        if (iTamanho <= 0) {
        throw new MXApplicationException("vistoria", "AoMenosUmaMaquinaEquipamento");
        }

        mboSR = getMbo().getMboSet("MARL08INSIND");
        iTamanho = mboSR.count();
        if (iTamanho <= 0) {
        throw new MXApplicationException("vistoria", "AoMenosUmaInstalacaoIndustrial");
        }

        mboSR = getMbo().getMboSet("MARL09PRO");
        iTamanho = mboSR.count();
        if (iTamanho <= 0) {
        throw new MXApplicationException("vistoria", "AoMenosUmProdutoFrabricar");
        }

         */

        return super.ROUTEWF();
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

    @Override
    public void bindComponent(BoundComponentInstance boundComponent) {
        try {
            super.bindComponent(boundComponent);
            MboRemote gMbo;
            System.out.println("----------------ma_apr01 count " + getMbo().getMboSet("MARL01CLAEST").count());
            for (int i = 0; (gMbo = getMbo().getMboSet("MARL01CLAEST").getMbo(i)) != null; i++) {
                System.out.println("-------------ma_apr01 i " + i);
                System.out.println("--------------ma_apr01 tf " + gMbo.isNull("ID2ESP"));
                System.out.println("--------------ma_apr01 tf b 1 " + gMbo.isNull("ID2CAPABA"));
                System.out.println("--------------ma_apr01 tf b 2 " + gMbo.isNull("ID2VELMAX"));
                gMbo.setFieldFlag(new String[]{"ID2CAPABA", "ID2VELMAX"}, MboConstants.READONLY, gMbo.isNull("ID2ESP"));
                System.out.println("--------------ma_apr01 tf a 1 " + gMbo.isNull("ID2CAPABA"));
                System.out.println("--------------ma_apr01 tf a 2 " + gMbo.isNull("ID2VELMAX"));
            }
        } catch (MXException ex) {
            Logger.getLogger(SolicitarAprovacaoProjetos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("-----------------ma_apr01 ex " + ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(SolicitarAprovacaoProjetos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("-----------------ma_apr01 ex " + ex.getMessage());
        }
    }

    @Override
    public synchronized void listenerChangedEvent(DataBean speaker) {
        super.listenerChangedEvent(speaker);
        try {
            System.out.println("--- claest " + speaker.getUniqueIdName());
            if (speaker.getUniqueIdName().equalsIgnoreCase("MATBTIPESTID")) {
                if (getMbo().getMboSet("MARL01CLAEST").count() > 0) {

                    if (!getMbo().getMboSet("MARL01CLAEST").getMbo(getMbo().getMboSet("MARL01CLAEST").getCurrentPosition()).isNull("ID2ARE")
                            && !getMbo().getMboSet("MARL01CLAEST").getMbo(getMbo().getMboSet("MARL01CLAEST").getCurrentPosition()).isNull("ID2CAT")) {
                        getMbo().getMboSet("MARL01CLAEST").save();
                    }
                }
            }
        } catch (MXException ex) {
            Logger.getLogger(SolicitarAprovacaoProjetos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(SolicitarAprovacaoProjetos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public synchronized boolean fetchRecordData() throws MXException,
    		RemoteException {
    	System.out.println("Entrou no fetchRecordData");
    	System.out.println("Nome " + sessionContext.getUserInfo().getLoginUserName());
    	getMbo().setValue("id2solapr", sessionContext.getUserInfo().getLoginUserName());
    	return super.fetchRecordData();
    }
}
