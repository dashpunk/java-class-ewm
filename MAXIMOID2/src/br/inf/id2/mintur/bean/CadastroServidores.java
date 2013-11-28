package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;

/**
 * 
 * @author Ricardo S Gomes
 *
 */
public class CadastroServidores extends AppBean {

    int mxUOExe;
    String id;

    public CadastroServidores() {
    }

    @Override
    public synchronized void dataChangedEvent(DataBean speaker) {
        try {
        	if (getMbo().getString("RHTBCASE01ID") != null) {
	            if (mxUOExe == 0 || id == null || !id.equals(getMbo().getString("RHTBCASE01ID"))) {
	                //System.out.println("--valor nullo " + getMbo().getString("RHTBCASE01ID"));
	                mxUOExe = getMbo().getInitialValue("RHNUCODUOEXEID").isNull() ? 999999999 : getMbo().getInitialValue("RHNUCODUOEXEID").asInt();
	                id = getMbo().getString("RHTBCASE01ID");
	
	                //System.out.println(mxUOExe);
	                //System.out.println(id);
	            }
        	}
        } catch (MXException ex) {
            Logger.getLogger(CadastroServidores.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(CadastroServidores.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.dataChangedEvent(speaker);
    }

    @Override
    public int SAVE() throws RemoteException, MXException {
        if (mxUOExe != 0 && !id.equals(getMbo().getString("RHTBCASE01ID"))) {
            mxUOExe = 0;
        }
        if (!getMbo().getBoolean("RHNUFLGSALVO")) {
            getMbo().getMboSet("RHRLPERSON01").getMbo(0).setValue("FIRSTNAME", getMbo().getString("FIRSTNAME"));
            getMbo().getMboSet("RHRLPERSON01").getMbo(0).setValue("LASTNAME", getMbo().getString("LASTNAME"));
            getMbo().getMboSet("RHRLPERSON01").getMbo(0).setValue("BGSTNOMNOMSOC", getMbo().getString("BGSTNOMNOMSOC"));
            getMbo().getMboSet("RHRLPERSON01").getMbo(0).setValue("BIRTHDATE", getMbo().getDate("BIRTHDATE"));
            getMbo().getMboSet("RHRLPERSON01").getMbo(0).setValue("BGSTCODSEXO", getMbo().getString("BGSTCODSEXO"));
            getMbo().getMboSet("RHRLPERSON01").getMbo(0).setValue("BGSTCODPAIS", getMbo().getString("BGSTCODPAIS"));
            getMbo().getMboSet("RHRLPERSON01").getMbo(0).setValue("BGSTCODESTCIV", getMbo().getString("BGSTCODESTCIV"));
        }

        int retorno = super.SAVE();
        System.out.println("-------===== retorno "+retorno);
        getMbo().setValue("RHNUFLGSALVO", true);
        //System.out.println(" ----- antes " + getMbo().getInitialValue("RHNUCODUOLOTID").asString());
        //System.out.println(" ----- antes " + getMbo().getInitialValue("RHNUCODUOEXEID").asString());
        //mxUOLot = getMbo().getInitialValue("RHSTCODUOLOT").asString();
        //mxUOExe = getMbo().getInitialValue("RHSTCODUOEXE").asString();
        //System.out.println("---- RHSTCODUOLOT P OU n... " + getMbo().getString("RHNUCODUOLOTID"));
        //System.out.println("---- RHSTCODUOEXE P OU n... " + getMbo().getString("RHNUCODUOEXEID"));

        //System.out.println("---- RHSTCODUOEXE ... " + mxUOExe);

        if (mxUOExe != 0 && !(mxUOExe == getMbo().getInt("RHNUCODUOEXEID"))) {
        	System.out.println("*** if insereMxUOExe");
            insereMxUOExe();
        }
        if (!getMbo().getInitialValue("RHNUCODUOLOTID").equals(getMbo().getString("RHNUCODUOLOTID"))) {
            //insereRHSTCODUOEXE();
        }
        if (!getMbo().getInitialValue("RHNUCODUOEXEID").equals(getMbo().getString("RHNUCODUOEXEID"))) {
            //insereMxUOExe();
        }


        //if (!getMbo().getString("MXUOLOT").equals(mxUOLot) && !getMbo().isNull("MXUOLOT")) {
        //insereMxUOLot();
        // }
        //if (!getMbo().getString("MXUOEXE").equals(mxUOExe) && !getMbo().isNull("MXUOEXE")) {
        //insereMxUOExe();
        //}

        //mxUOLot = getMbo().getString("MXUOLOT");
        //mxUOExe = getMbo().getString("MXUOEXE");
        mxUOExe = 0;
        return retorno;

    }

    private void insereMxUOExe() throws RemoteException, MXException {
        //System.out.println("----- RHNUCODUOEXEID " + mxUOExe);
        MboSet mboSet;
        mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("RHTBHULE01", sessionContext.getUserInfo());
        mboSet.setWhere("RHSTCODTIPO = \'EXE\' AND RHTBUOID = \'" + mxUOExe + "\' AND PERSONID = \'" + getMbo().getString("PERSONID") + "\' AND RHSTDTASAIDA is NULL");
        mboSet.setOrderBy("RHSTDTAENTRAD DESC");
        //System.out.println("----- reset exe a");
        mboSet.reset();
        //System.out.println("----- reset exe b");
        //System.out.println("----- exe count " + mboSet.count());
        if (mboSet.count() > 0) {
            MboRemote mbo = mboSet.getMbo(0);
            mbo.setValue("RHSTDTASAIDA", new Date());
            //System.out.println("----- exe update");
        }

        //System.out.println("----- exe add");
        MboRemote mbo = mboSet.add();
        //System.out.println("----- exe vals");
        mbo.setValue("PERSONID", getMbo().getString("PERSONID"));
        mbo.setValue("RHTBUOID", getMbo().getString("RHNUCODUOEXEID"));
        mbo.setValue("RHSTCODTIPO", "EXE");
        mbo.setValue("RHSTDTAENTRAD", new Date());
        //System.out.println("----- exe save");
        mboSet.save();
        //System.out.println("----- exe fim");
    }
}
