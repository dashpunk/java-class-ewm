package br.inf.id2.valec.bean;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.UploadFile;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import br.inf.id2.common.util.LerPlanilhaExcel;

/**
 *
 * @author Nelson Benevides
 */
public class ImportaMedicaoObras extends DataBean {

    private List retornoExcel = null;
    private String breakLine = "\r\n";

    public void setaCabecalho() throws MXException{
        throw new MXApplicationException("system", "invaliddate");
    }
    
	public int execute() throws MXException, RemoteException {
        UploadFile file = (UploadFile) app.get("importfile");
        file.setDirectoryName("C:\\UPLOAD\\");
        try {
            String fileContent = "";

            fileContent = file.getFileOutputStream().toString("UTF-8");
            file.writeToDisk();
            retornoExcel = LerPlanilhaExcel.readExcel(file.getAbsoluteFileName());

            System.out.println("INICIO DO TRATAMENTO DAS LINHAS");

            tratamentoImportacao();
        } catch (IOException e) {
            e.printStackTrace();
            //throw new MXApplicationException("obras", "importacaoFalha");
        }
        File x = new File(file.getAbsoluteFileName());
        x.deleteOnExit();
        if (!clientSession.hasLongOpCompleted()) {
            return 1;
        }
        //}
        //}
        
        throw new MXApplicationException("obras", "importacaoSucesso");      
    }
    @SuppressWarnings({ "unused", "rawtypes" })
    private void tratamentoImportacao() throws MXApplicationException, RemoteException {
        try {
            MboRemote mbo = null;

            MboSetRemote contractLine = MXServer.getMXServer().getMboSet("TVCONTRACTLINE", app.getDataBean("MAINRECORD").getMbo().getUserInfo());

            int posicaoLinha = 0;
            Locale ptBR = new Locale("pt", "BR");

            NumberFormat brFormat = NumberFormat.getNumberInstance(ptBR);
            
            int tipoMedicao = app.getDataBean("MAINRECORD").getMbo().getInt("GONUNUMMEDICAO")+6;

            String xlsRetItemValec = ""; //ITEM VALEC
            String xlsRetAssetNum = ""; //ITEM PGV
            String xlsRetItemNum = ""; //ATIVO
            String xlsRetDescricao = ""; //DESCRICAO
            String xlsRetIndiceEco = ""; //INDICE
            String xlsRetOrderQty = ""; //QUANTIDADE INICIAL
            String xlsRetAmcustMate = ""; //VALOR AP0
            String xlsRetValorMedicao = ""; //VALOR DA MEDICAO
            
            String itemNum = "";
            String assetNum = "";
            String contractNum = "";

            posicaoLinha = app.getDataBean("MAINRECORD").getMbo().getMboSet("GORLMEDCONTITEM01").getCurrentPosition();

            System.out.println("======= INICIANDO IMPORTACAO DE CONTRATOS =======");
            
            for (int linha = 0; linha < retornoExcel.size(); linha++) {
			
                System.out.println("ENTROU NO FOR");

                mbo = app.getDataBean("MAINRECORD").getMbo().getMboSet("GORLMEDCONTITEM01").add();

                itemNum = (String) ((HashMap)((List)retornoExcel).get(linha)).get(1);
                assetNum = (String) ((HashMap)((List)retornoExcel).get(linha)).get(2);
                contractNum = app.getDataBean("MAINRECORD").getMbo().getString("FKGOCONTRACTNUM");
                
                mbo.setValue("FKGONUNUMMEDICAO", app.getDataBean("MAINRECORD").getMbo().getString("GONUNUMMEDICAO"));
                mbo.setValue("FKGOCONTRACTNUM", contractNum);
                mbo.setValue("FKGOASSETNUM", assetNum);
                mbo.setValue("FKGOITEMNUM", itemNum);
                mbo.setValue("GONUQTDITEM", brFormat.format(Double.valueOf((String) ((HashMap)((List)retornoExcel).get(linha)).get(tipoMedicao))));
                
                System.out.println("where MASTERNUM = '"+contractNum+"' and ITEMNUM = '"+itemNum+"' AND ASSETNUM = '"+assetNum+"'");
                contractLine.setWhere("where MASTERNUM = '"+contractNum+"' and ITEMNUM = '"+itemNum+"' AND ASSETNUM = '"+assetNum+"'");
                
                contractLine.reset();
               
                if(contractLine.count() == 0){
                	 throw new MXApplicationException("system", "notvalid", new String[]{"Não foi possível encontrar o ativo: ", assetNum});
                }
                
                mbo.setValue("GONUVLRITEM", contractLine.getMbo(0).getDouble("unitcost"));

            }

            app.getDataBean("MAINRECORD").reloadTable();
            app.getDataBean("MAINRECORD").refreshTable();
            app.getDataBean("tbgrditensmed").reloadTable();
            app.getDataBean("tbgrditensmed").refreshTable();
    
            WebClientEvent event = sessionContext.getCurrentEvent();

            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));


        } 
        catch (MXException ex) 
        {
        	System.out.println("####################### Erro: "+ex.getMessage());
        }

    }
}
