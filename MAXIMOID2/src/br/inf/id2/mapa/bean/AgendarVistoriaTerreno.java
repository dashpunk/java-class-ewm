package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;

import psdi.mbo.MboSetRemote;
import psdi.mbo.custapp.CustomMboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class AgendarVistoriaTerreno extends psdi.webclient.system.beans.AppBean {

    String camposObrigatorios = "";
    String relacionamentosObrigatorios = "";

    /**
     *
     */
    public AgendarVistoriaTerreno() {
    }


    @Override
    public int ROUTEWF() throws MXException, RemoteException {
        MboSetRemote mboSR;
        int iTamanho = 0;

        camposObrigatorios = "";
        relacionamentosObrigatorios = "";
        
        mboSR = getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS");
        iTamanho = getTotalRegistros(mboSR);

        if (iTamanho <= 0) {
        	setResultadoRelacionamentoObrigatorio("Equipe");
//            throw new MXApplicationException("agendarvistoriaterreno", "RelacionamentoMARL05MEMOSSemRegistros");
        }

        if (!preencimentoObrigatorio((MboSet) getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS"), "ID2LID", 1)) {
        	setResultadoRelacionamentoObrigatorio("Deve selecionar um Líder");
//            throw new MXApplicationException("agendarvistoriaterreno", "RelacionamentoMARL05MEMOSLider");
        }

        MboRemote mbo;
        for (int i = 0; ((mbo = getMbo().getMboSet("MARL05WOR").getMbo(i)) != null); i++) {
            //Início Programado
            if (mbo.isNull("MADTPROINI")) {
                setResultadoCampoObrigatorio("Início Programado");
                //throw new MXApplicationException("agendarvistoriaterreno", "MADTPROINIIsNull");
            }
            //Término Programado
            if (mbo.isNull("MADTPROFIM")) {
                setResultadoCampoObrigatorio("Término Programado");
                //throw new MXApplicationException("agendarvistoriaterreno", "MADTPROFIMIsNull");
            }
        }
        for (int i = 0; ((mbo = getMbo().getMboSet("MARL05WOR").getMbo(i)) != null); i++) {
            MboRemote mbob;

            for (int j = 0; ((mbob = mbo.getMboSet("MARL05MEMOS").getMbo(j)) != null); j++) {
                //CPF
                if (mbob.isNull("ID2CPF")) {
                    setResultadoCampoObrigatorio("CPF");
                    //throw new MXApplicationException("agendarvistoriaterreno", "ID2CPFIsNull");
                }
                MboRemote mboc;
                for (int k = 0; ((mboc = mbob.getMboSet("MARLPER01").getMbo(k)) != null); k++) {
                    //Nome
                    if (mboc.isNull("DISPLAYNAME")) {
                        setResultadoCampoObrigatorio("Nome");
                        //throw new MXApplicationException("agendarvistoriaterreno", "DISPLAYNAMEIsNull");
                    }
                }
            }
        }
        //Status
        if (getMbo().isNull("ID2APRAGECHE")) {
            setResultadoCampoObrigatorio("Status");
            //throw new MXApplicationException("agendarvistoriaterreno", "ID2APRAGECHEIsNull");
        }
        if (!camposObrigatorios.equals("")) {
            throw new MXApplicationException("system", "null", new String[]{camposObrigatorios});
        }
        if (!relacionamentosObrigatorios.equals("")) {
            throw new MXApplicationException("system", "relacionamentoVazio", new String[]{relacionamentosObrigatorios});
        }
        return super.ROUTEWF();
    }

    /**
     *
     */
    public void adicionaTodos() {
        //try {
        //System.out.println("Adicionou");

        try {
            CustomMboSet memoset;

            MboRemote memosetWO = getMbo().getMboSet("MARL05WOR").getMbo(0);
            MboRemote memosetLoc = getMbo();
            //System.out.println("afet memosSet...");
            //Thread.sleep(3000);
            //System.out.println("count " + getMbo().getMboSet("PERSON2WO").count());
            //Thread.sleep(3000);
            ////System.out.println("vai começar");
            boolean existe;
            for (int i = 0; i < getMbo().getMboSet("PERSON2WO").count(); i++) {
                //System.out.println("i " + i);
                try {
                    existe = false;
                    //System.out.println("j = " + getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS").count());
                    for (int j = 0; j < getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS").count(); j++) {
                        //System.out.println("j " + j);
                        //System.out.println("ID2CPF   " + getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS").getMbo(j).getString("ID2CPF"));
                        //System.out.println("PERSONID " + getMbo().getMboSet("PERSON2WO").getMbo(i).getString("PERSONID"));
                        if (getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS").getMbo(j).getString("ID2CPF").equalsIgnoreCase(getMbo().getMboSet("PERSON2WO").getMbo(i).getString("PERSONID"))) {
                            //System.out.println("EXISTS");
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        MboRemote memo = (MboRemote) getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS").add();
                        //System.out.println("adicionou");
                        //Thread.sleep(3000);
                        //System.out.println("personid " + getMbo().getMboSet("PERSON2WO").getMbo(i).getString("PERSONID"));
                        memo.setValue("ID2CPF", getMbo().getMboSet("PERSON2WO").getMbo(i).getString("PERSONID"));
                        //System.out.println("wonum " + memosetWO.getString("WONUM"));
                        //Thread.sleep(3000);
                        memo.setValue("WONUM", memosetWO.getString("WONUM"));
                        //System.out.println("location " + memosetLoc.getString("LOCATION"));
                        //Thread.sleep(3000);
                        memo.setValue("LOCATION", memosetLoc.getString("LOCATION"));

                        //Thread.sleep(3000);
                        getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS").save();
                    }
                } catch (Exception e) {
                }
                //System.out.println("after save");
                //Thread.sleep(3000);
            }
            memoset = (CustomMboSet) getMbo().getMboSet("MARL05WOR").getMbo(0).getMboSet("MARL05MEMOS");
        } catch (MXException ex) {
            Logger.getLogger(AgendarVistoriaTerreno.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(AgendarVistoriaTerreno.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("end");
        refreshTable();
        reloadTable();
        refreshTable();
        reloadTable();
        //Thread.sleep(3000);
        //} catch (InterruptedException ex) {
        //    Logger.getLogger(AgendarVistoriaTerreno.class.getName()).log(Level.SEVERE, null, ex);
        // }
    }

    /**
     *
     * @param mboSet
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    public static int getTotalRegistros(MboSetRemote mboSet) throws MXException, RemoteException {
        int resultado = 0;
        MboRemote mbo;
        for (int i = 0; ((mbo = mboSet.getMbo(i))) != null; i++) {
            if (!mbo.toBeDeleted()) {
                resultado++;
            }
        }
        return resultado;
    }

    /**
     *
     * @param aMboSet
     * @param atributoYorN
     * @param preenchimentoTamanho
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    public static boolean preencimentoObrigatorio(MboSet aMboSet, String atributoYorN, int preenchimentoTamanho) throws MXException, RemoteException {

        int contador = 0;
        ////Uteis.espera("***************Antes de chamar...");

        Mbo linha;
        ////Uteis.espera("***************Após de chamar...");

        if (aMboSet != null) {
            for (int i = 0; i < aMboSet.count(); i++) {
                linha = (Mbo) aMboSet.getMbo(i);
                if ((!linha.getString(atributoYorN).equals("N")) && (!linha.toBeDeleted())) {
                    contador++;
                    ////Uteis.espera("***************Contador = " + contador);
                }
            }

        }

        ////Uteis.espera("***************Total Contador = " + contador);
        if (contador == preenchimentoTamanho) {
            return true;
        } else {
            return false;
        }

    }

    private void setResultadoCampoObrigatorio(String string) {
        if (string.equals("")) {
            camposObrigatorios = "";
        } else {
            if (camposObrigatorios.indexOf(string) == -1) {
                camposObrigatorios += "\n" + string;
                //System.out.println("*** "+camposObrigatorios);
            }
        }
    }

    private void setResultadoRelacionamentoObrigatorio(String string) {
        if (string.equals("")) {
            relacionamentosObrigatorios = "";
        } else {
            if (relacionamentosObrigatorios.indexOf(string) == -1) {
                relacionamentosObrigatorios += "\n" + string;
                //System.out.println("*** "+relacionamentosObrigatorios);
            }
        }
    }
}
