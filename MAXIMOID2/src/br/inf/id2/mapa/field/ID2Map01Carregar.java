package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 *
 */
public class ID2Map01Carregar extends MboValueAdapter {

    public ID2Map01Carregar(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();

        if (!getMboValue().getMbo().isNull("MADATABA") && !getMboValue().getMbo().isNull("MANUMREG") && !getMboValue().getMbo().isNull("COMMODITY")) {
            duplicidadeDeRegistros();
        }

        String valor1 = getMboValue().getMbo().getString("MANUMREG");
        String valor2 = getMboValue().getMbo().getString("COMMODITY");
        Date valor3 = getMboValue().getMbo().getDate("MADATABA");



        if (!valor1.equals("") && !valor2.equals("") && valor3 != null) {


            MboSetRemote mboSetOrigem;
            MboSetRemote mboSetDestino;

            mboSetOrigem = getMboValue().getMbo().getMboSet("MAVWMAP02");
            mboSetDestino = getMboValue().getMbo().getMboSet("MATBSALABA");

            MboRemote mboOrigem;
            MboRemote mboDestino;


            mboSetDestino.deleteAll(MboConstants.NOACCESSCHECK);
            getMboValue().getMbo().getMboSet("MATBLOTABA").deleteAll(MboConstants.NOACCESSCHECK);



            for (int a = 0; ((mboOrigem = mboSetOrigem.getMbo(a)) != null); a++) {


                mboDestino = mboSetDestino.add();
                int aonde = 0;

                mboDestino.setValue("PERSONID", mboOrigem.getString("PRODUTOR"), MboConstants.NOACCESSCHECK);

                mboDestino.setValue("ID2CODPROP", mboOrigem.getString("COD_PROPRIEDADE"), MboConstants.NOACCESSCHECK);

                mboDestino.setValue("MAQTDMACHO", mboOrigem.getString("QTD_MACHO"), MboConstants.NOACCESSCHECK);

                mboDestino.setValue("MAQTDFEMEA", mboOrigem.getString("QTD_FEMEA"), MboConstants.NOACCESSCHECK);

                mboDestino.setValue("MAQTDTOTAL", mboOrigem.getString("QTD_TOTAL"), MboConstants.NOACCESSCHECK);

                mboDestino.setValue("LOCATION", getMboValue().getMbo().getString("LOCATION"), MboConstants.NOACCESSCHECK);

                mboDestino.setValue("COMMODITY", mboOrigem.getString("ESPECIE"), MboConstants.NOACCESSCHECK);



            }


        }


    }

    public void duplicidadeDeRegistros() throws RemoteException, MXException {
        MboRemote mboa;
        MboSet mboSetMatblotaba;
        mboSetMatblotaba = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MATBLOTABA", getMboValue().getMbo().getUserInfo());

        MboSet mboSetMadataba;
        mboSetMadataba = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWLOC06", getMboValue().getMbo().getUserInfo());

        mboSetMadataba.reset();

        for (int i = 0; ((mboa = getMboValue().getMbo().getThisMboSet().getMbo(i)) != null); i++) {
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yy");
            String data = formatador.format(mboa.getDate("MADATABA"));
            mboSetMadataba.setWhere("to_char(MADATABA) = '" + data + "' AND COMMODITY = '" + mboa.getString("COMMODITY") + "'" + " AND appname = '" + getMboValue("APPNAME").getString().toUpperCase() + "'");
            System.out.println("########## Quantidade Depois: " + mboSetMadataba.count());
            if (getMboValue().getMbo().isNew() && mboSetMadataba.count() > 0) {
                throw new MXApplicationException("madataba", "existemRegistrosEmDuplicidade");
            }
        }
    }
}
