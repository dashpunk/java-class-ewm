package br.inf.id2.mapa.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.List;

import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.UploadFile;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import br.inf.id2.common.util.LerPlanilhaExcel;

public class ImportarPlanilhaIdentificacao extends DataBean {

    private List retornoExcel = null;
    private String breakLine = "\r\n";

    public void setaCabecalho() throws MXException {
        throw new MXApplicationException("system", "invaliddate");
    }

    public int execute() throws MXException, RemoteException {
        //geraLog("############## - iniciando upload.");
        //if (clientSession.hasLongOpStarted()) {
        //geraLog("############ if 1");
        //if (clientSession.runLongOp(this, "execute")) {
        //geraLog("############ if 2 UPLOAD");

        UploadFile file = (UploadFile) app.get("importfile");
        //geraLog("-------------" + file.getFullFileName());
        file.setDirectoryName("C:\\UPLOAD\\");
        //geraLog("############ FILE " + file.getFileName() + file.getAbsoluteFileName());

        String fileContent = "";
        try {
            //geraLog("############ TRY");
            fileContent = file.getFileOutputStream().toString("UTF-8");
            try {
                //geraLog("****************** ESCREVE NO DISCO.");
                file.writeToDisk();
                //geraLog("****************** OK.");
                retornoExcel = LerPlanilhaExcel.readExcel(file.getAbsoluteFileName());
                tratamentoImportacao();
            } catch (IOException e) {
                //geraLog("ERRO AO IMPORTAR" + e.getMessage());
            }
        } catch (UnsupportedEncodingException uee) {
            //geraLog("Unsupported encoding exception!!!");
            //geraLog("############ ERRO");
            uee.printStackTrace();
        }

        File x = new File(file.getAbsoluteFileName());
        x.deleteOnExit();

        if (!clientSession.hasLongOpCompleted()) {
            return 1;
        }
        //}
        //}
        return 0;
    }

