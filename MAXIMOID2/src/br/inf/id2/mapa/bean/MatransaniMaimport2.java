package br.inf.id2.mapa.bean;

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
public class MatransaniMaimport2 extends DataBean {

    private List retornoExcel = null;
    private String breakLine = "\r\n";

    public void setaCabecalho() throws MXException {
        throw new MXApplicationException("system", "invaliddate");
    }

    public int execute() throws MXException, RemoteException {

        UploadFile file = (UploadFile) app.get("importfile");

        file.setDirectoryName("C:\\UPLOAD\\");

        String fileContent = "";
        try {

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
    	String assetNum = "";

        //geraLog("########### CRIA INSTANCIA MBO");
        MboSetRemote mboSet = app.getDataBean().getMboSet();
        
        MboRemote mbo;
        
        MboSetRemote assetSet;
        assetSet = psdi.server.MXServer.getMXServer().getMboSet("ASSET", sessionContext.getUserInfo());

        String assetNotFound = "";
        
        int matranseleid = app.getDataBean("MAINRECORD").getMbo().getInt("MATRANSELEID");
        String malocalori = app.getDataBean("MAINRECORD").getMbo().getString("MALOCALORI");
        
        for (int linha = 0; linha < retornoExcel.size(); linha++) {

        	assetNum = (String) ((List) retornoExcel.get(linha)).get(0);
        	
        	assetSet.setWhere("ASSETNUM = \'"+assetNum.trim()+"\' AND STATUS = 'EM USO' AND LOCATION IN (SELECT STORELOC FROM PO WHERE ID2STORELOCDEST = \'"+app.getDataBean("MAINRECORD").getMbo().getString("MALOCALORI")+"\' AND STATUS IN ('EM_TRANSITO','BAIXADA')) AND MAESTRATIFICACAO IN (SELECT ITEMNUM FROM POLINE WHERE PONUM IN (SELECT PONUM FROM PO WHERE ID2STORELOCDEST = \'"+app.getDataBean("MAINRECORD").getMbo().getString("MALOCALORI")+"\' AND STATUS IN ('EM_TRANSITO','BAIXADA')))");
            assetSet.reset();
            
            System.out.println(assetSet.getWhere());
            
            if (assetSet.count() > 0) {
            	
            	mbo = app.getDataBean("MAINRECORD").getMbo().getMboSet("MARLTRANSELELINE").add();
            	
            	mbo.setValue("MATRANSELEID", matranseleid, MboConstants.NOVALIDATION_AND_NOACTION);
            	mbo.setValue("MALOCALORI", malocalori, MboConstants.NOVALIDATION_AND_NOACTION);
            	
            	mbo.setValue("ASSETNUM", assetNum.trim(), MboConstants.NOVALIDATION_AND_NOACTION);
            	
            	mbo.getThisMboSet().save();
            	
            } else {
            	assetNotFound += assetNum +"-";
            	continue;
            }
            

        	
        }
        
               
        WebClientEvent event = sessionContext.getCurrentEvent();

        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));

        Utility.sendEvent(new WebClientEvent("SAVE", app.getApp(), null, sessionContext));

        Utility.sendEvent(new WebClientEvent("dialogclose", event.getTargetId(), event.getValue(), sessionContext));

        if (assetNotFound.length() > 0) {
        	
        	throw new MXApplicationException("matransani", "maimport2AssetNotFound", new String[]{assetNotFound.substring(1, assetNotFound.length()-1)});
        	
        }
        
        mboSet.save();
    }

}

/*            	mbo = assetSet.getMbo(0);

String location = mbo.getString("LOCATION"); 
String maEstratificacao = mbo.getString("MAESTRATIFICACAO");


poSet.setWhere("LOCATION = '" +assetNum + "'");
poSet.reset();

String storeLoc = mbo.getString("MAESTRATIFICACAO");


if (mbo.getString("STATUS").equalsIgnoreCase("EM USO") && mbo.getString("STATUS").equalsIgnoreCase("EM USO")) {
	
} 
*/