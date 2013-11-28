package br.inf.id2.tj.mbo;

import br.inf.id2.mintur.mbo.*;
import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class MatUseTrans extends psdi.plusp.app.inventory.PlusPMatUseTrans
        implements MatUseTransRemote {

    public MatUseTrans(MboSet mboset) throws MXException, RemoteException {
        super(mboset);
    }

    @Override
    public void appValidate() throws MXException, RemoteException {
        boolean material = getString("DSTIPO").equals("MATERIAL");
        System.out.println("############### MATUSETRANS...");
        if (getBoolean("YNMATETJDF") && material) {
        	
        	boolean isReadOnlyField = getMboValue("POSITIVEQUANTITY").isReadOnly();
        	boolean isReadOnlyMboSet = getThisMboSet().isFlagSet(MboConstants.READONLY);
        	boolean isReadOnlyMbo = isFlagSet(MboConstants.READONLY);
       		getMboValue("POSITIVEQUANTITY").setFlag(MboConstants.READONLY, false);
       		setFieldFlag("POSITIVEQUANTITY", MboConstants.READONLY, false);
       		getThisMboSet().setFlag(MboConstants.READONLY, false);
       		setFlag(MboConstants.READONLY, false);
       		//getMboValue("POSITIVEQUANTITY").setReadOnly(false);
       		
            setValue("POSITIVEQUANTITY", 1, MboConstants.NOVALIDATION_AND_NOACTION);
            //getMboValue("POSITIVEQUANTITY").setReadOnly(isReadOnly);
            getMboValue("POSITIVEQUANTITY").setFlag(MboConstants.READONLY, isReadOnlyField);
       		setFieldFlag("POSITIVEQUANTITY", MboConstants.READONLY, isReadOnlyField);
            
            
       		isReadOnlyField = getMboValue("QUANTITY").isReadOnly();
            getMboValue("QUANTITY").setFlag(MboConstants.READONLY, false);
       		setFieldFlag("QUANTITY", MboConstants.READONLY, false);
            //getMboValue("QUANTITY").setReadOnly(false);
        	
       		if (getString("ISSUETYPE").equalsIgnoreCase("CONSUMIR")) {
       			setValue("QUANTITY", -1, MboConstants.NOVALIDATION_AND_NOACTION);
       		} else {
       			setValue("QUANTITY", 1, MboConstants.NOVALIDATION_AND_NOACTION);
       		}
            //getMboValue("QUANTITY").setReadOnly(isReadOnly);
            getMboValue("QUANTITY").setFlag(MboConstants.READONLY, isReadOnlyField);
       		setFieldFlag("QUANTITY", MboConstants.READONLY, isReadOnlyField);
       		
       		getThisMboSet().setFlag(MboConstants.READONLY, isReadOnlyMboSet);
       		setFlag(MboConstants.READONLY, isReadOnlyMbo);
            
        }
        System.out.println("################ MatUseTrans 2...");
        super.appValidate();
        System.out.println("################ MatUseTrans 3...");
        if (getBoolean("YNMATETJDF") && material) {
        	boolean isReadOnlyField = getMboValue("POSITIVEQUANTITY").isReadOnly();
        	boolean isReadOnlyMboSet = getThisMboSet().isFlagSet(MboConstants.READONLY);
        	boolean isReadOnlyMbo = isFlagSet(MboConstants.READONLY);
       		//getMboValue("POSITIVEQUANTITY").setReadOnly(false);
        	getMboValue("POSITIVEQUANTITY").setFlag(MboConstants.READONLY, false);
        	setFieldFlag("POSITIVEQUANTITY", MboConstants.READONLY, false);
       		getThisMboSet().setFlag(MboConstants.READONLY, false);
       		setFlag(MboConstants.READONLY, false);
        	
            setValue("POSITIVEQUANTITY", 0, MboConstants.NOVALIDATION_AND_NOACTION);
            //getMboValue("POSITIVEQUANTITY").setReadOnly(isReadOnly);
            getMboValue("POSITIVEQUANTITY").setFlag(MboConstants.READONLY, isReadOnlyField);
            setFieldFlag("POSITIVEQUANTITY", MboConstants.READONLY, isReadOnlyField);
            
            
            isReadOnlyField = getMboValue("QUANTITY").isReadOnly();
            //getMboValue("QUANTITY").setReadOnly(false);
            getMboValue("QUANTITY").setFlag(MboConstants.READONLY, false);
            setFieldFlag("QUANTITY", MboConstants.READONLY, false);
            
            setValue("QUANTITY", 0, MboConstants.NOVALIDATION_AND_NOACTION);
            //getMboValue("QUANTITY").setReadOnly(isReadOnly);
            getMboValue("QUANTITY").setFlag(MboConstants.READONLY, isReadOnlyField);
            setFieldFlag("QUANTITY", MboConstants.READONLY, isReadOnlyField);
            
            
            getThisMboSet().setFlag(MboConstants.READONLY, isReadOnlyMboSet);
       		setFlag(MboConstants.READONLY, isReadOnlyMbo);
        }
        
        System.out.println("################ MatUseTrans 4...");
    }
}
