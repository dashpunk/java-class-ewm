package br.inf.id2.tj.field;

import java.rmi.RemoteException;
import java.util.Date;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class StatusOS extends MboValueAdapter {

    public StatusOS(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        System.out.println("*** StatusOS ***");

        System.out.println("*** STATUS - " + getMboValue().getString());
        if (getMboValue().getString().equals("EM ANDAMENTO")) {
            System.out.println("*** if EM ANDAMENTO");
            getMboValue().getMbo().getMboValue("ACTSTART").setReadOnly(false);
            getMboValue().getMbo().setValue("ACTSTART", new Date());
            getMboValue().getMbo().getMboValue("ACTSTART").setReadOnly(true);
        }
        //Requisitado pelo Eduardo de Assis no dia 27 de Setembro de 2011
        System.out.println("--- status = "+getMboValue().getString());
        if (getMboValue().getString().equals("FECHADA")) {
            System.out.println("--- equals");
            MboSet invReserveMboSet;
            invReserveMboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("INVRESERVE", getMboValue().getMbo().getUserInfo());
            System.out.println("--- wonum = "+getMboValue("WONUM").getString());
            invReserveMboSet.setWhere("WONUM = '" + getMboValue("WONUM").getString() + "'");
            System.out.println("--- antes do reset");
            invReserveMboSet.reset();
            System.out.println("--- apos reset");

            invReserveMboSet.deleteAll();
            System.out.println("--- antes do save");
            invReserveMboSet.save();
            System.out.println("--- apos save");
        }
        System.out.println("*** Nao entrou no IF");
    }
}
