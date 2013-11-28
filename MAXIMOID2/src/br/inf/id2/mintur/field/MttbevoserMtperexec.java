package br.inf.id2.mintur.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Ricardo S Gomes
 */
public class MttbevoserMtperexec extends MboValueAdapter {

    public MttbevoserMtperexec(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("------ MttbevoserMtperexec() 1949");
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        System.out.println("------ MttbevoserMtperexec validate()");
        System.out.println("------ MttbevoserMtperexec validate() v1 "+getMboValue().getMbo().getDouble("MTVLRTOTITEM"));
        System.out.println("------ MttbevoserMtperexec validate() v2 "+getMboValue().getMbo().getDouble("MTVLREXEC"));
        getMboValue("MTPEREXEC").setFlag(MboConstants.READONLY, false);
        if (getMboValue().getMbo().getDouble("MTVLRTOTITEM") > 0 && getMboValue().getMbo().getDouble("MTVLREXEC") > 0) {
            getMboValue().getMbo().setValue("MTPEREXEC", (getMboValue().getMbo().getDouble("MTVLREXEC") / getMboValue().getMbo().getDouble("MTVLRTOTITEM")) * 100, MboConstants.NOVALIDATION_AND_NOACTION);
        } else {
            getMboValue().getMbo().setValue("MTPEREXEC", 0, MboConstants.NOVALIDATION_AND_NOACTION);
        }
        getMboValue("MTPEREXEC").setFlag(MboConstants.READONLY, true);
    }
}
