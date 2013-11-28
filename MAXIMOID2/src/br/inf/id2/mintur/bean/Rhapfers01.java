package br.inf.id2.mintur.bean;

import br.inf.id2.common.util.Data;
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author ricardo
 */
public class Rhapfers01 extends psdi.webclient.system.beans.AppBean {

    public Rhapfers01() {
        System.out.println("--- Rhapfers01");
    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        MboRemote mbo;
        System.out.println("--- Rhapfers01.SAVE() " + getMbo().getMboSet("RHRLPARCELAS01").count());
        int contadorNormal = 0;
        for (int i = 0; ((mbo = getMbo().getMboSet("RHRLPARCELAS01").getMbo(i)) != null); i++) {
            System.out.println("i " + i);
            if (Data.dataInicialMenorFinal(mbo.getDate("RHDTDTAINICIOG"), getMbo().getDate("RHDTDTAINICIOP"))
                    || Data.dataInicialMenorFinal(mbo.getDate("RHDTDTATERMINOG"), getMbo().getDate("RHDTDTAINICIOP"))
                    || Data.dataInicialMenorFinal(getMbo().getDate("RHDTDTATERMINOP"), mbo.getDate("RHDTDTAINICIOG"))) {
            	
                throw new MXApplicationException("rhtbparcelas", "periodoInvalido");
            }
            System.out.println("--- mbo.RHSTCODSTAPARCELA "+mbo.getString("RHSTCODSTAPARCELA"));
            contadorNormal += (mbo.getString("RHSTCODSTAPARCELA").equalsIgnoreCase("NORMAL") ? 1 : 0);
            System.out.println("--- contadorNormal "+contadorNormal);
            if (contadorNormal > getMbo().getInt("RHNUNUMQNTPARCELAS")) {
                throw new MXApplicationException("rhtbparcelas", "numeroParcelasNormalInvalido");
            }
        }


        System.out.println("--- Rhapfers01.SAVE() FIM");
        return super.SAVE();
    }
}
