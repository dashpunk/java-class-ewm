package br.inf.id2.mintur.field;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class PurchviewId2cfxdesem extends psdi.app.company.FldCompanyCompany {

    public PurchviewId2cfxdesem(MboValue mbv) throws MXException {
        super(mbv);
        
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        super.validate();
        if (getMboValue().getMbo().isNull("ID2CPTOTALVAL") || getMboValue().getMbo().isNull("ID2CFXDESEM")) {
            return;
        }
        double valorContrato = getMboValue().getMbo().getDouble("ID2CPTOTALVAL");
        double aliquota = getMboValue().getMbo().getDouble("ID2CFXDESEM") / 100;
        getMboValue().getMbo().getMboValue("ID2VALPREST").setValue(valorContrato * aliquota, MboConstants.NOACTION);
    }
}
