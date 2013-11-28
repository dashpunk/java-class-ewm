package psdi.id2.seedf;

import psdi.id2.*;

import psdi.mbo.*;

import java.rmi.RemoteException;
import psdi.util.MXException;

// Referenced classes of package psdi.app.financial:
//FinancialService
public class ID2FldApenasNumeros extends psdi.mbo.MboValueAdapter {

    public ID2FldApenasNumeros(MboValue mbv)
            throws MXException, RemoteException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        getMboValue().setValue(Uteis.getApenasNumeros(getMboValue().getString()));
        super.validate();

    }
}
