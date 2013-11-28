package br.inf.id2.valec.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.app.contract.Contract;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * @author Nelson Benevides dos Santos
 * 
 */
public class ContratosMestreRL extends AppBean {

    public ContratosMestreRL() {
    }

    /*public int LERPLANILHA() {
        try {
            MboRemote mbo = getMbo().getMboSet("NOME DO RELACIONAMENTO").add();
            
            mbo.setValue("CAMPO", "VALOR");
            
            
            getMbo().getMboSet("NOME DO RELACIONAMENTO").save();
            
            refreshTable();
            reloadTable();
            
            
            
        } catch (MXException ex) {
            Logger.getLogger(ContratosMestreRL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ContratosMestreRL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        return 0;

    }*/
}
