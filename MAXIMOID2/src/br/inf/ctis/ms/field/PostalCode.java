package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author marcelosydney.lima
 *  
 */
public class PostalCode extends MboValueAdapter {

    public PostalCode(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

    	MboRemote mbo = getMboValue().getMbo();
    	
        super.validate();

        if (mbo.getString("MSALNENDENT").equalsIgnoreCase("SIM")) {
        	
        	mbo.setValue("MSPOSTALCODE", mbo.getString("POSTALCODE"));
			System.out.println("########### POSTALCODE = " + mbo.getString("POSTALCODE"));
			
			mbo.setValue("ADDRESSLINE4", mbo.getString("ADDRESSLINE1"));
			System.out.println("########### ADDRESSLINE4 = " + mbo.getString("ADDRESSLINE1"));
			
			mbo.setValue("ADDRESSLINE5", mbo.getString("ADDRESSLINE3"));
			System.out.println("########### ADDRESSLINE5 = " + mbo.getString("ADDRESSLINE3"));
			
			mbo.setValue("MSALNCITY", mbo.getString("CITY"));
			System.out.println("########### MSALNCITY = " + mbo.getString("CITY"));
			
			mbo.setValue("ADDRESSLINE6", mbo.getString("ADDRESSLINE2"));
			System.out.println("########### ADDRESSLINE6 = " + mbo.getString("ADDRESSLINE2"));
			
			mbo.setValue("MSNUMNUM02", mbo.getString("MSNUMNUM"));
			System.out.println("########### MSNUMNUM02 = " + mbo.getString("MSNUMNUM"));
			
			mbo.setValue("MSALNSTATE", mbo.getString("STATEPROVINCE"));
			System.out.println("########### MSALNSTATE = " + mbo.getString("STATEPROVINCE"));
        }

    }
}