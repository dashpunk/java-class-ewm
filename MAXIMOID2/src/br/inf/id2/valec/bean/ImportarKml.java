package br.inf.id2.valec.bean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.List;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;


import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.UploadFile;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

public class ImportarKml extends DataBean {

    private List retornoExcel = null;
    private String breakLine = "\r\n";

    public void setaCabecalho() throws MXException {
        throw new MXApplicationException("system", "invaliddate");
    }

    public int execute() throws MXException, RemoteException {
        app.getAppBean().save();
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
                //retornoExcel = LerPlanilhaExcel.readExcel(file.getAbsoluteFileName());
                File fis = null;

                fis = new File(file.getAbsoluteFileName());

                //XmlLoader xml = new XmlLoader();
                try {
                    FileReader reader = new FileReader(fis.getAbsolutePath());
                    BufferedReader leitor = new BufferedReader(reader);
                    String linha = null;
                    String valor = "";
                    boolean importar = false;
                    while ((linha = leitor.readLine()) != null) {
                        if (importar) {
                            valor = linha.trim();
                        }
                        importar = (linha.contains("<coordinates>"));

                    }

                    String[] valores = valor.split(",0 ");

                    MboSetRemote mboset = app.getDataBean("MAINRECORD").getMboSet().getMbo().getMboSet("MXRL01ASSGEO");
                    mboset.deleteAll();
                    MboRemote mbo;
                    for (int i = 0; i < valores.length; i++) {
                        String string = valores[i];
                        //System.out.println(string);
                        mbo = mboset.add();
                        String[] latLon = string.split(",");
                        geraLog(latLon[0].replaceAll("\\.", "\\,"));
                        geraLog(latLon[1].replaceAll("\\.", "\\,"));
                        mbo.setValue("ASSETNUM", app.getDataBean().getMboSet().getMbo().getString("ASSETNUM"));
                        mbo.setValue("NUSEQ", i + 1);
                        mbo.setValue("UTMLAT", latLon[1].replaceAll("\\.", "\\,"));
                        mbo.setValue("UTMLON", latLon[0].replaceAll("\\.", "\\,"));

                    }
                    geraLog("antes do save");
                    mboset.save();
                    geraLog("após save");
                } catch (Exception e) {
                    geraLog(e.getMessage());
                    e.printStackTrace();
                }


                //tratamentoImportacao();
            } catch (IOException e) {
                geraLog(e.getMessage());
                //geraLog("ERRO AO IMPORTAR" + e.getMessage());
            }
        } catch (UnsupportedEncodingException uee) {
            geraLog(uee.getMessage());
            //geraLog("Unsupported encoding exception!!!");
            //geraLog("############ ERRO");
            uee.printStackTrace();
        }

        //File x = new File(file.getAbsoluteFileName());
        //x.deleteOnExit();

        //if (!clientSession.hasLongOpCompleted()) {
        //    return 1;
        //}
        //}
        //}
//        geraLog("a");
//        geraLog("a1");
//        
//        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
//        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));
//        //Cria dois objetos de evento, sendo que o eventType receberá a página no qual queremos redirecionar.
//        geraLog("b");
//        //WebClientEvent eventType = new WebClientEvent("loadapp", null, "mxcl01ass", sessionContext);
//        geraLog("c");
//
//        //WebClientEvent eventExec = new WebClientEvent("execevent", sessionContext.getCurrentAppId(), eventType, sessionContext);
//        geraLog("d");
//
//        //Define o evento criado na sessionContext
//        //sessionContext.setCurrentEvent(eventExec);
//        geraLog("e");
//        //O método sendEvent irá redirecionar a página registrada
////        Utility.sendEvent(eventExec);
//        geraLog("f");
//
        WebClientEvent event = sessionContext.getCurrentEvent();
        Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));
        
        sessionContext.queueRefreshEvent();

        //TODO testar a parte abaixo
//        String _assetid = String.valueOf(Integer.parseInt(getString("assetid")) - 1);
//        WebClientEvent eventType = new WebClientEvent("loadapp", null, "mxcl01ass", sessionContext);
//        WebClientEvent eventExec = new WebClientEvent("execevent", sessionContext.getCurrentAppId(), eventType, sessionContext);
//        eventExec.setAdditionalEvent("useqbe");
//        eventExec.setAdditionalEventValue("assetid=" + _assetid);
//
//        sessionContext.setCurrentEvent(eventExec);
//        Utility.sendEvent(eventExec);
        
        return 1;
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
