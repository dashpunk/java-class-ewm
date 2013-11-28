package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;

import psdi.webclient.system.beans.DataBean;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class GestaoRegistroEstabelecimento extends psdi.webclient.system.beans.AppBean {

    /**
     *
     */
    public GestaoRegistroEstabelecimento() {
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        super.dataChangedEvent(speaker);
        try {
        	
            MboSet mboSetOcorrencia = (MboSet) app.getDataBean().getMbo(0).getMboSet("MARL15MATBOCO");
            MboRemote mbo;
            
            for (int i = 0;((mbo = mboSetOcorrencia.getMbo(i)) != null); i++) {
            	    System.out.println("ENTROU NO FOR DO PRINT");
                    mbo.setValue("PRINT", false);       
            }
            
            int atual = mboSetOcorrencia.getCurrentPosition();
            for (int i = 0;((mbo = mboSetOcorrencia.getMbo(i)) != null); i++) {
                if (mbo.isSelected() && i != atual) {
                    mbo.setValue("PRINT", false);
                }
            }
            mboSetOcorrencia.save();
            reloadTable();
            refreshTable();

        } catch (MXException ex) {
            Logger.getLogger(GestaoRegistroEstabelecimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(GestaoRegistroEstabelecimento.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Override
    public synchronized boolean fetchRecordData() throws MXException,
    		RemoteException {
    	super.fetchRecordData();
    	
        MboSet mboSetOcorrencia = (MboSet) app.getDataBean().getMbo(0).getMboSet("MARL15MATBOCO");
        MboRemote mbo;
        
        for (int i = 0;((mbo = mboSetOcorrencia.getMbo(i)) != null); i++) {
        	    System.out.println("ENTROU NO FOR DO PRINT");
                mbo.setValue("PRINT", false);       
        }
            
    	return super.fetchRecordData();
    }
}
