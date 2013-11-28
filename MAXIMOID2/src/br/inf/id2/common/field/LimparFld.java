package br.inf.id2.common.field;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Ricardo S Gomes
 * br.inf.id2.common.field.LimparFld
 */
public class LimparFld extends MboValueAdapter {

	public LimparFld(MboValue mbv) throws MXException {
		super(mbv);
	}

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        System.out.println("--- i");
        getMboValue().setValueNull(MboConstants.NOACCESSCHECK);
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        System.out.println("--- a");
        getMboValue().setValueNull(MboConstants.NOACCESSCHECK);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        System.out.println("--- v");
        getMboValue().setValueNull(MboConstants.NOACCESSCHECK);
    }


    
}
