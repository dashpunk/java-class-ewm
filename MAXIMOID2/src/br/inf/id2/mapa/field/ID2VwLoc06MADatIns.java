package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.Calendar;
import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 * 
 */
public class ID2VwLoc06MADatIns extends MboValueAdapter {

    public ID2VwLoc06MADatIns(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("---- ID2VwLoc06MADatIns validate()");
        super.validate();

        Calendar data = Calendar.getInstance();
        data.setTime(getMboValue().getMbo().getDate("MADATINS"));
        System.out.println("-- data " + data);
        System.out.println("-- data " + data.get(Calendar.YEAR));

        getMboValue().getMbo().setValue("ID2ANOMAP", String.valueOf(data.get(Calendar.YEAR)), MboConstants.NOACCESSCHECK);
        System.out.println("---- ID2VwLoc06MADatIns validate() FIM");
    }
}
