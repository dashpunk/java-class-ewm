package br.inf.id2.mintur.field;

import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class PurchviewDatlimvig extends psdi.app.company.FldCompanyCompany {

    public PurchviewDatlimvig(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        super.validate();
        if (getMboValue().getMbo().isNull("MTDATINI") || getMboValue().getMbo().isNull("MTPRAMAXVIG")) {
            return;
        }
        int meses = getMboValue().getMbo().getInt("MTPRAMAXVIG");
        Date dataInicio = getMboValue().getMbo().getDate("MTDATINI");
        Date dataLimiteVigencia = Data.adicionaMesesData(dataInicio, meses);
        getMboValue().getMbo().getMboValue("DATLIMVIG").setValue(dataLimiteVigencia, MboConstants.NOACTION);
    }
}
