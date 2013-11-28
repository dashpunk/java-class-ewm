package br.inf.id2.mapa.bean;

import br.inf.id2.common.util.Data;
import br.inf.id2.common.util.Uteis;
import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.util.Validar;
import java.util.Date;

/**
 *
 * @author Dyogo
 *
 */
public class VistoriaTerreno extends psdi.webclient.system.beans.AppBean {

    String camposObrigatorios = "";
    String relacionamentosObrigatorios = "";

    /**
     *
     */
    public VistoriaTerreno() {
    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        int retorno;
        if (getMbo().isNew()) {
            int ano = Data.getAno(new Date());


            //System.out.println("__ANO  = " + ano);
            MboSet mboSet;
            mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWLOC05", getMbo().getUserInfo());

//            mboSet.setWhere("where cast(substr(coalesce(ID2NUMPROTOCOLO, \'EST.000000001." + ano + "\'),5,9) as integer)-1 not in "
//                    + "(select cast(substr(coalesce(x.ID2NUMPROTOCOLO, \'EST.000000001." + ano + "\'),5,9) as integer) from id2vwloc05 x) and substr(coalesce(ID2NUMPROTOCOLO, 'EST.000000001." + ano + "\'),15,4) = \'" + ano + "\'");
//            mboSet.reset();
//
//            //System.out.println("___ mboSet count " + mboSet.count());
//
//            MboRemote mbo;
//            for (int i = 0; ((mbo = mboSet.getMbo(i)) != null); i++) {
//                //System.out.println("___ i " + i);
//                //System.out.println("___ ID2NUMPROTOCOLO " + mbo.getString("ID2NUMPROTOCOLO"));
//                if (!mbo.getString("ID2NUMPROTOCOLO").equalsIgnoreCase("EST.000000001." + ano)) {
//                    atribuirValorComMascara(mbo.getString("ID2NUMPROTOCOLO"), -1);
//                    return super.SAVE();
//                }
//            }
            mboSet.setWhere("ID2NUMPROTOCOLO =  coalesce((select max(ID2NUMPROTOCOLO) from id2vwloc05 where substr(coalesce(ID2NUMPROTOCOLO, 'EST.000000001." + ano + "\'),15,4) = '" + ano + "\' and substr(coalesce(ID2NUMPROTOCOLO, 'EST.000000001."+ ano + "\'),0,3) = \'EST\'), 'EST.000000001." + ano + "\')");
            mboSet.reset();
            //System.out.println("___ mboSet count " + mboSet.count());
            if (mboSet.count() == 0) {
                //System.out.println("___ if 1");
                atribuirValorComMascara("EST.000000001." + ano, 0);
                
            }
            //System.out.println("___ > 0 " + mboSet.getMbo(0).getString("ID2NUMPROTOCOLO"));
            atribuirValorComMascara(mboSet.getMbo(0).getString("ID2NUMPROTOCOLO"), +1);

        }
        return super.SAVE();
    }

    private void atribuirValorComMascara(String valor, int fator) throws MXException, RemoteException {
        //System.out.println("___atribuirValorComMascara");
        //System.out.println("___ valor " + valor);
        //System.out.println("___ fator " + fator);
        int sequencia = Integer.valueOf(valor.substring(4, 13)) + fator;
        //System.out.println("___ sequencia " + sequencia);

        valor = "EST." + Uteis.adicionaValorEsquerda(String.valueOf(sequencia), "0", 9) + "." + Data.getAno(new Date());
        //System.out.println("___ novo valor" + valor);
        getMbo().setValue("ID2NUMPROTOCOLO", valor, MboConstants.NOACCESSCHECK);
        //System.out.println("___ throw e FIM");
        super.SAVE();
        //throw new MXApplicationException("vistoriaTerreno", "protocoloGerado", new String[]{valor});

    }

