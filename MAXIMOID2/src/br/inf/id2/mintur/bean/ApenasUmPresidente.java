package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import br.inf.id2.common.util.Executa;

/**
 * @author Patrick Silva
 */
public class ApenasUmPresidente extends psdi.webclient.beans.common.StatefulAppBean {

    public ApenasUmPresidente() {
        System.out.println("*** ApenasUmPresidente");
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        super.dataChangedEvent(speaker);
        try {
            System.out.println("*** speaker " + speaker.getUniqueIdName());
            //Endereco
            if (speaker.getUniqueIdName().equalsIgnoreCase("MXTBPESCOMISSID")) {
                System.out.println("MXTBPESCOMISSID inicio");
                System.out.println("MXRL01PESCOMISS count "+getMbo().getMboSet("MXRL01PESCOMISS").count());
                
                //Verifica antes se
                int linha = getMbo().getMboSet("MXRL01PESCOMISS").getCurrentPosition();
                System.out.println("############ Linha atual = " + linha + " | MBO = " + getMbo().getMboSet("MXRL01PESCOMISS").getMbo(linha));
                if (getMbo().getMboSet("MXRL01PESCOMISS").getMbo(linha).getBoolean("PRESIDENTE")) {
                	Executa.setSelecionaAtualDeselecionarDemais(getMbo().getMboSet("MXRL01PESCOMISS"), "PRESIDENTE");
                }
                System.out.println("MXTBPESCOMISSID FIM");
            }
        } catch (MXException ex) {
            Logger.getLogger(ApenasUmPresidente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ApenasUmPresidente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
