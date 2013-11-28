package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.inf.id2.common.util.Validar;
import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.*;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class EstabelecimentoRural extends psdi.webclient.beans.storeroom.StoreroomAppBean {

    int var = 1;

    /**
     * Método construtor de ID2PropriedadeRuralAppBean
     */
    public EstabelecimentoRural() {
    }

    /**
     *
     * Sobrescrita do método SAVE <p> Valida se o código da Unidade da Federação
     * faz parte da formação do código da priedade. Abaixo exemplo de um código
     * de propriedade valido: <p> Código da UF = <b>123</b> <p> Código da
     * Propriedade = <b>123</b>00009871
     *
     * @since 10/03/2010
     * @since 24/03/2010
     */
    public int SAVE() throws MXException, RemoteException {


        //Setar Valor LOCATION paa todos proprietários
        MboSet matbProprietarios = (MboSet) getMbo().getMboSet("ID2LUCTYPE14");

        for (int i = 0; ((matbProprietarios.getMbo(i)) != null); i++) {
            matbProprietarios.setValue("LOCATION", getMbo().getString("LOCATION"));
        }

        // FIM

        String param[] = {new String()};
        //TODO validar método (getMboValue() ou getMboValueData() de chamada e parâmetro
        String aCodigoUF = getMbo().getString("ID2LOCUF");
        //TODO validar método (getMboValue() ou getMboValueData() de chamada e parâmetro
        String aCodigoPropriedadeRural = getMbo().getString("LOCATION");

        if (!Validar.preenchimentoMinimoObrigatorio((MboSet) getMbo().getMboSet("ID2LUCTYPE14"), 1)) {
            throw new MXApplicationException("company", "NumeroRelacionamentoInvalidoPropriedade");
        }

        if (!Validar.preencimentoMaximoObrigatorio((MboSet) getMbo().getMboSet("ID2COOEXT"), "MAIN", 1)) {
            throw new MXApplicationException("company", "CorrdenadaPrincipal", param);
        }

        insereMaTbCon();
        //TODO validar se existe validador para o Código do muniCópio, uma vez que o mesmo serve como parâmetro para o tamanho da validação
        int ret = -1;
        if (aCodigoPropriedadeRural.length() > 0) {
            if (aCodigoPropriedadeRural.substring(0, 2).equals(aCodigoUF)) {
                ret = super.SAVE();
            } else {
                //TODO validar parâmetros
                throw new MXApplicationException("company", "CodigoPropriedadeInvalido", param);
            }
        }
        return ret;
    }

    public void ADDROWONTABLE() {
        WebClientEvent event = sessionContext.getCurrentEvent();
        System.out.println("Teste 1");
        Utility.sendEvent(new WebClientEvent("addrow", (String) event.getValue(), event.getValue(), sessionContext));
        System.out.println("Teste 2");
    }

    private void insereMaTbCon() throws MXException, RemoteException {
        System.out.println("---- insereMaTbCon");

        MboSet id2AreasLocs;

        try {
            System.out.println("x------ " + psdi.server.MXServer.getMXServer().getRegistryHostName());
            System.out.println("x------ " + psdi.server.MXServer.getMXServer().getRegistryPort());
            System.out.println("x------ " + psdi.server.MXServer.getMXServer().getURL());
            System.out.println("x------ " + psdi.server.MXServer.getMXServer().getServerHost());
        } catch (Exception e) {
            System.out.println("--- e " + e.getMessage());
        }
        id2AreasLocs = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2AREALOC", sessionContext.getUserInfo());
        MboSet maTbCons = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MATBCON", sessionContext.getUserInfo());

        //verifica se existe para ID2LOCUF
        id2AreasLocs.setWhere("LOCATION = '" + getMbo().getString("ID2LOCUF") + "'");
        id2AreasLocs.reset();

        MboRemote mboUf;
        MboRemote mboMunicipio;
        System.out.println("--- id2AreasLocs.count " + id2AreasLocs.count());
        if (id2AreasLocs.count() > 0) {
            System.out.println("---vals ");
            System.out.println(getMbo().getString("LOCATION"));
            System.out.println(id2AreasLocs.getMbo(0).getString("ID2AREALOC"));

            maTbCons.setWhere("where MAESTRURID = \'" + getMbo().getString("LOCATION") + "\' and MAMICREGID = \'" + id2AreasLocs.getMbo(0).getString("ID2AREALOC") + "\'");
            maTbCons.reset();

            if (maTbCons.count() == 0) {
                mboUf = maTbCons.add();
                mboUf.setValue("MAESTRURID", getMbo().getString("LOCATION"));
                mboUf.setValue("MAMICREGID", id2AreasLocs.getMbo(0).getString("ID2AREALOC"));
                maTbCons.save();
            }
        }


        //verifica se existe para ID2LOCMUN
        id2AreasLocs.setWhere("LOCATION = '" + getMbo().getString("ID2CODMUN") + "'");
        id2AreasLocs.reset();
        System.out.println("--- id2AreasLocs.count " + id2AreasLocs.count());
        if (id2AreasLocs.count() > 0) {
            System.out.println("---vals ");
            System.out.println(getMbo().getString("LOCATION"));
            System.out.println(id2AreasLocs.getMbo(0).getString("ID2AREALOC"));
            maTbCons.setWhere("where MAESTRURID = \'" + getMbo().getString("LOCATION") + "\' and MAMICREGID = \'" + id2AreasLocs.getMbo(0).getString("ID2AREALOC") + "\'");
            maTbCons.reset();

            if (maTbCons.count() == 0) {
                mboMunicipio = maTbCons.add();
                mboMunicipio.setValue("MAESTRURID", getMbo().getString("LOCATION"));
                mboMunicipio.setValue("MAMICREGID", id2AreasLocs.getMbo(0).getString("ID2AREALOC"));
                System.out.println("--- save b");
                try {
                    maTbCons.save();
                } catch (Exception e) {
                    System.out.println("---eeee " + e.getMessage());
                }
                System.out.println("--- save a");
            }
        }
    }

    @Override
    public int INSERT() throws MXException, RemoteException {
        int retorno = super.INSERT();
        MboRemote mboDestino = app.getDataBean().getMboSet().getMbo().getMboSet("ID2LUCTYPE14").add();
        mboDestino.setValue("ID2LUCTYPE", "14", MboConstants.NOACCESSCHECK);
        mboDestino.setValue("LOCATION", getMbo().getString("LOCATION"), MboConstants.NOACCESSCHECK);
        mboDestino.setValue("ID2PROPRIETARIO", "01", MboConstants.NOACCESSCHECK);
        reloadTable();
        refreshTable();
        return retorno;

    }
}
