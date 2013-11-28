package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class MaProConAttsReadOnly extends MboValueAdapter {

    public MaProConAttsReadOnly(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        System.out.println("-----------------------------");
        System.out.println(getMboValue().getMbo().getString("MAARECON"));
        System.out.println(getMboValue().getMbo().getString("MAARECON").length() == 0);
        System.out.println(getMboValue().getMbo().getString("MAFINCON"));
        System.out.println(getMboValue().getMbo().getString("MAFINCON").length() == 0);
        System.out.println("-----------------------------");

        System.out.println("--- g1 " + (getMboValue().getMbo().getString("MAARECON").length() == 0));
        System.out.println("--- g2 " + (getMboValue().getMbo().getString("MAARECON").length() == 0 || getMboValue().getMbo().getString("MAFINCON").length() == 0));
        System.out.println("--- g3 " + (getMboValue().getMbo().getString("MAARECON").length() == 0 || getMboValue().getMbo().getString("MAFINCON").length() == 0 || getMboValue().getMbo().getString("MACATCON").length() == 0));

        getMboValue().getMbo().setFieldFlag("MAFINCON", MboConstants.READONLY, (getMboValue().getMbo().getString("MAARECON").length() == 0));
        getMboValue().getMbo().setFieldFlag("MACATCON", MboConstants.READONLY, (getMboValue().getMbo().getString("MAARECON").length() == 0 || getMboValue().getMbo().getString("MAFINCON").length() == 0));
        getMboValue().getMbo().setFieldFlag("MAPROCON", MboConstants.READONLY, (getMboValue().getMbo().getString("MAARECON").length() == 0 || getMboValue().getMbo().getString("MAFINCON").length() == 0 || getMboValue().getMbo().getString("MACATCON").length() == 0));

    }

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        getMboValue().getMbo().setFieldFlag("MAFINCON", MboConstants.READONLY, (getMboValue().getMbo().getString("MAARECON").length() == 0));
        getMboValue().getMbo().setFieldFlag("MACATCON", MboConstants.READONLY, (getMboValue().getMbo().getString("MAARECON").length() == 0 || getMboValue().getMbo().getString("MAFINCON").length() == 0));
        getMboValue().getMbo().setFieldFlag("MAPROCON", MboConstants.READONLY, (getMboValue().getMbo().getString("MAARECON").length() == 0 || getMboValue().getMbo().getString("MAFINCON").length() == 0 || getMboValue().getMbo().getString("MACATCON").length() == 0));

    }
}
