package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;

import psdi.mbo.MboSet;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 *
 * @author Willians Andrade
 *
 */
public class Id2mapa03 extends AppBean {

    public Id2mapa03() {
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


        MboSet mboSetMapasFechados = (MboSet) MXServer.getMXServer().getMboSet("MATBGESMAPAS", sessionContext.getUserInfo());

        if ((!mboSetMapasFechados.isEmpty()) && (getMbo().getDate("ID2DATABA") != null)) {
            mboSetMapasFechados.setWhere("TO_DATE('" + getMbo().getString("ID2DATABA") + "') > (SELECT MAX (ID2DATAFIM) FROM MATBGESMAPAS)");

            if (mboSetMapasFechados.count() <= 0) {
                throw new MXApplicationException("madataba", "mapasFechados");
            }
        }

        Mbo linha;
        if (getMbo().getString("ID2STAMAP03").equals("CONCLUÍDO")) {

            if (!getMbo().getString("ID2STAMAP01").equals("CONCLUÍDO") && !getMbo().getString("ID2STAMAP01").equals("FECHADO")) {
                throw new MXApplicationException("madataba", "mapaDeAbateNConcluido");
            }

            MboSet matbLotaba = (MboSet) getMbo().getMboSet("MARL01LOTABA");
            Mbo mboLotAba;

            if (!getMbo().getBoolean("ID2SEMCON")) {
                for (int i = 0; ((matbLotaba.getMbo(i)) != null); i++) {
                    int contadorLDeletePartes = 0;
                    mboLotAba = (Mbo) matbLotaba.getMbo(i);

                    MboSet parteCondenada = (MboSet) mboLotAba.getMboSet("MATBCONANI");

                    if (!mboLotAba.getBoolean("MASEMREG")) {
                        if (parteCondenada.count() <= 0 && getMbo().getString("ID2STAMAP03").equalsIgnoreCase("CONCLUÍDO")) {
                            throw new MXApplicationException("id2mapa03", "condProdutoNull");
                        }
                        if (parteCondenada != null) {
                            for (int m = 0; m < parteCondenada.count(); m++) {
                                linha = (Mbo) parteCondenada.getMbo(m);
                                if ((!linha.toBeDeleted())) {
                                    contadorLDeletePartes++;
                                }
                            }
                            System.out.println(contadorLDeletePartes + " = " + parteCondenada.count());

                        }

                        System.out.println(contadorLDeletePartes);
                        System.out.println(parteCondenada.count());
                        if (contadorLDeletePartes <= 0) {
                            throw new MXApplicationException("id2mapa03", "condProdutoNull");
                        }
                    }
                }
            }
        }

        super.save();
    }
}
