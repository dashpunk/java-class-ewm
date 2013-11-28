package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class RequisicoesElementos extends psdi.webclient.beans.pr.PRAppBean {

    public RequisicoesElementos() {
        super();
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {

        try {
            super.dataChangedEvent(speaker);

            MboRemote mbo;
            System.out.println(">>> mboSet " + getMbo().getMboSet("PRLINE").count());
            for (int i = 0; (mbo = getMbo().getMboSet("PRLINE").getMbo(i)) != null; i++) {
                System.out.println(">>> i " + i);
                if (app.getApp().equalsIgnoreCase("MA_CL01PR")) {
                    System.out.println(">>> v " + getMbo().getString("MAOESA"));
                    mbo.setValue("MAOESA", getMbo().getString("MAOESA"));
                } else {
                    System.out.println(">>> v " + getMbo().getString("MASTORELOC"));
                    mbo.setValue("MASTORELOC", getMbo().getString("MASTORELOC"));
                }
            }
        } catch (MXException ex) {
            Logger.getLogger(RequisicoesElementos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(RequisicoesElementos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
