package br.inf.id2.mintur.field;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Ricardo S Gomes
 */
public class FnrhSNNum extends MboValueAdapter {

    public FnrhSNNum(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("------ FnrhSNNum()");
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        System.out.println("------ FnrhSNNum initValue()");
        super.initValue();

        int numero = getMboValue().getMbo().getInt("SNTBFNRHID");
        String mxnumslc = getMboValue().getMbo().getString("SNNUM");
        System.out.println("######### Numero = " + numero + "| mxnumslc = " + mxnumslc);
        if (mxnumslc == null || mxnumslc.indexOf("/") == -1) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int anoCorrente = calendar.get(Calendar.YEAR);

            getMboValue().getMbo().setValue("SNNUM", numero + "/" + anoCorrente);
        }
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("------ FnrhSNNum validate()");
        super.validate();
    }

    @Override
    public void action() throws MXException, RemoteException {
        System.out.println("------ FnrhSNNum action()");
        super.action();
    }


}

