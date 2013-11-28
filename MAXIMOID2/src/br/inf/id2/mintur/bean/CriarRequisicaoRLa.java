package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.BoundComponentInstance;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class CriarRequisicaoRLa extends psdi.webclient.beans.desktopreq.DesktopReqAppBean {

    public CriarRequisicaoRLa() {
        System.out.println("---CriarRequisicaoRL 1");
    }

    @Override
    public synchronized void save() throws MXException {
        System.out.println("---CriarRequisicaoRL save");
        subistituiValor();
        System.out.println("---CriarRequisicaoRL FIM");
        super.save();
    }

    @Override
    public int SAVE() throws RemoteException, MXException {
        System.out.println("---CriarRequisicaoRL SAVE");
        subistituiValor();
        System.out.println("---CriarRequisicaoRL FIM");
        return super.SAVE();
    }

//    @Override
//    public void submit() throws MXException, RemoteException {
//        System.out.println("---CriarRequisicaoRL submit");
//        subistituiValor();
//        getMbo().getThisMboSet().save(MboConstants.NOACCESSCHECK);
//        System.out.println("---CriarRequisicaoRL FIM");
//        super.submit();
//    }
    @Override
    public synchronized void fireDataChangedEvent() {
        System.out.println("---CriarRequisicaoRL fireDataChangedEvent");
        subistituiValor();
        super.fireDataChangedEvent();
    }

    @Override
    public synchronized void listenerChangedEvent(DataBean speaker) {
        System.out.println("---CriarRequisicaoRL listenerChangedEvent");
        subistituiValor();
        super.listenerChangedEvent(speaker);
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        System.out.println("---CriarRequisicaoRL dataChangedEvent");
        subistituiValor();
        super.dataChangedEvent(speaker);
    }

    private void subistituiValor() {

        System.out.println("---CriarRequisicaoRL subistituiValor");
        try {
            System.out.println("---CriarRequisicaoRL count a " + getMbo().getMboSet("MXRL01TBCASE").count());
            if (getMbo().getMboSet("MXRL01TBCASE").count() > 0) {
                System.out.println("a");
                if (getMbo().getMboSet("MXRL01TBCASE").getMbo(0).getMboSet("RHRLUO0003").count() > 0) {
                    System.out.println("b");
                    System.out.println("c");
                    String valor = getMbo().getMboSet("MXRL01TBCASE").getMbo(0).getMboSet("RHRLUO0003").getMbo(0).getString("RHSTSGLUO");
                    System.out.println("--- valor = " + valor);
                    getMbo().setValue("RHSTSGLUO", valor, MboConstants.NOACCESSCHECK);
                    System.out.println("c fim");
                }
            }
        } catch (MXException e) {
            System.out.println("---CriarRequisicaoRL e " + e.getMessage());
            e.getStackTrace();
        } catch (RemoteException e) {
            System.out.println("---CriarRequisicaoRL e " + e.getMessage());
            e.getStackTrace();
        }

    }
}