    @Override
    public int ROUTEWF() throws MXException, RemoteException {

        camposObrigatorios = "";
        relacionamentosObrigatorios = "";

        //TODO refazer esses 3 proximos if's
        if (getMbo().getMboSet("MARL01CLAEST").count() == 0) {
            setResultadoRelacionamentoObrigatorio("Categoria do Estabelecimento");
            //throw new MXApplicationException("vistoria", "MARL01CLAESTisEmpty");
        }
        //System.out.println("----------- MARL03COOEXT " + getMbo().getMboSet("MARL03COOEXT").count());
        if (getMbo().getMboSet("MARL03COOEXT").count() == 0) {
            setResultadoRelacionamentoObrigatorio("Coordenagas Geográficas");
            //throw new MXApplicationException("vistoria", "MARL03COOEXTisEmpty");
        }
        //System.out.println("----------- MARL02AGUABA " + getMbo().getMboSet("MARL02AGUABA").count());
        if (getMbo().getMboSet("MARL02AGUABA").count() == 0) {
            setResultadoRelacionamentoObrigatorio("Água de Abastecimento");
            //throw new MXApplicationException("vistoria", "MARL02AGUABAisEmpty");
        }

        /*
         *
         * //inicio das validações if
         * (Validar.getMboSetTamanho(getMbo().getMboSet("MARL01CLAEST")) < 1) {
         * setResultadoRelacionamentoObrigatorio("Categoria do
         * Estabelecimento"); //throw new MXApplicationException("vistoria",
         * "MARL01CLAESTisEmpty"); } if
         * (Validar.getMboSetTamanho(getMbo().getMboSet("MARL03COOEXT")) < 1) {
         * setResultadoRelacionamentoObrigatorio("Coordenagas Geográficas");
         * //throw new MXApplicationException("vistoria",
         * "MARL03COOEXTisEmpty"); } if
         * (Validar.getMboSetTamanho(getMbo().getMboSet("MARL02AGUABA")) < 1) {
         * setResultadoRelacionamentoObrigatorio("Água de Abastecimento");
         * //throw new MXApplicationException("vistoria",
         * "MARL02AGUABAisEmpty"); }
         */
        //Localização
        if (getMbo().isNull("ID2LOC")) {
            setResultadoCampoObrigatorio("Localização");
            //throw new MXApplicationException("vistoria", "ID2LOCIsNull");
        }
        //Tipo de Estabelecimento
        if (getMbo().isNull("ID2TIPESTA")) {
            setResultadoCampoObrigatorio("Tipo de Estabelecimento");
            //throw new MXApplicationException("vistoria", "ID2TIPESTAIsNull");
        }
        //Responsável Técnico pelo Projeto
        if (getMbo().isNull("ID2RESTEC")) {
            setResultadoCampoObrigatorio("Responsável Técnico pelo Projet");
            //throw new MXApplicationException("vistoria", "ID2RESTECIsNull");
        }
        //Área Total do Terreno (m²)
        if (getMbo().isNull("ID2ARETOTTER")) {
            setResultadoCampoObrigatorio("Área Total do Terreno (m²)");
            //throw new MXApplicationException("vistoria", "ID2ARETOTTERIsNull");
        }
        //Área a ser Construída (m²)
        if (getMbo().isNull("ID2ARECON")) {
            setResultadoCampoObrigatorio("Área a ser Construída (m²)");
            //throw new MXApplicationException("vistoria", "ID2ARECONIsNull");
        }
        //Área Útil (m²)
        if (getMbo().isNull("ID2AREUTI")) {
            setResultadoCampoObrigatorio("Área Útil (m²)");
            //throw new MXApplicationException("vistoria", "ID2AREUTIIsNull");
        }
        //Recuo do Alinhamento da Rua (m)
        if (getMbo().isNull("ID2RECALI")) {
            setResultadoCampoObrigatorio("Recuo do Alinhamento da Rua (m)");
            //throw new MXApplicationException("vistoria", "ID2RECALIIsNull");
        }
        //Perfil do Terreno
        if (getMbo().isNull("ID2PERTER1")) {
            setResultadoCampoObrigatorio("Perfil do Terreno");
            //throw new MXApplicationException("vistoria", "ID2PERTER1IsNull");
        }
        //Facilidade de Escoamento (Águas Pluviais)
        if (getMbo().isNull("ID2FACESC1")) {
            setResultadoCampoObrigatorio("Facilidade de Escoamento (Águas Pluviais)");
            //throw new MXApplicationException("vistoria", "ID2FACESC1IsNull");
        }
        //Distância Escoamento (km)
        if (getMbo().isNull("ID2DISTESC")) {
            setResultadoCampoObrigatorio("Distância Escoamento (km)");
            //throw new MXApplicationException("vistoria", "ID2DISTESCIsNull");
        }
        //Existência de Estabelecimentos que Produzam
        if (getMbo().isNull("ID2ESTMAU")) {
            setResultadoCampoObrigatorio("Existência de Estabelecimentos que Produzam");
            //throw new MXApplicationException("vistoria", "ID2ESTMAUIsNull");
        }


        //UF
        if (getMbo().getString("ID2LOC").equalsIgnoreCase("Rural")) {
            if (getMbo().isNull("ID2ADDUF")) {
                setResultadoCampoObrigatorio("UF");
                //throw new MXApplicationException("vistoria", "ID2ADDUFIsNull");
            }
        }
        //Município
        if (getMbo().getString("ID2LOC").equalsIgnoreCase("Rural")) {
            if (getMbo().isNull("ID2CODMUN")) {
                setResultadoCampoObrigatorio("Município");
                //throw new MXApplicationException("vistoria", "ID2CODMUNIsNull");
            }
        }
        //Descrição da Localização
        if (getMbo().getString("ID2LOC").equalsIgnoreCase("Rural")) {
            if (getMbo().isNull("ID2DESCLOCVIS")) {
                setResultadoCampoObrigatorio("Descrição da Localização");
                //throw new MXApplicationException("vistoria", "ID2DESCLOCVISIsNull");
            }
        }
        //Descrição da CEP
        if (getMbo().getString("ID2LOC").equalsIgnoreCase("Urbana") || getMbo().getString("ID2LOC").equalsIgnoreCase("Suburbana")) {
            if (getMbo().isNull("ID2CEPCODE")) {
                setResultadoCampoObrigatorio("Descrição da CEP");
                //throw new MXApplicationException("vistoria", "ID2CEPCODEIsNull");
            }
        }

        MboRemote mbo;
        for (int i = 0; ((mbo = getMbo().getMboSet("MARL01CLAEST").getMbo(i)) != null); i++) {
            //Área
            if (mbo.isNull("ID2ARE")) {
                setResultadoCampoObrigatorio("Área");
                //throw new MXApplicationException("vistoria", "ID2AREIsNull");
            }
            //Categoria
            if (mbo.isNull("ID2CAT")) {
                setResultadoCampoObrigatorio("Categoria");
                //throw new MXApplicationException("vistoria", "ID2CATIsNull");
            }
            //Espécie
            if (mbo.isFlagSet(MboConstants.READONLY)) {
                if (mbo.isNull("ID2ESP")) {
                    setResultadoCampoObrigatorio("Espécie");
                    //throw new MXApplicationException("vistoria", "ID2ESPIsNull");
                }
            }
        }

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

        if (getMbo().getBoolean("ID2EXIPRELIM")) {
            if (getMbo().isNull("ID2DESCPRELIM")) {
                setResultadoCampoObrigatorio("Descrição da Existência de Prédios Limitrofes");
                //throw new MXApplicationException("vistoria", "ID2DESCPRELIMIsNull");
            }

        }
        if (!camposObrigatorios.equals("")) {

            throw new MXApplicationException("system", "null", new String[]{camposObrigatorios});

        }
        if (!relacionamentosObrigatorios.equals("")) {

            throw new MXApplicationException("system", "relacionamentoVazio", new String[]{relacionamentosObrigatorios});

        }
        //if (Validar.preencimentoObrigatorio((MboSet) getMbo().getMboSet("MARL01CLAEST"), "ID2ESTPRI", 1)) {
        //    throw new MXApplicationException("vistoria", "ID2ESTPRIDifUm");
        //}
        //Coordenada Principal
        if (!Validar.preencimentoObrigatorio((MboSet) getMbo().getMboSet("MARL03COOEXT"), "MAIN", 1)) {
            throw new MXApplicationException("vistoria", "MAINDifUm");
        }

        System.err.println("############# Entrei no SAVE");



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

        //Nova regra, deve ter pelo menos um item das tabelas:
        //- Aguas de abastecimento - MARL02AGUABA
        //- Coordenadas Geograficas (e deve ser principal) - MARL03COOEXT

        /*
         * mboSR = getMbo().getMboSet("MARL02AGUABA"); iTamanho = mboSR.count();
         * if (iTamanho <= 0) { throw new MXApplicationException("vistoria",
         * "DeveExistirPeloMenosUmaAguaAbastecimento"); }
         *
         * mboSR = getMbo().getMboSet("MARL03COOEXT"); iTamanho = mboSR.count();
         * if (iTamanho <= 0) { throw new MXApplicationException("vistoria",
         * "DeveExistirPeloMenosUmaCoordenadaGeografica"); } else { boolean
         * bEvento = false; for (int i = 0; i < iTamanho; i++) { if
         * (mboSR.getMbo(i).getString("MAIN").trim().equals("S") && bEvento ==
         * true) { throw new MXApplicationException("vistoria",
         * "SoPermiteUmaCoordenadaPrincipal"); } if
         * (mboSR.getMbo(i).getString("MAIN").trim().equals("S")) { bEvento =
         * true; } }
         *
         * if (bEvento == false) { throw new MXApplicationException("vistoria",
         * "DeveTerUmaCoordenadaPrincipal"); } }
         *
         */

        //Categoria Principal
        return super.ROUTEWF();
    }

