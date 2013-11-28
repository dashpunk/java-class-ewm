package br.inf.id2.tj.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Willians Andrade 
 */
public class LabTransPayRate extends MboValueAdapter {

    public LabTransPayRate(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("------------------ validate");
        super.validate();
        try {
            calculeValor();
        } catch (Exception e) {
        }
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        try {
            calculeValor();
        } catch (Exception e) {
        }
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        try {
            calculeValor();
        } catch (Exception e) {
        }
    }

    private void calculeValor() throws MXException, RemoteException {
        double payrate = getMboValue().getMbo().getDouble("PAYRATE");
        
        if (!getMboValue().getMbo().getBoolean("YNMAOTJDF")) { // IF FALSE
            getMboValue().getMbo().setValue("PAYRATE", payrate, MboConstants.NOVALIDATION_AND_NOACTION_ALLOWCROSSOVER);
            getMboValue().getMbo().getMboValue("PAYRATE").setReadOnly(false);
        } else {
            getMboValue().getMbo().setValue("PAYRATE", 0, MboConstants.NOVALIDATION_AND_NOACTION_ALLOWCROSSOVER);
            getMboValue().getMbo().getMboValue("PAYRATE").setReadOnly(true);
        }
        
        System.out.println("############ Fim LabTransPayRate");
    }
}
