package br.inf.magna.mdic.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.List;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.UploadFile;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import br.inf.id2.common.util.LerPlanilhaExcel;

/**
 * 
 * @author Ricardo S Gomes
 *
 */
public class ImportaHistoricoSMS extends DataBean {

	
    private List retornoExcel = null;
    private String breakLine = "\r\n";

    public void setaCabecalho() throws MXException {
        throw new MXApplicationException("system", "invaliddate");
    }

    public int execute() throws MXException, RemoteException {

    	System.out.println("----- ImportaHistoricoSMS");
    	
        UploadFile file = (UploadFile) app.get("importfile");

        file.setDirectoryName("C:\\UPLOAD\\");

        String fileContent = "";
        try {

        	fileContent = file.getFileOutputStream().toString("UTF-8");
            try {
                file.writeToDisk();
                retornoExcel = LerPlanilhaExcel.readExcel(file.getAbsoluteFileName());
                tratamentoImportacao();
            } catch (IOException e) {
            	System.out.println("----------- ex lerPlanilha "+e.getMessage());
            	e.getStackTrace();
            }
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        File x = new File(file.getAbsoluteFileName());
        x.deleteOnExit();

        if (!clientSession.hasLongOpCompleted()) {
            return 1;
        }
        return 0;
    }

    @SuppressWarnings({"deprecation", "rawtypes", "unused"})
    private void tratamentoImportacao() throws MXException, RemoteException {
    	System.out.println("-----tratamentoImportacao ");

        MboSetRemote mboSet = app.getDataBean().getMboSet();
        
        MboRemote mbo;
        
        MboSetRemote assetSet;
        assetSet = psdi.server.MXServer.getMXServer().getMboSet("MXSMS", sessionContext.getUserInfo());
    	//assetSet.setWhere("");
        //assetSet.reset();

        String tipo = "";
        String sender = "";
        String target = "";
        String operadora = "";
        String vinculado = "";
        
        System.out.println("-----tratamentoImportacao linhas excel "+retornoExcel.size());
        
        for (int linha = 1; linha < retornoExcel.size(); linha++) {
        	
        	System.out.println("-----tratamentoImportacao linha "+linha);
        	
        	tipo = (String) ((List) retornoExcel.get(linha)).get(0);
        	sender = (String) ((List) retornoExcel.get(linha)).get(1);
        	target = (String) ((List) retornoExcel.get(linha)).get(2);
        	operadora = (String) ((List) retornoExcel.get(linha)).get(3);
        	vinculado = (String) ((List) retornoExcel.get(linha)).get(4);
            	
        	System.out.println("-----tratamentoImportacao add ");
            mbo = app.getDataBean("MAINRECORD").getMboSet().add();
            	
            System.out.println("-----tratamentoImportacao add FIM ");
            
            mbo.setValue("WSTIPO", tipo, MboConstants.NOACCESSCHECK);
            mbo.setValue("WSSENDER", sender, MboConstants.NOACCESSCHECK);
            mbo.setValue("WSTARGET", target, MboConstants.NOACCESSCHECK);
            mbo.setValue("WSOPERADORA", operadora, MboConstants.NOACCESSCHECK);
            mbo.setValue("WSSCHED", vinculado, MboConstants.NOACCESSCHECK);
            
            System.out.println("-----tratamentoImportacao add  columns ");
        	
        }
        
        System.out.println("-----tratamentoImportacao before save");
        save();
        System.out.println("-----tratamentoImportacao after save");
       
        refreshTable();
        reloadTable();
        
        WebClientEvent event = sessionContext.getCurrentEvent();

        Utility.sendEvent(new WebClientEvent("refreshTable", event.getTargetId(), event.getValue(), sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", event.getTargetId(), event.getValue(), sessionContext));

        Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));

        mboSet.save();
    }

}
