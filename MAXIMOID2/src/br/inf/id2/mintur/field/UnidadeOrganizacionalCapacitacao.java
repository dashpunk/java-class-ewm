package br.inf.id2.mintur.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Dyogo Dantas
 *  
 */
public class UnidadeOrganizacionalCapacitacao extends MboValueAdapter {

    public UnidadeOrganizacionalCapacitacao(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        
        System.out.println("###################### UnidadeOrganizacionalCapacitacao - initValue");
        if (getMboValue().getMbo().isNew()) {
        	System.out.println("############## " + getMboValue().getMbo().getMboSet("RHRLCASE01"));
        	System.out.println("############## " + getMboValue().getMbo().getMboSet("RHRLCASE01").getMbo(0));
        	System.out.println("############## " + getMboValue().getMbo().getMboSet("RHRLCASE01").getMbo(0).getInt("RHNUCODUOEXEID"));
        	System.out.println("############## " + getMboValue().getMbo().getMboSet("RHRLCASE01").getMbo(0).getInt("RHNUCODUOLOTID"));
        	
        	if (getMboValue().getMbo().getMboSet("RHRLCASE01").getMbo(0).getInt("RHNUCODUOEXEID") == 0) {
        		getMboValue().getMbo().setValue("RHTBUOID", getMboValue().getMbo().getMboSet("RHRLCASE01").getMbo(0).getInt("RHNUCODUOLOTID"));
        	} else {
        		getMboValue().getMbo().setValue("RHTBUOID", getMboValue().getMbo().getMboSet("RHRLCASE01").getMbo(0).getInt("RHNUCODUOEXEID"));
        	}
        }
    }
}