    /*
     * @Override public synchronized void dataChangedEvent(DataBean speaker) {
     * try { if (speaker.getUniqueIdName().equals("MATBTIPESTID")) {
     * ////System.out.println("############ Name = " +speaker.getUniqueIdName());
     * System.err.println("############ ENTREI NO LISTENER()");
     * validaReadOnly("MARL01CLAEST", "ID2TIPESTA");
     *
     * }
     * } catch (Exception e) { e.printStackTrace(); }
     * super.dataChangedEvent(speaker); }
     */
    @Override
    public synchronized boolean fetchRecordData() throws MXException, RemoteException {
        if (app.getApp().equalsIgnoreCase("ma_vis01")) {
            MboRemote mbo;
            if ((mbo = getMbo().getMboSet("MARL01CLAEST").getMbo(0)) != null) {
                validaReadOnly("MARL01CLAEST", "ID2TIPESTA");
            }
        }
        return super.fetchRecordData();
    }

    private void validaReadOnly(String mboSet, String campo) throws MXException, RemoteException {
        MboSetRemote mboSR = getMbo().getMboSet(mboSet);
        int iTamanho = mboSR.count();
        System.err.println("############ " + mboSet + ": " + iTamanho);
        if (iTamanho > 0) {
            getMbo().setFieldFlag(campo, MboConstants.READONLY, true);
        } else {
            getMbo().setFieldFlag(campo, MboConstants.READONLY, false);
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
}
