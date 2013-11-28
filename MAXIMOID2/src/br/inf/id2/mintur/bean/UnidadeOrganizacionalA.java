package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.util.Date;
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
public class UnidadeOrganizacionalA extends psdi.webclient.system.beans.AppBean  {

    String[][] pessoas;
    int id;
    private final int DEL = 1;
    private final int NEW = 2;
    private final int OLD = 3;

    public UnidadeOrganizacionalA() {
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        try {
            if (pessoas == null || id != getMbo().getInt(("RHTBUOID"))) {

                int contador = getMbo().getMboSet("RHRLVAGA0001").count();
                pessoas = new String[contador][2];
                System.out.println("---------- contador " + contador);
                for (int i = 0; i < contador; i++) {
                    System.out.println("------------- i " + i);
                    System.out.println("--valor nullo " + getMbo().getMboSet("RHRLVAGA0001").getMbo(i).getString("RHSTCODSERV"));
                    pessoas[i][0] = getMbo().getMboSet("RHRLVAGA0001").getMbo(i).getInitialValue("RHSTCODSERV").asString();
                    pessoas[i][1] = getMbo().getMboSet("RHRLVAGA0001").getMbo(i).getInitialValue("RHSTCODTIPCAR").asString();
                    System.out.println("----- pessoa atribuida: " + pessoas[i][0]);
                    System.out.println("----- pessoa atribuida: " + pessoas[i][1]);
                }
                id = getMbo().getInt("RHTBUOID");
                System.out.println("------ ID " + id);
            }
        } catch (MXException ex) {
            Logger.getLogger(UnidadeOrganizacionalA.class.getName()).log(Level.SEVERE, null, ex);
            ex.getStackTrace();
        } catch (RemoteException ex) {
            Logger.getLogger(UnidadeOrganizacionalA.class.getName()).log(Level.SEVERE, null, ex);
            ex.getStackTrace();
        }
        super.dataChangedEvent(speaker);
    }

    @Override
    public int SAVE() throws RemoteException, MXException {

        System.out.println("___ SAVE() ");
        System.out.println("___ SAVE() pessoa "+pessoas);
        if (pessoas != null) {
            System.out.println("___ SAVE() if");
            insereMxUOLot();
            System.out.println("___ SAVE() if FIM");
        }
        int retorno = super.SAVE();

        pessoas = null;
        return retorno;

    }

    private void insereMxUOLot() throws RemoteException, MXException {

        System.out.println("___ insereMxUOLot()");
        System.out.println("----------------------------------");
        System.out.println("Valores pessoas[][]");
        System.out.println("----------------------------------");
        for (int i = 0; i < pessoas.length; i++) {
            System.out.println(i + " - " + pessoas[i][0] + " - " + pessoas[i][1]);
        }
        System.out.println("----------------------------------");
        System.out.println("Valores mboSet");
        System.out.println("----------------------------------");
        for (int i = 0; i < getMbo().getMboSet("RHRLVAGA0001").count(); i++) {
            System.out.println(i + " - " + getMbo().getMboSet("RHRLVAGA0001").getMbo(i).getString("RHSTCODSERV") + " - " + getMbo().getMboSet("RHRLVAGA0001").getMbo(i).getString("RHSTCODTIPCAR"));
        }
        System.out.println("----------------------------------");

        System.out.println("---- insereMxUOLot()");
        int contadorPessoas = pessoas.length;
        int contadorMboSet = getMbo().getMboSet("RHRLVAGA0001").count();
        System.out.println("--- contadorPessoas " + contadorPessoas);
        System.out.println("--- contadorMboSet " + contadorMboSet);
        for (int i = 0; i < contadorMboSet; i++) {
            if (getMbo().getMboSet("RHRLVAGA0001") != null && getMbo().getMboSet("RHRLVAGA0001").getMbo(i) != null) {

                getMbo().getMboSet("RHRLVAGA0001").getMbo(i).setValue("RHNUFLGSALVO", true);
                System.out.println("--- insereMxUOLot i = " + i);
                if (getMbo().getMboSet("RHRLVAGA0001").getMbo(i).toBeDeleted()) {
                    System.out.println("--- insereMxUOLot deleted");
                    muda(getMbo().getMboSet("RHRLVAGA0001").getMbo(i).getString("RHSTCODSERV"), (i < contadorPessoas ? pessoas[i][0] : null), id, DEL, i);
                } else if (getMbo().getMboSet("RHRLVAGA0001").getMbo(i).isNew()) {
                    System.out.println("--- insereMxUOLot isNew");
                    muda(getMbo().getMboSet("RHRLVAGA0001").getMbo(i).getString("RHSTCODSERV"), (i < contadorPessoas ? pessoas[i][0] : null), id, NEW, i);
                } else if (i < contadorPessoas && !getMbo().getMboSet("RHRLVAGA0001").getMbo(i).getString("RHSTCODSERV").equals(pessoas[i][0])) {
                    System.out.println("--- insereMxUOLot isOld");
                    muda(getMbo().getMboSet("RHRLVAGA0001").getMbo(i).getString("RHSTCODSERV"), pessoas[i][0], id, OLD, i);
                }
            }
        }

        System.out.println("___ insereMxUOLot() FIM");
    }

