package br.inf.id2.mapa.bean;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class CadastroProdutoFinal extends AppBean {

    public CadastroProdutoFinal() {
        super();
    }

    @Override
    public int SAVE() throws MXException, RemoteException {


        String strs = "";
        if ((!getMbo().isNull("ID2FORCON") && getMbo().isNull("ID2SEQ02"))) {
            strs += " ID2SEQ02";
        }
        if ((getMbo().isNull("ID2FORCON") && !getMbo().isNull("ID2SEQ02"))) {
            strs += " ID2FORCON";
        }

        if ((!getMbo().isNull("ID2PROTEC") && getMbo().isNull("ID2SEQ03"))) {
            strs += " ID2SEQ03";
        }
        if ((getMbo().isNull("ID2PROTEC") && !getMbo().isNull("ID2SEQ03"))) {
            strs += " ID2PROTEC";
        }

        if ((!getMbo().isNull("ID2ESP") && getMbo().isNull("ID2SEQ04"))) {
            strs += " ID2SEQ04";
        }
        if ((getMbo().isNull("ID2ESP") && !getMbo().isNull("ID2SEQ04"))) {
            strs += " ID2ESP";
        }

        if ((!getMbo().isNull("ID2CAR") && getMbo().isNull("ID2SEQ05"))) {
            strs += " ID2SEQ05";
        }
        if ((getMbo().isNull("ID2CAR") && !getMbo().isNull("ID2SEQ05"))) {
            strs += " ID2CAR";
        }
        //System.out.println("---- strs = "+ strs);


        if (strs.length() > 0) {
            String[] params = strs.trim().split(" ");
            //System.out.println("---- params count "+params.length);
            throw new MXApplicationException("system", "camposObrigatorios", params);
        }
        String sequencia = "";
        for (int i = 1; i <= 5; i++) {

            String valorAtual = "ID2SEQ0" + i;
            //System.out.println("sequencia = " + sequencia);
            //System.out.println("valorAtual = " + valorAtual);
            //System.out.println("gValorAtual = " + getMbo().getString(valorAtual));
            if (!getMbo().getString(valorAtual).equals("") && getMbo().getInt(valorAtual) < 1 && getMbo().getInt(valorAtual) > 5) {
                throw new MXApplicationException("cadastroProdutoFinal", "valorInvalido");
            }

            if (!getMbo().getString(valorAtual).equals("") && sequencia.indexOf(getMbo().getString(valorAtual)) != -1) {
                throw new MXApplicationException("cadastroProdutoFinal", "valorRepetido");
            }
            sequencia += getMbo().getString(valorAtual);
        }

        String str = "";
        for (int i = 1; i <= 5; i++) {

            //System.out.println("--- i " + i);

            if (i > 1) {
                str += " ";
            }

            if (!getMbo().isNull("ID2SEQ01") && getMbo().getInt("ID2SEQ01") == i) {
                str += getString("ID2PRO");

            } else if (!getMbo().isNull("ID2SEQ02") && getMbo().getInt("ID2SEQ02") == i) {
                str += getString("ID2FORCON");

            } else if (!getMbo().isNull("ID2SEQ03") && getMbo().getInt("ID2SEQ03") == i) {
                str += getString("ID2PROTEC");

            } else if (!getMbo().isNull("ID2SEQ04") && getMbo().getInt("ID2SEQ04") == i) {
                if (i > 1) {
                    str += "DE ";
                }
                str += getString("ID2ESP");

            } else if (!getMbo().isNull("ID2SEQ05") && getMbo().getInt("ID2SEQ05") == i) {
                str += getString("ID2CAR");
            }
            //System.out.println("str= " + str);

        }

        MboSet mboSet;
        mboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MATBPROFIN", sessionContext.getUserInfo());

        mboSet.setWhere("DESCRIPTION = '" + str + "'");
        mboSet.reset();

        if (mboSet.count() > 0 ) {
            throw new MXApplicationException("cadastroProdutoFinal", "descriptionExistente");
        }

        getMbo().setFieldFlag("DESCRIPTION", MboConstants.READONLY, false);
        getMbo().setValue("DESCRIPTION", str);
        getMbo().setFieldFlag("DESCRIPTION", MboConstants.READONLY, true);
        return super.SAVE();
    }
}
