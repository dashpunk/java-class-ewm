package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 *
 * @author Willians Andrade
 *
 */
public class ID2Map07 extends AppBean {

    public ID2Map07() {
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
        MboSet mboMaProcon = (MboSet) getMbo().getMboSet("MARL01COM");
        if (getMbo().getBoolean("ID2SEMCON48") == false) {
            if (mboMaProcon.count() <= 0 && getMbo().getString("ID2STAMAP48").equalsIgnoreCase("CONCLUÍDO")) {
                throw new MXApplicationException("id2mapa07", "condProdutoNull");
            }

            if (!preencimentoObrigatorio((MboSet) getMbo().getMboSet("MARL01COM")) && getMbo().getString("ID2STAMAP48").equalsIgnoreCase("CONCLUÍDO")) {
                throw new MXApplicationException("id2mapa07", "condProdutoNull");
            }
        }
        super.save();
    }

    public static boolean preencimentoObrigatorio(MboSet aMboSet) throws MXException, RemoteException {

        int contador = 0;

        Mbo linha;

        if (aMboSet != null) {
            for (int i = 0; i < aMboSet.count(); i++) {
                linha = (Mbo) aMboSet.getMbo(i);
                if ((!linha.toBeDeleted())) {
                    contador++;
                }
            }
            System.out.println(contador + " = " + aMboSet.count());

        }

        if (contador == aMboSet.count()) {
            return true;
        } else {
            return false;
        }

    }
}
