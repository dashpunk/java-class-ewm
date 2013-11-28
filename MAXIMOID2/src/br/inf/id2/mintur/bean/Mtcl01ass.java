package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.util.MXException;


/**
 *
 * @author Ricardo S Gomes
 *
 */
public class Mtcl01ass extends psdi.webclient.beans.asset.AssetAppBean {

    /**
     *
     */
    public Mtcl01ass() {
    }

    @Override
    public synchronized boolean fetchRecordData() throws MXException, RemoteException {
        System.out.println("---fetchRecordData ");
        bloqueiaRegistros();

        return super.fetchRecordData();
    }


  
//    @Override
//    public synchronized void dataChangedEvent(DataBean speaker) {
//        System.out.println("---dataChangedEvent");
//        super.dataChangedEvent(speaker);
//        try {
//            bloqueiaRegistros();
//        } catch (RemoteException ex) {
//            Logger.getLogger(Mtcl01ass.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MXException ex) {
//            Logger.getLogger(Mtcl01ass.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    @Override
    public int SAVE() throws MXException, RemoteException {

        int retorno = super.SAVE();
        bloqueiaRegistros();
        refreshTable();
        System.out.println("---SAVE");
        return retorno;
    }




    private void bloqueiaRegistros() throws RemoteException, MXException {
        System.out.println("---bloqueiaRegistros "+getMbo().getMboSet("MTRL01REDREA").count());
        System.out.println("---bloqueiaRegistros "+getMbo().getMboSet("MTRL02REDREA").count());
        MboRemote mbo;

        for (int i = 0; ((mbo = getMbo().getMboSet("MTRL01REDREA").getMbo(i)) != null); i++) {
            mbo.setFlag(MboConstants.READONLY, true);
        }
        for (int i = 0; ((mbo = getMbo().getMboSet("MTRL02REDREA").getMbo(i)) != null); i++) {
            mbo.setFlag(MboConstants.READONLY, true);
        }

    }
}
