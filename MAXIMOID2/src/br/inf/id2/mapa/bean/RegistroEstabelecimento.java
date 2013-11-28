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
 * @author Andre Almeida
 *
 */
public class RegistroEstabelecimento extends psdi.webclient.system.beans.AppBean {

    String camposObrigatorios = "";
    String relacionamentosObrigatorios = "";

    public RegistroEstabelecimento() {
        System.out.println("*** RegistroEstabelecimento ***");
    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        System.out.println("*** SAVE ***");
        int retorno;
        if (getMbo().isNew()) {
            int ano = Data.getAno(new Date());

            MboSet mboSet;
            mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWLOC05", getMbo().getUserInfo());

            mboSet.setWhere("ID2NUMPROTOCOLO =  coalesce((select max(ID2NUMPROTOCOLO) from id2vwloc05 where substr(coalesce(ID2NUMPROTOCOLO, 'EST.000000001." + ano + "\'),15,4) = '" + ano + "\' and substr(coalesce(ID2NUMPROTOCOLO, 'EST.000000001."+ ano + "\'),0,3) = \'EST\'), 'EST.000000001." + ano + "\')");
            mboSet.reset();

            if (mboSet.count() == 0) {

                atribuirValorComMascara("EST.000000001." + ano, 0);
                
            }
            atribuirValorComMascara(mboSet.getMbo(0).getString("ID2NUMPROTOCOLO"), +1);

            // Validar Campos


            camposObrigatorios = "";
            relacionamentosObrigatorios = "";
            
            
            if (getMbo().getMboSet("MARL01CLAEST").count() == 0) {
                setResultadoRelacionamentoObrigatorio("Categoria do Estabelecimento");
                //throw new MXApplicationException("vistoria", "MARL01CLAESTisEmpty");
            }
            
            if (getMbo().getMboSet("MARL03COOEXT").count() == 0) {
                setResultadoRelacionamentoObrigatorio("Coordenagas GeogrÃ¡ficas");
                //throw new MXApplicationException("vistoria", "MARL03COOEXTisEmpty");
            }

            if (getMbo().getMboSet("MARL02AGUABA").count() == 0) {
                setResultadoRelacionamentoObrigatorio("Ã�gua de Abastecimento");
                //throw new MXApplicationException("vistoria", "MARL02AGUABAisEmpty");
            }
            //LocalizaÃ§Ã£o
            if (getMbo().isNull("ID2LOC")) {
                setResultadoCampoObrigatorio("LocalizaÃ§Ã£o");
                //throw new MXApplicationException("vistoria", "ID2LOCIsNull");
            }
            //Tipo de Estabelecimento
            if (getMbo().isNull("ID2TIPESTA")) {
                setResultadoCampoObrigatorio("Tipo de Estabelecimento");
                //throw new MXApplicationException("vistoria", "ID2TIPESTAIsNull");
            }
            //ResponsÃ¡vel TÃ©cnico pelo Projeto
            if (getMbo().isNull("ID2RESTEC")) {
                setResultadoCampoObrigatorio("ResponsÃ¡vel TÃ©cnico pelo Projet");
                //throw new MXApplicationException("vistoria", "ID2RESTECIsNull");
            }
            //Ã�rea Total do Terreno (mÂ²)
            if (getMbo().isNull("ID2ARETOTTER")) {
                setResultadoCampoObrigatorio("Ã�rea Total do Terreno (mÂ²)");
                //throw new MXApplicationException("vistoria", "ID2ARETOTTERIsNull");
            }
            //Ã�rea a ser ConstruÃ­da (mÂ²)
            if (getMbo().isNull("ID2ARECON")) {
                setResultadoCampoObrigatorio("Ã�rea a ser ConstruÃ­da (mÂ²)");
                //throw new MXApplicationException("vistoria", "ID2ARECONIsNull");
            }
            //Ã�rea Ãštil (mÂ²)
            if (getMbo().isNull("ID2AREUTI")) {
                setResultadoCampoObrigatorio("Ã�rea Ãštil (mÂ²)");
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
            //Facilidade de Escoamento (Ã�guas Pluviais)
            if (getMbo().isNull("ID2FACESC1")) {
                setResultadoCampoObrigatorio("Facilidade de Escoamento (Ã�guas Pluviais)");
                //throw new MXApplicationException("vistoria", "ID2FACESC1IsNull");
            }
            //DistÃ¢ncia Escoamento (km)
            if (getMbo().isNull("ID2DISTESC")) {
                setResultadoCampoObrigatorio("DistÃ¢ncia Escoamento (km)");
                //throw new MXApplicationException("vistoria", "ID2DISTESCIsNull");
            }
            //ExistÃªncia de Estabelecimentos que Produzam
            if (getMbo().isNull("ID2ESTMAU")) {
                setResultadoCampoObrigatorio("ExistÃªncia de Estabelecimentos que Produzam");
                //throw new MXApplicationException("vistoria", "ID2ESTMAUIsNull");
            }
          //UF
            if (getMbo().getString("ID2LOC").equalsIgnoreCase("Rural")) {
                if (getMbo().isNull("ID2ADDUF")) {
                    setResultadoCampoObrigatorio("UF");
                    //throw new MXApplicationException("vistoria", "ID2ADDUFIsNull");
                }
            }
            //MunicÃ­pio
            if (getMbo().getString("ID2LOC").equalsIgnoreCase("Rural")) {
                if (getMbo().isNull("ID2CODMUN")) {
                    setResultadoCampoObrigatorio("MunicÃ­pio");
                    //throw new MXApplicationException("vistoria", "ID2CODMUNIsNull");
                }
            }
            //DescriÃ§Ã£o da LocalizaÃ§Ã£o
            if (getMbo().getString("ID2LOC").equalsIgnoreCase("Rural")) {
                if (getMbo().isNull("ID2DESCLOCVIS")) {
                    setResultadoCampoObrigatorio("DescriÃ§Ã£o da LocalizaÃ§Ã£o");
                    //throw new MXApplicationException("vistoria", "ID2DESCLOCVISIsNull");
                }
            }
            //DescriÃ§Ã£o da CEP
            if (getMbo().getString("ID2LOC").equalsIgnoreCase("Urbana") || getMbo().getString("ID2LOC").equalsIgnoreCase("Suburbana")) {
                if (getMbo().isNull("ID2CEPCODE")) {
                    setResultadoCampoObrigatorio("DescriÃ§Ã£o da CEP");
                    //throw new MXApplicationException("vistoria", "ID2CEPCODEIsNull");
                }
            }
            //Localização UF Rural
            if (getMbo().getString("ID2LOC").equalsIgnoreCase("Rural")) {
                if (getMbo().isNull("ID2LOCUF")) {
                    setResultadoCampoObrigatorio("Localização da UF Rural");
                    //throw new MXApplicationException("vistoria", "ID2CODMUNIsNull");
                }
            }
            //Localização UF Urbana
            if (getMbo().getString("ID2LOC").equalsIgnoreCase("Urbana") || getMbo().getString("ID2LOC").equalsIgnoreCase("Suburbana")) {
                if (getMbo().isNull("ID2LOCUF")) {
                    setResultadoCampoObrigatorio("Localização da UF Urbana");
                    //throw new MXApplicationException("vistoria", "ID2CEPCODEIsNull");
                }
            }
            MboRemote mbo;
            for (int i = 0; ((mbo = getMbo().getMboSet("MARL01CLAEST").getMbo(i)) != null); i++) {
                //Ã�rea
                if (mbo.isNull("ID2ARE")) {
                    setResultadoCampoObrigatorio("Ã�rea");
                    //throw new MXApplicationException("vistoria", "ID2AREIsNull");
                }
                //Categoria
                if (mbo.isNull("ID2CAT")) {
                    setResultadoCampoObrigatorio("Categoria");
                    //throw new MXApplicationException("vistoria", "ID2CATIsNull");
                }
                //EspÃ©cie
                if (mbo.isFlagSet(MboConstants.READONLY)) {
                    if (mbo.isNull("ID2ESP")) {
                        setResultadoCampoObrigatorio("EspÃ©cie");
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
                //AbundÃ¢ncia ProvÃ¡vel (LÂ³/hora)
                if (mbo.isNull("ID2ABUPRO")) {
                    setResultadoCampoObrigatorio("AbundÃ¢ncia ProvÃ¡vel (LÂ³/hora)");
                    //throw new MXApplicationException("vistoria", "ID2ABUPROIsNull");
                }
                //Possibilidade de PoluiÃ§Ã£o
                if (mbo.isNull("ID2POSPOL")) {
                    setResultadoCampoObrigatorio("Possibilidade de PoluiÃ§Ã£o");
                    //throw new MXApplicationException("vistoria", "ID2POSPOLIsNull");
                }
                //Possibilidade de PoluiÃ§Ã£o
                if (mbo.isNull("ID2POSPOL")) {
                    setResultadoCampoObrigatorio("Possibilidade de PoluiÃ§Ã£o");
                    //throw new MXApplicationException("vistoria", "ID2POSPOLIsNull");
                }

            }

            if (getMbo().getBoolean("ID2EXIPRELIM")) {
                if (getMbo().isNull("ID2DESCPRELIM")) {
                    setResultadoCampoObrigatorio("DescriÃ§Ã£o da ExistÃªncia de PrÃ©dios Limitrofes");
                    //throw new MXApplicationException("vistoria", "ID2DESCPRELIMIsNull");
                }

            }
            if (!camposObrigatorios.equals("")) {

                throw new MXApplicationException("system", "null", new String[]{camposObrigatorios});

            }
            if (!relacionamentosObrigatorios.equals("")) {

                throw new MXApplicationException("system", "relacionamentoVazio", new String[]{relacionamentosObrigatorios});

            }

            //Coordenada Principal
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
            
        }
        return super.SAVE();
    }

    private void atribuirValorComMascara(String valor, int fator) throws MXException, RemoteException {

        int sequencia = Integer.valueOf(valor.substring(4, 13)) + fator;

        valor = "EST." + Uteis.adicionaValorEsquerda(String.valueOf(sequencia), "0", 9) + "." + Data.getAno(new Date());

        getMbo().setValue("ID2NUMPROTOCOLO", valor, MboConstants.NOACCESSCHECK);

        super.SAVE();


    }
    
    @Override
    public synchronized boolean fetchRecordData() throws MXException, RemoteException {
        if (app.getApp().equalsIgnoreCase("id2est00")) {
            MboRemote mbo;
            System.out.print("Chegou aqui no fetchRecordData");
            if ((mbo = getMbo().getMboSet("MARL01CLAEST").getMbo(0)) != null) {
                validaReadOnly("MARL01CLAEST", "ID2TIPESTA");
            }
            System.out.print("Saiu aqui no fetchRecordData");
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
