package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class MATbLotAbaMaQtdMac extends MboValueAdapter {

    public MATbLotAbaMaQtdMac(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("---- validate()");
        super.validate();

        String valor1 = getMboValue().getMbo().getString("MAQTDMAC");

        System.out.println(valor1);

        if (!valor1.equals("")) {
            MboSetRemote origem;
            origem = psdi.server.MXServer.getMXServer().getMboSet("MAVWMAP02", getMboValue().getMbo().getUserInfo());
            origem.setWhere("local_destino in (select location from id2vwloc02 where id2numcont = \'"+getMboValue().getMbo().getString("MANUMREG")
                    +"\') and especie = '1.1'");
            origem.reset();
            System.out.println("---entrou");
            getMboValue().getMbo().getThisMboSet().save();

            MboSetRemote mboSetOrigem;
            MboSetRemote mboSetDestino;

            mboSetOrigem = getMboValue().getMbo().getMboSet("MAVWMAP02");
            mboSetDestino = getMboValue().getMbo().getMboSet("MATBSALABA");

            MboRemote mboOrigem;
            MboRemote mboDestino;

            System.out.println("---t1 " + mboSetOrigem.count());
            System.out.println("---t2 " + mboSetDestino.count());

            mboSetDestino.deleteAll(MboConstants.NOACCESSCHECK);
            System.out.println("-s1");
            mboSetDestino.save();
            System.out.println("-s2");

            System.out.println("--- apor save e deleteall");

            for (int a = 0; ((mboOrigem = mboSetOrigem.getMbo(a)) != null); a++) {
                System.out.println("--- a " + a);

                mboDestino = mboSetDestino.add();
                int aonde = 0;
                System.out.println("--- aonde  " + ++aonde);
                mboDestino.setValue("PERSONID", mboOrigem.getString("PRODUTOR"), MboConstants.NOACCESSCHECK);
                System.out.println("--- aonde  " + ++aonde);
                mboDestino.setValue("ID2CODPROP", mboOrigem.getString("COD_PROPRIEDADE"), MboConstants.NOACCESSCHECK);
                System.out.println("--- aonde  " + ++aonde);
                mboDestino.setValue("MAQTDMACHO", mboOrigem.getString("QTD_MACHO"), MboConstants.NOACCESSCHECK);
                System.out.println("--- aonde  " + ++aonde);
                mboDestino.setValue("MAQTDFEMEA", mboOrigem.getString("QTD_FEMEA"), MboConstants.NOACCESSCHECK);
                System.out.println("--- aonde  " + ++aonde);
                mboDestino.setValue("MAQTDTOTAL", mboOrigem.getString("QTD_TOTAL"), MboConstants.NOACCESSCHECK);
                System.out.println("--- aonde  " + ++aonde);
                mboDestino.setValue("LOCATION", mboOrigem.getString("LOCAL_DESTINO"), MboConstants.NOACCESSCHECK);
                System.out.println("--- aonde  " + ++aonde);
                mboDestino.setValue("COMMODITY", mboOrigem.getString("ESPECIE"), MboConstants.NOACCESSCHECK);
                System.out.println("--- aonde  " + ++aonde);
            }
            System.out.println("--- antes do save");
            mboSetDestino.save();
            System.out.println("--- apos do save");
        }
        getMboValue().getMbo().getThisMboSet().save();
        System.out.println("FIM");
    }
}

/*
MboSet pessoa;
pessoa = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("ID2VWMAXUSER", sessionContext.getUserInfo());

pessoa.setWhere("personid = '" + sessionContext.getUserInfo().getUserName() + "'");
pessoa.reset();
 */
