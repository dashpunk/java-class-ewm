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
 * @author Willians Andrade
 * psdi.webclient.beans.servicedesk.TicketAppBean
 */
public class ID2SVT01 extends psdi.webclient.beans.servicedesk.TicketAppBean {

    String camposObrigatorios = "";
    String relacionamentosObrigatorios = "";

    public ID2SVT01() {
    }

    @Override
    public int SAVE() throws MXException, RemoteException { 
        int retorno;

        return super.SAVE();
    }
    
    @Override
    public int ROUTEWF() throws MXException, RemoteException {

        camposObrigatorios = "";
        relacionamentosObrigatorios = "";

        if (getMbo().getMboSet("MARL01CLAEST").count() == 0) {
            setResultadoRelacionamentoObrigatorio("Categoria do Estabelecimento");
            //throw new MXApplicationException("vistoria", "MARL01CLAESTisEmpty");
        }

        //System.out.println("----------- MARL01AGUABA " + getMbo().getMboSet("MARL01AGUABA").count());
        if (getMbo().getMboSet("MARL01AGUABA").count() == 0) {
            setResultadoRelacionamentoObrigatorio("Água de Abastecimento");
            //throw new MXApplicationException("vistoria", "MARL01AGUABAisEmpty");
        }

        //Responsável Técnico pelo Projeto
        if (getMbo().isNull("ID2RESTEC")) {
            setResultadoCampoObrigatorio("Responsável Técnico pelo Projeto");
            //throw new MXApplicationException("vistoria", "ID2RESTECIsNull");
        }
        //Área Total do Terreno (ma²)
        if (getMbo().isNull("ID2ARETOTTER")) {
            setResultadoCampoObrigatorio("Área Total do Terreno (m²)");
            //throw new MXApplicationException("vistoria", "ID2ARETOTTERIsNull");
        }
        //Área a ser ConstruÃ­da (mÂ²)
        if (getMbo().isNull("ID2ARECON")) {
            setResultadoCampoObrigatorio("Área a ser Construídaa (m²)");
            //throw new MXApplicationException("vistoria", "ID2ARECONIsNull");
        }
        //Área Útil (ma²)
        if (getMbo().isNull("ID2AREUTI")) {
            setResultadoCampoObrigatorio("Áea Útil (m²)");
            //throw new MXApplicationException("vistoria", "ID2AREUTIIsNull");
        }
        //Recuo do Alinhamento da Rua (m)
        if (getMbo().isNull("ID2RECALI")) {
            setResultadoCampoObrigatorio("Recuo do Alinhamento da Rua (m)");
            //throw new MXApplicationException("vistoria", "ID2RECALIIsNull");
        }
        //Perfil do Terreno
        if (getMbo().isNull("ID2PERTER")) {
            setResultadoCampoObrigatorio("Perfil do Terreno");
            //throw new MXApplicationException("vistoria", "ID2PERTER1IsNull");
        }
        //Facilidade de Escoamento (Águas Pluviais)
        if (getMbo().isNull("ID2FACESC")) {
            setResultadoCampoObrigatorio("Facilidade de Escoamento (Águas Pluviais)");
            //throw new MXApplicationException("vistoria", "ID2FACESC1IsNull");
        }
        //Distância Escoamento (km)
        if (getMbo().isNull("ID2DISTESC")) {
            setResultadoCampoObrigatorio("Distância Escoamento (km)");
            //throw new MXApplicationException("vistoria", "ID2DISTESCIsNull");
        }
        //Forma de Acesso
        if (getMbo().isNull("ID2FORACE")) {
            setResultadoCampoObrigatorio(" 	Forma de Acesso");
        }
        
        //Existência de Estabelecimentos que Produzam
        if (getMbo().isNull("ID2ESTMAU")) {
            setResultadoCampoObrigatorio("Existência de Estabelecimentos que Produzam");
            //throw new MXApplicationException("vistoria", "ID2ESTMAUIsNull");
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

        for (int i = 0; ((mbo = getMbo().getMboSet("MARL01AGUABA").getMbo(i)) != null); i++) {
            //Fonte Produtora
            if (mbo.isNull("ID2FONPRO")) {
                setResultadoCampoObrigatorio("Fonte Produtora");
                //throw new MXApplicationException("vistoria", "ID2FONPROIsNull");
            }
            //AbundÃ¢ncia ProvÃ¡vel (LÂ³/hora)
            if (mbo.isNull("ID2ABUPRO")) {
                setResultadoCampoObrigatorio("Abundância Provável (L³/hora)");
                //throw new MXApplicationException("vistoria", "ID2ABUPROIsNull");
            }
            //Possibilidade de PoluiÃ§Ã£o
            if (mbo.isNull("ID2POSPOL")) {
                setResultadoCampoObrigatorio("Possibilidade de Poluição");
                //throw new MXApplicationException("vistoria", "ID2POSPOLIsNull");
            }
            //Possibilidade de PoluiÃ§Ã£o
            if (mbo.isNull("ID2POSPOL")) {
                setResultadoCampoObrigatorio("Possibilidade de Poluição");
                //throw new MXApplicationException("vistoria", "ID2POSPOLIsNull");
            }

        }
        
        if (getMbo().getBoolean("ID2EXIPRELIM")) {
            if (getMbo().isNull("ID2DESPRELIM")) {
                setResultadoCampoObrigatorio("Descrição da Existência de Prédios Limitrofes");
                //throw new MXApplicationException("vistoria", "ID2DESCSVTLIMIsNull");
            }

        }
        if (!camposObrigatorios.equals("")) {

            throw new MXApplicationException("system", "null", new String[]{camposObrigatorios});

        }
        if (!relacionamentosObrigatorios.equals("")) {

            throw new MXApplicationException("system", "relacionamentoVazio", new String[]{relacionamentosObrigatorios});

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

        //Categoria Principal
        return super.ROUTEWF();
    }

    @Override
    public synchronized boolean fetchRecordData() throws MXException, RemoteException {

        if (getMbo().getString("ID2NUMPROTOCOLO").equals("")){
	    	int retorno;
	
	        int ano = Data.getAno(new Date());
	
	        MboSet mboSet;
	        mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("SVT", getMbo().getUserInfo());
	
	        int TicketId = getMbo().getInt("TICKETID");
	        
	        String valor = "SVT." + Uteis.adicionaValorEsquerda(String.valueOf(TicketId), "0", 9) + "." + Data.getAno(new Date());
	        getMbo().setValue("ID2NUMPROTOCOLO", valor, MboConstants.NOACCESSCHECK);
	        
	        reloadTable();
	        refreshTable();
	        System.out.print("saiu do fetchRecordData()");
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
