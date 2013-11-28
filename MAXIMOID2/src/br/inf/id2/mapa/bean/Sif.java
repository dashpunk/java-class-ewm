package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.beans.common.StatefulAppBean;
import psdi.webclient.system.beans.DataBean;

public class Sif extends StatefulAppBean {

    public Sif() {
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        super.dataChangedEvent(speaker);
        try {
            System.out.println("--------------- speaker " + speaker.getUniqueIdName());
            Thread.sleep(4000);
            selecionarApenasRegistroAtual(getMbo().getMboSet("ID2LUCTYPE06"), "MAFUN");
        } catch (InterruptedException ex) {
            Logger.getLogger(Sif.class.getName()).log(Level.SEVERE, null, ex);

        } catch (MXException ex) {
            Logger.getLogger(Sif.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Sif.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selecionarApenasRegistroAtual(MboSetRemote mboSet, String atributo) throws RemoteException, MXException {
        try {
            int posicaoAtual = mboSet.getCurrentPosition();
            System.out.println("------------- posicaoAtual " + posicaoAtual);
            System.out.println("------------- count " + mboSet.count());
            System.out.println("------------- atributo " + mboSet.getMbo(posicaoAtual).getBoolean(atributo));
            Thread.sleep(2000);
            if (mboSet.getMbo(posicaoAtual).getBoolean(atributo)) {

                MboRemote mbo;

                for (int i = 0; ((mbo = mboSet.getMbo(i)) != null); i++) {
                    System.out.println("-------------- i "+i);
                    System.out.println("-------------- atributo i "+mboSet.getMbo(i).getBoolean(atributo));
                    Thread.sleep(4000);
                    if ((i != posicaoAtual) && (mboSet.getMbo(i).getBoolean(atributo))) {
                        System.out.println("--------------- match");
                        mboSet.getMbo(i).setValue(atributo, false);
                        System.out.println("--------------- after setValue");
                    }

                }
            }
        } catch (InterruptedException e) {
        }
    }
}