    @SuppressWarnings({"deprecation", "rawtypes", "unused"})
    private void tratamentoImportacao() throws MXException, RemoteException {
        String CodigoExploracao = "";
        String ProtocoloRequisicao = "";
        String TipoElemento = "";
        String NumeroElemento = "";

        String xlsRetCodigoExploracao = "";
//        String xlsRetProtocoloRequisicao = "";
        String xlsRetTipoElemento = "";
        String xlsRetNumeroElemento = "";

        String xlsRetGenero = "";
        String xlsRetEspecie = "";
        String xlsRetEstratificacao = "";
        String xlsRetDataNascimento = "";
        String xlsRetDataIdentificacao = "";

        //geraLog("########### CRIA INSTANCIA MBO");
        MboSetRemote mboSet = app.getDataBean().getMboSet();

        for (int i = 0; i < mboSet.count(); i++) {

            CodigoExploracao = mboSet.getMbo(i).getMboSet("MARLPR01").getMbo(0).getMboSet("PRLINE").getMbo(0).getString("MASTORELOC");
            //geraLog("------------------------------- CodigoExploracao :" + CodigoExploracao);

            ProtocoloRequisicao = mboSet.getMbo(i).getMboSet("MARLPR01").getMbo(0).getString("PRNUM");
            //geraLog("------------------------------- ProtocoloRequisicao :" + ProtocoloRequisicao);

            TipoElemento = mboSet.getMbo(i).getMboSet("PRLINE").getMbo(0).getString("DESCRIPTION");
            //geraLog("------------------------------- TipoElemento :" + TipoElemento);

            NumeroElemento = mboSet.getMbo(i).getString("ASSETNUM");
            //geraLog("------------------------------- NumeroElemento :" + NumeroElemento.substring(1) + " *********** EXCEL TAMANHO :" + retornoExcel.size());

            for (int linha = 1; linha < retornoExcel.size(); linha++) {

                xlsRetCodigoExploracao = (String) ((List) retornoExcel.get(linha)).get(0);
//                xlsRetProtocoloRequisicao = (String) ((List) retornoExcel.get(linha)).get(1);
                xlsRetTipoElemento = (String) ((List) retornoExcel.get(linha)).get(1);
                xlsRetNumeroElemento = (String) ((List) retornoExcel.get(linha)).get(2);

                xlsRetGenero = (String) ((List) retornoExcel.get(linha)).get(3);
                xlsRetEspecie = (String) ((List) retornoExcel.get(linha)).get(4);
                xlsRetEstratificacao = (String) ((List) retornoExcel.get(linha)).get(5);
                xlsRetDataNascimento = (String) ((List) retornoExcel.get(linha)).get(6);
                xlsRetDataIdentificacao = (String) ((List) retornoExcel.get(linha)).get(7);

                /*	geraLog("---------------- ANTES DO IF xlsRetGenero : " + xlsRetGenero);
                geraLog("---------------- ANTES DO IF xlsRetEspecie : " + xlsRetEspecie);
                geraLog("---------------- ANTES DO IF xlsRetEstratificacao : " + xlsRetEstratificacao);
                geraLog("---------------- ANTES DO IF xlsRetDataNascimento : " + xlsRetDataNascimento);
                geraLog("---------------- ANTES DO IF xlsRetDataIdentificacao : " + xlsRetDataIdentificacao);
                geraLog("dentro do for : vai entrar no if " + xlsRetNumeroElemento + " %%%%" + NumeroElemento.substring(1) + "####" + xlsRetProtocoloRequisicao + "$$$$" + ProtocoloRequisicao);
                geraLog("ITEM 1 : " + xlsRetNumeroElemento + "comparar com :" + NumeroElemento + "E COMPARAR COM :" + xlsRetProtocoloRequisicao + " COMPARA COM : " + ProtocoloRequisicao);*/
                //if((xlsRetNumeroElemento.equalsIgnoreCase(NumeroElemento.substring(1)) && (xlsRetProtocoloRequisicao.equalsIgnoreCase(ProtocoloRequisicao)))){
//                if ((xlsRetNumeroElemento.equalsIgnoreCase(NumeroElemento) && (xlsRetProtocoloRequisicao.equalsIgnoreCase(ProtocoloRequisicao)))) {
                if ((xlsRetNumeroElemento.equalsIgnoreCase(NumeroElemento))) {
                    geraLog("Entro no IF");
                    //mboSet.getMbo(i).setFieldFlag("MAGEN", MboConstants.READONLY, false);
                    try {
                        mboSet.getMbo(i).setValue("MAGEN", xlsRetGenero); //GENERO
                    } catch (Exception e) {
                        throw new MXApplicationException("system", "notvalid", new String[]{"Especie do Animal ", xlsRetGenero});
                    }

                    //mboSet.getMbo(i).setFieldFlag("MAGEN", MboConstants.READONLY, true);
                    try {
                        mboSet.getMbo(i).setValue("MAESPECIE", xlsRetEspecie); //ESPECIE							
                    } catch (Exception e) {
                        throw new MXApplicationException("system", "notinvaluelist", new String[]{"Gênero do Animal ", xlsRetEspecie});
                    }
                    try {
                        mboSet.getMbo(i).setValue("MADATENASC", xlsRetDataNascimento); //DATA NASCIMENTO
                        mboSet.getMbo(i).setValue("MADATIDENT", xlsRetDataIdentificacao); //DATA IDENTIFICACAO
                    } catch (Exception e) {
                        throw new MXApplicationException("system", "invaliddate");
                    }
                    //geraLog("++++++++++++++++++++++++++++++++ PASSOU PELO IF");
                    break;
                }
                /*geraLog("---------------- ANTES DO IF xlsRetGenero : " + xlsRetGenero);
                geraLog("---------------- ANTES DO IF xlsRetEspecie : " + xlsRetEspecie);
                geraLog("---------------- ANTES DO IF xlsRetEstratificacao : " + xlsRetEstratificacao);
                geraLog("---------------- ANTES DO IF xlsRetDataNascimento : " + xlsRetDataNascimento);
                geraLog("---------------- ANTES DO IF xlsRetDataIdentificacao : " + xlsRetDataIdentificacao);*/
            }
            geraLog("++++++++++++++++++++++++++++++++ FOR DOS MBOS");
        }
        refreshTable();
        reloadTable();
        WebClientEvent event = sessionContext.getCurrentEvent();

        Utility.sendEvent(new WebClientEvent("refreshTable", event.getTargetId(), event.getValue(), sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", event.getTargetId(), event.getValue(), sessionContext));

        Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));

        mboSet.save();
    }

    public void geraLog(String texto) {
        try {
            BufferedWriter bufferOut = new BufferedWriter(new FileWriter("c:\\upload\\logimport.txt", true));

            bufferOut.write(texto + breakLine);
            bufferOut.close();
        } catch (IOException ex) {
            geraLog(ex.getMessage());
        }
    }
}