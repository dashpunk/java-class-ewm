package br.inf.id2.mapa.field;

import psdi.mbo.*;
import psdi.server.MXServer;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;


import psdi.app.rfq.virtual.*;

public class ID2FldBloqueio extends psdi.mbo.ALNDomain {


    public ID2FldBloqueio(MboValue mbv) throws MXException {
        super(mbv);
    }

    /**
     *
     * Sobrescrita do método action  BMXZZ0001E
     *<p>
     * Alteração do Status de Bloqueio dos Filhos
     */
    public void action()
            throws MXException, RemoteException {
        MboSetRemote filhos;
        MboRemote filho;
        int i = 0;

        System.out.println("TIPO DO LOCAL: " + getMboValue().getMbo().getString("ID2TIPOLOCAL"));

        if (getMboValue().getMbo().getString("ID2TIPOLOCAL").equals("01")) {
            filhos = getMboValue().getMbo().getMboSet("ID2LOC04VWLOC01");

            System.out.println("filhos size " + filhos.getSize() + " e count " + filhos.count());

            if (filhos != null) {
                do {
                    filho = filhos.getMbo(i);
                    if (filho != null) {
                        System.out.println(filho.getString("LOCATION") + " | valor anterior = " + filho.getString("ID2BLOCKSTATUS") + " - valor " + getMboValue().getString());
                        filho.setValue("ID2BLOCKSTATUS", getMboValue().getString());
                        System.out.println("------ ID2BLOCKSTATUS " + filho.getString("ID2BLOCKSTATUS"));
                    } else {
                        break;
                    }
                    i++;
                } while (true);
                i = 0;
            }
            filhos.save();
        } else if (getMboValue().getMbo().getString("ID2TIPOLOCAL").equals("21") || getMboValue().getMbo().getString("ID2TIPOLOCAL").equals("22")) {
            filhos = getMboValue().getMbo().getMboSet("ID2LOCATIONS_CHILD");


            if (filhos != null) {
                System.out.println("filhos size " + filhos.getSize() + " e count " + filhos.count());
                do {
                    filho = filhos.getMbo(i);
                    if (filho != null) {
                        System.out.println(filho.getString("LOCATION") + " | valor anterior = " + filho.getString("ID2BLOCKSTATUS") + " - valor " + getMboValue().getString());
                        filho.setValue("ID2BLOCKSTATUS", getMboValue().getString());
                        System.out.println("------ ID2BLOCKSTATUS " + filho.getString("ID2BLOCKSTATUS"));
                    } else {
                        break;
                    }
                    i++;
                } while (true);
                i = 0;
            }
            filhos.save();
        } else if (getMboValue().getMbo().getString("ID2TIPOLOCAL").equals("23")) {
            filhos = getMboValue().getMbo().getMboSet("ID2VWLOC01_CHILD");


            if (filhos != null) {
                System.out.println("filhos size " + filhos.getSize() + " e count " + filhos.count());
                do {
                    filho = filhos.getMbo(i);
                    if (filho != null) {
                        System.out.println(filho.getString("LOCATION") + " | valor anterior = " + filho.getString("ID2BLOCKSTATUS") + " - valor " + getMboValue().getString());
                        filho.setValue("ID2BLOCKSTATUS", getMboValue().getString());
                        System.out.println("------ ID2BLOCKSTATUS " + filho.getString("ID2BLOCKSTATUS"));
                    } else {
                        break;
                    }
                    i++;
                } while (true);
                i = 0;
            }
            filhos.save();

            filhos = getMboValue().getMbo().getMboSet("ID2VWLOC03_CHILD");


            if (filhos != null) {
                System.out.println("filhos 2 size " + filhos.getSize() + " e count " + filhos.count());
                do {
                    filho = filhos.getMbo(i);
                    if (filho != null) {
                        System.out.println(filho.getString("LOCATION") + " | valor anterior = " + filho.getString("ID2BLOCKSTATUS") + " - valor " + getMboValue().getString());
                        filho.setValue("ID2BLOCKSTATUS", getMboValue().getString());
                        System.out.println("------ ID2BLOCKSTATUS " + filho.getString("ID2BLOCKSTATUS"));
                    } else {
                        break;
                    }
                    i++;
                } while (true);
                i = 0;
            }
            filhos.save();

            filhos = getMboValue().getMbo().getMboSet("ID2VWLOC0301_CHILD");

            if (filhos != null) {
                System.out.println("filhos 3 size " + filhos.getSize() + " e count " + filhos.count());
                do {
                    filho = filhos.getMbo(i);
                    if (filho != null) {
                        System.out.println(filho.getString("LOCATION") + " | valor anterior = " + filho.getString("ID2BLOCKSTATUS") + " - valor " + getMboValue().getString());
                        filho.setValue("ID2BLOCKSTATUS", getMboValue().getString());
                        System.out.println("------ ID2BLOCKSTATUS " + filho.getString("ID2BLOCKSTATUS"));

                    } else {
                        break;
                    }
                    i++;
                } while (true);
                i = 0;
            }
            filhos.save();
        }
        super.action();
    }
}
