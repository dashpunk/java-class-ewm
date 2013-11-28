package br.inf.id2.me.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 *
 * @author Ricardo S Gomes
 *
 * os trechos comentados foram solicitados no dia 08/03/12, dia internacional da mulher, pelo Marcelo
 */
public class CadastroServidores extends AppBean {

//    Integer mxUOExe;
//    Integer id;

    public CadastroServidores() {
    }

//    @Override
//    public synchronized void dataChangedEvent(DataBean speaker) {
//        try {
//            if (getMbo().getString("MXTBRHCASEID") != null) {
//                System.out.println("---CadastroServidores dataChangeEvent a ");
//                if (mxUOExe == null || id == null || !id.equals(getMbo().getInt("MXTBRHCASEID"))) {
//                    mxUOExe = getMbo().getInitialValue("MXUOEXEID").asInt();
//                    id = getMbo().getInt("MXTBRHCASEID");
//                }
//            }
//        } catch (MXException ex) {
//            Logger.getLogger(CadastroServidores.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (RemoteException ex) {
//            Logger.getLogger(CadastroServidores.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        super.dataChangedEvent(speaker);
//    }

    @Override
    public int SAVE() throws RemoteException, MXException {
//        if (mxUOExe != null && !id.equals(getMbo().getInt("MXTBRHCASEID"))) {
//            mxUOExe = null;
//        }
        if (!getMbo().getBoolean("MXSALVO")) {
            getMbo().getMboSet("MXRLPERSON01").getMbo(0).setValue("FIRSTNAME", getMbo().getString("FIRSTNAME"));
            getMbo().getMboSet("MXRLPERSON01").getMbo(0).setValue("LASTNAME", getMbo().getString("LASTNAME"));
            getMbo().getMboSet("MXRLPERSON01").getMbo(0).setValue("MXNOMSOC", getMbo().getString("MXNOMSOC"));
            getMbo().getMboSet("MXRLPERSON01").getMbo(0).setValue("BIRTHDATE", getMbo().getDate("BIRTHDATE"));
            getMbo().getMboSet("MXRLPERSON01").getMbo(0).setValue("MXSEX", getMbo().getString("MXSEX"));
            getMbo().getMboSet("MXRLPERSON01").getMbo(0).setValue("MXPAIS", getMbo().getString("MXPAIS"));
            getMbo().getMboSet("MXRLPERSON01").getMbo(0).setValue("MXESTCIV", getMbo().getString("MXESTCIV"));
        }

        int retorno = super.SAVE();
        System.out.println("-------===== retorno " + retorno);
        getMbo().setValue("MXSALVO", true);
        //System.out.println(" ----- antes " + getMbo().getInitialValue("MXUOLOTID").asString());
        //System.out.println(" ----- antes " + getMbo().getInitialValue("MXUOEXEID").asString());
        //mxUOLot = getMbo().getInitialValue("MXUOLOT").asString();
        //mxUOExe = getMbo().getInitialValue("MXUOEXE").asString();

//        if (mxUOExe != null && !mxUOExe.equals(getMbo().getInt("MXUOEXEID"))) {
//            insereMxUOExe();
//        }
//        if (!getMbo().getInitialValue("MXUOLOTID").equals(getMbo().getInt("MXUOLOTID"))) {
//            //insereMxUOLot();
//        }
//        if (!getMbo().getInitialValue("MXUOEXEID").equals(getMbo().getInt("MXUOEXEID"))) {
//            //insereMxUOExe();
//        }


        //if (!getMbo().getString("MXUOLOT").equals(mxUOLot) && !getMbo().isNull("MXUOLOT")) {
        //insereMxUOLot();
        // }
        //if (!getMbo().getString("MXUOEXE").equals(mxUOExe) && !getMbo().isNull("MXUOEXE")) {
        //insereMxUOExe();
        //}

        //mxUOLot = getMbo().getString("MXUOLOT");
        //mxUOExe = getMbo().getString("MXUOEXE");
//        mxUOExe = null;
        return retorno;

    }

//    private void insereMxUOExe() throws RemoteException, MXException {
//        //System.out.println("----- MXUOEXEID " + mxUOExe);
//        MboSet mboSet;
//        mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MXTBRHHULE", sessionContext.getUserInfo());
//        mboSet.setWhere("MXTIPO = \'EXE\' AND MXTBUAID = \'" + mxUOExe + "\' AND PERSONID = \'" + getMbo().getString("PERSONID") + "\' AND MXDATSAI is NULL");
//        mboSet.setOrderBy("MXDATENT DESC");
//        //System.out.println("----- reset exe a");
//        mboSet.reset();
//        //System.out.println("----- reset exe b");
//        //System.out.println("----- exe count " + mboSet.count());
//        if (mboSet.count() > 0) {
//            MboRemote mbo = mboSet.getMbo(0);
//            mbo.setValue("MXDATSAI", new Date());
//            //System.out.println("----- exe update");
//        }
//
//        //System.out.println("----- exe add");
//        MboRemote mbo = mboSet.add();
//        //System.out.println("----- exe vals");
//        mbo.setValue("PERSONID", getMbo().getString("PERSONID"));
//        mbo.setValue("MXTBUAID", getMbo().getInt("MXUOEXEID"));
//        mbo.setValue("MXTIPO", "EXE");
//        mbo.setValue("MXDATENT", new Date());
//        //System.out.println("----- exe save");
//        mboSet.save();
//        //System.out.println("----- exe fim");
//    }
}
