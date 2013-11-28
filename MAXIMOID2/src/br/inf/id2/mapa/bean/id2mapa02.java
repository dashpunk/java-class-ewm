package br.inf.id2.mapa.bean;
//br.inf.id2.mapa.bean.id2mapa02
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;

/**
 *
 * @author Willians Andrade
 *
 */
public class id2mapa02 extends AppBean {

    public id2mapa02() {
    }

    @Override 
    public void save() throws MXException {

        try {
            if (getMbo() != null) {
                validarSalvar();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.save();
    }

    private void validarSalvar() throws MXException, RemoteException {
        MboRemote mboa = null;
        MboRemote mbob = null;
        MboRemote mboc = null;
        MboSet mboSetMadataba;
        mboSetMadataba = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MATBMAPAS", sessionContext.getUserInfo());
        Integer locationID = getMbo().getInt("MATBMAPASID");


        // NÃ£o permite a inserÃ§Ã£o de registros, sendo que para a data especificada e a especie o mapa de abate nÃ£o foi concluido, correÃ§Ã£o ou fechado.

        if (getMbo().getString("ID2STAMAP02").equals("CONCLUÃ�DO")) {

            if (getMbo().getDate("ID2DATABA") != null) {
                mboSetMadataba.setWhere("ID2STAMAP01 NOT IN ('CONCLUÃ�DO') AND MATBMAPASID = '" + getMbo().getString("MATBMAPASID") + "\'");
                mboSetMadataba.reset();

                if (mboSetMadataba.count() > 0) {
                    throw new MXApplicationException("madataba", "mapaDeAbateNConcluido");
                }
            }
        }


        //InÃ­cio da verificaÃ§Ã£o de DiagnÃ³sticos e Partes
        MboSet matbLotaba = (MboSet) getMbo().getMboSet("MARL01LOTABA");
        Mbo mboLotAba;

        for (int i = 0; ((matbLotaba.getMbo(i)) != null); i++) {
            mboLotAba = (Mbo) matbLotaba.getMbo(i);

            MboSet diagnosticos = (MboSet) mboLotAba.getMboSet("MATBDIADON");

            for (int j = 0; ((diagnosticos.getMbo(j)) != null); j++) {
                Mbo mboDiag = (Mbo) diagnosticos.getMbo(j);

                MboSet partes = (MboSet) mboDiag.getMboSet("MATBPARDON");

                if (diagnosticos.getMbo(j).toBeDeleted()) {
                    super.save();
                }

                if (partes == null || partes.count() == 0) {
                    throw new MXApplicationException("ID2Map02", "naoExistePartesAcometidos");
                }

                for (int y = 0; ((partes.getMbo(y)) != null); y++) {
                    if (partes.getMbo(0).toBeDeleted() && partes.getCurrentPosition() == y) {
                        throw new MXApplicationException("ID2Map02", "naoExistePartesAcometidos");
                    }
                }
            }
        }

        // NÃ£o permite a inserÃ§Ã£o de registros com a mesma data e espÃ©cie
        if (getMbo().getDate("ID2DATABA") != null) {
            mboSetMadataba.setWhere("MATBMAPASID <> " + locationID + " AND ID2CODESP = \'" + getMbo().getString("ID2CODESP") + "\'" + " AND ID2NUMREG = \'" + getMbo().getString("ID2NUMREG") + "\'");
            mboSetMadataba.reset();

            MboRemote mbo;
            for (int n = 0; ((mbo = mboSetMadataba.getMbo(n)) != null); n++) {
                if (getMbo().getDate("ID2DATABA").equals(mbo.getDate("ID2DATABA"))) {
                    throw new MXApplicationException("madataba", "existemRegistrosEmDuplicidade");
                }
            }
        }

        super.save();
    }

    @Override
    public int toggledeleterow() throws MXException {
        // TODO Auto-generated method stub
        return super.toggledeleterow();
    }

    @Override
    public synchronized void fireDataChangedEvent(DataBean arg0) {
        super.fireDataChangedEvent(arg0);
        try {
            System.out.println("fireDataChangedEvent(DataBean arg0) " + arg0.getUniqueIdName());
            if (arg0.getUniqueIdName().equalsIgnoreCase("MATBMAPASID")) {
                reload();
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MXException e) {
            e.printStackTrace();
        }

    }

    @Override
    public synchronized void fireChildChangedEvent() {
        super.fireChildChangedEvent();
        reload();
    }

    private void reload() {
        reloadTable();
    }
}
