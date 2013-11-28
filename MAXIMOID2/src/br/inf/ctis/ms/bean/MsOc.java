package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;

import psdi.webclient.system.beans.DataBean;

/**
 *
 * @author Willians L Andrade
 *
 */
public class MsOc extends psdi.webclient.system.beans.AppBean {

    public MsOc() {
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        super.dataChangedEvent(speaker);
        try {
        	
            MboSet mboSetOrdemCompra = (MboSet) app.getDataBean().getMbo(0).getMboSet("MSTBMEDICAMENTO");
            MboRemote mbo;
            
            for (int i = 0;((mbo = mboSetOrdemCompra.getMbo(i)) != null); i++) {
            	    System.out.println("ENTROU NO FOR DO PRINT");
                    mbo.setValue("SELECIONADO", false);       
            }
            
            int atual = mboSetOrdemCompra.getCurrentPosition();
            for (int i = 0;((mbo = mboSetOrdemCompra.getMbo(i)) != null); i++) {
                if (mbo.isSelected() && i != atual) {
                    mbo.setValue("SELECIONADO", false);
                }
            }
            mboSetOrdemCompra.save();
            reloadTable();
            refreshTable();

        } catch (MXException ex) {
            Logger.getLogger(MsOc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(MsOc.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Override
    public synchronized boolean fetchRecordData() throws MXException,
    		RemoteException {
    	super.fetchRecordData();
    	
        MboSet mboSetOrdemCompra = (MboSet) app.getDataBean().getMbo(0).getMboSet("MSTBMEDICAMENTO");
        MboRemote mbo;
        
        for (int i = 0;((mbo = mboSetOrdemCompra.getMbo(i)) != null); i++) {
        	    System.out.println("ENTROU NO FOR DO PRINT");
                mbo.setValue("SELECIONADO", false);       
        }
            
    	return super.fetchRecordData();
    }
}