    /*private enum Acao {

        DEL, NEW, OLD;
    }*/

    private void muda(String pessoa, String pessoaOld, int RHTBUOID, int acao, int posicao) throws MXException, RemoteException {
        System.out.println("--- muda");
        MboSet mboSet;
        mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("RHTBHULE01", sessionContext.getUserInfo());
        MboSet mboSet2;
        mboSet2 = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("RHTBCASE01", sessionContext.getUserInfo());
        System.out.println("--- muda antes do switch");
        switch (acao) {
            case DEL:
                System.out.println("--- muda DEL");
                if (pessoaOld != null) {
                    System.out.println("--- muda DEL pessoaOld != null");
                    mboSet.setWhere("RHSTCODTIPO = \'LOT\' AND RHTBUOID = \'" + RHTBUOID + "\' AND PERSONID = \'" + pessoaOld + "\' AND RHSTDTASAIDA is NULL");
                    mboSet.setOrderBy("RHSTDTAENTRAD DESC");
                    mboSet.reset();
                    if (mboSet.count() > 0) {
                        System.out.println("--- muda DEL pessoaOld > 0");
                        mboSet.getMbo(0).setValue("RHSTDTASAIDA", new Date());
                    }
                    System.out.println("--- muda DEL save 1");
                    mboSet.save();
                    System.out.println("--- muda DEL save 1 Fim");
                }

                if (getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").count() != 0) {
                    getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").getMbo(0).setValueNull("RHSTCODUOLOT");
                    getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").getMbo(0).setValueNull("RHNUCODUOLOTID");
                }
                System.out.println("--- muda DEL save 2");
                getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").save();
                System.out.println("--- muda DEL save 2 Fim");
                break;
            case NEW:
                System.out.println("--- muda NEW");
                if (pessoa != null) {
                    System.out.println("--- muda NEW pessoaNew != null");
                    /*
                    mboSet.setWhere("RHSTCODTIPO = \'LOT\' AND RHTBUOID = \'" + RHTBUOID + "\' AND PERSONID = \'" + pessoa + "\' AND RHSTDTASAIDA is NULL");
                    mboSet.setOrderBy("RHSTDTAENTRAD DESC");
                    mboSet.reset();
                    if (mboSet.count() > 0) {
                        System.out.println("--- muda NEW > 0");
                        mboSet.getMbo(0).setValue("RHSTDTASAIDA", new Date());
                    }
                    */
                    System.out.println("----- lot add");
                    MboRemote mbo = mboSet.add();
                    System.out.println("----- lot vals");
                    mbo.setValue("PERSONID", pessoa);
                    mbo.setValue("RHTBUOID", RHTBUOID);
                    mbo.setValue("RHSTCODTIPO", "LOT");
                    System.out.println("############ Novos campos: " + getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getDate("RHSTDTAENTRAD"));
                    mbo.setValue("RHSTDTAENTRAD", getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getDate("RHSTDTAENTRAD"));
                    mbo.setValue("RHSTDTAPOSSE", getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getDate("RHSTDTAPOSSE"));
                    mbo.setValue("RHSTDSCATONOR", getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getString("RHSTDSCATONOR"));
                    System.out.println("----- lot save");
                    mboSet.save();
                    System.out.println("--- muda NEW v1");
                    System.out.println("-----vals ");
                    System.out.println(posicao);
                    System.out.println("####### Nome= " + getMbo().getName());
                    System.out.println(getMbo().getString("RHSTSGLUO"));
                    System.out.println(getMbo().getInt("RHTBUOID"));
                    System.out.println("################### COUNT MBO: " + getMbo().getMboSet("RHRLVAGA0001").count());
                    System.out.println("################### COUNT RHRLCASE01: " + getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").count());
                    MboRemote mboRH;
                    if (getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").count() == 0) {
                        //mboRH = getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").add();
                    	//throw new MXApplicationException("uo", "RelacionamentoRHRLCASE01Vazio");
                    } else {
                        mboRH = getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").getMbo(0);
                        mboRH.setValue("RHSTCODUOLOT", getMbo().getString("RHSTSGLUO"));
                        System.out.println("--- muda NEW v2");
                        mboRH.setValue("RHNUCODUOLOTID", getMbo().getInt("RHTBUOID"));
                        System.out.println("--- muda NEW save");
                        getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").save();
                        System.out.println("--- muda NEW save FIM");
                    }

                }
                break;
            case OLD:
                System.out.println("--- muda OLD");
                if (pessoaOld != null) {
                    System.out.println("--- muda OLD pessoaOld != null");
                    /*mboSet.setWhere("RHSTCODTIPO = \'LOT\' AND RHTBUOID = \'" + RHTBUOID + "\' AND PERSONID = \'" + pessoaOld + "\' AND RHSTDTASAIDA is NULL");
                    mboSet.setOrderBy("RHSTDTAENTRAD DESC");
                    mboSet.reset();
                    if (mboSet.count() > 0) {
                        System.out.println("--- muda OLD > 0");
                        mboSet.getMbo(0).setValue("RHSTDTASAIDA", new Date());
                    }
                    System.out.println("--- muda OLD save1");
                    mboSet.save();
                    */
                    System.out.println("--- muda OLD save1 Fim");
                    mboSet2.setWhere("PERSONID = \'" + pessoaOld + "\' ");
                    mboSet2.reset();
                    if (mboSet2.count() > 0) {
                        System.out.println("--- muda OLD v1");
                        mboSet2.getMbo(0).setValueNull("RHSTCODUOLOT");
                        System.out.println("--- muda OLD v2");
                        mboSet2.getMbo(0).setValueNull("RHNUCODUOLOTID");
                        System.out.println("--- muda OLD save2");
                        mboSet2.save();
                        System.out.println("--- muda OLD save2 FIM");
                    }
                }

                if (pessoa.length() > 0) {
                    System.out.println("--- muda OLD pessoa != null");
                    /*mboSet.setWhere("RHSTCODTIPO = \'LOT\' AND RHTBUOID = \'" + RHTBUOID + "\' AND PERSONID = \'" + pessoa + "\' AND RHSTDTASAIDA is NULL");
                    mboSet.setOrderBy("RHSTDTAENTRAD DESC");
                    mboSet.reset();
                    if (mboSet.count() > 0) {
                        System.out.println("--- muda OLD pessoa > 0");
                        mboSet.getMbo(0).setValue("RHSTDTASAIDA", new Date());
                    }

                    System.out.println("--- muda OLD mboSet save");
                    mboSet.save();
                    */
                    System.out.println("--- muda OLD mboSet save FIM");
                    System.out.println("----- lot add");
                    MboRemote mbo = mboSet.add();
                    System.out.println("----- lot vals");
                    mbo.setValue("PERSONID", pessoa);
                    mbo.setValue("RHTBUOID", RHTBUOID);
                    mbo.setValue("RHSTCODTIPO", "LOT");
                    System.out.println("############ Novos campos: " + getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getDate("RHSTDTAENTRAD"));
                    mbo.setValue("RHSTDTAENTRAD", getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getDate("RHSTDTAENTRAD"));
                    mbo.setValue("RHSTDTAPOSSE", getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getDate("RHSTDTAPOSSE"));
                    mbo.setValue("RHSTDSCATONOR", getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getString("RHSTDSCATONOR"));
                    System.out.println("----- lot save");
                    mboSet.save();
                    System.out.println("----- lot save FIM");
                    System.out.println("-----vals ");
                    System.out.println(posicao);
                    System.out.println("####### Nome= " + getMbo().getName());
                    System.out.println(getMbo().getString("RHSTSGLUO"));
                    System.out.println(getMbo().getInt("RHTBUOID"));
                    System.out.println("################### COUNT MBO: " + getMbo().getMboSet("RHRLVAGA0001").count());
                    System.out.println("################### COUNT RHRLCASE01: " + getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").count());
                    MboRemote mboRH;
                    if (getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").count() == 0) {
                        //mboRH = getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").add();
                    	//throw new MXApplicationException("uo", "RelacionamentoRHRLCASE01Vazio");
                    } else {
                        mboRH = getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").getMbo(0);
		                mboRH.setValue("RHSTCODUOLOT", getMbo().getString("RHSTSGLUO"));
		                System.out.println("----- lot save v1");
		                mboRH.setValue("RHNUCODUOLOTID", getMbo().getInt("RHTBUOID"));
		                System.out.println("----- lot save save rel");
		                getMbo().getMboSet("RHRLVAGA0001").getMbo(posicao).getMboSet("RHRLCASE01").save();
		                System.out.println("----- lot save save rel fim");
                    }
                }


                break;
        }
    }
}
