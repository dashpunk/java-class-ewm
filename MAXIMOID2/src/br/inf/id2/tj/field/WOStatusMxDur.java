package br.inf.id2.tj.field;

import br.inf.id2.common.util.Data;
import br.inf.id2.common.util.Uteis;
import psdi.mbo.*;
import java.rmi.RemoteException;
import java.util.Date;
import javax.rmi.CORBA.Util;
import psdi.app.workorder.WOStatus;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class WOStatusMxDur extends MboValueAdapter {

    public WOStatusMxDur(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        MboSet wostatus;
        wostatus = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("WOSTATUS", getMboValue().getMbo().getUserInfo());
        //System.out.println("wonum = \'" + getMboValue().getMbo().getString("wonum") + "\' and wostatusid <> " + getMboValue().getMbo().getInt("wostatusid"));
        wostatus.setWhere("wonum = \'" + getMboValue().getMbo().getString("wonum") + "\' and wostatusid <> " + getMboValue().getMbo().getInt("wostatusid"));
        wostatus.setOrderBy("wostatusid");
        wostatus.reset();


        System.out.println("v3------- count  " + wostatus.count());

        MboRemote mbo;
        MboRemote mbob;
        for (int i = 0; ((mbo = wostatus.getMbo(i)) != null); i++) {
            Date dataFinal;
            if (((mbob = wostatus.getMbo(i + 1)) == null)) {
                dataFinal = new Date();
            } else {
                dataFinal = mbob.getDate("CHANGEDATE");
            }
            System.out.println("- dataFinal " + i + " " + dataFinal);
            System.out.println("- changeby " + mbo.getString("CHANGEBY"));
            Date data = mbo.getDate("CHANGEDATE");
            System.out.println("--- data " + data);
            try {
                Date duracao = null;
                duracao = Data.getHorasUteis(data, dataFinal, getMboValue().getMbo().getUserInfo());

                System.out.println("--- duracao = " + duracao);

                String b = "" + duracao.getTime() / 3600000.00D;
                System.out.println("b = " + b);
                int h = 0;
                int m = 0;
                if (b.contains(".")) {
                    String[] valor = b.split("\\.");

                    Double x = Double.valueOf("0." + valor[1]) * 60;

                    //System.out.println(" x " + Math.round(x));

                    h = Integer.valueOf(valor[0]);
                    m = (int) Math.round(x);
                } else {
                    h = Integer.valueOf(b);
                    m = 0;
                }

                int horas = h * 60 + m;
                
                //mbo.setValue("MXDUR", h + ":" + Uteis.adicionaValorEsquerda(String.valueOf(m), "0", 2), MboConstants.NOACCESSCHECK);
                mbo.setValue("MXDUR", "" + horas, MboConstants.NOACCESSCHECK);

            } catch (Exception e) {
                System.out.println("---- e " + e.getMessage());
            }
        }
        System.out.println("------ antes do save");


        wostatus.save(MboConstants.NOVALIDATION_AND_NOACTION);

        System.out.println("------ apos do save");

    }
}
