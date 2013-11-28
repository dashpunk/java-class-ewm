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
public class MaMapProAttsReadOnly extends MboValueAdapter {

    public MaMapProAttsReadOnly(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("--- MaMapProAttsReadOnly validate() ");
        super.validate();
        System.out.println("1 "+(getMboValue().getMbo().getString("MAAREPRO").length() == 0));
        System.out.println("2 "+(getMboValue().getMbo().getString("MAAREPRO").length() == 0 || getMboValue().getMbo().getString("MACATPRO").length() == 0));
        getMboValue().getMbo().setFieldFlag("MACATPRO", MboConstants.READONLY, (getMboValue().getMbo().getString("MAAREPRO").length() == 0));
        getMboValue().getMbo().setFieldFlag("MAPROPRO", MboConstants.READONLY, (getMboValue().getMbo().getString("MAAREPRO").length() == 0 || getMboValue().getMbo().getString("MACATPRO").length() == 0));
        System.out.println("3");
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        System.out.println("--- MaMapProAttsReadOnly initValue() ");
        super.initValue();
        System.out.println("a "+(getMboValue().getMbo().getString("MAAREPRO").length() == 0));
        System.out.println("b "+(getMboValue().getMbo().getString("MAAREPRO").length() == 0 || getMboValue().getMbo().getString("MACATPRO").length() == 0));
        getMboValue().getMbo().setFieldFlag("MACATPRO", MboConstants.READONLY, (getMboValue().getMbo().getString("MAAREPRO").length() == 0));
        getMboValue().getMbo().setFieldFlag("MAPROPRO", MboConstants.READONLY, (getMboValue().getMbo().getString("MAAREPRO").length() == 0 || getMboValue().getMbo().getString("MACATPRO").length() == 0));
        System.out.println("c");
    }
}
