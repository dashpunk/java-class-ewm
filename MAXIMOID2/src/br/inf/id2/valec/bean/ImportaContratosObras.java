package br.inf.id2.valec.bean;

import java.io.File;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
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
public class ImportaContratosObras extends DataBean {

    private List retornoExcel = null;
    private String breakLine = "\r\n";

    public void setaCabecalho() throws MXException {
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new MXApplicationException("obras", "importacaoFalha");
        }
        File x = new File(file.getAbsoluteFileName());
        x.deleteOnExit();

        //if (!clientSession.hasLongOpCompleted()) {
        //   return 1;
        //}
        throw new MXApplicationException("obras", "importacaoSucesso");
    }
    @SuppressWarnings("unused")
    private void tratamentoImportacao() {
        try {
            MboRemote mbo = null;

            int posicaoLinha = 0;
            Locale ptBR = new Locale("pt", "BR");

            NumberFormat brFormat = NumberFormat.getNumberInstance(ptBR);
                        
            String xlsRetAssetNum = "";
            String xlsRetItemNum = "";
            String xlsRetIndiceEco = "";
            String xlsRetOrderQty = "";
            String xlsRetAmcustMate = "";
            //DecimalFormat df = new DecimalFormat("#,###.00");

            posicaoLinha = app.getDataBean("MAINRECORD").getMbo().getMboSet("CHILDCONTRACTS").getCurrentPosition();

            System.out.println("======= INICIANDO IMPORTACAO DE CONTRATOS =======");
            /*System.out.println("===========================" + (String) ((List) retornoExcel.get(0)).get(0));
            System.out.println("===========================" + (String) ((List) retornoExcel.get(0)).get(1));
            System.out.println("===========================" + (String) ((List) retornoExcel.get(0)).get(2));
            System.out.println("===========================" + (String) ((List) retornoExcel.get(0)).get(3));
            System.out.println("===========================" + (String) ((List) retornoExcel.get(0)).get(4));
            System.out.println("===========================" + (String) ((List) retornoExcel.get(0)).get(5));
            System.out.println("===========================" + (String) ((List) retornoExcel.get(0)).get(6));
             * 
             * 
            
            System.out.println("===========================" + (String) ((HashMap)((List)retornoExcel).get(0)).get(1));
            System.out.println("===========================" + (String) ((HashMap)((List)retornoExcel).get(0)).get(2));
            System.out.println("===========================" + (String) ((HashMap)((List) retornoExcel).get(0)).get(3));
            System.out.println("===========================" + (String) ((HashMap)((List) retornoExcel).get(0)).get(4));    
            System.out.println("===========================" + (String) ((HashMap)((List) retornoExcel).get(0)).get(5));
            */
            for (int linha = 0; linha < retornoExcel.size(); linha++) {

                //if (((List) retornoExcel.get(linha)).size() > 1) {
                    /*xlsRetItemNum = (String) ((List) retornoExcel.get(linha)).get(1);
                    xlsRetAssetNum = (String) ((List) retornoExcel.get(linha)).get(2);
                    xlsRetIndiceEco = (String) ((List) retornoExcel.get(linha)).get(3);
                    xlsRetOrderQty = (String) ((List) retornoExcel.get(linha)).get(4);
                    xlsRetAmcustMate = (String) ((List) retornoExcel.get(linha)).get(5);
                     
                    xlsRetItemNum = (String) ((List) retornoExcel.get(linha)).get(4);
                    xlsRetAssetNum = (String) ((List) retornoExcel.get(linha)).get(3);
                    xlsRetIndiceEco = (String) ((List) retornoExcel.get(linha)).get(2);
                    xlsRetOrderQty = (String) ((List) retornoExcel.get(linha)).get(1);
                    xlsRetAmcustMate = (String) ((List) retornoExcel.get(linha)).get(0);
                    */
                
                    /*[16/12/11 18:03:31:218 BRST] 0000006b SystemOut     O ######################################### xlsRetAssetNum : 24
                    [16/12/11 18:03:31:218 BRST] 0000006b SystemOut     O ######################################### xlsRetItemNum : 3882
                    [16/12/11 18:03:31:218 BRST] 0000006b SystemOut     O ######################################### xlsRetIndiceEco : 81
                    [16/12/11 18:03:31:218 BRST] 0000006b SystemOut     O ######################################### xlsRetOrderQty : 9483.81
                     */
                    System.out.println("ENTROU NO FOR");
                    
                    xlsRetItemNum = (String) ((HashMap)((List)retornoExcel).get(linha)).get(1);
                    xlsRetAssetNum =   (String) ((HashMap)((List) retornoExcel).get(linha)).get(2);
                    xlsRetIndiceEco = (String) ((HashMap)((List)retornoExcel).get(linha)).get(3);
                    xlsRetOrderQty = (String) ((HashMap)((List) retornoExcel).get(linha)).get(4);
                    xlsRetAmcustMate = (String) ((HashMap)((List) retornoExcel).get(linha)).get(5);    
                    
                    System.out.println("VARIAVEIS OK");
                    
                    mbo = app.getDataBean("MAINRECORD").getMbo().getMboSet("CHILDCONTRACTS").getMbo(posicaoLinha).getMboSet("CONTRACTLINE").add();

                    System.out.println(linha);
                    /*System.out.println("######################################### xlsRetAmcustMate : " + NumberFormat.getCurrencyInstance().format(Double.valueOf(xlsRetAmcustMate)).substring(3));
                    System.out.println("######################################### xlsRetAssetNum : " + xlsRetAssetNum);
                    System.out.println("######################################### xlsRetItemNum : " + xlsRetItemNum);
                    System.out.println("######################################### xlsRetIndiceEco : " + xlsRetIndiceEco);
                    System.out.println("######################################### xlsRetOrderQty : " + xlsRetOrderQty);
                    System.out.println("######################################### xlsRetAmcustMate : " + brFormat.format(Double.valueOf( xlsRetAmcustMate)));
                     * 
                     */

                    mbo.setValue("ITEMNUM", xlsRetItemNum); //Item (AnalÃ­tico)
                    mbo.setValue("ASSETNUM", xlsRetAssetNum); //Ativo Relacionado
                    mbo.setValue("INDECONID", xlsRetIndiceEco); //Ã�ndice EconÃ´mico
                    mbo.setValue("ORDERQTY", brFormat.format(Double.valueOf(xlsRetOrderQty))); //Quantidade
                    //mbo.setValue("AMCUSTMATE", brFormat.format(Double.valueOf(xlsRetAmcustMate))); //Custo do Item (P0)
                    mbo.setValue("AMCUSTMATE", brFormat.format(Double.valueOf(xlsRetAmcustMate))); //Custo do Item (P0)
                    //mbo.setValue("AMCUSTMATE", NumberFormat.getCurrencyInstance().format(Double.valueOf( xlsRetAmcustMate)).substring(3)); //Custo do Item (P0)
                    
                    app.getDataBean("MAINRECORD").getMbo().getMboSet("CHILDCONTRACTS").getMbo(posicaoLinha).getMboSet("CONTRACTLINE").save();
                 
                //}
             
            }

            app.getDataBean("MAINRECORD").reloadTable();
            app.getDataBean("MAINRECORD").refreshTable();
            app.getDataBean("MAINRECORD").save();
 
            WebClientEvent event = sessionContext.getCurrentEvent();

            Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));


        } catch (MXException ex) {
            Logger.getLogger(ContratosMestreRL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ContratosMestreRL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
